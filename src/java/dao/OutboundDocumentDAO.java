package dao;

import model.OutboundDocument;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutboundDocumentDAO {

    public List<OutboundDocument> getAll() {
        List<OutboundDocument> list = new ArrayList<>();
        String sql = "SELECT od.*, w.warehouse_name, c.customer_name FROM OutboundDocuments od " +
                     "JOIN Warehouses w ON od.warehouse_id=w.warehouse_id " +
                     "JOIN DeliveryOrders o ON od.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY od.outbound_id ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public OutboundDocument getById(int id) {
        String sql = "SELECT od.*, w.warehouse_name, c.customer_name FROM OutboundDocuments od " +
                     "JOIN Warehouses w ON od.warehouse_id=w.warehouse_id " +
                     "JOIN DeliveryOrders o ON od.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE od.outbound_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<OutboundDocument> search(String fromDate, String toDate, String warehouseId, String status) {
        List<OutboundDocument> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT od.*, w.warehouse_name, c.customer_name FROM OutboundDocuments od " +
            "JOIN Warehouses w ON od.warehouse_id=w.warehouse_id " +
            "JOIN DeliveryOrders o ON od.order_id=o.order_id " +
            "JOIN Customers c ON o.customer_id=c.customer_id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (fromDate != null && !fromDate.isEmpty()) { sql.append(" AND od.doc_date >= ?"); params.add(fromDate + " 00:00:00"); }
        if (toDate != null && !toDate.isEmpty())     { sql.append(" AND od.doc_date <= ?"); params.add(toDate + " 23:59:59"); }
        if (warehouseId != null && !warehouseId.isEmpty()) { sql.append(" AND od.warehouse_id=?"); params.add(Integer.parseInt(warehouseId)); }
        if (status != null && !status.isEmpty())     { sql.append(" AND od.status=?"); params.add(status); }
        sql.append(" ORDER BY od.doc_date ASC");
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

    public boolean insert(OutboundDocument od) {
        String sql = "INSERT INTO OutboundDocuments (order_id, warehouse_id, status) VALUES (?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, od.getOrderId());
            ps.setInt(2, od.getWarehouseId());
            ps.setString(3, od.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(OutboundDocument od) {
        String sql = "UPDATE OutboundDocuments SET status=? WHERE outbound_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, od.getStatus());
            ps.setInt(2, od.getOutboundId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private OutboundDocument map(ResultSet rs) throws SQLException {
        OutboundDocument od = new OutboundDocument();
        od.setOutboundId(rs.getInt("outbound_id"));
        od.setOrderId(rs.getInt("order_id"));
        od.setWarehouseId(rs.getInt("warehouse_id"));
        od.setDocDate(rs.getTimestamp("doc_date"));
        od.setStatus(rs.getString("status"));
        od.setWarehouseName(rs.getString("warehouse_name"));
        od.setCustomerName(rs.getString("customer_name"));
        return od;
    }
}
