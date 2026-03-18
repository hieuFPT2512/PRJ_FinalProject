package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private int invoiceId;
    private BigDecimal amount;
    private Timestamp paidDate;
    private String method;
    // joined
    private int orderId;
    private String customerName;

    public Payment() {}

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Timestamp getPaidDate() { return paidDate; }
    public void setPaidDate(Timestamp paidDate) { this.paidDate = paidDate; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
