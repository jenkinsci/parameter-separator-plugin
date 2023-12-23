/*
 *Copyright (c) 2014 Mike Chmielewski
 *See the file license.txt for copying permission.
 */

package jenkins.plugins.parameter_separator;

import com.google.common.base.Strings;
import edu.umd.cs.findbugs.annotations.Nullable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.model.ParameterValue;
import org.kohsuke.stapler.DataBoundConstructor;

@SuppressFBWarnings("SE_NO_SERIALVERSIONID")
public class ParameterSeparatorValue extends ParameterValue {

    private final @Nullable String separatorStyle;
    private final @Nullable String sectionHeader;
    private final @Nullable String sectionHeaderStyle;

    @DataBoundConstructor
    public ParameterSeparatorValue(
            String name, String separatorStyle, String sectionHeader, String sectionHeaderStyle) {
        super(name);
        this.separatorStyle = Strings.emptyToNull(separatorStyle);
        this.sectionHeader = Strings.emptyToNull(sectionHeader);
        this.sectionHeaderStyle = Strings.emptyToNull(sectionHeaderStyle);
    }

    public String getEffectiveSeparatorStyle() {
        return Utils.getEffectiveSeparatorStyle(separatorStyle);
    }

    public boolean needsSectionHeader() {
        return sectionHeader != null;
    }

    public String getFormattedSectionHeader() {
        return Utils.getFormattedSectionHeader(sectionHeader);
    }

    public String getEffectiveSectionHeaderStyle() {
        return Utils.getEffectiveSectionHeaderStyle(sectionHeaderStyle);
    }

    @Override
    public String toString() {
        return "(ParameterSeparatorValue) " + getName();
    }

    @Override
    public String getShortDescription() {
        return getName();
    }

    @Override
    public String getValue() {
        return "";
    }
}
