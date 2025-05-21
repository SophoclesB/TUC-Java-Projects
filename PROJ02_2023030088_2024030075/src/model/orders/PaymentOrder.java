package model.orders;

import java.time.LocalDate;

import managers.AccountManager;
import model.accounts.BankAccount;
import model.orders.StandingOrder.OrderType;

public class PaymentOrder extends StandingOrder{
    private String paymentCode;
    private float maxAmount;
    private static final float fee = 0.3f;
    private BankAccount chargeAccount;

    public PaymentOrder() {}

    @Override
    public String marshal() {
        return super.marshal()+
        String.join(",",

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
                case "type":this.type = OrderType.valueOf(value); break;
                case "orderId":this.orderId = value; break;
                case "paymentCode":this.paymentCode = value; break;
                case "title":this.title = value; break;
                case "description":this.description = value; break;
                case "customer":this.customerVat = value; break;
                case "maxAmount":this.maxAmount = Float.parseFloat(value); break;
                case "startDate":this.startDate = LocalDate.parse(value); break;
                case "endDate":this.endDate = LocalDate.parse(value); break;
                case "chargeAccount":this.chargeAccount = AccountManager.getInstance().getAccounts().get(value); break;
            }
        }
    }

    @Override
    public void process(LocalDate date) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'process'");
    }
}
