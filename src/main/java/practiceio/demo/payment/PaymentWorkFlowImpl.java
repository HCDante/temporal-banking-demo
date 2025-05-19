package practiceio.demo.payment;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class PaymentWorkFlowImpl implements PaymentWorkFlow {
    private final ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .build();

    private final PaymentActitivities activities =
            Workflow.newActivityStub(PaymentActitivities.class, options);

    @Override
    public String processPayment(String accountId, double amount) {
        return activities.withDraw(accountId, amount);
    }
}
