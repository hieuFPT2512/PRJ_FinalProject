package dao;

import model.ProofOfDelivery;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProofOfDeliveryDAO {

    public List<ProofOfDelivery> getAll() {
        List<ProofOfDelivery> list = new ArrayList<>();
        String sql = "SELECT p.*, s.driver_name, s.order_id FROM ProofOfDeliveries p " +
                     "JOIN Shipments s ON p.shipment_id=s.shipment_id ORDER BY p.pod_id ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public ProofOfDelivery getById(int id) {
        String sql = "SELECT p.*, s.driver_name, s.order_id FROM ProofOfDeliveries p " +
                     "JOIN Shipments s ON p.shipment_id=s.shipment_id WHERE p.pod_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(ProofOfDelivery pod) {
        String sql = "INSERT INTO ProofOfDeliveries (shipment_id, image_url, signed_by, status) VALUES (?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pod.getShipmentId());
            ps.setString(2, pod.getImageUrl());
            ps.setString(3, pod.getSignedBy());
            ps.setString(4, pod.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(ProofOfDelivery pod) {
        String sql = "UPDATE ProofOfDeliveries SET signed_by=?, received_at=GETDATE(), status=? WHERE pod_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pod.getSignedBy());
            ps.setString(2, pod.getStatus());
            ps.setInt(3, pod.getPodId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private ProofOfDelivery map(ResultSet rs) throws SQLException {
        ProofOfDelivery p = new ProofOfDelivery();
        p.setPodId(rs.getInt("pod_id"));
        p.setShipmentId(rs.getInt("shipment_id"));
        p.setImageUrl(rs.getString("image_url"));
        p.setSignedBy(rs.getString("signed_by"));
        p.setReceivedAt(rs.getTimestamp("received_at"));
        p.setStatus(rs.getString("status"));
        p.setDriverName(rs.getString("driver_name"));
        p.setOrderId(rs.getInt("order_id"));
        return p;
    }
}
