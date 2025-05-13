package model.accounts;

import java.util.List;
import model.user.Customer;
import storage.Storable;

public abstract class BankAccount implements Storable {
    private String type;
    private String iban;
    private String primaryOwner;
    private String dateCreated;
    private double rate;
    private int balance;
    private List<String> coOwner;

    public BankAccount(Customer owner){
        this.primaryOwner = owner.getVAT();
    }
}
