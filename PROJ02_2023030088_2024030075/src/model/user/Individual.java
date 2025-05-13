package model.user;

import java.util.List;
import model.accounts.*;
import model.orders.StandingOrder;

public class Individual extends Customer{
    private List<PersonalAccount> accounts;
    private List<StandingOrder> standingOrders;


    protected String getType(){
        return "Individual";
    }
}
