/*
 *Copyright (c) 2014 Mike Chmielewski
 *See the file license.txt for copying permission.
 */

package jenkins.plugins.parameter_separator;

import java.util.UUID;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import com.google.common.base.Strings;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class ParameterSeparatorDefinition extends ParameterDefinition {

    @Extension
    @Symbol("separator")
    public static class ParameterSeparatorDescriptor extends ParameterDescriptor {

        private String globalSeparatorStyle = "";

        private static final String DEFAULT_SEPARATOR_STYLE = "margin-top:10px; margin-bottom:10px;";

        public ParameterSeparatorDescriptor() {
            load();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            req.bindJSON(this, json.getJSONObject("parameter_separator"));
            save();
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.ParameterSeparatorDefinition_DisplayName();
        }

        public String getGlobalSeparatorStyle() {
            return globalSeparatorStyle;
        }

        public void setGlobalSeparatorStyle(final String gss) {
            globalSeparatorStyle = gss;
        }

        public String getComputedGlobalSeparatorStyle() {
            return globalSeparatorStyle.equals("") ? getGlobalDefaultSeparatorStyle() : globalSeparatorStyle;
        }

        private String getGlobalDefaultSeparatorStyle() {
            return DEFAULT_SEPARATOR_STYLE;
        }

        @Override
        public ParameterSeparatorDefinition newInstance(final StaplerRequest request, final JSONObject jObj) {
            return new ParameterSeparatorDefinition("", jObj.getString("separatorStyle"),
                    jObj.getString("sectionHeader"), jObj.getString("sectionHeaderStyle"));
        }
    }

    private String sectionHeader = "";

    public String getSectionHeader() {
        return sectionHeader;
    }

    public void setSectionHeader(final String sh) {
        this.sectionHeader = sh;
    }

    private String sectionHeaderStyle = "";

    public String getSectionHeaderStyle() {
        return sectionHeaderStyle;
    }

    public void setSectionHeaderStyle(final String shs) {
        this.sectionHeaderStyle = shs;
    }

    private String separatorStyle = "";

    public String getSeparatorStyle() {
        return separatorStyle;
    }

    public String getComputedSeparatorStyle() {
        return this.separatorStyle.equals("") ? getDescriptor().getComputedGlobalSeparatorStyle() : this.separatorStyle;
    }

    public void setSeparatorStyle(final String ss) {
        this.separatorStyle = ss;
    }

    @DataBoundConstructor
    public ParameterSeparatorDefinition(final String name, final String separatorStyle, final String sectionHeader,
            final String sectionHeaderStyle) {
        super(Strings.isNullOrEmpty(name) ? "separator-" + UUID.randomUUID().toString() : name, "");

        this.separatorStyle = separatorStyle;
        this.sectionHeader = sectionHeader;
        this.sectionHeaderStyle = sectionHeaderStyle;
    }

    @Override
    public ParameterValue getDefaultParameterValue() {
        // TODO: Should load a default, not hard-code it
        return new ParameterSeparatorValue(getName(), getComputedSeparatorStyle(), getSectionHeader(),
                getSectionHeaderStyle());
    }

    @Override
    public ParameterValue createValue(final StaplerRequest request) {
        return getDefaultParameterValue();
    }

    @Override
    public ParameterValue createValue(final StaplerRequest request, final JSONObject jObj) {
        return getDefaultParameterValue();
    }

    @Override
    public ParameterSeparatorDescriptor getDescriptor() {
        return (ParameterSeparatorDescriptor) super.getDescriptor();
    }
}
