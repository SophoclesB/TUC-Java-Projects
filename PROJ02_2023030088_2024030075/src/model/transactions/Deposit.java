package model.transactions;

import model.accounts.BankAccount;

public class Deposit extends Transaction{
    private BankAccount creditAccount;
    private float amount;

    public Deposit(String code, String transactor, String reason, BankAccount creditAccount, float amount){
        super(TransactionType.Deposit, code, transactor, reason);
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    @Override
    public void execute(){
        
    }
}
