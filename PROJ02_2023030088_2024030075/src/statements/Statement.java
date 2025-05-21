package statements;
import storage.Storable;

public class Statement implements Storable{
    public enum StatementType {Debit,Credit}

    private StatementType type;
    private String iban;
    private String time;
    private String description;
    private float amount;
    private float currBalance;

    public Statement() {}
    public Statement(StatementType type, String iban, String time, String description, float amount, float currBalance) {
        this.type = type;
        this.iban = iban;
        this.time = time;
        this.description = description;
        this.amount = amount;
        this.currBalance = currBalance;
    }

    @Override
    public String marshal() {
            return String.join(",",
                    "type:" + type.toString(),
                    "iban:" + getIban(),
                    "time:" + getTime(),
                    "description:" + getDescription(),
                    "amount:" + getAmount(),
                    "currentBalance" + getCurrBalance()
            );
    }

    @Override
    public void unmarshal(String data) {
        String[] parts = data.split(",");
        for (String part : parts){
            String[] kv = part.split(":");
            String key = kv[0];
            String value = kv[1];

            switch (key) {
                case "type":            this.type = StatementType.valueOf(value); break;
                case "iban":            this.iban = value; break;
                case "time":            this.time = value; break;
                case "description":     this.description = value; break;
                case "amount":          this.amount = Float.parseFloat(value); break;
                case "currentBalance":  this.currBalance = Float.parseFloat(value); break;
            }
        }
    }
    public StatementType getType() {return type;}
    public String getIban() {return iban;}
    public String getTime() {return time;}
    public String getDescription() {return description;}
    public float getAmount() {return amount;}
    public float getCurrBalance() {return currBalance;}

    
}
