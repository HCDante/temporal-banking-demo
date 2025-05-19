package practiceio.demo.orchestration;

import io.temporal.workflow.ChildWorkflowOptions;
import io.temporal.workflow.Workflow;
import practiceio.demo.transaction.TransactionWorkFlow;
import practiceio.demo.Starter;
import java.time.Duration;

public class OrchestrationWorkFlowImpl implements OrchestrationWorkFlow {
    private final TransactionWorkFlow child = Workflow.newChildWorkflowStub(
        TransactionWorkFlow.class,
        ChildWorkflowOptions.newBuilder()
            .setTaskQueue(Starter.TASK_QUEUE)
                .setWorkflowRunTimeout(Duration.ofSeconds(30))

                .build());

    @Override
    public String orchestratePayment(String fromAccount, String toAccount, double amount) {
        return child.processTransaction(fromAccount, toAccount, amount);
    }
}
