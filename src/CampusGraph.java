import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Requirements 7-11: Models the campus as an undirected graph.
 * Vertices are location names; edges are roads/paths between them.
 * Uses an adjacency list (LinkedHashMap of LinkedHashSet) so that
 * insertion order is preserved for predictable, readable output.
 * Supports add/remove of locations and connections, displaying the
 * network, and BFS/DFS traversal.
 */
public class CampusGraph {

    private final Map<String, Set<String>> adjacencyList = new LinkedHashMap<>();

    public boolean addLocation(String location) {
        if (adjacencyList.containsKey(location)) {
            return false; // duplicate location
        }
        adjacencyList.put(location, new LinkedHashSet<>());
        return true;
    }

    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    /** Removes a location and every connection/road that touches it. */
    public boolean removeLocation(String location) {
        if (!adjacencyList.containsKey(location)) {
            return false;
        }
        adjacencyList.remove(location);
        for (Set<String> neighbours : adjacencyList.values()) {
            neighbours.remove(location);
        }
        return true;
    }

    /** Adds an undirected road/connection between two existing locations. */
    public boolean addConnection(String locationA, String locationB) {
        if (!adjacencyList.containsKey(locationA) || !adjacencyList.containsKey(locationB)) {
            return false; // one or both locations don't exist
        }
        if (adjacencyList.get(locationA).contains(locationB)) {
            return false; // connection already exists
        }
        adjacencyList.get(locationA).add(locationB);
        adjacencyList.get(locationB).add(locationA);
        return true;
    }

    public boolean removeConnection(String locationA, String locationB) {
        if (!adjacencyList.containsKey(locationA) || !adjacencyList.containsKey(locationB)) {
            return false;
        }
        boolean removed = adjacencyList.get(locationA).remove(locationB);
        adjacencyList.get(locationB).remove(locationA);
        return removed;
    }

    public int locationCount() {
        return adjacencyList.size();
    }

    /** Prints every location and its directly connected neighbours. */
    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations have been added yet.");
            return;
        }
        for (Map.Entry<String, Set<String>> entry : adjacencyList.entrySet()) {
            String neighbours = entry.getValue().isEmpty()
                    ? "(no connections)"
                    : String.join(", ", entry.getValue());
            System.out.println(entry.getKey() + " -> " + neighbours);
        }
    }

    /** Requirement 11: Breadth-first traversal starting from the given location. */
    public List<String> bfs(String start) {
        List<String> visitOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) {
            return visitOrder;
        }
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitOrder.add(current);
            for (String neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return visitOrder;
    }

    /** Requirement 11: Depth-first traversal starting from the given location. */
    public List<String> dfs(String start) {
        List<String> visitOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) {
            return visitOrder;
        }
        Set<String> visited = new LinkedHashSet<>();
        dfsRec(start, visited, visitOrder);
        return visitOrder;
    }

    private void dfsRec(String current, Set<String> visited, List<String> visitOrder) {
        visited.add(current);
        visitOrder.add(current);
        for (String neighbour : adjacencyList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsRec(neighbour, visited, visitOrder);
            }
        }
    }
}
