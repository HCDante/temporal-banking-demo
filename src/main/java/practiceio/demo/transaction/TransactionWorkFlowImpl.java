package practiceio.demo.transaction;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class TransactionWorkFlowImpl implements TransactionWorkFlow {
    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .build();

    private final TransactionActivities activities =
            Workflow.newActivityStub(TransactionActivities.class, options);

    @Override
    public String processTransaction(String fromAccount, String toAccount, double amount) {
        activities.debit(fromAccount, amount);
        activities.credit(toAccount, amount);
        return "Transaction complete: $" + amount
               + " from " + fromAccount
               + " to " + toAccount;
    }
}
