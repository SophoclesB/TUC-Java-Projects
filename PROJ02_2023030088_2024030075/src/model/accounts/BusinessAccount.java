package model.accounts;

import model.user.Customer;
public class BusinessAccount extends BankAccount {
    float maintenanceFee;

    public BusinessAccount(Customer owner){
        super(owner);
        this.maintenanceFee = 0.00f;
        recentBusiness = this;
    }

    public BusinessAccount() {super();}

    @Override
    public String marshal() {
	        return String.join(",",
					"type:" + getType(),
	                "iban:" + getIban(),
					"primaryOwner:" + getPrimaryOwner(),
	                "dateCreated:" + getDateCreated(),
                    "rate" + getRate(),
                    "balance" + getBalance(),
                    "fee" + getMaintenanceFee()
	        );
	    }
    
    @Override
    public void unmarshal(String data){
        String[] parts = data.split(".");
        for (String pair : parts){
            String[] kv = pair.split(":");
				String key = kv[0];
				String value = kv[1];

                switch(key) {
					case "type": this.type = value; break;
					case "iban": this.iban = value; break;
					case "primaryOwner": this.primaryOwner = value; break;
					case "dateCreated": this.dateCreated = value; break;
					case "rate": this.rate = Double.valueOf(value); break;
                    case "balance": this.balance = Double.valueOf(value); break;
                    case "fee": this.maintenanceFee = Integer.valueOf(value); break;
                }
            }     	
    }

    public float getMaintenanceFee() {
        return maintenanceFee;
    }

    
}
