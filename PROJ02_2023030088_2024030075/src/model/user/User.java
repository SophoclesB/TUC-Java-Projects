package model.user;

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

	protected User(){} // EMPTY CONSTRUCTOR FOR CREATING NULL VALUE OBJECT
	
	/**
	     * Επιστρέφει ένα string με τα δεδομένα μας στην μορφή που θέλουμε για το CSV
		 * για αποθήκευση.
	     */
	    public String marshal() {
	        return String.join(",",
					"type:" + getType(),
	                "legalName:" + getLegalName(),
					"userName:" + getUserName(),
	                "password:" +getPassword()
	        );
	    }

	    /**
	     * Unmarshals a string to populate the Admin object
	     * @param data The string data to unmarshall
	     */
	    public void unmarshal(String data) {
	        String[] parts = data.split(",");
			for(String pair : parts){
				String[] kv = pair.split(":");
				String key = kv[0];
				String value = kv[1];

				switch(key) {
					case "type": this.type = value; break;
					case "legalName": this.legalName = value; break;
					case "userName": this.userName = value; break;
					case "password": this.password = value; break;
					case "vatNumber": 
                    	if (this instanceof Customer)
                    	    this.vatNumber = value;
                    	break;
				}
			}
	    }



	// ABSTRACT METHODS THAT SUBCLASSES IMPLEMENT
	protected abstract String getType();

	
	// USER PARENT CLASS METHODS
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
