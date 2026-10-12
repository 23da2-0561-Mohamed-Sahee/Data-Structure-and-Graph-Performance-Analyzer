package analyzer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores every meaningful result (search results, traversal results,
 * performance rows) so that menu option 8 "Display All Results" can show them.
 */
public final class ResultLog {

    private static final List<String> RESULTS = new ArrayList<>();

    private ResultLog() { }

    public static void add(String category, String message) {
        RESULTS.add(String.format("[%-11s] %s", category, message));
    }

    public static boolean isEmpty() {
        return RESULTS.isEmpty();
    }

    public static void clear() {
        RESULTS.clear();
    }

    public static void displayAll() {
        System.out.println("=============================================");
        System.out.println(" ALL RECORDED RESULTS");
        System.out.println("=============================================");
        if (RESULTS.isEmpty()) {
            System.out.println("No results recorded yet. Run a search, a graph");
            System.out.println("traversal or a performance comparison first.");
        } else {
            for (int i = 0; i < RESULTS.size(); i++) {
                System.out.printf("%3d. %s%n", i + 1, RESULTS.get(i));
            }
            System.out.println("---------------------------------------------");
            System.out.println("Total results recorded: " + RESULTS.size());
        }
        System.out.println("=============================================");
    }
}
