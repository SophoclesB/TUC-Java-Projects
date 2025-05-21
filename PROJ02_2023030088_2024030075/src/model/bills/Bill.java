package model.bills;

import java.time.LocalDate;

import model.user.User.UserType;
import storage.Storable;

public class Bill implements Storable{
    private String billRf;
    private String billNumber;
    private String issuer;
    private String customer;
    private float amount;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private boolean status;

    public Bill() {}

    @Override
    public String marshal() {
        return String.join(",",
					"type:Bill" + 
                    "paymentCode:" + getRf(),
	                "billNumber:" + getBillNumber(),
					"issuer:" + getIssuer(),
	                "customer:" + getCustomer(),
                    "amount:" + getAmount(),
                    "issueDate:" + getIssueDate(),
                    "dueDate:" + getDueDate()
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
                case "paymentCode"  :this.billRf = value;
                case "billNumber"   :this.billNumber = value;
                case "issuer"       :this.issuer = value;
                case "customer"     :this.customer = value;
                case "amount"       :this.amount = Float.parseFloat(value);
                case "issueDate"    :this.issueDate = LocalDate.parse(value);
                case "dueDate"      :this.dueDate = LocalDate.parse(value);
                case "paid"         :this.status = Boolean.parseBoolean(value);
			}
		}
    }

    public String getRf() { return billRf; }
    public String getBillNumber() { return billNumber; }
    public String getIssuer() { return issuer; }
    public String getCustomer() { return customer; }
    public float getAmount() { return amount; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isStatus() { return this.status; }
    public void setStatus(boolean status) { this.status = status; }
}
