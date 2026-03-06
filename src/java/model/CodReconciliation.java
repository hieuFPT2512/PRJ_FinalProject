package model;

import java.math.BigDecimal;

public class CodReconciliation {
    private int codId;
    private int orderId;
    private BigDecimal expectedCod;
    private BigDecimal receivedCod;
    private BigDecimal mismatchAmount;
    private String status;
    // joined
    private String customerName;

    public CodReconciliation() {}

    public int getCodId() { return codId; }
    public void setCodId(int codId) { this.codId = codId; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public BigDecimal getExpectedCod() { return expectedCod; }
    public void setExpectedCod(BigDecimal expectedCod) { this.expectedCod = expectedCod; }
    public BigDecimal getReceivedCod() { return receivedCod; }
    public void setReceivedCod(BigDecimal receivedCod) { this.receivedCod = receivedCod; }
    public BigDecimal getMismatchAmount() { return mismatchAmount; }
    public void setMismatchAmount(BigDecimal mismatchAmount) { this.mismatchAmount = mismatchAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
