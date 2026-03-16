package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AlertEvent {
    private int alertId;
    private int orderId;
    private int ruleId;
    private Timestamp detectedAt;
    private String status;
    // joined
    private String ruleName;
    private String severity;
    private String customerName;

    public AlertEvent() {}

    public int getAlertId() { return alertId; }
    public void setAlertId(int alertId) { this.alertId = alertId; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getRuleId() { return ruleId; }
    public void setRuleId(int ruleId) { this.ruleId = ruleId; }
    public Timestamp getDetectedAt() { return detectedAt; }
    public void setDetectedAt(Timestamp detectedAt) { this.detectedAt = detectedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
