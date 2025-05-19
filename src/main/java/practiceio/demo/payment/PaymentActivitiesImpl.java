package practiceio.demo.payment;

public class PaymentActivitiesImpl implements PaymentActitivities {
    @Override
    public String withDraw(String accountId, double amount) {
        return "WithDraw $" + amount + " from account " + accountId;
    }
}
