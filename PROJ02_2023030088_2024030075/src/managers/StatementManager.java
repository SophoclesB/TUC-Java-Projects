package managers;

import statements.Statement;

import java.io.File;

import model.orders.StandingOrder;
import storage.CSVManager;
import storage.Storable;
import storage.StorableList;
import storage.StorableMap;

public class StatementManager {
    private static final StatementManager INSTANCE = new StatementManager();
    private static final StorableMap<String, StorableList<Statement>> statementMap = new StorableMap<>();

    private StatementManager() {}

    public static StatementManager getInstance() { return INSTANCE; }

    public void saveStatements(String filePath, boolean append) {
        for(String key : statementMap.keySet()) {
            CSVManager.INSTANCE.save(statementMap.get(key), filePath + "statements/" + "iban" + key + ".csv", append);
        }
    }

    public void loadStatements(String filePath, Storable s) {
        File[] fls = new File(filePath + "statements/").listFiles();

        for (File file : fls) {
            CSVManager.INSTANCE.load(s, file.getPath());
           
            if (s instanceof Statement) {
                loadStatement(s);
                return;
            }
    
            StorableList<?> list = (StorableList<?>) s;
            for (Storable statement : list) {
                loadStatement(statement);
            }
        }
    }

    public void loadStatement(Storable s) {
        Statement order = (Statement) s;
        if(statementMap.containsKey(order.getIban())) {
            StorableList<Statement> oldOrders = statementMap.get(order.getIban());
            if (!oldOrders.contains(order)) {
                oldOrders.add(order);
                statementMap.replace(order.getIban(), oldOrders);
            }
        } else {
            StorableList<Statement> newOrders = new StorableList<>();
            newOrders.add(order);
            statementMap.put(order.getIban(), newOrders);
        }
    }

    public void printStatementsForIban(String iban) {
        if(statementMap.containsKey(iban)) {
            StorableList<Statement> statements = statementMap.get(iban);
            for (Statement statement : statements) {
                System.out.println(statement);
            }
        } else {
            System.out.println("No statements found for the given IBAN.");
        }
    }
}
