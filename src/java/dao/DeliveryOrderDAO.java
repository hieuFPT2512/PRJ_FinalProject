package dao;

import model.DeliveryOrder;
import model.OrderItem;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderDAO {

    public List<DeliveryOrder> getAll() {
        List<DeliveryOrder> list = new ArrayList<>();
        String sql = "SELECT o.*, c.customer_name, w.warehouse_name FROM DeliveryOrders o " +
                     "JOIN Customers c ON o.customer_id=c.customer_id " +
                     "JOIN Warehouses w ON o.warehouse_id=w.warehouse_id ORDER BY o.order_id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public DeliveryOrder getById(int id) {
        String sql = "SELECT o.*, c.customer_name, w.warehouse_name FROM DeliveryOrders o " +
                     "JOIN Customers c ON o.customer_id=c.customer_id " +
                     "JOIN Warehouses w ON o.warehouse_id=w.warehouse_id WHERE o.order_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DeliveryOrder o = map(rs);
                o.setItems(getItemsByOrderId(id));
                return o;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<DeliveryOrder> search(String fromDate, String toDate, String status, String warehouseId,
                                       String customerId, String route, String hasCod) {
        List<DeliveryOrder> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT o.*, c.customer_name, w.warehouse_name FROM DeliveryOrders o " +
            "JOIN Customers c ON o.customer_id=c.customer_id " +
            "JOIN Warehouses w ON o.warehouse_id=w.warehouse_id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (fromDate != null && !fromDate.isEmpty()) { sql.append(" AND o.order_date >= ?"); params.add(fromDate + " 00:00:00"); }
        if (toDate != null && !toDate.isEmpty())   { sql.append(" AND o.order_date <= ?"); params.add(toDate + " 23:59:59"); }
        if (status != null && !status.isEmpty())   { sql.append(" AND o.status = ?"); params.add(status); }
        if (warehouseId != null && !warehouseId.isEmpty()) { sql.append(" AND o.warehouse_id = ?"); params.add(Integer.parseInt(warehouseId)); }
        if (customerId != null && !customerId.isEmpty())   { sql.append(" AND o.customer_id = ?");  params.add(Integer.parseInt(customerId)); }
        if (route != null && !route.isEmpty())     { sql.append(" AND o.route LIKE ?"); params.add("%" + route + "%"); }
        if (hasCod != null && !hasCod.isEmpty())   { sql.append(" AND o.has_cod = ?"); params.add(hasCod); }
        sql.append(" ORDER BY o.order_date DESC");
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

    public int insert(DeliveryOrder o) {
        String sql = "INSERT INTO DeliveryOrders (customer_id, warehouse_id, route, has_cod, total_amount, status) " +
                     "VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, o.getCustomerId());
            ps.setInt(2, o.getWarehouseId());
            ps.setString(3, o.getRoute());
            ps.setString(4, o.getHasCod());
            ps.setBigDecimal(5, o.getTotalAmount());
            ps.setString(6, o.getStatus());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public boolean update(DeliveryOrder o) {
        String sql = "UPDATE DeliveryOrders SET customer_id=?, warehouse_id=?, route=?, has_cod=?, total_amount=?, status=? WHERE order_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getCustomerId());
            ps.setInt(2, o.getWarehouseId());
            ps.setString(3, o.getRoute());
            ps.setString(4, o.getHasCod());
            ps.setBigDecimal(5, o.getTotalAmount());
            ps.setString(6, o.getStatus());
            ps.setInt(7, o.getOrderId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM DeliveryOrders WHERE order_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, p.product_name, p.sku FROM OrderItems oi " +
                     "JOIN Products p ON oi.product_id=p.product_id WHERE oi.order_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQty(rs.getInt("qty"));
                item.setUnitPrice(rs.getBigDecimal("unit_price"));
                item.setSubtotal(rs.getBigDecimal("subtotal"));
                item.setProductName(rs.getString("product_name"));
                item.setSku(rs.getString("sku"));
                items.add(item);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    public boolean insertItem(OrderItem item) {
        String sql = "INSERT INTO OrderItems (order_id, product_id, qty, unit_price) VALUES (?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQty());
            ps.setBigDecimal(4, item.getUnitPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private DeliveryOrder map(ResultSet rs) throws SQLException {
        DeliveryOrder o = new DeliveryOrder();
        o.setOrderId(rs.getInt("order_id"));
        o.setCustomerId(rs.getInt("customer_id"));
        o.setWarehouseId(rs.getInt("warehouse_id"));
        o.setOrderDate(rs.getTimestamp("order_date"));
        o.setRoute(rs.getString("route"));
        o.setHasCod(rs.getString("has_cod"));
        o.setTotalAmount(rs.getBigDecimal("total_amount"));
        o.setStatus(rs.getString("status"));
        o.setCustomerName(rs.getString("customer_name"));
        o.setWarehouseName(rs.getString("warehouse_name"));
        return o;
    }
}
