package model.transactions;

public abstract class Transaction { 
    public enum TransactionType{Deposit,Withdrawal,Transfer,Payment}

    protected static String transactionCode;
    protected String transactorVat;
    protected TransactionType type;
    protected String description;
    protected String timeStamp;

    public Transaction(TransactionType type, String code, String transactor, String description, String timeStamp){
        this.transactionCode = code;
        this.transactorVat = transactor;
        this.description = description;
        this.timeStamp = timeStamp;
    }

    public abstract void execute();

}
