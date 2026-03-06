package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String DB_NAME = "DeliverAcctDB";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME
                    + ";encrypt=true;trustServerCertificate=true";
            return DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }
    }
}
