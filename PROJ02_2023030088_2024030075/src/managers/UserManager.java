package managers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.user.Admin;
import model.user.Company;
import model.user.Individual;
import model.user.User;
import storage.CSVManager;
import storage.StorageManager;

public class UserManager {
    private User sampleUser;
    public Set<User> userList;
    private CSVManager csvManager;

    public UserManager(){
        this.csvManager = new CSVManager();
        sampleUser = csvManager.createUserFromCSV("data/users/users.csv");
        csvManager.load(sampleUser, "data/users/users.csv");
    }

    
}
