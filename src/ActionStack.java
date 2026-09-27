/**
 * Requirement 3: Custom linked-list-based stack (LIFO) that tracks
 * recent actions (add/update/delete) and supports an undo feature
 * by popping the most recent action and restoring its previous state.
 */
public class ActionStack {

    private static class Node {
        Action data;
        Node next;

        Node(Action data) {
            this.data = data;
        }
    }

    private Node top;
    private int size;

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void push(Action action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /** Removes and returns the most recent action (used for undo). Null if empty. */
    public Action pop() {
        if (isEmpty()) {
            return null;
        }
        Action data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public Action peek() {
        return isEmpty() ? null : top.data;
    }

    /** Prints the most recent actions from newest to oldest without modifying the stack. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded yet.");
            return;
        }
        System.out.println("Recent actions (most recent first):");
        Node current = top;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.data);
            current = current.next;
            count++;
        }
    }
}
