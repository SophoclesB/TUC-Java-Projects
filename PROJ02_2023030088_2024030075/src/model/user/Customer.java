package model.user;

import model.user.Individual;

public abstract class Customer extends User{
	protected String vatNumber;
	private static int latestIndividualVat = 688307;
	private static int latestCompanyVat = 800900;

	public Customer(){}
	public Customer(UserType type, String legalName, String userName, String password){
		super(type, legalName, userName, password);
	}

	public String getVAT(){ return vatNumber; }
	public abstract UserType getType();

	@Override
    public String marshal() {
        return super.marshal() + ",vatNumber:" + vatNumber;
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
                case "vatNumber": this.vatNumber = value; break;
			}
		}
	}
}
