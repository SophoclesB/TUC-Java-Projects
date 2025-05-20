package model.orders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import storage.Storable;

public abstract class StandingOrder implements Storable {
    public enum OrderType {TransferOrder, PaymentOrder}

    protected OrderType type;
    protected String orderId;
    protected String title;
    protected String description;
    protected String customerVat;
    protected LocalDate startDate;
    protected LocalDate endDate;

    protected static final DateTimeFormatter format = DateTimeFormatter.ISO_DATE;

    public StandingOrder() {};

    @Override
    public String marshal() {
        return String.join(",",
                "type:" + type.toString(),
                "orderId:" + getOrderId()
        );
    }

    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public String getOrderId() {return orderId;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getCustomerVat() {return customerVat;}

    public abstract void process(LocalDate date) throws Exception;
}