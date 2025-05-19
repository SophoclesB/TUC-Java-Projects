package model.user;

import storage.Storable;

public abstract class User implements Storable{
	public enum UserType { Individual, Company, Admin};

	protected UserType type;
	protected String legalName;
	protected String userName;
	protected String password;
	protected String vatNumber;
	
	protected User(UserType type, String legalName, String userName, String password) {
		setType(type);
		setLegalName(legalName);
		setUserName(userName);
		setPassword(password);
	}

	protected User(){} // EMPTY CONSTRUCTOR FOR CREATING NULL VALUE OBJECT
	
		/**
	     * Επιστρέφει ένα string με τα δεδομένα μας στην μορφή που θέλουμε για το CSV
		 * για αποθήκευση.
	     */
	    public String marshal() {
	        return String.join(",",
					"type:" + getType().toString(),
	                "legalName:" + getLegalName(),
					"userName:" + getUserName(),
	                "password:" +getPassword()
	        );
	    }

	// SET PASSWORD
	private void setType(UserType type) { this.type = type; }
	private void setLegalName(String legalName) { this.legalName = legalName; }
	private void setUserName(String userName) { this.userName = userName; }
	protected void setPassword(String password) {
		if (password != null && password.matches("^[0-9]{6,6}$"))
			this.password = password;
		else
			throw new IllegalArgumentException("ERROR: Invalid password, please give another. (MUST BE 6 DIGITS)");
	}

	public UserType getType() { return type; }
	public String getLegalName() { return legalName; }
	public String getUserName() { return userName; }
	public String getPassword() { return password; }

}
