package model;

import java.sql.Timestamp;

public class ProofOfDelivery {
    private int podId;
    private int shipmentId;
    private String imageUrl;
    private String signedBy;
    private Timestamp receivedAt;
    private String status;
    // joined
    private String driverName;
    private int orderId;

    public ProofOfDelivery() {}

    public int getPodId() { return podId; }
    public void setPodId(int podId) { this.podId = podId; }
    public int getShipmentId() { return shipmentId; }
    public void setShipmentId(int shipmentId) { this.shipmentId = shipmentId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getSignedBy() { return signedBy; }
    public void setSignedBy(String signedBy) { this.signedBy = signedBy; }
    public Timestamp getReceivedAt() { return receivedAt; }
    public void setReceivedAt(Timestamp receivedAt) { this.receivedAt = receivedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
}
