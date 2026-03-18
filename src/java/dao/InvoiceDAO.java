package dao;

import model.Invoice;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT i.*, c.customer_name FROM Invoices i " +
                     "JOIN DeliveryOrders o ON i.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY i.invoice_id ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Invoice getById(int id) {
        String sql = "SELECT i.*, c.customer_name FROM Invoices i " +
                     "JOIN DeliveryOrders o ON i.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE i.invoice_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Invoice> search(String fromDate, String toDate, String status, String orderId) {
        List<Invoice> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT i.*, c.customer_name FROM Invoices i " +
            "JOIN DeliveryOrders o ON i.order_id=o.order_id " +
            "JOIN Customers c ON o.customer_id=c.customer_id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (fromDate != null && !fromDate.isEmpty()) { sql.append(" AND i.issue_date>=?"); params.add(fromDate + " 00:00:00"); }
        if (toDate != null && !toDate.isEmpty())     { sql.append(" AND i.issue_date<=?"); params.add(toDate + " 23:59:59"); }
        if (status != null && !status.isEmpty())     { sql.append(" AND i.status=?");      params.add(status); }
        if (orderId != null && !orderId.isEmpty())   { sql.append(" AND i.order_id=?");    params.add(Integer.parseInt(orderId)); }
        sql.append(" ORDER BY i.issue_date ASC");
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) ps.setInt(i + 1, (Integer) params.get(i));
                else ps.setString(i + 1, (String) params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Invoice inv) {
        String sql = "INSERT INTO Invoices (order_id, total_amount, status) VALUES (?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inv.getOrderId());
            ps.setBigDecimal(2, inv.getTotalAmount());
            ps.setString(3, inv.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Invoice inv) {
        String sql = "UPDATE Invoices SET status=? WHERE invoice_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, inv.getStatus());
            ps.setInt(2, inv.getInvoiceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Invoice map(ResultSet rs) throws SQLException {
        Invoice i = new Invoice();
        i.setInvoiceId(rs.getInt("invoice_id"));
        i.setOrderId(rs.getInt("order_id"));
        i.setIssueDate(rs.getTimestamp("issue_date"));
        i.setTotalAmount(rs.getBigDecimal("total_amount"));
        i.setStatus(rs.getString("status"));
        i.setCustomerName(rs.getString("customer_name"));
        return i;
    }
}
