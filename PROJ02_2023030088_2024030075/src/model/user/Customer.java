package model.user;

public class Customer extends User{
	private String vatNumber;

	public Customer(String type, String legalName, String userName) {
		super(type, legalName, userName);
	}

	private long generateVAT(){
		int VAT= 0;
		return VAT;
	}
}
