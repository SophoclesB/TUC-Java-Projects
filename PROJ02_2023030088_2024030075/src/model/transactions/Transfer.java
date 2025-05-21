package model.transactions;

import managers.AccountManager;
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
        var mgr = AccountManager.getInstance();
        mgr.getAccountsForVAT(transactorVat).stream()
            .filter(a -> a.getIban().equals(chargeAccount.getIban()))
            .findFirst()
            .ifPresent(a -> a.setBalance(a.getBalance() - amount));
        mgr.getAccountsForVAT(transactorVat).stream()
            .filter(a -> a.getIban().equals(creditAccount.getIban()))
            .findFirst()
            .ifPresent(a -> a.setBalance(a.getBalance() + amount));
    }
}
