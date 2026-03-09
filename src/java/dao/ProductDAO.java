package dao;

import model.Product;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Products ORDER BY product_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Product getById(int id) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Products WHERE product_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Product p) {
        String sql = "INSERT INTO Products (sku, product_name, category, unit, price) VALUES (?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getSku());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getUnit());
            ps.setBigDecimal(5, p.getPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Product p) {
        String sql = "UPDATE Products SET sku=?, product_name=?, category=?, unit=?, price=? WHERE product_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getSku());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getUnit());
            ps.setBigDecimal(5, p.getPrice());
            ps.setInt(6, p.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Products WHERE product_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Product map(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setSku(rs.getString("sku"));
        p.setProductName(rs.getString("product_name"));
        p.setCategory(rs.getString("category"));
        p.setUnit(rs.getString("unit"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}
