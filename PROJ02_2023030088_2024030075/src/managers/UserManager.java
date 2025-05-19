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
    public User sampleUser;
    public Set<User> userList;
    private CSVManager csvManager;

    public UserManager(){
        this.csvManager = new CSVManager();
        sampleUser = csvManager.createUserFromCSV("D:\\ECE\\TUC-Java-Projects\\PROJ02_2023030088_2024030075\\src\\data\\users\\users.csv");
        csvManager.load(sampleUser, "D:\\ECE\\TUC-Java-Projects\\PROJ02_2023030088_2024030075\\src\\data\\users\\users.csv");
    }

    
}
