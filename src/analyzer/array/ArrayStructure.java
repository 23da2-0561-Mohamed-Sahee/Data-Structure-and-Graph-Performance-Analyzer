package analyzer.array;

import analyzer.util.EmptyStructureException;

/**
 * A resizable integer array built on top of a plain int[].
 * Modifying methods record the number of element shifts so that the
 * performance module can explain Big-O behaviour.
 */
public class ArrayStructure {

    private int[] data;
    private int size;
    private int lastSteps;   // shifts / comparisons done by the last operation

    public ArrayStructure(int initialCapacity) {
        data = new int[Math.max(initialCapacity, 4)];
        size = 0;
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return size == 0; }

    /** Number of steps (shifts) performed by the most recent operation. */
    public int getLastSteps() { return lastSteps; }

    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    /** Insert at the end - O(1) amortised. */
    public void insertAtEnd(int value) {
        ensureCapacity();
        data[size++] = value;
        lastSteps = 0;
    }

    /** Insert at a given index (0..size) - O(n) because of shifting. */
    public void insertAt(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + size);
        }
        ensureCapacity();
        int shifts = 0;
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            shifts++;
        }
        data[index] = value;
        size++;
        lastSteps = shifts;
    }

    /** Delete the element at an index and return it - O(n). */
    public int deleteAt(int index) {
        if (isEmpty()) {
            throw new EmptyStructureException("Array is empty - nothing to delete.");
        }
        checkIndex(index);
        int removed = data[index];
        int shifts = 0;
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            shifts++;
        }
        size--;
        lastSteps = shifts;
        return removed;
    }

    /** Delete the first occurrence of a value. Returns its old index, or -1. */
    public int deleteByValue(int value) {
        if (isEmpty()) {
            throw new EmptyStructureException("Array is empty - nothing to delete.");
        }
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                deleteAt(i);
                return i;
            }
        }
        return -1;
    }

    /** True when the elements are in non-decreasing order (needed by binary search). */
    public boolean isSorted() {
        for (int i = 1; i < size; i++) {
            if (data[i - 1] > data[i]) {
                return false;
            }
        }
        return true;
    }

    /** Insertion sort (own implementation); returns the number of comparisons. */
    public int sort() {
        int comparisons = 0;
        for (int i = 1; i < size; i++) {
            int key = data[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (data[j] > key) {
                    data[j + 1] = data[j];
                    j--;
                } else {
                    break;
                }
            }
            data[j + 1] = key;
        }
        lastSteps = comparisons;
        return comparisons;
    }

    /** Returns a copy containing only the used part of the array. */
    public int[] toArray() {
        int[] copy = new int[size];
        System.arraycopy(data, 0, copy, 0, size);
        return copy;
    }

    public void clear() {
        size = 0;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Array is empty.");
            return;
        }
        StringBuilder idx = new StringBuilder("Index : ");
        StringBuilder values = new StringBuilder("Value : ");
        for (int i = 0; i < size; i++) {
            idx.append(String.format("%5d", i));
            values.append(String.format("%5d", data[i]));
        }
        System.out.println(idx);
        System.out.println(values);
        System.out.println("Size  : " + size + " (capacity " + data.length + ")");
    }

    // ---------------------------------------------------------------- helpers
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is invalid. Valid range: 0 to " + (size - 1));
        }
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int[] bigger = new int[data.length * 2];
            System.arraycopy(data, 0, bigger, 0, size);
            data = bigger;
        }
    }
}
