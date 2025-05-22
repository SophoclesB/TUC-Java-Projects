package model.transactions;

import java.util.List;

import managers.BillManager;
import model.bills.Bill;

public class Payment extends Transaction{
    private String billRf;
    private float amount;

    public Payment(String code, String transactor, String description, String timeStamp, String billRf, float amount){
        super(TransactionType.Payment, code, transactor, description, timeStamp);
        this.billRf = billRf;
        this.amount = amount;
    }

    @Override
    public void execute() {
        BillManager mgr = BillManager.getInstance();
        List<Bill> issued = mgr.getIssuedBills();
        Bill b = issued.stream()
            .filter(x -> x.getRf().equals(billRf))
            .findFirst().orElse(null);
        if (b != null) {
            mgr.payBill(b);
        }
    }
}
