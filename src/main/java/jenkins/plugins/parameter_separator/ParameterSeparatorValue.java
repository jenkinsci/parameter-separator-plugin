/*
 *Copyright (c) 2014 Mike Chmielewski
 *See the file license.txt for copying permission.
 */

package jenkins.plugins.parameter_separator;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.model.ParameterValue;

@SuppressWarnings("serial")
public class ParameterSeparatorValue extends ParameterValue {

    private String separatorStyle = "";
    private String sectionHeader = "";
    private String sectionHeaderStyle = "";

    public String getSectionHeader() {
        return sectionHeader;
    }

    public void setSectionHeader(final String sh) {
        this.sectionHeader = sh;
    }

    public String getSectionHeaderStyle() {
        return sectionHeaderStyle;
    }

    public void setSectionHeaderStyle(final String shs) {
        this.sectionHeaderStyle = shs;
    }

    public String getSeparatorStyle() {
        return separatorStyle;
    }

    @DataBoundConstructor
    public ParameterSeparatorValue(final String name, final String separatorStyle, final String sectionHeader,
            final String sectionHeaderStyle) {
        super(name, "");
        this.separatorStyle = separatorStyle;
        this.sectionHeader = sectionHeader;
        this.sectionHeaderStyle = sectionHeaderStyle;
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
        return toString();
    }
}
