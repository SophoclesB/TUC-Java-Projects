package model.accounts;

import java.time.LocalDate;

import model.user.Customer;
import storage.Storable;

public abstract class BankAccount implements Storable {
    protected String type;
    protected String iban = "GR000202505000000000";
    protected String primaryOwner;
    protected Customer primaryOwnerObject;
    protected String dateCreated;
    protected double rate;
    protected double balance;
    protected int fee;

    protected static final String COUNTRY_CODE = "GR";
    protected static BusinessAccount recentBusiness;
    protected static PersonalAccount recentPersonal;

    public BankAccount(Customer owner){
        this.primaryOwnerObject = owner;
        this.primaryOwner = owner.getVAT();
        this.balance = 0.00f;
        this.rate = 0.00f;
        this.iban = generateIban(owner);
    }

    public BankAccount() {}

    public String marshal() {
	        return String.join(",",
					"type:" + getType(),
	                "iban:" + getIban(),
					"primaryOwner:" + getPrimaryOwner(),
	                "dateCreated:" + getDateCreated(),
                    "rate" + getRate(),
                    "balance" + getBalance()
	        );
	    }

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
                }
            }     	
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public String generateIban(Customer owner) {
        StringBuffer sb = new StringBuffer();
        sb.append(COUNTRY_CODE);
        if(owner.getType().equals("Individual"))
            sb.append("100");
        else
            sb.append("200");
        
        sb.append(2025+nextIban(owner.getType()));
        return sb.toString();
    }

    private String nextIban(String ownerType){
        String nextIban;
        String latestIban;
        if(ownerType == "Individual")
            latestIban = recentPersonal.getIban();
        else
            latestIban = recentPersonal.getIban();

        String[] split = latestIban.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        int next = Integer.valueOf(split[1]) + 1; 
        nextIban = split[0] + String.valueOf(next);
        return nextIban;
    }

    public String getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(String primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public double getRate() {
        return rate;
    }

    public double getBalance() {
        return balance;
    }

    public static String getCountryCode() {
        return COUNTRY_CODE;
    }


    
}
