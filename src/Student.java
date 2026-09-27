/**
 * Represents a single university student record.
 * Requirement 1: Store Student ID, Name, Programme, and Marks.
 */
public class Student {
    private String studentId;
    private String name;
    private String programme;
    private double marks;

    public Student(String studentId, String name, String programme, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    /**
     * Returns a shallow copy of this student, used by the ActionStack
     * to snapshot state before an update/delete for the history feature.
     */
    public Student copy() {
        return new Student(studentId, name, programme, marks);
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-25s | %6.2f |",
                studentId, name, programme, marks);
    }

    public static String tableHeader() {
        return String.format("| %-10s | %-20s | %-25s | %6s |",
                "ID", "Name", "Programme", "Marks");
    }
}
