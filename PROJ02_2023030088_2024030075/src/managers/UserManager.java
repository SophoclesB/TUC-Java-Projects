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
    Set<User> userList;
    CSVManager CSVManager = new CSVManager();

    public static User createUser(String data){
            String[] parts = data.split(",");
            String[] kv = parts[0].split(":");
            switch(kv[1]){
                case "Admin": return new Admin();
                case "Individual": return new Individual(); 
                case "Company": return new Company();
                default: throw new NullPointerException();
            }
        }

    
}
