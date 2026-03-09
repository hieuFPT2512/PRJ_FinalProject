package dao;

import model.Payment;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public List<Payment> getAll() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT p.*, i.order_id, c.customer_name FROM Payments p " +
                     "JOIN Invoices i ON p.invoice_id=i.invoice_id " +
                     "JOIN DeliveryOrders o ON i.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY p.payment_id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Payment pay) {
        String sql = "INSERT INTO Payments (invoice_id, amount, paid_date, method) VALUES (?,?,GETDATE(),?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pay.getInvoiceId());
            ps.setBigDecimal(2, pay.getAmount());
            ps.setString(3, pay.getMethod());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Payment map(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setPaymentId(rs.getInt("payment_id"));
        p.setInvoiceId(rs.getInt("invoice_id"));
        p.setAmount(rs.getBigDecimal("amount"));
        p.setPaidDate(rs.getTimestamp("paid_date"));
        p.setMethod(rs.getString("method"));
        p.setOrderId(rs.getInt("order_id"));
        p.setCustomerName(rs.getString("customer_name"));
        return p;
    }
}
