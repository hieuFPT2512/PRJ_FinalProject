package dao;

import authentication.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class UserDAO {

    private static final String SELECT_ALL_USERS
            = "SELECT u.user_id, u.username, u.full_name, u.role_id, u.status, u.created_at, r.role_name "
            + "FROM Users u "
            + "JOIN Roles r ON u.role_id = r.role_id";
    private static final String LOGIN = "SELECT u.user_id, u.username, u.full_name, u.role_id, u.created_at, u.status, r.role_name "
            + "FROM Users u "
            + "JOIN Roles r ON u.role_id = r.role_id "
            + "WHERE u.username = ? AND u.password_hash = ?";
    private static final String ADD = "INSERT INTO Users (username, password_hash, full_name, role_id, status) VALUES (?, ?, ?, ?, 'Active')";
    private static final String UPDATE = "UPDATE Users SET full_name=?, role_id=?, password_hash=?, status=? WHERE user_id=?";
    private static final String DELETE = "DELETE FROM Users WHERE user_id=?";
    private static final String GET_USER_BY_ID = "SELECT username, password_hash, full_name, role_id, status FROM Users WHERE user_id=?";
    
    public User checkLogin(String username, String password) {
        User user = null;
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(LOGIN)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRoleId(rs.getInt("role_id"));
                user.setStatus(rs.getString("status"));
                user.setCreatedAt(rs.getTimestamp("created_at"));

                user.setRoleName(rs.getString("role_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public User getUserById(int id) throws SQLException{
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()){
                String username = rs.getString("username");
                String password_hash = rs.getString("password_hash");
                String fullname = rs.getString("full_name");
                int role_id = rs.getInt("role_id");
                String status = rs.getString("status");
                user = new User(username, password_hash, fullname, role_id);
                user.setStatus(status);
                user.setUserId(id);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(SELECT_ALL_USERS);
                rs = ps.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setStatus(rs.getString("status"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));

                    user.setRoleName(rs.getString("role_name"));

                    list.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public boolean insertUser(User user) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getFullName());
            ps.setInt(4, user.getRoleId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateUser(User user) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, user.getFullName());
            ps.setInt(2, user.getRoleId());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getStatus());
            ps.setInt(5, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteUser(int id) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
