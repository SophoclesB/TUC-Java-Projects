package model.transactions;

import managers.AccountManager;
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
    public void execute() {
        var acc = AccountManager.getInstance()
            .getAccountsForVAT(transactorVat).stream()
            .filter(a -> a.getIban().equals(chargeAccount.getIban()))
            .findFirst();
        acc.ifPresent(a -> a.setBalance(a.getBalance() - money));
    }
}
