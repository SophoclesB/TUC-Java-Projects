package model.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import managers.UserManager;
import model.user.Customer;
import model.user.Individual;
import storage.StorableMap;

public class PersonalAccount extends BankAccount {
    private StorableMap<String, Individual> coOwners;


    public PersonalAccount(Customer owner, List<Individual> coOwners){
        super(owner);
        this.coOwners = new StorableMap<>();
        for(Individual it : coOwners){ 
            this.coOwners.put(it.getVAT(), it); 
        }
        recentPersonal = this;
    }
    public PersonalAccount(Customer owner){
        super(owner);
        this.coOwners = new StorableMap<>();
        recentPersonal = this;
    }
    public PersonalAccount(){
        super();
        this.coOwners = new StorableMap<>();
        recentPersonal = this;
    }

    @Override
    public String marshal() {
        StringBuffer sb = new StringBuffer();
	    sb.append(super.marshal());
        if(coOwners.size()> 0){
            for(String s : coOwners.keySet()){ // This is going to be the IBAN 
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
            String[] kv = pair.split(":", 2);
			String key = kv[0];
			String value = kv[1];
            switch(key) {
                case "coOwner": this.coOwners.put(value, (Individual)UserManager.getInstance().getCustomerMap().get(value)); break;
            }
        }     	
    }

    private String printCoOwners(){
        Iterator<String> it = coOwners.keySet().iterator();
        StringBuffer s = new StringBuffer();
        while(it.hasNext()){
            s.append(",coOwner:"+it.hasNext());
        }
        return s.toString();
    }

    
}
