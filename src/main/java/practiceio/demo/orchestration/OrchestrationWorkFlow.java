package practiceio.demo.orchestration;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrchestrationWorkFlow {
    @WorkflowMethod
    String orchestratePayment(String fromAccount, String toAccount, double amount);
}
