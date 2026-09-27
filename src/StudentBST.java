/**
 * Requirement 5: Binary Search Tree that organizes student records by
 * Student ID, supporting insert, search, delete, and in-order display
 * (which naturally lists students in sorted ID order).
 *
 * Note: this BST is rebuilt from the linked list before it is displayed,
 * so it always reflects the current set of records (see Main.rebuildBST()).
 */
public class StudentBST {

    private static class TreeNode {
        Student data;
        TreeNode left, right;

        TreeNode(Student data) {
            this.data = data;
        }
    }

    private TreeNode root;
    private int nodeCount;

    public void clear() {
        root = null;
        nodeCount = 0;
    }

    public int size() {
        return nodeCount;
    }

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private TreeNode insertRec(TreeNode node, Student student) {
        if (node == null) {
            nodeCount++;
            return new TreeNode(student);
        }
        int cmp = student.getStudentId().compareTo(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertRec(node.left, student);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, student);
        }
        // equal IDs are ignored (duplicates prevented at the linked-list level)
        return node;
    }

    public Student search(String studentId) {
        TreeNode current = root;
        while (current != null) {
            int cmp = studentId.compareTo(current.data.getStudentId());
            if (cmp == 0) {
                return current.data;
            }
            current = cmp < 0 ? current.left : current.right;
        }
        return null;
    }

    public boolean delete(String studentId) {
        int before = nodeCount;
        root = deleteRec(root, studentId);
        return nodeCount < before;
    }

    private TreeNode deleteRec(TreeNode node, String studentId) {
        if (node == null) {
            return null;
        }
        int cmp = studentId.compareTo(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, studentId);
        } else {
            nodeCount--;
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // two children: replace with in-order successor (smallest in right subtree)
            TreeNode successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.data = successor.data;
            nodeCount++; // compensate: deleteRec below will decrement again for the successor
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    /** Prints all students in ascending Student ID order (in-order traversal). */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("No student records found in the tree.");
            return;
        }
        System.out.println(Student.tableHeader());
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderRec(node.left);
        System.out.println(node.data);
        inOrderRec(node.right);
    }
}
