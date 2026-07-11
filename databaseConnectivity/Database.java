
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Replace these with your Supabase details
    private static final String URL =
        "https://evyjssebvybtzfczpvgr.supabase.co";

    private static final String USER = "mohan051107";

    private static final String PASSWORD = "mohanramfarhan";

    private static Connection connection = null;

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                Class.forName("org.postgresql.Driver");

                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

                System.out.println("Connected to Supabase!");

            }

        } catch (ClassNotFoundException e) {

            System.out.println("PostgreSQL Driver Not Found");

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Database Connection Failed");

            e.printStackTrace();

        }

        return connection;

    }

    public static void closeConnection() {

        try {

            if (connection != null) {

                connection.close();

                System.out.println("Connection Closed");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}