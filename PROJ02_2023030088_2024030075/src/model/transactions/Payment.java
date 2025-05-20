package model.transactions;


public class Payment extends Transaction{
    private String billRf;
    private float amount;

    public Payment(String code, String transactor, String reason, String billRf, float amount){
        super(TransactionType.Payment, code, transactor, reason);
        this.billRf = billRf;
        this.amount = amount;
    }

    @Override
    public void execute(){

    }
}
