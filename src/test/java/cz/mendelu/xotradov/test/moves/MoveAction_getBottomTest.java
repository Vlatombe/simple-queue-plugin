package cz.mendelu.xotradov.test.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.util.Arrays;

import cz.mendelu.xotradov.MoveAction;
import cz.mendelu.xotradov.test.TestHelper;
import hudson.model.FreeStyleProject;
import hudson.model.Queue;

public class MoveAction_getBottomTest {

    @Rule
    public final JenkinsRule jenkinsRule = new JenkinsRule();
    public final TestHelper helper = new TestHelper(jenkinsRule);
   


    @Test
    public void getBottom() {
        try {
            long maxTestTime = 20000;
            helper.fillQueueFor(maxTestTime);
            FreeStyleProject C = helper.createAndSchedule("C",maxTestTime);
            FreeStyleProject D = helper.createAndSchedule("D",maxTestTime);
            FreeStyleProject E = helper.createAndSchedule("E",maxTestTime);
            MoveAction moveAction = helper.getMoveAction();
            Queue queue = jenkinsRule.jenkins.getQueue();
            assertEquals(C.getDisplayName(),queue.getItems()[2].task.getDisplayName());
            assertEquals(C.getDisplayName(),moveAction.getBottom(Arrays.asList(queue.getItems())).task.getDisplayName());
            moveAction.moveUp(C.getQueueItem(),queue);
            queue.maintain();
            assertEquals(D.getDisplayName(),moveAction.getBottom(Arrays.asList(queue.getItems())).task.getDisplayName());
            moveAction.moveToBottom(E.getQueueItem(),queue);
            queue.maintain();
            assertEquals(E.getDisplayName(),moveAction.getBottom(Arrays.asList(queue.getItems())).task.getDisplayName());
        }catch (Exception e){
            fail();
        }
    }
}