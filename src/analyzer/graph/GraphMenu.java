package analyzer.graph;

import analyzer.util.InputHelper;
import analyzer.util.ResultLog;
import java.util.List;

/** Console submenu for the Graph component. */
public class GraphMenu {

    private final Graph graph;

    public GraphMenu(Graph graph) {
        this.graph = graph;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- GRAPH OPERATIONS ---------------");
            System.out.println("1. Add Vertex");
            System.out.println("2. Add Edge");
            System.out.println("3. Remove Edge");
            System.out.println("4. Display Graph");
            System.out.println("5. BFS Traversal");
            System.out.println("6. DFS Traversal");
            System.out.println("7. Search Path Between Two Vertices");
            System.out.println("8. Load Sample Graph");
            System.out.println("9. Clear Graph");
            System.out.println("10. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 10);
            try {
                switch (choice) {
                    case 1:
                        String v = InputHelper.readName("Vertex name: ");
                        System.out.println(graph.addVertex(v) ? "Vertex " + v + " added."
                                                              : "Vertex " + v + " already exists.");
                        break;
                    case 2:
                        if (graph.vertexCount() < 2) {
                            System.out.println("Add at least two vertices first.");
                            break;
                        }
                        String a = InputHelper.readName("First vertex : ");
                        String b = InputHelper.readName("Second vertex: ");
                        System.out.println(graph.addEdge(a, b) ? "Edge " + a + " - " + b + " added."
                                                               : "That edge already exists.");
                        break;
                    case 3:
                        String x = InputHelper.readName("First vertex : ");
                        String y = InputHelper.readName("Second vertex: ");
                        System.out.println(graph.removeEdge(x, y) ? "Edge removed."
                                                                  : "No such edge.");
                        break;
                    case 4:
                        graph.display();
                        break;
                    case 5:
                    case 6:
                        traverse(choice == 5);
                        break;
                    case 7:
                        findPath();
                        break;
                    case 8:
                        loadSample();
                        break;
                    case 9:
                        graph.clear();
                        System.out.println("Graph cleared.");
                        break;
                    default:
                        return;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("  [!] " + e.getMessage());
            }
            InputHelper.pause();
        }
    }

    private void traverse(boolean useBfs) {
        if (graph.isEmpty()) {
            System.out.println("Graph is empty - add vertices first (or load the sample graph).");
            return;
        }
        System.out.println("Available vertices: " + graph.vertices());
        String start = InputHelper.readName("Start vertex: ");
        TraversalResult r = useBfs ? graph.bfs(start) : graph.dfs(start);
        System.out.println(r);
        ResultLog.add("Graph", r + " (start " + start + ")");
    }

    private void findPath() {
        if (graph.isEmpty()) {
            System.out.println("Graph is empty.");
            return;
        }
        String s = InputHelper.readName("From vertex: ");
        String e = InputHelper.readName("To vertex  : ");
        List<String> path = graph.findPath(s, e);
        if (path.isEmpty()) {
            System.out.println("No path exists between " + s + " and " + e + ".");
        } else {
            System.out.println("Shortest path: " + String.join(" -> ", path)
                    + " (" + (path.size() - 1) + " edges)");
            ResultLog.add("Graph", "Path " + s + " to " + e + ": " + String.join("->", path));
        }
    }

    private void loadSample() {
        graph.clear();
        for (String v : new String[] {"A", "B", "C", "D", "E", "F", "G"}) {
            graph.addVertex(v);
        }
        String[][] edges = {{"A", "B"}, {"A", "C"}, {"B", "D"}, {"B", "E"},
                            {"C", "F"}, {"E", "G"}, {"F", "G"}};
        for (String[] e : edges) {
            graph.addEdge(e[0], e[1]);
        }
        System.out.println("Sample graph loaded (7 vertices, 7 edges).");
        graph.display();
    }
}
