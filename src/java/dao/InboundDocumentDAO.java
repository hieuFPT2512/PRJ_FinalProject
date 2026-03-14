package dao;

import model.InboundDocument;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InboundDocumentDAO {

    public List<InboundDocument> getAll() {
        List<InboundDocument> list = new ArrayList<>();
        String sql = "SELECT id.*, w.warehouse_name, c.customer_name FROM InboundDocuments id " +
                     "JOIN Warehouses w ON id.warehouse_id=w.warehouse_id " +
                     "JOIN DeliveryOrders o ON id.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY id.inbound_id ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public InboundDocument getById(int id) {
        String sql = "SELECT ind.*, w.warehouse_name, c.customer_name FROM InboundDocuments ind " +
                     "JOIN Warehouses w ON ind.warehouse_id=w.warehouse_id " +
                     "JOIN DeliveryOrders o ON ind.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE ind.inbound_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(InboundDocument ind) {
        String sql = "INSERT INTO InboundDocuments (order_id, warehouse_id, reason) VALUES (?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ind.getOrderId());
            ps.setInt(2, ind.getWarehouseId());
            ps.setString(3, ind.getReason());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(InboundDocument ind) {
        String sql = "UPDATE InboundDocuments SET reason=? WHERE inbound_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ind.getReason());
            ps.setInt(2, ind.getInboundId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private InboundDocument map(ResultSet rs) throws SQLException {
        InboundDocument ind = new InboundDocument();
        ind.setInboundId(rs.getInt("inbound_id"));
        ind.setOrderId(rs.getInt("order_id"));
        ind.setWarehouseId(rs.getInt("warehouse_id"));
        ind.setDocDate(rs.getTimestamp("doc_date"));
        ind.setReason(rs.getString("reason"));
        ind.setWarehouseName(rs.getString("warehouse_name"));
        ind.setCustomerName(rs.getString("customer_name"));
        return ind;
    }
}
