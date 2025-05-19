package model.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.user.Customer;
import model.user.Individual;

public class PersonalAccount extends BankAccount {
    private List<String> coOwners;
    private List<Individual> coOwnerObjects;
    private int coOwnerCount;

    public PersonalAccount(Customer owner){
        super(owner);
        coOwners = new ArrayList<>();
        coOwnerObjects = new ArrayList<>();
        this.coOwnerCount = 0;
        recentPersonal = this;
    }

    @Override
    public String marshal() {
	        return String.join(",",
					"type:" + getType(),
	                "iban:" + getIban(),
					"primaryOwner:" + getPrimaryOwner(),
	                "dateCreated:" + getDateCreated(),
                    "rate" + getRate(),
                    "balance" + getBalance(),
                    printCoOwners()
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
                    case "coOwner": this.coOwners.add(value); break;
                }
            }     	
    }

    private String printCoOwners(){
        Iterator<String> it = coOwner.iterator();
        StringBuffer s = new StringBuffer();
        while(it.hasNext()){
            s.append(",coOwner:"+it.hasNext());
        }
        return s.toString();
    }

    public List<String> getCoOwner() {
        return coOwners;
    }

    public int getCoOwnerCount() {
        return coOwnerCount;
    }

    
}
