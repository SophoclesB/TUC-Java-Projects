package model.user;
import java.util.Objects;

import model.accounts.BankAccount;
import model.bills.Bill;
import model.orders.StandingOrder;
import model.transactions.Transaction;

public class Admin extends User {
    public Admin(String legalName, String userName, String password) {
        super(UserType.Admin, legalName, userName, password);
    }
	public Admin() {}
	

	@Override
	public String marshal() {
		return super.marshal();
	}
	@Override
	public void unmarshal(String data) {
        String[] parts = data.split(",");
		for(String pair : parts){
			String[] kv = pair.split(":");
			String key = kv[0];
			String value = kv[1];
			switch(key) {
				case "type": this.type = UserType.valueOf(value); break;
				case "legalName": this.legalName = value; break;
				case "userName": this.userName = value; break;
				case "password": this.password = value; break;
			}
		}
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
		return true;
    }
}

