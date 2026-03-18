package dao;

import model.AuditLog;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

    public void log(int userId, String action, String tableName, int recordId) {
        String sql = "INSERT INTO AuditLogs (user_id, action, table_name, record_id) VALUES (?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, action);
            ps.setString(3, tableName);
            ps.setInt(4, recordId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<AuditLog> getAll() {
        List<AuditLog> list = new ArrayList<>();
        String sql = "SELECT al.*, u.username FROM AuditLogs al " +
                     "JOIN Users u ON al.user_id=u.user_id ORDER BY al.action_time ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AuditLog al = new AuditLog();
                al.setLogId(rs.getInt("log_id"));
                al.setUserId(rs.getInt("user_id"));
                al.setAction(rs.getString("action"));
                al.setTableName(rs.getString("table_name"));
                al.setRecordId(rs.getInt("record_id"));
                al.setActionTime(rs.getTimestamp("action_time"));
                al.setUsername(rs.getString("username"));
                list.add(al);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
