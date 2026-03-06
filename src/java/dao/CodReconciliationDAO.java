package dao;

import model.CodReconciliation;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodReconciliationDAO {

    public List<CodReconciliation> getAll() {
        List<CodReconciliation> list = new ArrayList<>();
        String sql = "SELECT cr.*, c.customer_name FROM CodReconciliations cr " +
                     "JOIN DeliveryOrders o ON cr.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY cr.cod_id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<CodReconciliation> getMismatches() {
        List<CodReconciliation> list = new ArrayList<>();
        String sql = "SELECT cr.*, c.customer_name FROM CodReconciliations cr " +
                     "JOIN DeliveryOrders o ON cr.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE cr.status='Mismatch' ORDER BY cr.mismatch_amount DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean update(CodReconciliation cr) {
        String sql = "UPDATE CodReconciliations SET received_cod=?, status=? WHERE cod_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, cr.getReceivedCod());
            ps.setString(2, cr.getStatus());
            ps.setInt(3, cr.getCodId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private CodReconciliation map(ResultSet rs) throws SQLException {
        CodReconciliation cr = new CodReconciliation();
        cr.setCodId(rs.getInt("cod_id"));
        cr.setOrderId(rs.getInt("order_id"));
        cr.setExpectedCod(rs.getBigDecimal("expected_cod"));
        cr.setReceivedCod(rs.getBigDecimal("received_cod"));
        cr.setMismatchAmount(rs.getBigDecimal("mismatch_amount"));
        cr.setStatus(rs.getString("status"));
        cr.setCustomerName(rs.getString("customer_name"));
        return cr;
    }
}
