package jenkins.plugins.parameter_separator;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;

import hudson.model.Label;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class PipelineTest {

    @Test
    public void testPipelineParameters(JenkinsRule jenkins) throws Exception {
        String pipeline = IOUtils.toString(
                PipelineTest.class.getResourceAsStream("/pipelines/declarative.groovy"), StandardCharsets.UTF_8);
        jenkins.createSlave(Label.get("test-agent"));
        WorkflowJob workflowJob = jenkins.createProject(WorkflowJob.class);
        workflowJob.setDefinition(new CpsFlowDefinition(pipeline, true));
        WorkflowRun run1 = workflowJob.scheduleBuild2(0).waitForStart();
        jenkins.waitForCompletion(run1);
        assertThat(run1.getResult(), equalTo(hudson.model.Result.SUCCESS));
        assertThat(run1.getLog(), allOf(containsString("Hello World")));
    }
}
