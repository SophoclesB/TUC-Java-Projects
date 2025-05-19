package model.accounts;

import model.user.Customer;
public class BusinessAccount extends BankAccount {
    private static final float maintenanceFee = 5.0f;

    public BusinessAccount(Customer owner){
        super(owner);
        recentBusiness = this;
    }
    public BusinessAccount() {super();}

    @Override
    public String marshal() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.marshal());
        sb.append(",fee:"+getMaintenanceFee());
	    return sb.toString();
	}
    
    // NO NEED TO OVERRIDE UNMARSHAL SINCE THE FEE IS CONSTANT FOR ALL COMPANIES (ALWAYS ADD 5)


    public void applyMonthlyFees() {balance -= getMaintenanceFee(); }
    public float getMaintenanceFee() { return maintenanceFee; }
}
