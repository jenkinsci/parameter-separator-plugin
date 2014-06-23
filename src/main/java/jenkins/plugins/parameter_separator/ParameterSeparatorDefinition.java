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

        private String separatorStyle;

        private static final String SEPARATOR_TEMPLATE = "<hr style=\"STYLE_HERE\" />";

        public static final String DEFAULT_SEPARATOR_STYLE = "margin-top:10px; margin-bottom:10px;";

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

        public String getSeparatorStyle() {
            return separatorStyle;
        }

        public void setSeparatorStyle(final String s) {
            separatorStyle = s;
        }

        public String getSeparator() {
            if (separatorStyle != null && separatorStyle.length() > 0) {
                return SEPARATOR_TEMPLATE.replace("STYLE_HERE", separatorStyle);
            } else {
                return SEPARATOR_TEMPLATE.replace("STYLE_HERE", DEFAULT_SEPARATOR_STYLE);
            }
        }

        @Override
        public ParameterSeparatorDefinition newInstance(final StaplerRequest request, final JSONObject jObj) {
            return new ParameterSeparatorDefinition("", jObj.getString("sectionHeader"), jObj.getString("sectionHeaderStyle"));
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

    @DataBoundConstructor
    public ParameterSeparatorDefinition(final String name, final String sectionHeader, final String sectionHeaderStyle) {
        super("separator-" + UUID.randomUUID().toString(), "");

        this.sectionHeader = sectionHeader;
        this.sectionHeaderStyle = sectionHeaderStyle;
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

    public String getSeparator() {
        return getDescriptor().getSeparator();
    }

    @Override
    public ParameterSeparatorDescriptor getDescriptor() {
        return (ParameterSeparatorDescriptor) super.getDescriptor();
    }
}
