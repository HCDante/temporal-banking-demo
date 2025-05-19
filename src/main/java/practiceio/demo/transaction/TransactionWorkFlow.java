package practiceio.demo.transaction;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TransactionWorkFlow {
    @WorkflowMethod
    String processTransaction(String fromAccount, String toAccount, double amount);
}
