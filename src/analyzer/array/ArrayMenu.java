package analyzer.array;

import analyzer.util.EmptyStructureException;
import analyzer.util.InputHelper;

/** Console submenu for the Array component. */
public class ArrayMenu {

    private final ArrayStructure array;

    public ArrayMenu(ArrayStructure array) {
        this.array = array;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- ARRAY OPERATIONS ---------------");
            System.out.println("1. Insert at End");
            System.out.println("2. Insert at Index");
            System.out.println("3. Delete by Index");
            System.out.println("4. Delete by Value");
            System.out.println("5. Search for a Value (position)");
            System.out.println("6. Display Array");
            System.out.println("7. Sort Array (Insertion Sort)");
            System.out.println("8. Load Sample Data");
            System.out.println("9. Clear Array");
            System.out.println("10. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 10);
            try {
                switch (choice) {
                    case 1:
                        array.insertAtEnd(InputHelper.readInt("Value to insert: "));
                        System.out.println("Inserted at the end.");
                        break;
                    case 2:
                        int idx = InputHelper.readInt("Index (0 to " + array.getSize() + "): ");
                        int val = InputHelper.readInt("Value to insert: ");
                        array.insertAt(idx, val);
                        System.out.println("Inserted. Elements shifted: " + array.getLastSteps());
                        break;
                    case 3:
                        if (array.isEmpty()) {
                            System.out.println("Array is empty - nothing to delete.");
                            break;
                        }
                        int removed = array.deleteAt(
                                InputHelper.readInt("Index to delete (0 to " + (array.getSize() - 1) + "): "));
                        System.out.println("Deleted value " + removed
                                + ". Elements shifted: " + array.getLastSteps());
                        break;
                    case 4:
                        int pos = array.deleteByValue(InputHelper.readInt("Value to delete: "));
                        System.out.println(pos >= 0 ? "Deleted value at index " + pos + "."
                                                    : "Value not found - nothing deleted.");
                        break;
                    case 5:
                        searchValue();
                        break;
                    case 6:
                        array.display();
                        break;
                    case 7:
                        int comps = array.sort();
                        System.out.println("Array sorted. Comparisons made: " + comps);
                        array.display();
                        break;
                    case 8:
                        loadSample();
                        break;
                    case 9:
                        array.clear();
                        System.out.println("Array cleared.");
                        break;
                    default:
                        return;
                }
            } catch (EmptyStructureException | IndexOutOfBoundsException e) {
                System.out.println("  [!] " + e.getMessage());
            }
            InputHelper.pause();
        }
    }

    private void searchValue() {
        if (array.isEmpty()) {
            System.out.println("Array is empty - nothing to search.");
            return;
        }
        int target = InputHelper.readInt("Value to search: ");
        for (int i = 0; i < array.getSize(); i++) {
            if (array.get(i) == target) {
                System.out.println("Found " + target + " at index " + i + ".");
                return;
            }
        }
        System.out.println(target + " was not found.");
    }

    private void loadSample() {
        array.clear();
        int[] sample = {42, 7, 19, 88, 3, 56, 71, 25, 64, 13};
        for (int v : sample) {
            array.insertAtEnd(v);
        }
        System.out.println("Sample data loaded (10 unsorted numbers).");
        array.display();
    }
}
