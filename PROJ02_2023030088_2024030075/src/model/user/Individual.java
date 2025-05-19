package model.user;

import java.util.ArrayList;
import java.util.List;
import model.accounts.*;
import model.orders.StandingOrder;

public class Individual extends Customer{
    public Individual(String legalName, String userName, String password, String vatNumber){
        super(UserType.Individual, legalName, userName, password);
        this.vatNumber = vatNumber;
    }
    public Individual(){}

    @Override
    public UserType getType(){
        return UserType.Individual;
    }

    @Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    if (!super.equals(o)) return false;
	    Individual ind = (Individual) o;
		return true;
	}
}
