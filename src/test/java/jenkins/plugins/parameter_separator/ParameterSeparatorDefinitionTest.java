package jenkins.plugins.parameter_separator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import hudson.ExtensionList;
import jenkins.plugins.parameter_separator.ParameterSeparatorDefinition.ParameterSeparatorDescriptor;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest2;

@WithJenkins
public class ParameterSeparatorDefinitionTest {

    @Test
    public void shouldConfigure(JenkinsRule jenkinsRule) throws Exception {
        ParameterSeparatorDescriptor descriptor = ExtensionList.lookupSingleton(ParameterSeparatorDescriptor.class);
        StaplerRequest2 request = mock(StaplerRequest2.class);
        JSONObject json = mock(JSONObject.class);
        JSONObject result = mock(JSONObject.class);
        doReturn(result).when(json).getJSONObject("parameter_separator");
        assertThat(descriptor.configure(request, json), is(true));
        verify(json).getJSONObject("parameter_separator");
        verify(request).bindJSON(descriptor, result);
        verifyNoMoreInteractions(request, json);
    }

    @Test
    public void shouldDoPreview(JenkinsRule jenkinsRule) throws Exception {
        ParameterSeparatorDescriptor descriptor = ExtensionList.lookupSingleton(ParameterSeparatorDescriptor.class);
        assertThat(
                descriptor.doPreview("the text", "separator style", "<h1>the section header</h1>"),
                instanceOf(HttpResponse.class));
    }

    @Test
    public void shoudAllNullName(JenkinsRule jenkinsRule) {
        new ParameterSeparatorDefinition(null, null, null, null);
    }

    @Test
    public void shouldWork(JenkinsRule jenkinsRule) {
        ParameterSeparatorDefinition definition = new ParameterSeparatorDefinition(
                "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>");
        assertThat(definition.getName(), is("the name"));
        assertThat(definition.getSeparatorStyle(), is("the style"));
        assertThat(definition.getSectionHeader(), is("<h1>the section header</h1>"));
        assertThat(definition.getSectionHeaderStyle(), is("<h1>the header style</h1>"));
        assertThat(definition.getEffectiveSeparatorStyle(), is("the style"));
        assertThat(definition.getFormattedSectionHeader(), is("&lt;h1&gt;the section header&lt;/h1&gt;"));
        assertThat(definition.needsSectionHeader(), is(true));
        assertThat(definition.getEffectiveSectionHeaderStyle(), is("<h1>the header style</h1>"));
        assertThat(
                definition.getDefaultParameterValue(),
                is(new ParameterSeparatorValue(
                        "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>")));
        definition = new ParameterSeparatorDefinition("the name", "the style", null, null);
        assertThat(definition.needsSectionHeader(), is(false));
    }

    @Test
    public void shouldCreateValueWithoutJson(JenkinsRule jenkinsRule) {
        ParameterSeparatorDefinition definition = new ParameterSeparatorDefinition(
                "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>");
        assertThat(
                definition.createValue(mock(StaplerRequest2.class)),
                is(new ParameterSeparatorValue(
                        "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>")));
        JSONObject json = mock(JSONObject.class);
        assertThat(
                definition.createValue(mock(StaplerRequest2.class), json),
                is(new ParameterSeparatorValue(
                        "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>")));
    }
}
