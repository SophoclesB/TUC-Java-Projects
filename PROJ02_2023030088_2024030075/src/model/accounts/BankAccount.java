package model.accounts;

import model.user.Customer;
import storage.Storable;

public abstract class BankAccount implements Storable {
    private String type;
    private String iban;
    private String primaryOwner;
    private Customer primaryOwnerObject;
    private String dateCreated;
    private double rate;
    private double balance;

    protected static final String COUNTRY_CODE = "GR";

    public BankAccount(Customer owner){
        this.primaryOwnerObject = owner;
        this.primaryOwner = owner.getVAT();

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

    public String generateIban() {
        StringBuffer sb = new StringBuffer();
        sb.append(COUNTRY_CODE);
        if(this.primaryOwnerObject.getType().equals("Individual"))
            sb.append("100");
        else
            sb.append("200");
        return sb.toString();
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
