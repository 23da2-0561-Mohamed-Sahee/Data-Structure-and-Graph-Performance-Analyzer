package analyzer.performance;

import analyzer.array.ArrayStructure;
import analyzer.graph.Graph;
import analyzer.graph.TraversalResult;
import analyzer.linkedlist.SinglyLinkedList;
import analyzer.search.SearchResult;
import analyzer.search.Searcher;
import analyzer.util.InputHelper;
import analyzer.util.ResultLog;
import java.util.Random;

/**
 * Performance / complexity demonstration.
 * Compares algorithms using (1) number of steps and (2) execution time.
 * Steps are the reliable measure; time varies with the CPU and the JVM (JIT warm-up).
 */
public class PerformanceAnalyzer {

    private static final int REPEATS = 5;            // timing repeated, best time kept
    private static final String LINE = "=".repeat(86);
    private static final String DASH = "-".repeat(86);

    private final Graph userGraph;

    public PerformanceAnalyzer(Graph userGraph) {
        this.userGraph = userGraph;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- PERFORMANCE COMPARISON ---------------");
            System.out.println("1. Searching: Linear vs Binary (generated data)");
            System.out.println("2. Graph Traversal: BFS vs DFS (your current graph)");
            System.out.println("3. Graph Traversal: BFS vs DFS (generated large graph)");
            System.out.println("4. Insertion: Array vs Linked List (insert at front)");
            System.out.println("5. Run Full Comparison (all of the above)");
            System.out.println("6. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    header();
                    compareSearch(InputHelper.readInt("Array size n (10 - 5000000): ", 10, 5_000_000));
                    footer();
                    break;
                case 2:
                    if (userGraph.isEmpty()) {
                        System.out.println("Your graph is empty. Build or load a graph first (Graph Operations).");
                        break;
                    }
                    header();
                    compareGraph(userGraph, "Your graph");
                    footer();
                    break;
                case 3:
                    header();
                    compareGraph(generateGraph(InputHelper.readInt("Number of vertices (5 - 50000): ", 5, 50_000)),
                            "Generated graph");
                    footer();
                    break;
                case 4:
                    header();
                    compareInsertion(InputHelper.readInt("Number of inserts (10 - 50000): ", 10, 50_000));
                    footer();
                    break;
                case 5:
                    runAll();
                    break;
                default:
                    return;
            }
            InputHelper.pause();
        }
    }

    /** Runs every comparison with sensible default sizes (used for the final demo). */
    private void runAll() {
        header();
        compareSearch(100_000);
        if (!userGraph.isEmpty()) {
            compareGraph(userGraph, "Your graph");
        }
        compareGraph(generateGraph(5_000), "Generated graph");
        compareInsertion(10_000);
        footer();
    }

    // ------------------------------------------------------------------ searching
    private void compareSearch(int n) {
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = i * 2;                      // sorted even numbers
        }
        int missing = -1;                           // worst case: target not present
        SearchResult lin = bestOf(sorted, missing, true);
        SearchResult bin = bestOf(sorted, missing, false);

        // average case over 1000 random targets that exist in the array
        Random rnd = new Random(42);
        long linSteps = 0, binSteps = 0, linTime = 0, binTime = 0;
        int trials = 1000;
        for (int t = 0; t < trials; t++) {
            int target = sorted[rnd.nextInt(n)];
            SearchResult l = Searcher.linearSearch(sorted, target);
            SearchResult b = Searcher.binarySearch(sorted, target);
            linSteps += l.getSteps();
            binSteps += b.getSteps();
            linTime += l.getNanos();
            binTime += b.getNanos();
        }
        System.out.println("Data: sorted array, n = " + n);
        row("Search (worst)", "Linear Search", lin.getSteps(), lin.getNanos(), "O(n)");
        row("Search (worst)", "Binary Search", bin.getSteps(), bin.getNanos(), "O(log n)");
        row("Search (avg x1000)", "Linear Search", (int) (linSteps / trials), linTime / trials, "O(n)");
        row("Search (avg x1000)", "Binary Search", (int) (binSteps / trials), binTime / trials, "O(log n)");
    }

    /** Runs a search several times and keeps the fastest run (reduces JIT noise). */
    private SearchResult bestOf(int[] arr, int target, boolean linear) {
        SearchResult best = null;
        for (int i = 0; i < REPEATS; i++) {
            SearchResult r = linear ? Searcher.linearSearch(arr, target)
                                    : Searcher.binarySearch(arr, target);
            if (best == null || r.getNanos() < best.getNanos()) {
                best = r;
            }
        }
        return best;
    }

    // ------------------------------------------------------------------ graph
    private void compareGraph(Graph g, String label) {
        String start = g.vertices().get(0);
        TraversalResult bfs = null;
        TraversalResult dfs = null;
        for (int i = 0; i < REPEATS; i++) {
            TraversalResult b = g.bfs(start);
            TraversalResult d = g.dfs(start);
            if (bfs == null || b.getNanos() < bfs.getNanos()) bfs = b;
            if (dfs == null || d.getNanos() < dfs.getNanos()) dfs = d;
        }
        System.out.println(label + ": V = " + g.vertexCount() + ", E = " + g.edgeCount()
                + ", start vertex = " + start);
        row("Graph Traversal", "BFS", bfs.getSteps(), bfs.getNanos(), "O(V + E)");
        row("Graph Traversal", "DFS", dfs.getSteps(), dfs.getNanos(), "O(V + E)");
        if (g.vertexCount() <= 12) {
            System.out.println("BFS order: " + String.join(" ", bfs.getOrder()));
            System.out.println("DFS order: " + String.join(" ", dfs.getOrder()));
        }
        ResultLog.add("Traversal", label + " BFS order: " + String.join(" ", bfs.getOrder().subList(
                0, Math.min(10, bfs.getOrder().size()))) + (bfs.getOrder().size() > 10 ? " ..." : ""));
        ResultLog.add("Traversal", label + " DFS order: " + String.join(" ", dfs.getOrder().subList(
                0, Math.min(10, dfs.getOrder().size()))) + (dfs.getOrder().size() > 10 ? " ..." : ""));
    }

    /** Builds a connected random graph: a random spanning tree plus n extra edges. */
    private Graph generateGraph(int n) {
        Graph g = new Graph();
        Random rnd = new Random(7);
        for (int i = 0; i < n; i++) {
            g.addVertex("V" + i);
        }
        for (int i = 1; i < n; i++) {
            g.addEdge("V" + i, "V" + rnd.nextInt(i));      // keeps the graph connected
        }
        for (int i = 0; i < n; i++) {
            int a = rnd.nextInt(n);
            int b = rnd.nextInt(n);
            if (a != b) {
                g.addEdge("V" + a, "V" + b);
            }
        }
        return g;
    }

    // ------------------------------------------------------------------ insertion
    private void compareInsertion(int n) {
        // Array: every insert at index 0 shifts all existing elements.
        ArrayStructure array = new ArrayStructure(n);
        long arrSteps = 0;
        long t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            array.insertAt(0, i);
            arrSteps += array.getLastSteps();
        }
        long arrTime = System.nanoTime() - t0;

        // Linked list: insert at head only re-links one pointer (1 step).
        SinglyLinkedList list = new SinglyLinkedList();
        long listSteps = 0;
        t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            list.insertFirst(i);
            listSteps++;
        }
        long listTime = System.nanoTime() - t0;

        System.out.println("Task: " + n + " inserts, each at the FRONT of the structure");
        row("Insert at front", "Array (shifting)", (int) Math.min(arrSteps, Integer.MAX_VALUE),
                arrTime, "O(n) each");
        row("Insert at front", "Linked List head", (int) listSteps, listTime, "O(1) each");
    }

    // ------------------------------------------------------------------ output
    private void header() {
        System.out.println("\n" + LINE);
        System.out.println(" PERFORMANCE COMPARISON");
        System.out.println(LINE);
        System.out.printf("%-20s %-18s %12s %14s   %s%n", "Operation", "Algorithm", "Steps",
                "Time (ns)", "Complexity");
        System.out.println(DASH);
    }

    private void footer() {
        System.out.println(LINE);
        System.out.println("Steps = comparisons / shifts / edges examined (machine independent).");
        System.out.println("Time varies by computer and JVM warm-up, so compare steps first.");
    }

    private void row(String operation, String algorithm, long steps, long nanos, String complexity) {
        System.out.printf("%-20s %-18s %,12d %,14d   %s%n", operation, algorithm, steps, nanos, complexity);
        ResultLog.add("Performance", String.format("%s | %s | steps=%,d | time=%,d ns | %s",
                operation, algorithm, steps, nanos, complexity));
    }
}
