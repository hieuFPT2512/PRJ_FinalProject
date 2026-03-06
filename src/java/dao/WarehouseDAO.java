package dao;

import model.Warehouse;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO {

    public List<Warehouse> getAll() {
        List<Warehouse> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Warehouses ORDER BY warehouse_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Warehouse getById(int id) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Warehouses WHERE warehouse_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Warehouse w) {
        String sql = "INSERT INTO Warehouses (warehouse_name, location, manager) VALUES (?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, w.getWarehouseName());
            ps.setString(2, w.getLocation());
            ps.setString(3, w.getManager());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Warehouse w) {
        String sql = "UPDATE Warehouses SET warehouse_name=?, location=?, manager=? WHERE warehouse_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, w.getWarehouseName());
            ps.setString(2, w.getLocation());
            ps.setString(3, w.getManager());
            ps.setInt(4, w.getWarehouseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Warehouses WHERE warehouse_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Warehouse map(ResultSet rs) throws SQLException {
        Warehouse w = new Warehouse();
        w.setWarehouseId(rs.getInt("warehouse_id"));
        w.setWarehouseName(rs.getString("warehouse_name"));
        w.setLocation(rs.getString("location"));
        w.setManager(rs.getString("manager"));
        w.setCreatedAt(rs.getTimestamp("created_at"));
        return w;
    }
}
