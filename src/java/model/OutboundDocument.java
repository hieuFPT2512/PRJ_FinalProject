package model;

import java.sql.Timestamp;

public class OutboundDocument {
    private int outboundId;
    private int orderId;
    private int warehouseId;
    private Timestamp docDate;
    private String status;
    // joined
    private String warehouseName;
    private String customerName;

    public OutboundDocument() {}

    public int getOutboundId() { return outboundId; }
    public void setOutboundId(int outboundId) { this.outboundId = outboundId; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public Timestamp getDocDate() { return docDate; }
    public void setDocDate(Timestamp docDate) { this.docDate = docDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
