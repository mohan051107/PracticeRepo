import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task {
    
    // Task properties
    private int taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority; // HIGH, MEDIUM, LOW
    private String status;   // PENDING, IN_PROGRESS, COMPLETED, OVERDUE
    private String category; // ASSIGNMENT, PROJECT, EXAM, STUDY, OTHER
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int studentId;   // Foreign key referencing the student who owns this task
    
    // Date formatter for display purposes
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Default constructor
     */
    public Task() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "PENDING";
        this.priority = "MEDIUM";
    }
    
    /**
     * Constructor with essential fields
     * 
     * @param title Task title
     * @param description Task description
     * @param dueDate Task due date
     * @param studentId ID of the student who owns this task
     */
    public Task(String title, String description, LocalDate dueDate, int studentId) {
        this();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.studentId = studentId;
    }
    
    /**
     * Full constructor with all fields
     * 
     * @param taskId Task ID
     * @param title Task title
     * @param description Task description
     * @param dueDate Task due date
     * @param priority Task priority (HIGH, MEDIUM, LOW)
     * @param status Task status (PENDING, IN_PROGRESS, COMPLETED, OVERDUE)
     * @param category Task category (ASSIGNMENT, PROJECT, EXAM, STUDY, OTHER)
     * @param studentId Student ID
     * @param createdAt Creation timestamp
     * @param updatedAt Last update timestamp
     */
    public Task(int taskId, String title, String description, LocalDate dueDate, 
                String priority, String status, String category, 
                int studentId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.studentId = studentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
        updateStatus(); // Recalculate status when due date changes
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        if (!isValidPriority(priority)) {
            throw new IllegalArgumentException("Invalid priority. Must be HIGH, MEDIUM, or LOW");
        }
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getStatus() {
        updateStatus(); // Auto-update status based on due date
        return status;
    }
    
    public void setStatus(String status) {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status. Must be PENDING, IN_PROGRESS, COMPLETED, or OVERDUE");
        }
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category. Must be ASSIGNMENT, PROJECT, EXAM, STUDY, or OTHER");
        }
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    /**
     * Updates the task status based on due date and current status
     * If task is not completed and due date has passed, status becomes OVERDUE
     */
    private void updateStatus() {
        if (status != null && status.equals("COMPLETED")) {
            return; // Don't change status if task is already completed
        }
        
        if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
            this.status = "OVERDUE";
        } else if (status == null || status.equals("OVERDUE")) {
            this.status = "PENDING";
        }
    }
    
    /**
     * Marks the task as completed
     */
    public void markAsCompleted() {
        this.status = "COMPLETED";
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Marks the task as in progress
     */
    public void markAsInProgress() {
        if (status != null && status.equals("COMPLETED")) {
            throw new IllegalStateException("Cannot mark completed task as in progress");
        }
        this.status = "IN_PROGRESS";
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Checks if the task is overdue
     * 
     * @return true if task is overdue, false otherwise
     */
    public boolean isOverdue() {
        if (status != null && status.equals("COMPLETED")) {
            return false;
        }
        return dueDate != null && dueDate.isBefore(LocalDate.now());
    }
    
    /**
     * Checks if the task is completed
     * 
     * @return true if task is completed, false otherwise
     */
    public boolean isCompleted() {
        return status != null && status.equals("COMPLETED");
    }
    
    /**
     * Validates priority values
     * 
     * @param priority Priority to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidPriority(String priority) {
        return priority != null && 
               (priority.equals("HIGH") || priority.equals("MEDIUM") || priority.equals("LOW"));
    }
    
    /**
     * Validates status values
     * 
     * @param status Status to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidStatus(String status) {
        return status != null && 
               (status.equals("PENDING") || status.equals("IN_PROGRESS") || 
                status.equals("COMPLETED") || status.equals("OVERDUE"));
    }
    
    /**
     * Validates category values
     * 
     * @param category Category to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidCategory(String category) {
        return category != null && 
               (category.equals("ASSIGNMENT") || category.equals("PROJECT") || 
                category.equals("EXAM") || category.equals("STUDY") || category.equals("OTHER"));
    }
    
    /**
     * Returns a formatted string representation of the task
     * 
     * @return Formatted task string
     */
    @Override
    public String toString() {
        return String.format("Task [ID: %d, Title: %s, Due: %s, Priority: %s, Status: %s, Category: %s]",
                taskId, title, 
                dueDate != null ? dueDate.format(DATE_FORMATTER) : "N/A",
                priority, status, category);
    }
    
    /**
     * Returns a detailed string representation of the task
     * 
     * @return Detailed task string
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== TASK DETAILS ==========\n");
        sb.append("Task ID: ").append(taskId).append("\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Description: ").append(description != null ? description : "N/A").append("\n");
        sb.append("Due Date: ").append(dueDate != null ? dueDate.format(DATE_FORMATTER) : "N/A").append("\n");
        sb.append("Priority: ").append(priority).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Category: ").append(category != null ? category : "N/A").append("\n");
        sb.append("Student ID: ").append(studentId).append("\n");
        sb.append("Created: ").append(createdAt.format(DATETIME_FORMATTER)).append("\n");
        sb.append("Updated: ").append(updatedAt.format(DATETIME_FORMATTER)).append("\n");
        sb.append("===================================");
        return sb.toString();
    }
    
    /**
     * Compares two tasks by their ID
     * 
     * @param other Task to compare with
     * @return true if tasks have same ID, false otherwise
     */
    public boolean equals(Task other) {
        if (other == null) {
            return false;
        }
        return this.taskId == other.taskId;
    }
    
    /**
     * Compares two tasks by their due date
     * 
     * @param other Task to compare with
     * @return Negative if this task is earlier, positive if later, zero if same
     */
    public int compareByDueDate(Task other) {
        if (this.dueDate == null && other.dueDate == null) {
            return 0;
        }
        if (this.dueDate == null) {
            return 1;
        }
        if (other.dueDate == null) {
            return -1;
        }
        return this.dueDate.compareTo(other.dueDate);
    }
    
    /**
     * Compares two tasks by priority (HIGH > MEDIUM > LOW)
     * 
     * @param other Task to compare with
     * @return Negative if this task has higher priority, positive if lower, zero if same
     */
    public int compareByPriority(Task other) {
        String[] priorityOrder = {"HIGH", "MEDIUM", "LOW"};
        int thisPriorityIndex = -1;
        int otherPriorityIndex = -1;
        
        for (int i = 0; i < priorityOrder.length; i++) {
            if (this.priority.equals(priorityOrder[i])) {
                thisPriorityIndex = i;
            }
            if (other.priority.equals(priorityOrder[i])) {
                otherPriorityIndex = i;
            }
        }
        
        return Integer.compare(thisPriorityIndex, otherPriorityIndex);
    }
}