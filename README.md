# University Student Record and Campus Route Management System

**Module:** CIT300 – Data Structures and Algorithms


A Java console application that manages university student records and
models the campus as a network of locations and roads, built entirely
with custom-implemented data structures (no built-in `java.util`
collections used for the core structures).

---

## ⚠️ Fill this in before submitting

> The assignment requires every group member's name, student ID,
> assigned responsibility, and individual contribution to be recorded
> correctly here. **Missing or incorrect information may result in
> marks being deducted.**

| # | Name        | Student ID   | Responsibility | Individual Contribution |
|---|-------------|--------------|-----------------|--------------------------|
| 1 | *KF.Safna*  | *23DA2-0860* | Linked list implementation and student-record management | *Implemented the linked list and developed add, update, delete, search, and display functions for student records.* |
| 2 | *KM.Saseen* | *23DA2-0861* | Stack and queue implementation and related operations | *Implemented stack and queue data structures and developed recent-action and student-service request operations.* |
| 3 | *MN.Ansaf*  | *23DA2-0933* | BST/AVL tree implementation and hashing/search functionality | *Implemented the BST/AVL tree and hashing functions for organizing and efficiently searching student records.* |
| 4 | *MFM.Sahee* | *23DA2-0561* | Graph implementation, campus locations, connections, and BFS/DFS traversal | *Implemented the campus graph, location and connection operations, and BFS/DFS traversal.* |



---

## Project Structure

```
project/
├── README.md
└── src/
    ├── Main.java              # Menu-driven console entry point
    ├── Student.java           # Student record model
    ├── StudentLinkedList.java # Custom singly linked list (Req. 2)
    ├── Action.java            # Log entry for the history/undo stack
    ├── ActionStack.java       # Custom stack for recent actions (Req. 3)
    ├── ServiceRequest.java    # Service request model
    ├── ServiceQueue.java      # Custom queue for service requests (Req. 4)
    ├── StudentBST.java        # Binary Search Tree by Student ID (Req. 5)
    ├── StudentHashTable.java  # Custom hash table w/ chaining (Req. 6)
    └── CampusGraph.java       # Adjacency-list graph + BFS/DFS (Req. 7-11)
```

## How to Compile and Run

Requires a JDK (Java 17+ recommended; developed/tested on OpenJDK 21).

```bash
# From the project root:
cd src
javac -d ../bin *.java
cd ..
java -cp bin Main
```

Or in one step from the project root:

```bash
javac -d bin src/*.java && java -cp bin Main
```

## Feature Summary

| Requirement | Implementation |
|---|---|
| Student records (ID, Name, Programme, Marks) | `Student.java` |
| Linked list storage | `StudentLinkedList.java` — custom singly linked list |
| Stack for recent actions / undo history | `ActionStack.java` — logs every add/update/delete with a "before" snapshot |
| Queue for service requests | `ServiceQueue.java` — strict FIFO order of arrival |
| BST for organizing/searching by Student ID | `StudentBST.java` — insert, search, delete (incl. two-child case), in-order display |
| Hashing for fast ID search | `StudentHashTable.java` — custom hash function + separate chaining, reports probe count |
| Graph of campus locations/connections | `CampusGraph.java` — adjacency list (`Map<String, Set<String>>`) |
| Add/remove locations and connections | `CampusGraph.addLocation/removeLocation/addConnection/removeConnection` |
| Display campus network | `CampusGraph.displayConnections()` |
| Graph traversal (BFS and DFS) | `CampusGraph.bfs()` and `CampusGraph.dfs()` — both available from the menu |
| Add/update/delete/search/display for students | Menu options 1–4 and 9 |
| Menu-driven interface with validation | `Main.java` — loops until a valid menu number (1–16) is entered |
| Input validation & error handling | Non-empty checks, numeric parsing with retry, marks range 0–100, duplicate ID/location checks, missing-record checks, invalid-connection checks |

### Notes on design choices

- **BST and hash table stay in sync with the linked list.** The linked
  list is the single source of truth for student records. After every
  add/update/delete, both the BST and hash table are rebuilt from the
  linked list (`Main.rebuildIndexes()`), so option 8 and option 9
  always reflect the current data.
- **The action stack keeps a "before" snapshot** for updates and
  deletes, which is enough to support an undo feature if you choose to
  extend the menu with one — the underlying stack already exposes
  `pop()` for that purpose.
- **The hash table prints probe counts** on search, to make the effect
  of the hash function and chaining visible for demonstration purposes.
- **The graph is undirected**: adding a connection between A and B
  updates both adjacency sets. Removing a location also removes every
  connection touching it.


