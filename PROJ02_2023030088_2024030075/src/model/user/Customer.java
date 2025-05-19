package model.user;

import model.user.Individual;

public abstract class Customer extends User{
	private String vatNumber;
	private static int latestIndividualVat = 688307;
	private static int latestCompanyVat = 800900;

	public Customer(String legalName, String userName, String password){
		super(legalName, userName, password);
		setNewVat();
	}

	public Customer(){}

	private void setNewVat(){
		this.vatNumber = generateVAT();
	}

	private String generateVAT(){
		StringBuffer sb = new StringBuffer();
		if(this instanceof Individual){
			sb.append("067");
			sb.append(++latestIndividualVat);
		}
		else {
			sb.append("090");
			sb.append(latestCompanyVat+100);
		}

		return sb.toString();
	}

	public String getVAT(){
		return vatNumber;
	}

	public abstract String getType();
}
