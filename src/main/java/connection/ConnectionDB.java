package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {
    private static String url = "jdbc:mysql://localhost:3306/universidad";
    private static String username = "root";
    private static String password = "";

    public static Connection getInstance() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Couldn´t connect!");
            throw new RuntimeException(ex);
        }
    }
}

