package jenkins.plugins.parameter_separator.jcasc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import hudson.ExtensionList;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import io.jenkins.plugins.casc.misc.junit.jupiter.WithJenkinsConfiguredWithCode;
import jenkins.plugins.parameter_separator.ParameterSeparatorDefinition.ParameterSeparatorDescriptor;
import org.junit.jupiter.api.Test;

@WithJenkinsConfiguredWithCode
public class ConfigurationAsCodeTest {

    @Test
    @ConfiguredWithCode("configuration-as-code.yml")
    public void should_support_configuration_as_code(JenkinsConfiguredWithCodeRule r) throws Exception {
        ParameterSeparatorDescriptor descriptor = ExtensionList.lookupSingleton(ParameterSeparatorDescriptor.class);
        assertThat(descriptor.getGlobalSectionHeaderStyle(), is("global header style"));
        assertThat(descriptor.getGlobalSeparatorStyle(), is("global separator style"));
    }
}
