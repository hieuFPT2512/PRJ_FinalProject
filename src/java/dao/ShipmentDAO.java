package dao;

import model.Shipment;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO {

    public List<Shipment> getAll() {
        List<Shipment> list = new ArrayList<>();
        String sql = "SELECT s.*, c.customer_name, o.route FROM Shipments s " +
                     "JOIN DeliveryOrders o ON s.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id ORDER BY s.shipment_id DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Shipment getById(int id) {
        String sql = "SELECT s.*, c.customer_name, o.route FROM Shipments s " +
                     "JOIN DeliveryOrders o ON s.order_id=o.order_id " +
                     "JOIN Customers c ON o.customer_id=c.customer_id WHERE s.shipment_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Shipment s) {
        String sql = "INSERT INTO Shipments (order_id, driver_name, ship_date, status) VALUES (?,?,GETDATE(),?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getOrderId());
            ps.setString(2, s.getDriverName());
            ps.setString(3, s.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Shipment s) {
        String sql = "UPDATE Shipments SET driver_name=?, status=?, delivery_date=? WHERE shipment_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getDriverName());
            ps.setString(2, s.getStatus());
            ps.setTimestamp(3, s.getDeliveryDate());
            ps.setInt(4, s.getShipmentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Shipments WHERE shipment_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Shipment map(ResultSet rs) throws SQLException {
        Shipment s = new Shipment();
        s.setShipmentId(rs.getInt("shipment_id"));
        s.setOrderId(rs.getInt("order_id"));
        s.setDriverName(rs.getString("driver_name"));
        s.setShipDate(rs.getTimestamp("ship_date"));
        s.setDeliveryDate(rs.getTimestamp("delivery_date"));
        s.setStatus(rs.getString("status"));
        s.setCustomerName(rs.getString("customer_name"));
        s.setRoute(rs.getString("route"));
        return s;
    }
}
