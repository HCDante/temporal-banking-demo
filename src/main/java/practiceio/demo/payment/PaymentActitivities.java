package practiceio.demo.payment;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentActitivities {
    @ActivityMethod
    String withDraw(String accountId, double amount);
}
