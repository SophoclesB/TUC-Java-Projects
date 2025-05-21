package managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.user.Admin;
import model.user.Company;
import model.user.Customer;
import model.user.Individual;
import model.user.User;
import model.user.User.UserType;
import storage.CSVManager;
import storage.Storable;
import storage.StorableList;
import storage.StorableMap;
import storage.StorageManager;
import utils.RandomString;

public class UserManager {
    private StorableMap<String, Customer> customerMap = new StorableMap<>();
    private StorableList<User> userList = new StorableList<>();
    private static final UserManager INSTANCE = new UserManager();


    public UserManager(){}

    public static UserManager getInstance(){ return INSTANCE; }
    
    public void saveUsers(String filePath, boolean append) {
        CSVManager.INSTANCE.save(userList, filePath + "users/users.csv", append);
    }

    public void loadUsers(String filePath, Storable s) {
        CSVManager.INSTANCE.load(s, filePath + "users/users.csv");

        if(s instanceof User) {
            loadUser(s);
            return;
        }

        StorableList<?> list = (StorableList<?>) s;
        for (Storable storable : list) {
            loadUser(storable);
        }  
    }

    public void loadUser(Storable s) {
        User user = (User) s;
        if(s instanceof User) {
            userList.add(user);
            return;
        }
        if(s instanceof Customer) {
            customerMap.put(((Customer) user).getVAT() , (Customer) user);
        }
    }

    public void printCustomerDetails(String vatNumber){
        System.out.println("Customer Details:\t" + customerMap.get(vatNumber).marshal());
    }

    public User authenticate(String userName, String password) {
        return userList.stream()
                .filter(user -> user.getUserName().equals(userName) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean userNameExists(String userName) {
        return userList.stream()
                .anyMatch(user -> user.getUserName().equals(userName));
    }

    public void register(UserType type, String legalName, String userName, String password) {
        if(type == UserType.Company){
            String vatNumber = generateUniqueVatNumber(true);
            Company newComp = new Company(legalName, userName, password, vatNumber);
            this.userList.add(newComp);
        } else {
            String vatNumber = generateUniqueVatNumber(false);
            Individual newIndi = new Individual(legalName, userName, password, vatNumber);
            this.userList.add(newIndi);
        }
    }

    private String generateUniqueVatNumber(boolean isCompany) {
        String prefix;
        if (isCompany) { prefix = "090"; } else { prefix = "067"; }

        outer:
        while (true) {
            String rnd = RandomString.getAlphaNumericString(6);
            String vatNum = prefix + rnd;
            for (User user : userList) {
                if (user instanceof Company) {
                    Company compUser = (Company) user;
                    if (compUser.getVAT().equals(vatNum)) { continue outer; }
                } else if (user instanceof Individual) {
                    Individual indiUser = (Individual) user;
                    if (indiUser.getVAT().equals(vatNum)) { continue outer; }
                }
            }
            return vatNum;
        }
    }

    public void printAllUsers() {
        System.out.println("All Users:");
        for (User user : userList) {
            System.out.println(user.marshal());
        }
    }

    public StorableMap<String, Customer> getCustomerMap() {
        return customerMap;
    }

    
}
