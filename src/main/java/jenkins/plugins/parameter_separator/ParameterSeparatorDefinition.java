/*
 *Copyright (c) 2014 Mike Chmielewski
 *See the file license.txt for copying permission.
 */

package jenkins.plugins.parameter_separator;

import com.google.common.base.Strings;
import edu.umd.cs.findbugs.annotations.Nullable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.PersistentDescriptor;
import hudson.util.HttpResponses;
import java.io.IOException;
import java.util.UUID;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest2;

@SuppressFBWarnings("SE_NO_SERIALVERSIONID")
public class ParameterSeparatorDefinition extends ParameterDefinition {

    private @Nullable String separatorStyle;
    private @Nullable String sectionHeader;
    private @Nullable String sectionHeaderStyle;

    @DataBoundConstructor
    public ParameterSeparatorDefinition(
            String name, String separatorStyle, String sectionHeader, String sectionHeaderStyle) {
        super(Strings.isNullOrEmpty(name) ? ("separator-" + UUID.randomUUID().toString()) : name);
        this.separatorStyle = Strings.emptyToNull(separatorStyle);
        this.sectionHeader = Strings.emptyToNull(sectionHeader);
        this.sectionHeaderStyle = Strings.emptyToNull(sectionHeaderStyle);
    }

    public String getSeparatorStyle() {
        return separatorStyle;
    }

    public String getEffectiveSeparatorStyle() {
        return Utils.getEffectiveSeparatorStyle(separatorStyle);
    }

    public String getSectionHeader() {
        return sectionHeader;
    }

    public boolean needsSectionHeader() {
        return sectionHeader != null;
    }

    public String getFormattedSectionHeader() {
        return Utils.getFormattedSectionHeader(sectionHeader);
    }

    public String getSectionHeaderStyle() {
        return sectionHeaderStyle;
    }

    public String getEffectiveSectionHeaderStyle() {
        return Utils.getEffectiveSectionHeaderStyle(sectionHeaderStyle);
    }

    @Override
    public ParameterValue getDefaultParameterValue() {
        return new ParameterSeparatorValue(getName(), separatorStyle, sectionHeader, sectionHeaderStyle);
    }

    @Override
    public ParameterValue createValue(StaplerRequest2 request) {
        return getDefaultParameterValue();
    }

    @Override
    public ParameterValue createValue(StaplerRequest2 request, JSONObject jObj) {
        return getDefaultParameterValue();
    }

    @Extension
    @Symbol("separator")
    public static class ParameterSeparatorDescriptor extends ParameterDescriptor implements PersistentDescriptor {

        private String globalSeparatorStyle = "";
        private String globalSectionHeaderStyle = "font-weight: bold;";

        @Override
        public boolean configure(StaplerRequest2 req, JSONObject json) throws FormException {
            req.bindJSON(this, json.getJSONObject("parameter_separator"));
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.ParameterSeparatorDefinition_DisplayName();
        }

        public String getGlobalSeparatorStyle() {
            return globalSeparatorStyle;
        }

        public void setGlobalSeparatorStyle(String globalSeparatorStyle) {
            this.globalSeparatorStyle = globalSeparatorStyle;
            save();
        }

        public String getGlobalSectionHeaderStyle() {
            return globalSectionHeaderStyle;
        }

        public void setGlobalSectionHeaderStyle(String globalSectionHeaderStyle) {
            this.globalSectionHeaderStyle = globalSectionHeaderStyle;
            save();
        }

        public HttpResponse doPreview(
                @QueryParameter String text,
                @QueryParameter String separatorStyle,
                @QueryParameter String sectionHeaderStyle)
                throws IOException {
            return HttpResponses.literalHtml(Utils.getPreview(text, separatorStyle, sectionHeaderStyle));
        }
    }
}
