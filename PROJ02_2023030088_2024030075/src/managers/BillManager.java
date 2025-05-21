package managers;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import model.bills.Bill;
import storage.CSVManager;
import storage.Storable;
import storage.StorableList;
import storage.StorableMap;

public class BillManager {
    private static final BillManager INSTANCE = new BillManager();
    private final StorableMap<LocalDate, StorableList<Bill>> billMap = new StorableMap<>();
    private final StorableList<Bill> issuedBills = new StorableList<>();
    private final StorableList<Bill> paidBills = new StorableList<>();  

    private BillManager() {}

    public static BillManager getInstance() {
        return INSTANCE;
    }

    public void saveAllBills(String filePath, boolean append) {
        for(LocalDate key : this.billMap.keySet()){
            CSVManager.INSTANCE.save(issuedBills, filePath + "bills/issued.csv", append);
            CSVManager.INSTANCE.save(paidBills, filePath + "bills/paid.csv", append);
        }
    }

    //////////////////////////// METHODS FOR LOADING BILLS /////////////////////////////

    public void loadBill(Storable s) {
        Bill bill = (Bill) s;
        if(this.billMap.containsKey(bill.getIssueDate())) {
            StorableList<Bill> oldBills = this.billMap.get(bill.getIssueDate());
            if (!oldBills.contains(bill)) {
                oldBills.add(bill);
                this.billMap.replace(bill.getIssueDate(), oldBills);
            }
        } else {
            StorableList<Bill> newBills = new StorableList<>();
            newBills.add(bill);
            this.billMap.put(bill.getIssueDate(), newBills);
        }

        if (bill.isStatus()) {
            loadPaid(bill);
        } else {
            loadIssued(bill);
        }
    }

    public void loadBills(String filePath, Storable s) {
        File[] fls = new File(filePath + "bills/").listFiles();

        for(File f : fls) {
            if (f.getName().equals("paid.csv") || f.getName().equals("issued.csv")) {
                continue;
            }

            CSVManager.INSTANCE.load(s, f.getAbsolutePath());

            if(s instanceof Bill) {
                loadBill(s);
                return;
            }

            StorableList<?> list = (StorableList<?>) s;
            for (Storable storable : list) {
                loadBill(storable);
            }
        }
    }



    private void loadIssued(Storable s) {
        Bill bill = (Bill) s;
        if (!issuedBills.contains(bill)) {
            issuedBills.add(bill);
        }
    }

    public void loadIssued(String filePath, Storable s) {
        CSVManager.INSTANCE.load(s, filePath + "bills/issued.csv");

        if(s instanceof Bill) {
            loadIssued(s);
            return;
        }
        
        StorableList<?> list = (StorableList<?>) s;
        for (Storable storable : list) {
            loadIssued(storable);
        }
    }   



    public void loadPaid(Storable s) {
        Bill bill = (Bill) s;
        if (!paidBills.contains(bill)) {
            paidBills.add(bill);
            bill.setStatus(false);
            issuedBills.remove(bill);
        }
    }

    public void loadPaid(String filePath, Storable s) {
        CSVManager.INSTANCE.load(s, filePath + "bills/paid.csv");

        if(s instanceof Bill) {
            loadPaid(s);
            return;
        }
        
        StorableList<?> list = (StorableList<?>) s;
        for (Storable storable : list) {
            loadPaid(storable);
        }
    }



    public StorableList<Bill> getPaidBills() {
        return paidBills;
    }
    public StorableList<Bill> getIssuedBills() {
        return issuedBills;
    }

    public List<Bill> getPendingBills(String vatNumber) {
        return issuedBills.stream()
                .filter(b -> b.getIssuer().equals(vatNumber))
                .toList();
    }

    public void payBill(Bill b) {
        if(!paidBills.contains(b) && issuedBills.contains(b)) {
            paidBills.add(b);
            issuedBills.remove(b);
            b.setStatus(true);
        }
    }

    public void printIssuedBills() {
        System.out.println("Issued Bills:");
        for (Bill bill : issuedBills) {
            System.out.println(bill.marshal());
        }
    }   

    public void printPaidBills() {
        System.out.println("Paid Bills:");
        for (Bill bill : paidBills) {
            System.out.println(bill.marshal());
        }
    }

    public void printIssuedBillsFor(String vatNumber) {
        System.out.println("Issued Bills for " + vatNumber + ":");
        for (Bill bill : issuedBills) {
            if (bill.getIssuer().equals(vatNumber)) {
                System.out.println(bill.marshal());
            }
        }
    }   

    public void printPaidBillsFor(String vatNumber) {
        System.out.println("Paid Bills for " + vatNumber + ":");
        for (Bill bill : paidBills) {
            if (bill.getIssuer().equals(vatNumber)) {
                System.out.println(bill.marshal());
            }
        }
    }



    public void loadAllBills(String filePath) {
        loadBills(filePath, new StorableList<>());
        loadIssued(filePath, new StorableList<>());
        loadPaid(filePath, new StorableList<>());
    }
}
