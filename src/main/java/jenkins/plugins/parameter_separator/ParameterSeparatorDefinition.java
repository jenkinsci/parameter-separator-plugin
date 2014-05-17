/*
 *Copyright (c) 2013 Costco, Vimil Saju
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

		public static final String defaultSeparatorStyle = "margin-top:10px; margin-bottom:10px;";

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
        	}
        	else {
        		return SEPARATOR_TEMPLATE.replace("STYLE_HERE", defaultSeparatorStyle);
        	}
        }
	}

	@Override
	public ParameterValue getDefaultParameterValue() {
		return null;
	}

	@DataBoundConstructor
	public ParameterSeparatorDefinition(final String name) {
		super("separator-" + UUID.randomUUID().toString());
	}

	@Override
	public ParameterValue createValue(final StaplerRequest request) {
		return null;
	}

	@Override
	public ParameterValue createValue(final StaplerRequest request, final JSONObject jObj) {
		return null;
	}

	   public String getSeparator() {
        return getDescriptor().getSeparator();
    }

    @Override
    public ParameterSeparatorDescriptor getDescriptor() {
        return (ParameterSeparatorDescriptor) super.getDescriptor();
    }
}
