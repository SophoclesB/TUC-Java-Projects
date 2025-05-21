package managers;

import model.accounts.BankAccount;
import model.user.*;
import storage.Storable;
import storage.StorableMap;

public class AccountManager {
    private static final AccountManager INSTANCE = new AccountManager();
    private final StorableMap<String, BankAccount> accountMap = new StorableMap<>();
    
    private AccountManager() {}


    public static AccountManager getInstance() { return INSTANCE; }
    
    
    public StorableMap<String, BankAccount> getAccounts(){
        return accountMap;
    }
    
}
