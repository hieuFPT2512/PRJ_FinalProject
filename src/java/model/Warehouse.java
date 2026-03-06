package model;

import java.sql.Timestamp;

public class Warehouse {
    private int warehouseId;
    private String warehouseName;
    private String location;
    private String manager;
    private Timestamp createdAt;

    public Warehouse() {}

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
