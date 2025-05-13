package model.user;

import java.util.List;
import model.bills.Bill;
import model.accounts.BusinessAccount;

public class Company extends Customer {
    private BusinessAccount businessAccount;
    private List<Bill> issuedBills;

    protected String getType(){
        return "Company";
    }
}
