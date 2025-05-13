package model.user;

import java.util.ArrayList;
import java.util.List;
import storage.Storable;

public abstract class User implements Storable{
	private String type;
	private String legalName;
	private String userName;
	private String password;
	private String vatNumber;
	
	protected User(String legalName, String userName, String password) {
		setLegalName(legalName);
		setUserName(userName);
		setPassword(password);
	}
	

	// ABSTRACT METHODS THAT SUBCLASSES IMPLEMENT
	public abstract String marshal();
	public abstract void unmarshal(String data);

	// USER PARENT CLASS METHODS
	protected String getType(){
		return type;
	}

	protected String getLegalName() {
		return legalName;
	}
	
	protected void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	
	protected String getUserName() {
		return userName;
	}
	
	protected void setUserName(String userName) {
		this.userName = userName;
	}
	
	protected void setPassword(String password) {
		if (password != null && password.matches("^[0-9]{6,6}$"))
			this.password = password;
		else
			throw new IllegalArgumentException("ERROR: Invalid password, please give another.");
	}

	protected String getPassword() {
		return password;
	}

	
	
	
}
