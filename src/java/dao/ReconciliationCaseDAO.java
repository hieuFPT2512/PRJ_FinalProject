package dao;

import model.ReconciliationCase;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReconciliationCaseDAO {

    public List<ReconciliationCase> getAll() {
        List<ReconciliationCase> list = new ArrayList<>();
        String sql = "SELECT rc.*, u.full_name AS assigned_name, c.customer_name, ar.rule_name " +
                     "FROM ReconciliationCases rc " +
                     "JOIN Users u ON rc.assigned_to=u.user_id " +
                     "JOIN DeliveryOrders o ON rc.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id " +
                     "JOIN AlertEvents ae ON rc.alert_id=ae.alert_id " +
                     "JOIN AlertRules ar ON ae.rule_id=ar.rule_id ORDER BY rc.case_id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public ReconciliationCase getById(int id) {
        String sql = "SELECT rc.*, u.full_name AS assigned_name, c.customer_name, ar.rule_name " +
                     "FROM ReconciliationCases rc " +
                     "JOIN Users u ON rc.assigned_to=u.user_id " +
                     "JOIN DeliveryOrders o ON rc.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id " +
                     "JOIN AlertEvents ae ON rc.alert_id=ae.alert_id " +
                     "JOIN AlertRules ar ON ae.rule_id=ar.rule_id WHERE rc.case_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStatus(int caseId, String status) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE ReconciliationCases SET status=? WHERE case_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, caseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean assignCase(int caseId, int userId) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE ReconciliationCases SET assigned_to=? WHERE case_id=?")) {
            ps.setInt(1, userId);
            ps.setInt(2, caseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private ReconciliationCase map(ResultSet rs) throws SQLException {
        ReconciliationCase rc = new ReconciliationCase();
        rc.setCaseId(rs.getInt("case_id"));
        rc.setOrderId(rs.getInt("order_id"));
        rc.setAlertId(rs.getInt("alert_id"));
        rc.setAssignedTo(rs.getInt("assigned_to"));
        rc.setIssueType(rs.getString("issue_type"));
        rc.setStatus(rs.getString("status"));
        rc.setAssignedName(rs.getString("assigned_name"));
        rc.setCustomerName(rs.getString("customer_name"));
        rc.setRuleName(rs.getString("rule_name"));
        return rc;
    }
}
