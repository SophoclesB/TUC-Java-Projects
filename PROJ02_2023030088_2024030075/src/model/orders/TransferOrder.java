package model.orders;

import java.time.LocalDate;
import java.util.UUID;

import managers.AccountManager;
import managers.TransactionManager;
import model.accounts.BankAccount;
import model.transactions.Transfer;

public class TransferOrder extends StandingOrder{
    private float amount;
    private static final float fee = 1.2f;
    private BankAccount chargeAccount;
    private BankAccount creditAccount;
    private int frequencyInMonths;
    private int dayOfMonth;

    public TransferOrder() {}

    @Override
    public String marshal() {
        return super.marshal() +
        String.join(",",
	                "title:" + getTitle(),
					"description:" + getDescription(),
	                "customer:" + getCustomerVat(),
                    "amount:" + getAmount(),
                    "startDate:" + getStartDate().format(format),
                    "endDate:" + getEndDate().format(format),
                    "fee:" + getFee(),
                    "chargeAccount:" + getChargeAccount(),
                    "creditAccount" + getCreditAccount(),
                    "frequencyInMonths:" + getFrequencyInMonths(),
                    "dayOfMonth" + getDayOfMonth()
	        );
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
		for(String pair : parts){
			String[] kv = pair.split(":");
			String key = kv[0];
			String value = kv[1];

			switch(key) {
                case "type"                 :this.type = OrderType.valueOf(value); break;
                case "orderId"              :this.orderId = value; break;
                case "title"                :this.title = value; break;
                case "description"          :this.description = value; break;
                case "amount"               :this.amount = Float.parseFloat(value); break;
                case "startDate"            :this.startDate = LocalDate.parse(value); break;
                case "endDate"              :this.endDate = LocalDate.parse(value); break;
                case "chargeAccount"        :this.chargeAccount = AccountManager.getInstance().getAccountMap().get(value); break;
                case "creditAccount"        :this.creditAccount = AccountManager.getInstance().getAccountMap().get(value); break;
                case "frequencyInMonths"    :this.frequencyInMonths = Integer.parseInt(value); break;
                case "dayOfMonth"           :this.dayOfMonth = Integer.parseInt(value); break;
			}
		}
    }

    @Override
    public void process(LocalDate date) throws Exception{
        if(date.getDayOfMonth() != dayOfMonth) return;

        Transfer trans = new Transfer(
            UUID.randomUUID().toString(),
            customerVat,
            null,
            date.toString(),
            chargeAccount,
            creditAccount,
            amount
        );

        TransactionManager.getInstance().executeTransaction(trans);


    }

    public float getAmount() {return amount;}
    public static float getFee() {return fee;}
    public BankAccount getChargeAccount() {return chargeAccount;}
    public BankAccount getCreditAccount() {return creditAccount;}
    public int getFrequencyInMonths() {return frequencyInMonths;}
    public int getDayOfMonth() {return dayOfMonth;}

    
}
