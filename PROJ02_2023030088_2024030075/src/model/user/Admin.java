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

	    @Override
	    public boolean viewCustomerDetails() {
	        return true;
	    }

	    /**
	     * Admins can view all account details
	     */
	    @Override
	    public boolean viewAccountDetails() {
	        return true;
	    }

	    /**
	     * Admins can view all transactions
	     */
	    @Override
	    public boolean viewTransactions() {
	        return true;
	    }

	    /**
	     * Admins can perform administrative actions
	     */
	    @Override
	    public boolean isAdmin() {
	        return true;
	    }

	    /**
	     * Admins don't have personal bank accounts
	     */
	    @Override
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

	    @Override
	    public int hashCode() {
	        return Objects.hash(super.hashCode());
	    }

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
	    /**@Override
	    public String marshall() {
	        return String.join(",",
	                getUsername(),
	                getPassword(), // Note: password should already be hashed
	                getEmail(),
	                employeeId,
	                department,
	                getType().name()
	        );
	    }*/

	    /**
	     * Unmarshals a string to populate the Admin object
	     * @param data The string data to unmarshall
	     */
	    /**@Override
	    public void unmarshall(String data) {
	        String[] parts = data.split(",");
	        if (parts.length >= 6) {
	            setUsername(parts[0]);
	            setPassword(parts[1]); // Already hashed
	            setEmail(parts[2]);
	            this.employeeId = parts[3];
	            this.department = parts[4];
	            // UserType is already set by constructor
	        }
	    }
	}*/

