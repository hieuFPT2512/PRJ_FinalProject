package model;

import java.sql.Timestamp;

public class StockLedger {
    private int ledgerId;
    private int warehouseId;
    private int productId;
    private int refId;
    private String refType;
    private int qtyChange;
    private int balanceAfter;
    private Timestamp createdAt;
    // joined
    private String warehouseName;
    private String productName;
    private String sku;

    public StockLedger() {}

    public int getLedgerId() { return ledgerId; }
    public void setLedgerId(int ledgerId) { this.ledgerId = ledgerId; }
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getRefId() { return refId; }
    public void setRefId(int refId) { this.refId = refId; }
    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }
    public int getQtyChange() { return qtyChange; }
    public void setQtyChange(int qtyChange) { this.qtyChange = qtyChange; }
    public int getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(int balanceAfter) { this.balanceAfter = balanceAfter; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
}
