package model.transactions;

import managers.AccountManager;
import model.accounts.BankAccount;

public class Deposit extends Transaction{
    private BankAccount creditAccount;
    private float amount;

    public Deposit(String code, String transactor, String description, String timeStamp, BankAccount creditAccount, float amount){
        super(TransactionType.Deposit, code, transactor, description, timeStamp);
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    @Override
    public void execute() {
        var acc = AccountManager.getInstance()
            .getAccountsForVAT(transactorVat).stream()
            .filter(a -> a.getIban().equals(creditAccount.getIban()))
            .findFirst();
        acc.ifPresent(a -> a.setBalance(a.getBalance() + amount));
    }
}
