package model.transactions;

import model.accounts.BankAccount;

public class Transfer extends Transaction{
    private BankAccount chargeAccount;
    private BankAccount creditAccount;
    private final float amount;

    public Transfer(String code, String transactor, String reason, BankAccount chargeAccount, BankAccount creditAccount, float amount){
        super(TransactionType.Transfer, code, transactor, reason);
        this.chargeAccount = chargeAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    @Override
    public void execute() {

    }
}
