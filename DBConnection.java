import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db",
                "root",
                "root"
            );

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}