package model.user;
import java.util.Objects;

import model.accounts.BankAccount;
import model.bills.Bill;
import model.orders.StandingOrder;
import model.transactions.Transaction;

	public class Admin extends User {
	    public Admin(String legalName, String userName, String password) {
	        super(legalName, userName, password);
	    }

		public String getType(){
			return "Admin";
		}
 
	    public boolean viewCustomerDetails() {
	        return true;
	    }

	    /**
	     * Admins can view all account details
	     */
	    public boolean viewAccountDetails() {
	        return true;
	    }

	    /**
	     * Admins can view all transactions
	     */
	    public boolean viewTransactions() {
	        return true;
	    }

	    /**
	     * Admins can perform administrative actions
	     */
	    public boolean isAdmin() {
	        return true;
	    }

	    /**
	     * Admins don't have personal bank accounts
	     */
	    public boolean hasBankAccount() {
	        return false;
	    }

	    /**@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        if (!super.equals(o)) return false;
	        Admin admin = (Admin) o;
	        return Objects.equals(department, admin.employeeId) && 
	               Objects.equals(department, admin.department);
	    }*/

	    /**@Override
	    public String toString() {
	        return "Admin{" +
	                "username='" + getUsername() + '\'' +
	                ", email='" + getEmail() + '\'' +
	                ", employeeId='" + employeeId + '\'' +
	                ", department='" + department + '\'' +
	                '}';
	    }*/

	    /**
	     * Marshals the Admin object to a string for storage
	     * @return A string representation of the Admin
	     */
	    @Override
	    public String marshal() {
	        return String.join(",",
					getType(),
	                getUserName(),
	                getPassword()
	        );
	    }

	    /**
	     * Unmarshals a string to populate the Admin object
	     * @param data The string data to unmarshall
	     */
	    @Override
	    public void unmarshal(String data) {
	        String[] parts = data.split(",");
	        if (parts.length >= 6) {
	            setUserName(parts[0]);
	            // UserType is already set by constructor
	        }
	    }
	}

