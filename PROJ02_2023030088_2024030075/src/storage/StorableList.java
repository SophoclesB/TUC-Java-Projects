package storage;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import model.accounts.BusinessAccount;
import model.accounts.PersonalAccount;
import model.bills.Bill;
import model.orders.PaymentOrder;
import model.orders.TransferOrder;
import model.user.Admin;
import model.user.Company;
import model.user.Individual;

public class StorableList <T extends Storable> implements Storable, List<T>{
    private final List<T> storage;

    public StorableList() {
        storage = new ArrayList<>();
    }

    @Override
    public String marshal() {
        StringBuilder sb = new StringBuilder();
        for (Storable s : storage) {
            sb.append(s.marshal()+"\n");
        } 
        return sb.toString();
    }

    @Override
    public void unmarshal(String data) {
        this.clear();
        String[] lines = data.split("\n");
        for (String line : lines) {
            if (line.trim().equals("")) {
                continue;
            }
            String[] fields = line.split(",");
            String[] kv = fields[0].split(":");
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
            storage.add((T)s);
        } 
    }

    @Override public boolean add(T s) { return this.storage.add(s); }
    @Override public void add(int index, T s) { this.storage.add(index, s); }
    @Override public T get(int index) { return this.storage.get(index); }
    @Override public Stream<T> stream() { return this.storage.stream(); }
    @Override public int size() { return this.storage.size(); }
    @Override public T set(int index, T item) { return this.storage.set(index, item); }
    @Override public boolean retainAll(Collection<?> coll) { return this.storage.retainAll(coll); }
    @Override public boolean contains(Object obj) { return this.storage.contains(obj); }
    @Override public void clear() { this.storage.clear(); }
    @Override public boolean removeAll(Collection<?> col) { return this.storage.removeAll(col); }
    @Override public ListIterator<T> listIterator() { return this.listIterator(); }
    @Override public ListIterator<T> listIterator(int i) { return this.storage.listIterator(i); }
    @Override public T[] toArray() { return this.toArray(); }
    @Override public <A>A[] toArray(A[] a) { return this.storage.toArray(a); }
    @Override public boolean containsAll(Collection<?> col) { return this.storage.containsAll(col); }
    @Override public int indexOf(Object obj) { return this.storage.indexOf(obj); }
    @Override public boolean isEmpty() { return this.storage.isEmpty(); }
    @Override public boolean addAll(Collection<? extends T> col) { return this.addAll(col); }
    @Override public boolean addAll(int index, Collection<? extends T> col) { return this.addAll(index, col); }
    @Override public List<T> subList(int fromIndex, int toIndex) { return this.storage.subList(fromIndex, toIndex); }
    @Override public T remove(int index) { return this.storage.remove(index); }
    @Override public boolean remove(Object obj) { return this.storage.remove(obj); }
    @Override public int lastIndexOf(Object obj) { return this.storage.lastIndexOf(obj); } 
    @Override public Iterator<T> iterator() { return this.storage.iterator(); }
}
