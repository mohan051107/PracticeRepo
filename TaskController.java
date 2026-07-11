

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TaskController {

    private Connection connection;

    public TaskController() {
        connection = Database.getConnection();
    }

    // Add a new task
    public boolean addTask(Task task) {

        String query = "INSERT INTO tasks(title, description, due_date, status) VALUES(?,?,?,?)";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setDate(3, Date.valueOf(task.getDueDate()));
            ps.setString(4, task.getStatus());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Get all tasks
    public List<Task> getAllTasks() {

        List<Task> taskList = new ArrayList<>();

        String query = "SELECT * FROM tasks";

        try {

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Task task = new Task();

                task.setTaskId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDueDate(rs.getDate("due_date").toLocalDate());
                task.setStatus(rs.getString("status"));

                taskList.add(task);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return taskList;
    }

    // Get task by ID
    public Task getTaskById(int id) {

        String query = "SELECT * FROM tasks WHERE id=?";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Task task = new Task();

                task.setTaskId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDueDate(rs.getDate("due_date").toLocalDate());
                task.setStatus(rs.getString("status"));

                return task;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    // Update task
    public boolean updateTask(Task task) {

        String query = "UPDATE tasks SET title=?, description=?, due_date=?, status=? WHERE id=?";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setDate(3, Date.valueOf(task.getDueDate()));
            ps.setString(4, task.getStatus());
            ps.setInt(5, task.getTaskId());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Delete task
    public boolean deleteTask(int id) {

        String query = "DELETE FROM tasks WHERE id=?";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Mark task as completed
    public boolean markCompleted(int id) {

        String query = "UPDATE tasks SET status='Completed' WHERE id=?";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // Search tasks by title
    public List<Task> searchTask(String keyword) {

        List<Task> taskList = new ArrayList<>();

        String query = "SELECT * FROM tasks WHERE title LIKE ?";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Task task = new Task();

                task.setTaskId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDueDate(rs.getDate("due_date").toLocalDate());
                task.setStatus(rs.getString("status"));

                taskList.add(task);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return taskList;
    }
}