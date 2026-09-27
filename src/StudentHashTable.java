/**
 * Requirement 6: Custom hash table (separate chaining) that supports
 * efficient O(1)-average student ID lookup, independent of the
 * java.util.HashMap class, to demonstrate the hashing technique itself.
 *
 * Like the BST, this table is rebuilt from the linked list before use
 * (see Main.rebuildHashTable()) so it always reflects current records.
 */
public class StudentHashTable {

    private static class Entry {
        Student data;
        Entry next;

        Entry(Student data) {
            this.data = data;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private Entry[] buckets;
    private int count;

    @SuppressWarnings("unchecked")
    public StudentHashTable() {
        buckets = new Entry[DEFAULT_CAPACITY];
    }

    public void clear() {
        buckets = new Entry[DEFAULT_CAPACITY];
        count = 0;
    }

    /** Simple polynomial hash function over the Student ID string. */
    private int hash(String studentId) {
        int hash = 0;
        for (int i = 0; i < studentId.length(); i++) {
            hash = 31 * hash + studentId.charAt(i);
        }
        int index = hash % buckets.length;
        return Math.abs(index);
    }

    public void insert(Student student) {
        int index = hash(student.getStudentId());
        Entry newEntry = new Entry(student);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        count++;
    }

    /**
     * Searches for a student by ID and reports how many probes (chain
     * hops) were needed, to make the hashing behaviour visible.
     */
    public Student search(String studentId) {
        int index = hash(studentId);
        Entry current = buckets[index];
        int probes = 0;
        while (current != null) {
            probes++;
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                System.out.println("(Found at bucket " + index + " after " + probes + " probe(s))");
                return current.data;
            }
            current = current.next;
        }
        System.out.println("(Searched bucket " + index + ", " + probes + " probe(s), not found)");
        return null;
    }

    public int size() {
        return count;
    }

    /** Prints the contents of every bucket, useful for demonstrating chaining/collisions. */
    public void displayBuckets() {
        System.out.println("Hash table (capacity " + buckets.length + ", " + count + " entries):");
        for (int i = 0; i < buckets.length; i++) {
            StringBuilder sb = new StringBuilder("Bucket " + i + ": ");
            Entry current = buckets[i];
            if (current == null) {
                sb.append("(empty)");
            }
            while (current != null) {
                sb.append(current.data.getStudentId());
                if (current.next != null) {
                    sb.append(" -> ");
                }
                current = current.next;
            }
            System.out.println(sb);
        }
    }
}
