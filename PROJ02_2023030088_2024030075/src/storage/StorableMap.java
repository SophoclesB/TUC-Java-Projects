package storage;

import statements.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.accounts.BankAccount;
import model.accounts.BusinessAccount;
import model.accounts.PersonalAccount;
import model.bills.Bill;
import model.orders.PaymentOrder;
import model.orders.TransferOrder;
import model.user.Admin;
import model.user.Company;
import model.user.Customer;
import model.user.Individual;

public class StorableMap <K, V extends Storable> implements Storable, Map<K, V>{
    private final Map<K, V> storageMap;

    public StorableMap() { storageMap = new HashMap<>(); }

    public String marshal() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : storageMap.entrySet()) {
            sb.append(entry.getValue().marshal() + "\n");
        }
        return sb.toString();
    }

    public void unmarshal(String data) {
        this.clear();
        String[] lines = data.split("\n");
        for (String line : lines) {
            if (line.trim().equals("")) {
                continue;
            }
            String[] fields = line.split(",");
            String [] kv = fields[0].split(":");
            Storable s;
            switch (kv[1]) {
                case "Admin"            : s = new Admin(); break;
                case "Individual"       : s = new Individual(); break;
                case "Company"          : s = new Company(); break;
                case "PersonalAccount"  : s = new PersonalAccount(); break;
                case "BusinessAccount"  : s = new BusinessAccount(); break;
                case "Bill"             : s = new Bill(); break;
                case "Statement"        : s = new Statement(); break;
                case "TransferOrder"    : s = new TransferOrder(); break;
                case "PaymentOrder"     : s = new PaymentOrder(); break;
                default                 : System.out.println("Invalid type: " + kv[1]); return;
            }
            s.unmarshal(line);
            if(s instanceof Customer)
                storageMap.put((K) ((Customer)s).getVAT(), (V) s);
            if(s instanceof BankAccount)
                storageMap.put((K) ((BankAccount)s).getIban(), (V) s);
        }
    }


    @Override public int size() { return storageMap.size(); }
    @Override public boolean isEmpty() { return storageMap.isEmpty(); }
    @Override public boolean containsKey(Object key) { return storageMap.containsKey(key); }
    @Override public boolean containsValue(Object value) { return storageMap.containsValue(value); }
    @Override public V get(Object key) { return storageMap.get(key); }
    @Override public V put(K key, V value) { return storageMap.put(key, value); }
    @Override public V remove(Object key) { return storageMap.remove(key); }
    @Override public void putAll(Map<? extends K, ? extends V> m) { storageMap.putAll(m); }
    @Override public void clear() { storageMap.clear(); }
    @Override public Set<K> keySet() { return storageMap.keySet(); }
    @Override public Collection<V> values() { return storageMap.values(); }
    @Override public Set<Entry<K, V>> entrySet() { return storageMap.entrySet(); }
}


