package analyzer.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Undirected, unweighted graph stored as an ADJACENCY LIST
 * (vertex name -> list of neighbour names).
 * Space: O(V + E).  BFS and DFS both run in O(V + E).
 */
public class Graph {

    private final Map<String, List<String>> adjacency = new LinkedHashMap<>();

    public int vertexCount() { return adjacency.size(); }

    public int edgeCount() {
        int total = 0;
        for (List<String> neighbours : adjacency.values()) {
            total += neighbours.size();
        }
        return total / 2;   // every undirected edge is stored twice
    }

    public boolean isEmpty() { return adjacency.isEmpty(); }

    public boolean hasVertex(String name) { return adjacency.containsKey(name); }

    public List<String> vertices() { return new ArrayList<>(adjacency.keySet()); }

    /** Adds a vertex. Returns false if it already exists. */
    public boolean addVertex(String name) {
        if (adjacency.containsKey(name)) {
            return false;
        }
        adjacency.put(name, new ArrayList<>());
        return true;
    }

    /**
     * Adds an undirected edge between two EXISTING vertices.
     * Returns false if the edge already exists; throws for invalid input.
     */
    public boolean addEdge(String a, String b) {
        if (!hasVertex(a) || !hasVertex(b)) {
            throw new IllegalArgumentException("Both vertices must exist before adding an edge.");
        }
        if (a.equals(b)) {
            throw new IllegalArgumentException("Self-loops are not allowed.");
        }
        if (adjacency.get(a).contains(b)) {
            return false;
        }
        adjacency.get(a).add(b);
        adjacency.get(b).add(a);
        return true;
    }

    /** Removes an edge. Returns true if it existed. */
    public boolean removeEdge(String a, String b) {
        if (!hasVertex(a) || !hasVertex(b)) {
            return false;
        }
        boolean removed = adjacency.get(a).remove(b);
        adjacency.get(b).remove(a);
        return removed;
    }

    /** Prints the adjacency list. */
    public void display() {
        if (adjacency.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }
        for (Map.Entry<String, List<String>> e : adjacency.entrySet()) {
            System.out.println("  " + e.getKey() + " : " + (e.getValue().isEmpty()
                    ? "(no neighbours)" : String.join(", ", e.getValue())));
        }
        System.out.println("Vertices: " + vertexCount() + " | Edges: " + edgeCount());
    }

    /** Breadth-First Search using a queue. */
    public TraversalResult bfs(String start) {
        requireVertex(start);
        long t0 = System.nanoTime();
        int steps = 0;
        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            steps++;                         // vertex processed
            order.add(current);
            for (String next : adjacency.get(current)) {
                steps++;                     // edge examined
                if (visited.add(next)) {
                    queue.add(next);
                }
            }
        }
        return new TraversalResult("BFS", order, steps, System.nanoTime() - t0);
    }

    /**
     * Depth-First Search using an explicit stack (no recursion, so large graphs
     * cannot cause a StackOverflowError). Neighbours are pushed in reverse so the
     * visiting order is the same as the classic recursive DFS.
     */
    public TraversalResult dfs(String start) {
        requireVertex(start);
        long t0 = System.nanoTime();
        int steps = 0;
        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.add(current)) {
                continue;                    // already visited via another path
            }
            steps++;                         // vertex processed
            order.add(current);
            List<String> neighbours = adjacency.get(current);
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                steps++;                     // edge examined
                if (!visited.contains(neighbours.get(i))) {
                    stack.push(neighbours.get(i));
                }
            }
        }
        return new TraversalResult("DFS", order, steps, System.nanoTime() - t0);
    }

    /** Shortest path (fewest edges) from start to end using BFS; empty list if none. */
    public List<String> findPath(String start, String end) {
        requireVertex(start);
        requireVertex(end);
        Map<String, String> parent = new HashMap<>();
        Deque<String> queue = new ArrayDeque<>();
        parent.put(start, null);
        queue.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                break;
            }
            for (String next : adjacency.get(current)) {
                if (!parent.containsKey(next)) {
                    parent.put(next, current);
                    queue.add(next);
                }
            }
        }
        if (!parent.containsKey(end)) {
            return new ArrayList<>();
        }
        List<String> path = new ArrayList<>();
        for (String v = end; v != null; v = parent.get(v)) {
            path.add(v);
        }
        Collections.reverse(path);
        return path;
    }

    public void clear() { adjacency.clear(); }

    private void requireVertex(String name) {
        if (!hasVertex(name)) {
            throw new IllegalArgumentException("Vertex '" + name + "' does not exist.");
        }
    }
}
