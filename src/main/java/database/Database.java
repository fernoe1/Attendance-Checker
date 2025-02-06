package database;

import database.interfaces.IDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements IDatabase {
    private static final String host = "localhost";
    private static final String user = "postgres";
    private static final String password = "130924";
    private static final String dbName = "moodle";
    private static final String port = "1987";
    private static Connection connection;
    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            synchronized(Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }

        return instance;
    }

    @Override
    public Connection getConnection() {
        String connectionUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(connectionUrl, user, password);

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
