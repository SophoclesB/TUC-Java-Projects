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
					"type:Bill", 
                    "paymentCode:" + getRf(),
	                "billNumber:" + getBillNumber(),
					"issuer:" + getIssuer(),
	                "customer:" + getCustomer(),
                    "amount:" + getAmount(),
                    "issueDate:" + getIssueDate(),
                    "dueDate:" + getDueDate()
	        );
    }

    /* 

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
		for(String pair : parts){
			String[] kv = pair.split(":", 2);
            if(kv.length < 2) {
                continue;
            }
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

    */

    @Override
    public void unmarshal(String data) {
        String[] fields = data.split(",");
        for (String field : fields) {
            String[] kv = field.split(":");
            if (kv.length < 2) continue;
            switch (kv[0]) {
                case "paymentCode" -> billRf = kv[1];
                case "billNumber"  -> billNumber = kv[1];
                case "issuer"      -> issuer = kv[1];
                case "customer"    -> customer = kv[1];
                case "amount"      -> amount = Float.parseFloat(kv[1]);
                case "issueDate"   -> issueDate = LocalDate.parse(kv[1].trim());
                case "dueDate"     -> dueDate = LocalDate.parse(kv[1].trim());
                case "paid"        -> status = Boolean.parseBoolean(kv[1]);
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
