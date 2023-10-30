import java.sql.*;

public class Database {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "";

        try(Connection conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement()) {

            String table =
                    "CREATE TABLE logs "+
                    "(id INT AUTO_INCREMENT PRIMARY KEY,"+
                    "date TIMESTAMP,"+
                    "severity VARCHAR(255),"+
                    "library VARCHAR(255),"+
                    "thread VARCHAR(255),"+
                    "message VARCHAR(255))";

            statement.executeUpdate(table);

        } catch (SQLException e) {
            System.out.println("Error: "+e);
        }

    }
}