/*
 *Copyright (c) 2014 Mike Chmielewski
 *See the file license.txt for copying permission.
 */

package jenkins.plugins.parameter_separator;

import java.util.Date;
import java.util.UUID;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.ParameterDefinition;
import hudson.util.FormValidation;

import org.kohsuke.stapler.*;
import net.sf.json.JSONObject;

public class ParameterSeparatorDefinition extends ParameterDefinition {

    @Extension
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
            return globalSeparatorStyle.equals("") ? getGlobalDefaultSeparatorStyle() : globalSeparatorStyle;
        }

        public String getGlobalDefaultSeparatorStyle() {
            return DEFAULT_SEPARATOR_STYLE;
        }

        public void setGlobalSeparatorStyle(final String s) {
            globalSeparatorStyle = s;
        }

        @Override
        public ParameterSeparatorDefinition newInstance(final StaplerRequest request, final JSONObject jObj) {
            return new ParameterSeparatorDefinition("", jObj.getString("separatorStyle"), jObj.getString("sectionHeader"), jObj.getString("sectionHeaderStyle"));
        }
    }

    private String sectionHeader = "";

    public String getSectionHeader() {
        return this.sectionHeader;
    }

    public void setSectionHeader(final String sh) {
        this.sectionHeader = sh;
    }

    private String sectionHeaderStyle = "";

    public String getSectionHeaderStyle() {
        return this.sectionHeaderStyle;
    }

    public void setSectionHeaderStyle(final String shs) {
        this.sectionHeaderStyle = shs;
    }

    private String separatorStyle = "";

     public String getSeparatorStyle() {
        return this.separatorStyle.equals("") ? getDescriptor().getGlobalSeparatorStyle() : this.separatorStyle;
    }

    public void setSeparatorStyle(final String ss) {
        this.separatorStyle = ss;
    }   

    @DataBoundConstructor
    public ParameterSeparatorDefinition(final String name, final String separatorStyle, final String sectionHeader, final String sectionHeaderStyle) {
        super("separator-" + UUID.randomUUID().toString(), "");

        this.sectionHeader = sectionHeader;
        this.sectionHeaderStyle = sectionHeaderStyle;
        this.separatorStyle = separatorStyle;
    }

    @Override
    public ParameterValue getDefaultParameterValue() {
        // TODO: Should load a default, not hard-code it
        return new ParameterSeparatorValue(getName(), "");
    }

    @Override
    public ParameterValue createValue(final StaplerRequest request) {
        String[] requestFields = request.getParameterValues(getName());
        return new ParameterSeparatorValue(getName(), "");
    }

    @Override
    public ParameterValue createValue(final StaplerRequest request, final JSONObject jObj) {
        return new ParameterSeparatorValue(getName(), "");
    }

    @Override
    public ParameterSeparatorDescriptor getDescriptor() {
        return (ParameterSeparatorDescriptor) super.getDescriptor();
    }
}
