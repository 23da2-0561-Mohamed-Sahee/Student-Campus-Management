import java.util.ArrayList;
import java.util.List;

/**
 * Requirement 2: Custom singly linked list used as the primary store
 * for student records (add, update, delete, search, display).
 *
 * Implemented from scratch (no java.util.LinkedList) to demonstrate
 * the underlying data structure, as required by the assignment.
 */
public class StudentLinkedList {

    /** Internal node holding a Student and a reference to the next node. */
    private static class Node {
        Student data;
        Node next;

        Node(Student data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Adds a new student to the end of the list. Returns false if the ID already exists. */
    public boolean add(Student student) {
        if (search(student.getStudentId()) != null) {
            return false; // duplicate ID
        }
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    /** Finds a student by ID. Returns null if not found. */
    public Student search(String studentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /** Updates name/programme/marks for an existing student. Returns the OLD snapshot, or null if not found. */
    public Student update(String studentId, String newName, String newProgramme, double newMarks) {
        Student student = search(studentId);
        if (student == null) {
            return null;
        }
        Student oldSnapshot = student.copy();
        student.setName(newName);
        student.setProgramme(newProgramme);
        student.setMarks(newMarks);
        return oldSnapshot;
    }

    /** Removes a student by ID. Returns the removed Student, or null if not found. */
    public Student delete(String studentId) {
        if (head == null) {
            return null;
        }
        if (head.data.getStudentId().equalsIgnoreCase(studentId)) {
            Student removed = head.data;
            head = head.next;
            size--;
            return removed;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.data.getStudentId().equalsIgnoreCase(studentId)) {
                Student removed = current.next.data;
                current.next = current.next.next;
                size--;
                return removed;
            }
            current = current.next;
        }
        return null;
    }

    /** Returns all students as a List, useful for feeding the BST/hash table/traversals. */
    public List<Student> toList() {
        List<Student> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    /** Prints every student record in insertion order. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println(Student.tableHeader());
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.println("Total records: " + size);
    }
}
