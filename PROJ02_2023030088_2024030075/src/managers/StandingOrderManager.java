package managers;

import java.time.LocalDate;

import model.orders.StandingOrder;
import storage.CSVManager;
import storage.Storable;
import storage.StorableList;

public class StandingOrderManager {
    private static final StandingOrderManager INSTANCE = new StandingOrderManager();
    private final StorableList<StandingOrder> activeOrders = new StorableList<>();
    private final StorableList<StandingOrder> expiredOrders = new StorableList<>();
    private final StorableList<StandingOrder> failedOrders = new StorableList<>();

    private StandingOrderManager() {}

    public static StandingOrderManager getInstance() { return INSTANCE; }

    public void saveAllOrders(String filePath, boolean append) {
        CSVManager.INSTANCE.save(activeOrders, filePath + "orders/active.csv", append);
        CSVManager.INSTANCE.save(expiredOrders, filePath + "orders/expired.csv", append);
        CSVManager.INSTANCE.save(failedOrders, filePath + "orders/failed.csv", append);
    }

    public void loadAllOrders(String filePath) {

    }



    public void loadFailed(String filePath, Storable s) {
        CSVManager.INSTANCE.load(failedOrders, filePath + "orders/failed.csv");

        if(s instanceof StandingOrder) {
            loadFailed(s);
            return;
        }

        StorableList<?> list = (StorableList<?>) s;
        for(Storable standingOrder : list) {
            loadFailed(standingOrder);
        }
    }

    public void loadFailed(Storable s) {
        StandingOrder order = (StandingOrder) s;
        if(!failedOrders.contains(order)) {
            failedOrders.add(order);
        }
    }

    public void loadExpired(String filePath, Storable s) {
        CSVManager.INSTANCE.load(expiredOrders, filePath + "orders/expired.csv");

        if(s instanceof StandingOrder) {
            loadExpired(s);
            return;
        }

        StorableList<?> list = (StorableList<?>) s;
        for(Storable standingOrder : list) {
            loadExpired(standingOrder);
        }
    }

    public void loadExpired(Storable s) {
        StandingOrder order = (StandingOrder) s;
        if(!expiredOrders.contains(order)) {
            expiredOrders.add(order);
        }
    }

    public void loadActive(String filePath, Storable s) {
        CSVManager.INSTANCE.load(activeOrders, filePath + "orders/active.csv");

        if(s instanceof StandingOrder) {
            loadActive(s);
            return;
        }

        StorableList<?> list = (StorableList<?>) s;
        for(Storable standingOrder : list) {
            loadActive(standingOrder);
        }
    }

    public void loadActive(Storable s) {
        StandingOrder order = (StandingOrder) s;
        if(!activeOrders.contains(order)) {
            activeOrders.add(order);
        }
    }

    public void printAllOrders() {
        System.out.println("Active Orders:");
        for(StandingOrder order : activeOrders) {
            System.out.println(order.marshal());
        }
        System.out.println("Expired Orders:");
        for(StandingOrder order : expiredOrders) {
            System.out.println(order.marshal());
        }
        System.out.println("Failed Orders:");
        for(StandingOrder order : failedOrders) {
            System.out.println(order.marshal());
        }
    }

    public void executeDue(LocalDate today) {
        for(StandingOrder order : activeOrders) {
            try {
                if(order.getEndDate().isBefore(today)) {
                    expiredOrders.add(order);
                    activeOrders.remove(order);
                } else if(order.getStartDate().isEqual(today)) {
                    order.process(today);
                } else {
                    continue;
                }
            } catch (Exception e) {
                failedOrders.add(order);
                activeOrders.remove(order);
            }
        }
    }
    
}
