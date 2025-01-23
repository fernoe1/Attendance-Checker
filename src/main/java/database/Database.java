package database;

import database.interfaces.IDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements IDatabase {
    private static final String host = "34.116.231.197";
    private static final String user = "postgres";
    private static final String password = "130924";
    private static final String dbName = "moodle";
    private static final String port = "5432"; // Default PostgreSQL port
    private static Connection connection;

    @Override
    public Connection getConnection() {
        String connectionUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dbName; // Corrected JDBC URL
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            connection = DriverManager.getConnection(connectionUrl, user, password);

            System.out.println("Connected to PostgreSQL successfully.");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException ex) {
                System.out.println("Error while closing the connection: " + ex.getMessage());
            }
        }
    }
}
