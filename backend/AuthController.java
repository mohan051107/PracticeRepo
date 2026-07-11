import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {

    /**
     * Register a new user
     */
    public static String register(String fullName, String email, String password) {

        if (Utils.isEmpty(fullName) ||
            Utils.isEmpty(email) ||
            Utils.isEmpty(password)) {

            return Utils.error("All fields are required.");
        }

        if (!Utils.isValidEmail(email)) {
            return Utils.error("Invalid email address.");
        }

        try (Connection conn = Database.getConnection()) {

            // Check if email already exists
            String checkSql = "SELECT id FROM users WHERE email = ?";

            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return Utils.error("Email already registered.");
            }

            // Hash password
            String hashedPassword = Utils.hashPassword(password);

            // Insert user
            String insertSql =
                    "INSERT INTO users(full_name,email,password) VALUES(?,?,?)";

            PreparedStatement insertStmt =
                    conn.prepareStatement(insertSql);

            insertStmt.setString(1, fullName);
            insertStmt.setString(2, email);
            insertStmt.setString(3, hashedPassword);

            int rows = insertStmt.executeUpdate();

            if (rows > 0) {
                return Utils.success("Registration successful.");
            }

            return Utils.error("Registration failed.");

        } catch (SQLException e) {

            e.printStackTrace();
            return Utils.error(e.getMessage());

        }
    }

    /**
     * Login
     */
    public static String login(String email, String password) {

        if (Utils.isEmpty(email) ||
            Utils.isEmpty(password)) {

            return Utils.error("Email and password are required.");
        }

        try (Connection conn = Database.getConnection()) {

            String sql =
                    "SELECT id, full_name, password FROM users WHERE email=?";

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Utils.error("Invalid email or password.");
            }

            String storedPassword = rs.getString("password");

            if (!Utils.verifyPassword(password, storedPassword)) {
                return Utils.error("Invalid email or password.");
            }

            int id = rs.getInt("id");
            String fullName = rs.getString("full_name");

            return "{"
                    + "\"success\":true,"
                    + "\"message\":\"Login successful\","
                    + "\"user\":{"
                    + "\"id\":" + id + ","
                    + "\"fullName\":\"" + fullName + "\","
                    + "\"email\":\"" + email + "\""
                    + "}"
                    + "}";

        } catch (SQLException e) {

            e.printStackTrace();
            return Utils.error(e.getMessage());

        }
    }
}