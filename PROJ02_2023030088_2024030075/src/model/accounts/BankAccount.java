package model.accounts;

import java.time.LocalDate;

import managers.UserManager;
import model.user.*;
import model.user.User.UserType;
import storage.Storable;

public abstract class BankAccount implements Storable {
    public enum AccountType {PersonalAccount, BusinessAccount}

    protected AccountType type;
    protected String iban;
    protected Customer primaryOwner; //*kane primaryowner account */
    protected LocalDate dateCreated;
    protected double rate;
    protected double balance;

    protected static final String COUNTRY_CODE = "GR";
    protected static BusinessAccount recentBusiness;
    protected static PersonalAccount recentPersonal;

    public BankAccount(Customer owner){
        this.primaryOwner = owner;
        this.balance = 0.00f;
        this.rate = 0.00f;
        this.iban = generateIban(owner);
    }

    public BankAccount() {}

    public String marshal() {
	        return String.join(",",
					"type:" + type.toString(),
	                "iban:" + getIban(),
					"primaryOwner:" + getPrimaryOwner(),
	                "dateCreated:" + getDateCreated().toString(),
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
					case "type": this.type = AccountType.valueOf(value); break;
					case "iban": this.iban = value; break;
					case "primaryOwner": this.primaryOwner = (Individual)UserManager.getInstance().getCustomerMap().get(value); break;
					case "dateCreated": this.dateCreated = LocalDate.parse(value); break;
					case "rate": this.rate = Double.valueOf(value); break;
                    case "balance": this.balance = Double.valueOf(value); break;
                }
            }     	
    }

    public String generateIban(Customer owner) {
        StringBuffer sb = new StringBuffer();
        sb.append(COUNTRY_CODE);
        if(owner.getType().equals(UserType.Individual))
            sb.append("100");
        else
            sb.append("200");
        
        sb.append(2025+this.nextIban(owner.getType().toString()));
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
    
    public void applyDailyInterest() { balance += balance * (rate/365); }
    public void applyMonthlyFees() { balance -= 5.00; }

    public String getIban() { return iban; }
    public Customer getPrimaryOwner() { return primaryOwner; }
    public void setPrimaryOwner(Customer primaryOwner) { this.primaryOwner = primaryOwner; }
    private LocalDate getDateCreated(){ return this.dateCreated; }
    public double getRate() { return rate; }
    public double getBalance() { return balance; }
    public static String getCountryCode() { return COUNTRY_CODE; }
}
