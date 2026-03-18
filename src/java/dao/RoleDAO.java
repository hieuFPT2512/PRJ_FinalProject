package dao;

import model.Role;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    public List<Role> getAll() {
        List<Role> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Roles ORDER BY role_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Role(rs.getInt("role_id"), rs.getString("role_name")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
