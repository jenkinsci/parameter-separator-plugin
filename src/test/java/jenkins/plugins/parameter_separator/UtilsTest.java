package jenkins.plugins.parameter_separator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class UtilsTest {

    @Test
    public void shouldGetPreview(JenkinsRule jenkinsRule) {
        assertThat(
                Utils.getPreview("the header", "the separator", "the header style"),
                is("<hr style=\"the separator\"/><div style=\"the header style\">the header</div>"));
    }

    @Test
    public void shouldGetFormattedSectionHeader(JenkinsRule jenkinsRule) {
        assertThat(Utils.getFormattedSectionHeader("<h1>Header</h1>"), is("&lt;h1&gt;Header&lt;/h1&gt;"));
    }
}
