package dao;

import model.StockLedger;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockLedgerDAO {

    public List<StockLedger> getAll() {
        List<StockLedger> list = new ArrayList<>();
        String sql = "SELECT sl.*, w.warehouse_name, p.product_name, p.sku FROM StockLedgers sl " +
                     "JOIN Warehouses w ON sl.warehouse_id=w.warehouse_id " +
                     "JOIN Products p ON sl.product_id=p.product_id " +
                     "ORDER BY sl.ledger_id ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<StockLedger> search(String warehouseId, String productId, String refType, String fromDate, String toDate) {
        List<StockLedger> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT sl.*, w.warehouse_name, p.product_name, p.sku FROM StockLedgers sl " +
            "JOIN Warehouses w ON sl.warehouse_id=w.warehouse_id " +
            "JOIN Products p ON sl.product_id=p.product_id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (warehouseId != null && !warehouseId.isEmpty()) { sql.append(" AND sl.warehouse_id=?"); params.add(Integer.parseInt(warehouseId)); }
        if (productId != null && !productId.isEmpty())     { sql.append(" AND sl.product_id=?");   params.add(Integer.parseInt(productId)); }
        if (refType != null && !refType.isEmpty())         { sql.append(" AND sl.ref_type=?");      params.add(refType); }
        if (fromDate != null && !fromDate.isEmpty())       { sql.append(" AND sl.created_at>=?");   params.add(fromDate + " 00:00:00"); }
        if (toDate != null && !toDate.isEmpty())           { sql.append(" AND sl.created_at<=?");   params.add(toDate + " 23:59:59"); }
        sql.append(" ORDER BY sl.ledger_id ASC");
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

    public boolean insert(StockLedger sl) {
        String sql = "INSERT INTO StockLedgers (warehouse_id, product_id, ref_id, ref_type, qty_change, balance_after) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sl.getWarehouseId());
            ps.setInt(2, sl.getProductId());
            ps.setInt(3, sl.getRefId());
            ps.setString(4, sl.getRefType());
            ps.setInt(5, sl.getQtyChange());
            ps.setInt(6, sl.getBalanceAfter());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private StockLedger map(ResultSet rs) throws SQLException {
        StockLedger sl = new StockLedger();
        sl.setLedgerId(rs.getInt("ledger_id"));
        sl.setWarehouseId(rs.getInt("warehouse_id"));
        sl.setProductId(rs.getInt("product_id"));
        sl.setRefId(rs.getInt("ref_id"));
        sl.setRefType(rs.getString("ref_type"));
        sl.setQtyChange(rs.getInt("qty_change"));
        sl.setBalanceAfter(rs.getInt("balance_after"));
        sl.setCreatedAt(rs.getTimestamp("created_at"));
        sl.setWarehouseName(rs.getString("warehouse_name"));
        sl.setProductName(rs.getString("product_name"));
        sl.setSku(rs.getString("sku"));
        return sl;
    }
}
