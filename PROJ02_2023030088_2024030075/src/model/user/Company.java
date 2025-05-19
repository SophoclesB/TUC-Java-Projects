package model.user;

import java.util.List;
import model.bills.Bill;
import model.accounts.BusinessAccount;

public class Company extends Customer {
    public Company(String legalName, String userName, String password, String vatNumber) {
        super(UserType.Company, legalName, userName, password);
        this.vatNumber = vatNumber;
    }
    public Company() {}

    @Override
    public UserType getType(){
        return UserType.Company;
    }

    @Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    if (!super.equals(o)) return false;
	    Company comp = (Company) o;
		return true;
	}
}
