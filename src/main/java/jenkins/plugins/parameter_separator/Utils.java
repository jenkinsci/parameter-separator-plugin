package jenkins.plugins.parameter_separator;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.common.base.Strings;

import hudson.ExtensionList;
import jenkins.model.Jenkins;
import jenkins.plugins.parameter_separator.ParameterSeparatorDefinition.ParameterSeparatorDescriptor;

class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static String getPreview(String sectionHeader, String separatorStyle, String sectionHeaderStyle) {
        String formattedSectionHeader = getFormattedSectionHeader(sectionHeader);
        String effectiveSeparatorStyle = getEffectiveSeparatorStyle(separatorStyle);
        String effectiveSectionHeaderStyle = getEffectiveSectionHeaderStyle(sectionHeaderStyle);
        return String.format("<hr style=\"%s\"/><div style=\"%s\">%s</div>",
                StringEscapeUtils.escapeHtml(effectiveSeparatorStyle),
                StringEscapeUtils.escapeHtml(effectiveSectionHeaderStyle), formattedSectionHeader);
    }

    public static String getEffectiveSeparatorStyle(@Nullable String separatorStyle) {
        return nonEmptyOptional(separatorStyle).orElse(getDescriptor().getGlobalSeparatorStyle());
    }

    public static String getEffectiveSectionHeaderStyle(@Nullable String sectionHeaderStyle) {
        return nonEmptyOptional(sectionHeaderStyle).orElse(getDescriptor().getGlobalSectionHeaderStyle());
    }

    public static String getFormattedSectionHeader(@Nullable String sectionHeader) {
        return nonEmptyOptional(sectionHeader).map(Utils::doFormat).orElse("");
    }

    private static String doFormat(@Nonnull String html) {
        try {
            return Jenkins.get().getMarkupFormatter().translate(html);
        } catch (IOException e) {
            LOGGER.warning("failed to translate sectionHeader using configured markup formatter");
            return "";
        }
    }

    private static Optional<String> nonEmptyOptional(@Nullable String value) {
        return Optional.ofNullable(Strings.emptyToNull(value));
    }

    private static ParameterSeparatorDescriptor getDescriptor() {
        return ExtensionList.lookupSingleton(ParameterSeparatorDefinition.ParameterSeparatorDescriptor.class);
    }

}
