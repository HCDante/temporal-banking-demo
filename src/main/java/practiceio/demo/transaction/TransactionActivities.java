package practiceio.demo.transaction;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface TransactionActivities {
    @ActivityMethod
    String debit(String accountId, double amount);

    @ActivityMethod
    String credit(String accountId, double amount);
}
