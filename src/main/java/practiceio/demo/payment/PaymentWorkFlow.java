package practiceio.demo.payment;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PaymentWorkFlow {
    @WorkflowMethod
    String processPayment(String accountId, double amount);
}
