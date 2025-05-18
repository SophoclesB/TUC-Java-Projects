package model.user;

import java.util.List;
import model.accounts.*;
import model.orders.StandingOrder;

public class Individual extends Customer{
    private List<PersonalAccount> accounts;
    private List<StandingOrder> standingOrders;

    public Individual(String legalName, String userName, String password){
        super(legalName, userName, password);
    }

    @Override
    public String getType(){
        return "Individual";
    }
}
