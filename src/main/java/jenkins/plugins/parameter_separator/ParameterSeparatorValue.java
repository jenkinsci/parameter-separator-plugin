/*
 *Copyright (c) 2014 Mike Chmielewski
 *See the file license.txt for copying permission.
 */


package jenkins.plugins.parameter_separator;

import org.kohsuke.stapler.DataBoundConstructor;
import hudson.model.StringParameterValue;

public class ParameterSeparatorValue extends StringParameterValue{
	
	@DataBoundConstructor
	public ParameterSeparatorValue(final String name, final String value) {
		super(name, value);
	}

}
