package analyzer.search;

import analyzer.array.ArrayStructure;
import analyzer.util.InputHelper;
import analyzer.util.ResultLog;
import java.util.Arrays;

/** Console submenu for the Searching component (uses the shared array). */
public class SearchMenu {

    private final ArrayStructure array;

    public SearchMenu(ArrayStructure array) {
        this.array = array;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- SEARCHING OPERATIONS ---------------");
            System.out.println("1. Linear Search");
            System.out.println("2. Binary Search");
            System.out.println("3. Compare Linear vs Binary Search");
            System.out.println("4. Sort the Array");
            System.out.println("5. Display Array");
            System.out.println("6. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 6);
            switch (choice) {
                case 1: doLinear(); break;
                case 2: doBinary(); break;
                case 3: doCompare(); break;
                case 4:
                    if (array.isEmpty()) {
                        System.out.println("Array is empty - nothing to sort.");
                    } else {
                        System.out.println("Sorted. Comparisons: " + array.sort());
                        array.display();
                    }
                    break;
                case 5: array.display(); break;
                default: return;
            }
            InputHelper.pause();
        }
    }

    private boolean arrayReady() {
        if (array.isEmpty()) {
            System.out.println("The array is empty. Add data first (Array Operations > Load Sample Data).");
            return false;
        }
        return true;
    }

    private void doLinear() {
        if (!arrayReady()) return;
        int target = InputHelper.readInt("Value to search: ");
        SearchResult r = Searcher.linearSearch(array.toArray(), target);
        System.out.println(r);
        ResultLog.add("Search", "Target " + target + " -> " + r);
    }

    private void doBinary() {
        if (!arrayReady()) return;
        if (!array.isSorted()) {
            System.out.println("Binary search needs a SORTED array and the array is not sorted.");
            if (InputHelper.readYesNo("Sort the array now?")) {
                array.sort();
                array.display();
            } else {
                System.out.println("Binary search cancelled.");
                return;
            }
        }
        int target = InputHelper.readInt("Value to search: ");
        SearchResult r = Searcher.binarySearch(array.toArray(), target);
        System.out.println(r);
        ResultLog.add("Search", "Target " + target + " -> " + r);
    }

    private void doCompare() {
        if (!arrayReady()) return;
        int target = InputHelper.readInt("Value to search: ");
        int[] unsorted = array.toArray();
        int[] sorted = unsorted.clone();
        Arrays.sort(sorted);   // sorted copy so the user's array order is untouched
        SearchResult lin = Searcher.linearSearch(unsorted, target);
        SearchResult bin = Searcher.binarySearch(sorted, target);
        System.out.println("\nLinear Search ran on the array as it is; Binary Search ran on a sorted copy.");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-15s %-10s %-8s %-10s%n", "Algorithm", "Found", "Steps", "Time (ns)");
        System.out.println("-------------------------------------------------------");
        printRow(lin);
        printRow(bin);
        System.out.println("-------------------------------------------------------");
        System.out.println("Array size n = " + unsorted.length
                + " | Linear: O(n) | Binary: O(log n)");
        ResultLog.add("Search", "Compare target " + target + " (n=" + unsorted.length + "): linear "
                + lin.getSteps() + " steps, binary " + bin.getSteps() + " steps");
    }

    private void printRow(SearchResult r) {
        System.out.printf("%-15s %-10s %-8d %-10d%n", r.getAlgorithm(),
                r.isFound() ? "Yes" : "No", r.getSteps(), r.getNanos());
    }
}
