package model.user;

import java.util.List;
import model.bills.Bill;
import model.accounts.BusinessAccount;

public class Company extends Customer {
    private BusinessAccount businessAccount;
    private List<Bill> issuedBills;

    public Company(){}

    @Override
    public String getType(){
        return "Company";
    }
}
