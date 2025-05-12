package model.user;

import storage.Storable;

public abstract class User implements Storable{
	private String type;
	private String legalName;
	private String userName;
	private String password;
	
	protected User(String type, String legalName, String userName) {
		if(type == "null")
			throw new IllegalArgumentException("Type cannot be null!");
		setType(type);
		setLegalName(legalName);
		setUserName(userName);
	}
	
	private String getType() {
		return type;
	}
	
	private void setType(String type) {
		this.type = type;
	}
	
	private String getLegalName() {
		return legalName;
	}
	
	private void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	
	private String getUserName() {
		return userName;
	}
	
	private void setUserName(String userName) {
		this.userName = userName;
	}
	
	private String getPassword() {
		return password;
	}
	
	private void setPassword(String password) {
		if (password != null && password.matches("^[0-9]{6,6}$"))
			this.password = password;
		else
			throw new IllegalArgumentException("ERROR: Invalid password, please give another.");
	}

	
	
	
}
