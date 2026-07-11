import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // ==============================
    // SUPABASE DATABASE CONFIGURATION
    // ==============================

    // JDBC URL
    private static final String URL =
            "jdbc:postgresql://db.evyjssebvybtzfczpvgr.supabase.co:5432/postgres";

    // Database username
    private static final String USER = "postgres";

    // Replace with your database password
    private static final String PASSWORD = "YOUR_DATABASE_PASSWORD";

    // Connection object
    private static Connection connection;

    /**
     * Returns a database connection.
     */
    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                // Load PostgreSQL Driver
                Class.forName("org.postgresql.Driver");

                // Connect to database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("=================================");
                System.out.println(" Connected to Supabase Database ");
                System.out.println("=================================");

            }

        } catch (ClassNotFoundException e) {

            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Failed to connect to Supabase.");
            e.printStackTrace();

        }

        return connection;
    }

    /**
     * Close database connection.
     */
    public static void closeConnection() {

        try {

            if (connection != null && !connection.isClosed()) {

                connection.close();

                System.out.println("Database connection closed.");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    /**
     * Test database connection.
     */
    public static boolean testConnection() {

        try {

            Connection conn = getConnection();

            if (conn != null && !conn.isClosed()) {

                System.out.println("Database connection test successful.");
                return true;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

}