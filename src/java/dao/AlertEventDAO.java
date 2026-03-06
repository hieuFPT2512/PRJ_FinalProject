package dao;

import model.AlertEvent;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertEventDAO {

    public List<AlertEvent> getAll() {
        List<AlertEvent> list = new ArrayList<>();
        String sql = "SELECT ae.*, ar.rule_name, ar.severity, c.customer_name FROM AlertEvents ae " +
                     "JOIN AlertRules ar ON ae.rule_id=ar.rule_id " +
                     "JOIN DeliveryOrders o ON ae.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY ae.risk_score DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<AlertEvent> getOpen() {
        List<AlertEvent> list = new ArrayList<>();
        String sql = "SELECT ae.*, ar.rule_name, ar.severity, c.customer_name FROM AlertEvents ae " +
                     "JOIN AlertRules ar ON ae.rule_id=ar.rule_id " +
                     "JOIN DeliveryOrders o ON ae.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE ae.status='Open' ORDER BY ae.risk_score DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public AlertEvent getById(int id) {
        String sql = "SELECT ae.*, ar.rule_name, ar.severity, c.customer_name FROM AlertEvents ae " +
                     "JOIN AlertRules ar ON ae.rule_id=ar.rule_id " +
                     "JOIN DeliveryOrders o ON ae.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE ae.alert_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStatus(int alertId, String status) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE AlertEvents SET status=? WHERE alert_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, alertId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private AlertEvent map(ResultSet rs) throws SQLException {
        AlertEvent ae = new AlertEvent();
        ae.setAlertId(rs.getInt("alert_id"));
        ae.setOrderId(rs.getInt("order_id"));
        ae.setRuleId(rs.getInt("rule_id"));
        ae.setDetectedAt(rs.getTimestamp("detected_at"));
        ae.setRiskScore(rs.getBigDecimal("risk_score"));
        ae.setStatus(rs.getString("status"));
        ae.setRuleName(rs.getString("rule_name"));
        ae.setSeverity(rs.getString("severity"));
        ae.setCustomerName(rs.getString("customer_name"));
        return ae;
    }
}
