/**package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static String url = "jdbc:mysql://localhost:3306/test";
    private static String username = "root";
    private static String password = "admin";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}**/

