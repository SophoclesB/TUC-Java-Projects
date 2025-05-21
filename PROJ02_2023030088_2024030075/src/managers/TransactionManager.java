package managers;

import java.util.ArrayList;
import java.util.List;

import model.transactions.Transaction;

public class TransactionManager {
    private static final TransactionManager INSTANCE = new TransactionManager();
    private final List<Transaction> transactions = new ArrayList<>();


    private TransactionManager() {}
    public static TransactionManager getInstance() { return INSTANCE; }

    public void executeTransaction(Transaction transaction) {
        transaction.execute();
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
