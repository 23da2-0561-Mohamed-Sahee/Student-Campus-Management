import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * CIT300 - Graded Practical Assignment 1
 * University Student Record and Campus Route Management System
 *
 * Menu-driven console application tying together:
 *  - StudentLinkedList (linear structure / primary record store)
 *  - ActionStack        (recent actions / undo history)
 *  - ServiceQueue        (service requests in arrival order)
 *  - StudentBST          (sorted/searchable tree view by Student ID)
 *  - StudentHashTable    (fast hashed search by Student ID)
 *  - CampusGraph         (locations + connections + BFS/DFS)
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentLinkedList studentList = new StudentLinkedList();
    private static final ActionStack actionStack = new ActionStack();
    private static final ServiceQueue serviceQueue = new ServiceQueue();
    private static final StudentBST bst = new StudentBST();
    private static final StudentHashTable hashTable = new StudentHashTable();
    private static final CampusGraph campusGraph = new CampusGraph();

    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println(" University Student Record & Campus Route Management ");
        System.out.println("=======================================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateStudent();
                case 3 -> deleteStudent();
                case 4 -> studentList.displayAll();
                case 5 -> addServiceRequest();
                case 6 -> processNextServiceRequest();
                case 7 -> actionStack.displayAll();
                case 8 -> displayBST();
                case 9 -> searchByHashing();
                case 10 -> addCampusLocation();
                case 11 -> removeCampusLocation();
                case 12 -> addCampusConnection();
                case 13 -> removeCampusConnection();
                case 14 -> campusGraph.displayConnections();
                case 15 -> traverseCampus();
                case 16 -> {
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please select an option between 1 and 16.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("-------------------------------------------------------");
        System.out.println("1.  Add Student Record");
        System.out.println("2.  Update Student Record");
        System.out.println("3.  Delete Student Record");
        System.out.println("4.  Display All Records using Linked List");
        System.out.println("5.  Add Service Request to Queue");
        System.out.println("6.  Process Next Service Request");
        System.out.println("7.  Display Recent Actions using Stack");
        System.out.println("8.  Display Students using BST/AVL");
        System.out.println("9.  Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
        System.out.println("-------------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // Student record operations (Linked List + Stack + BST + Hash)
    // ---------------------------------------------------------------

    private static void addStudent() {
        String id = readNonEmpty("Enter Student ID: ");
        if (studentList.search(id) != null) {
            System.out.println("Error: A student with ID \"" + id + "\" already exists.");
            return;
        }
        String name = readNonEmpty("Enter Name: ");
        String programme = readNonEmpty("Enter Programme: ");
        double marks = readMarks("Enter Marks (0-100): ");

        Student student = new Student(id, name, programme, marks);
        studentList.add(student);
        actionStack.push(new Action(Action.Type.ADD, id, null));
        rebuildIndexes();
        System.out.println("Student record added successfully.");
    }

    private static void updateStudent() {
        String id = readNonEmpty("Enter Student ID to update: ");
        if (studentList.search(id) == null) {
            System.out.println("Error: No student found with ID \"" + id + "\".");
            return;
        }
        String name = readNonEmpty("Enter new Name: ");
        String programme = readNonEmpty("Enter new Programme: ");
        double marks = readMarks("Enter new Marks (0-100): ");

        Student oldSnapshot = studentList.update(id, name, programme, marks);
        actionStack.push(new Action(Action.Type.UPDATE, id, oldSnapshot));
        rebuildIndexes();
        System.out.println("Student record updated successfully.");
    }

    private static void deleteStudent() {
        String id = readNonEmpty("Enter Student ID to delete: ");
        Student removed = studentList.delete(id);
        if (removed == null) {
            System.out.println("Error: No student found with ID \"" + id + "\".");
            return;
        }
        actionStack.push(new Action(Action.Type.DELETE, id, removed));
        rebuildIndexes();
        System.out.println("Student record deleted successfully (kept in history for reference).");
    }

    /** Rebuilds the BST and hash table from the linked list so all three stay in sync. */
    private static void rebuildIndexes() {
        bst.clear();
        hashTable.clear();
        List<Student> all = studentList.toList();
        for (Student s : all) {
            bst.insert(s);
            hashTable.insert(s);
        }
    }

    private static void displayBST() {
        System.out.println("Students sorted by ID (in-order BST traversal):");
        bst.displayInOrder();
    }

    private static void searchByHashing() {
        String id = readNonEmpty("Enter Student ID to search: ");
        Student found = hashTable.search(id);
        if (found == null) {
            System.out.println("No student found with ID \"" + id + "\".");
        } else {
            System.out.println(Student.tableHeader());
            System.out.println(found);
        }
    }

    // ---------------------------------------------------------------
    // Service request queue
    // ---------------------------------------------------------------

    private static void addServiceRequest() {
        String id = readNonEmpty("Enter Student ID: ");
        String description = readNonEmpty("Describe the service request: ");
        serviceQueue.enqueue(new ServiceRequest(id, description));
        System.out.println("Service request added to the queue.");
    }

    private static void processNextServiceRequest() {
        ServiceRequest next = serviceQueue.dequeue();
        if (next == null) {
            System.out.println("There are no pending service requests.");
        } else {
            System.out.println("Processing request -> " + next);
        }
    }

    // ---------------------------------------------------------------
    // Campus graph operations
    // ---------------------------------------------------------------

    private static void addCampusLocation() {
        String location = readNonEmpty("Enter new campus location name: ");
        if (campusGraph.addLocation(location)) {
            System.out.println("Location \"" + location + "\" added.");
        } else {
            System.out.println("Error: Location \"" + location + "\" already exists.");
        }
    }

    private static void removeCampusLocation() {
        String location = readNonEmpty("Enter campus location to remove: ");
        if (campusGraph.removeLocation(location)) {
            System.out.println("Location \"" + location + "\" and its connections were removed.");
        } else {
            System.out.println("Error: Location \"" + location + "\" was not found.");
        }
    }

    private static void addCampusConnection() {
        String a = readNonEmpty("Enter first location: ");
        String b = readNonEmpty("Enter second location: ");
        if (campusGraph.addConnection(a, b)) {
            System.out.println("Connection added between \"" + a + "\" and \"" + b + "\".");
        } else {
            System.out.println("Error: Could not add connection (check both locations exist and aren't already connected).");
        }
    }

    private static void removeCampusConnection() {
        String a = readNonEmpty("Enter first location: ");
        String b = readNonEmpty("Enter second location: ");
        if (campusGraph.removeConnection(a, b)) {
            System.out.println("Connection removed between \"" + a + "\" and \"" + b + "\".");
        } else {
            System.out.println("Error: That connection does not exist.");
        }
    }

    private static void traverseCampus() {
        if (campusGraph.locationCount() == 0) {
            System.out.println("No campus locations have been added yet.");
            return;
        }
        String start = readNonEmpty("Enter starting location: ");
        if (!campusGraph.hasLocation(start)) {
            System.out.println("Error: Location \"" + start + "\" was not found.");
            return;
        }
        System.out.println("Choose traversal type: (1) BFS  (2) DFS");
        int type = readInt("Enter choice: ");
        List<String> order;
        if (type == 2) {
            order = campusGraph.dfs(start);
            System.out.println("DFS traversal order: " + String.join(" -> ", order));
        } else {
            order = campusGraph.bfs(start);
            System.out.println("BFS traversal order: " + String.join(" -> ", order));
        }
    }

    // ---------------------------------------------------------------
    // Input validation helpers (Requirement 13 & 14)
    // ---------------------------------------------------------------

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }

    private static double readMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double marks = Double.parseDouble(input);
                if (marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100. Please try again.");
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks. Please enter a number between 0 and 100.");
            }
        }
    }
}
