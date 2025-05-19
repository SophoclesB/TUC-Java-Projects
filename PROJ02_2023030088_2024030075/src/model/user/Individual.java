package model.user;

import java.util.ArrayList;
import java.util.List;
import model.accounts.*;
import model.orders.StandingOrder;

public class Individual extends Customer{
    private List<PersonalAccount> accounts = new ArrayList<>();
    private List<StandingOrder> standingOrders;

    public Individual(String legalName, String userName, String password){
        super(legalName, userName, password);
    }

    public Individual(){super();}

    @Override
    public String getType(){
        return "Individual";
    }
}
