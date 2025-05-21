package managers;

import java.util.List;
import java.util.stream.Stream;

import model.accounts.BankAccount;
import model.accounts.BusinessAccount;
import model.user.*;
import storage.CSVManager;
import storage.Storable;
import storage.StorableList;
import storage.StorableMap;

public class AccountManager {
    private static final AccountManager INSTANCE = new AccountManager();
    private final StorableMap<String, BankAccount> accountMap = new StorableMap<>();
    private final StorableList<BankAccount> accountList = new StorableList<>();
    
    private AccountManager() {}


    public static AccountManager getInstance() { return INSTANCE; }

    public void saveAccounts(String filePath, boolean append) {
        CSVManager.INSTANCE.save(accountMap, filePath + "accounts/accounts.csv", append);
    }

    public void loadAccounts(String filePath, Storable s) {
        CSVManager.INSTANCE.load(s, filePath + "accounts/accounts.csv");

        if(s instanceof BankAccount){
            loadAccount(s);
            return;
        }

        StorableList<?> list = (StorableList<?>) s;
        for (Storable storable : list) {
            loadAccount(storable);
        }
    }

    public void loadAccount(Storable s) {
        if(!accountList.contains(s)) {
            accountList.add((BankAccount) s);
        }
        if(!accountMap.containsKey(((BankAccount) s).getIban())) {
            accountMap.put(((BankAccount) s).getIban(), (BankAccount) s);
            return;
        }
    }

    public BankAccount getAccountByIban(String iban) {
        return accountMap.get(iban);
    }

    public List<BankAccount> getAccountsForVAT(String VAT) {
        Stream<BankAccount> stream = accountMap.values().stream();
        return stream.filter(account -> account.getPrimaryOwner().getVAT().equals(VAT)).toList();
    }

    public void printAccountsFor(String VAT) {
        getAccountsForVAT(VAT).forEach(a ->
            System.out.printf("%s [%s]: %.2f%n",
                a.getIban(), a.getClass().getSimpleName(), a.getBalance()));
    }

    public void printAllAccounts() {
        accountList.forEach(a ->
            System.out.printf("%s [%s]: %.2f%n",
                a.getIban(), a.getClass().getSimpleName(), a.getBalance()));
    }

    public void printAccountDetailsForIban(String iban) {
        if(!accountMap.containsKey(iban)) {
            System.out.println("Account with IBAN " + iban + " does not exist.");
            return;
        }
        System.out.println("Account Details:\t" + accountMap.get(iban).marshal());
    }

    public void applyDailyInterest() {
        accountList.forEach(BankAccount::applyDailyInterest);
    }

    public void applyMonthlyFees() {
        accountList.stream().filter(a -> a instanceof BusinessAccount).forEach(BankAccount::applyMonthlyFees);
    }

    public StorableMap<String, BankAccount> getAccountMap() {
        return accountMap;
    }
}
