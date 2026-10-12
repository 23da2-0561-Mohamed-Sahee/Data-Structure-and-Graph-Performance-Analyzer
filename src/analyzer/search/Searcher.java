package analyzer.search;

/**
 * Searching algorithms. Each method counts the number of comparisons
 * ("steps") and measures execution time with System.nanoTime().
 */
public final class Searcher {

    private Searcher() { }

    /** Linear search - works on any array. Time complexity O(n). */
    public static SearchResult linearSearch(int[] arr, int target) {
        long start = System.nanoTime();
        int steps = 0;
        int found = -1;
        for (int i = 0; i < arr.length; i++) {
            steps++;
            if (arr[i] == target) {
                found = i;
                break;
            }
        }
        return new SearchResult("Linear Search", found, steps, System.nanoTime() - start);
    }

    /** Binary search - the array MUST be sorted. Time complexity O(log n). */
    public static SearchResult binarySearch(int[] sorted, int target) {
        long start = System.nanoTime();
        int steps = 0;
        int low = 0;
        int high = sorted.length - 1;
        int found = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;   // avoids int overflow
            steps++;
            if (sorted[mid] == target) {
                found = mid;
                break;
            } else if (sorted[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return new SearchResult("Binary Search", found, steps, System.nanoTime() - start);
    }
}
