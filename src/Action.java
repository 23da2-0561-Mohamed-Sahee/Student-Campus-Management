import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents one logged action (ADD, UPDATE, DELETE) performed on a
 * student record. Used by ActionStack to power the recent-actions /
 * history / undo feature (Requirement 3).
 */
public class Action {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public enum Type { ADD, UPDATE, DELETE }

    private final Type type;
    private final String studentId;
    private final Student previousState; // snapshot before the change (null for ADD)
    private final String timestamp;

    public Action(Type type, String studentId, Student previousState) {
        this.type = type;
        this.studentId = studentId;
        this.previousState = previousState;
        this.timestamp = LocalDateTime.now().format(FORMAT);
    }

    public Type getType() {
        return type;
    }

    public String getStudentId() {
        return studentId;
    }

    public Student getPreviousState() {
        return previousState;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-6s -> Student ID: %s", timestamp, type, studentId);
    }
}
