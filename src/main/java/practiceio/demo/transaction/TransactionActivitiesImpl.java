package practiceio.demo.transaction;

public class TransactionActivitiesImpl implements TransactionActivities {
    @Override
    public String debit(String accountId, double amount) {
        return "Debited $" + amount + " from account " + accountId;
    }

    @Override
    public String credit(String accountId, double amount) {
        return "Credited $" + amount + " to account " + accountId;
    }
}
