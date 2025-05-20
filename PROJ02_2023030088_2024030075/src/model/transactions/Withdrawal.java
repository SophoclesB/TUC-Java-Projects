package model.transactions;

import model.accounts.BankAccount;

public class Withdrawal extends Transaction{
    private BankAccount chargeAccount;
    private float money;

    public Withdrawal(String code, String transactor, String reason, BankAccount chargeAccount, float money){
        super(TransactionType.Withdrawal, code, transactor, reason);
        this.chargeAccount = chargeAccount;
        this.money = money;
    }

    @Override
    public void execute(){

    }
}
