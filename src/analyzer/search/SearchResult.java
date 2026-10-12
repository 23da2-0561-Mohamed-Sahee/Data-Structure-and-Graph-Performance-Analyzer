package analyzer.search;

/** Immutable holder for the outcome of one search. */
public class SearchResult {

    private final String algorithm;
    private final int index;      // -1 when not found
    private final int steps;      // comparisons made
    private final long nanos;     // execution time

    public SearchResult(String algorithm, int index, int steps, long nanos) {
        this.algorithm = algorithm;
        this.index = index;
        this.steps = steps;
        this.nanos = nanos;
    }

    public String getAlgorithm() { return algorithm; }
    public int getIndex() { return index; }
    public boolean isFound() { return index >= 0; }
    public int getSteps() { return steps; }
    public long getNanos() { return nanos; }

    @Override
    public String toString() {
        return String.format("%-14s %s | steps=%d | time=%d ns",
                algorithm, isFound() ? "found at index " + index : "NOT found", steps, nanos);
    }
}
