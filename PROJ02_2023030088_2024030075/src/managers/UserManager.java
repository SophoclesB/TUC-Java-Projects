package managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.user.Admin;
import model.user.Company;
import model.user.Individual;
import model.user.User;
import storage.CSVManager;
import storage.StorableMap;
import storage.StorageManager;

public class UserManager {
    private static StorableMap<String, User> userMap = new StorableMap<>();
    private static final UserManager INSTANCE = new UserManager();


    public UserManager(){}

    public static UserManager getInstance(){ return INSTANCE; }
    
    public void saveUsers(String filePath, boolean append) {
        CSVManager.INSTANCE.save(userMap, filePath + "users/users.csv", append);
    }

    public StorableMap<String, User> getUserMap() {
        return userMap;
    }

    
}
