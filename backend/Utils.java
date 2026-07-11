import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    // Hash password using SHA-256
    public static String hashPassword(String password) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash password.");
        }
    }

    // Verify password
    public static boolean verifyPassword(String enteredPassword, String storedHash) {

        return hashPassword(enteredPassword).equals(storedHash);

    }

    // Check empty string
    public static boolean isEmpty(String value) {

        return value == null || value.trim().isEmpty();

    }

    // Validate email
    public static boolean isValidEmail(String email) {

        if (email == null)
            return false;

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    }

    // Success JSON
    public static String success(String message) {

        return "{"
                + "\"success\":true,"
                + "\"message\":\"" + message + "\""
                + "}";

    }

    // Error JSON
    public static String error(String message) {

        return "{"
                + "\"success\":false,"
                + "\"message\":\"" + message + "\""
                + "}";

    }

}