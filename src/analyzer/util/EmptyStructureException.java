package analyzer.util;

/**
 * Thrown when an operation (pop, dequeue, peek, delete ...) is attempted
 * on a data structure that contains no elements.
 */
public class EmptyStructureException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyStructureException(String message) {
        super(message);
    }
}
