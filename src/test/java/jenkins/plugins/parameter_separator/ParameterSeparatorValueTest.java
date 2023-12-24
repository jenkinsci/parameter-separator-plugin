package jenkins.plugins.parameter_separator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class ParameterSeparatorValueTest {

    @Test
    public void shouldWork(JenkinsRule jenkinsRule) {
        ParameterSeparatorValue value = new ParameterSeparatorValue(
                "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>");
        assertThat(value.getShortDescription(), is("the name"));
        assertThat(value.getValue(), is(""));
        assertThat(value.toString(), is("(ParameterSeparatorValue) the name"));
        assertThat(value.getEffectiveSectionHeaderStyle(), is("<h1>the header style</h1>"));
        assertThat(value.getFormattedSectionHeader(), is("&lt;h1&gt;the section header&lt;/h1&gt;"));
        assertThat(value.getEffectiveSeparatorStyle(), is("the style"));
    }

    @Test
    public void shouldTestNeedSectionHeader(JenkinsRule jenkinsRule) {
        ParameterSeparatorValue value = new ParameterSeparatorValue(
                "the name", "the style", "<h1>the section header</h1>", "<h1>the header style</h1>");
        assertThat(value.needsSectionHeader(), is(true));
        value = new ParameterSeparatorValue("the name", "the style", null, null);
        assertThat(value.needsSectionHeader(), is(false));
    }
}
