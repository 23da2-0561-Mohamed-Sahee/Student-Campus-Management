/**
 * Represents a single student service request (e.g. transcript request,
 * appointment, query) waiting to be processed in arrival order.
 */
public class ServiceRequest {
    private final String studentId;
    private final String description;

    public ServiceRequest(String studentId, String description) {
        this.studentId = studentId;
        this.description = description;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + " | Request: " + description;
    }
}
