package model.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.user.Customer;
import model.user.Individual;

public class PersonalAccount extends BankAccount {
    private List<String> coOwners;


    public PersonalAccount(Customer owner, List<Customer> coOwners){
        super(owner);
        this.coOwners = new ArrayList<>();
        for(Customer it : coOwners){ 
            this.coOwners.add(it.getVAT()); 
        }
        recentPersonal = this;
    }
    public PersonalAccount(Customer owner){
        super(owner);
        this.coOwners = new ArrayList<>();
        recentPersonal = this;
    }
    public PersonalAccount(){
        super();
        this.coOwners = new ArrayList<>();
        recentPersonal = this;
    }

    @Override
    public String marshal() {
        StringBuffer sb = new StringBuffer();
	    sb.append(super.marshal());
        if(coOwners.size()> 0){
            for(String s : coOwners) {
                sb.append(",coOwner:" + s);
            }
        }
        return sb.toString();
	}

    @Override
    public void unmarshal(String data){
        super.unmarshal(data);
        String[] parts = data.split(",");
        for (String pair : parts){
            String[] kv = pair.split(":");
			String key = kv[0];
			String value = kv[1];
            switch(key) {
                case "coOwner": this.coOwners.add(value); break;
            }
        }     	
    }

    private String printCoOwners(){
        Iterator<String> it = coOwners.iterator();
        StringBuffer s = new StringBuffer();
        while(it.hasNext()){
            s.append(",coOwner:"+it.hasNext());
        }
        return s.toString();
    }

    
}
