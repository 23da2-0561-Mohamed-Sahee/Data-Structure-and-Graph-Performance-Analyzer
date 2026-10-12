package analyzer.graph;

import java.util.List;

/** Result of a BFS or DFS traversal: visiting order, steps and time. */
public class TraversalResult {

    private final String algorithm;
    private final List<String> order;
    private final int steps;     // vertices processed + edges examined
    private final long nanos;

    public TraversalResult(String algorithm, List<String> order, int steps, long nanos) {
        this.algorithm = algorithm;
        this.order = order;
        this.steps = steps;
        this.nanos = nanos;
    }

    public String getAlgorithm() { return algorithm; }
    public List<String> getOrder() { return order; }
    public int getSteps() { return steps; }
    public long getNanos() { return nanos; }

    @Override
    public String toString() {
        return algorithm + " order: " + String.join(" -> ", order)
                + " | visited=" + order.size() + " | steps=" + steps + " | time=" + nanos + " ns";
    }
}
