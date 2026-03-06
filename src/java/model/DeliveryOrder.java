package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class DeliveryOrder {
    private int orderId;
    private int customerId;
    private int warehouseId;
    private Timestamp orderDate;
    private String route;
    private String hasCod;
    private BigDecimal totalAmount;
    private String status;
    // joined fields
    private String customerName;
    private String warehouseName;
    private List<OrderItem> items;

    public DeliveryOrder() {}

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    public String getHasCod() { return hasCod; }
    public void setHasCod(String hasCod) { this.hasCod = hasCod; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
