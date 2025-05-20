package model.transactions;

public abstract class Transaction { 
    public enum TransactionType{Deposit,Withdrawal,Transfer,Payment}

    protected static String transactionCode;
    protected String transactorVat;
    protected TransactionType type;
    protected String description;

    public Transaction(TransactionType type, String code, String transactor, String description){
        this.transactionCode = code;
        this.transactorVat = transactor;
        this.description = description;
    }

    public abstract void execute();

}
