package model.orders;

import java.time.LocalDate;
import java.util.UUID;

import managers.AccountManager;
import managers.BillManager;
import managers.TransactionManager;
import model.accounts.BankAccount;
import model.bills.Bill;
import model.orders.StandingOrder.OrderType;
import model.transactions.Payment;

public class PaymentOrder extends StandingOrder{
    private String paymentCode;
    private float maxAmount;
    private static final float fee = 0.3f;
    private BankAccount chargeAccount;

    public PaymentOrder() {}

    @Override
    public String marshal() {
        return super.marshal() +
        String.join(",",
                    "paymentCode:" + getPaymentCode(),
                    "title:" + getTitle(),
                    "customer:" + getCustomerVat(),
                    "maxAmount:" + getMaxAmount(),
                    "startDate:" + getStartDate().format(format),
                    "endDate:" + getEndDate().format(format),
                    "fee" + fee,
                    "chargeAccount" + getChargeAccount()
        );
    }
    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        for (String part : parts) {
            String[] kv = part.split(":");
            String key = kv[0];
            String value = kv[1];

            switch(key) {
                case "type":            this.type = OrderType.valueOf(value); break;
                case "orderId":         this.orderId = value; break;
                case "paymentCode":     this.paymentCode = value; break;
                case "title":           this.title = value; break;
                case "description":     this.description = value; break;
                case "customer":        this.customerVat = value; break;
                case "maxAmount":       this.maxAmount = Float.parseFloat(value); break;
                case "startDate":       this.startDate = LocalDate.parse(value); break;
                case "endDate":         this.endDate = LocalDate.parse(value); break;
                case "chargeAccount":   this.chargeAccount = AccountManager.getInstance().getAccountMap().get(value); break;
            }
        }
    }

    @Override
    public void process(LocalDate date) throws Exception {
        Bill bill = BillManager.getInstance().getIssued().stream()
        .filter(bill -> bill.getRf().equals(paymentCode) && !bill.isStatus())
        .findFirst().orElse(null);

        if (bill == null) return;

        if (bill.getAmount() > maxAmount) return;

        Payment trans = new Payment(
            UUID.randomUUID().toString(),
            customerVat,
            date.toString(),
            bill.getRf(),
            bill.getAmount()
        );

        TransactionManager.getInstance().executeTransaction(trans);
        BillManager.getInstance().payBill(bill);

        double underArmour = AccountManager.getInstance().getAccountMap().get(chargeAccount).getBalance(); // underArmour = newBalance

        
    }

    public String getPaymentCode() {return paymentCode;}
    public double getMaxAmount() {return maxAmount;}
    public BankAccount getChargeAccount() {return chargeAccount;}

    
}
