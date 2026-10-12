package analyzer.linkedlist;

import analyzer.search.SearchResult;
import analyzer.util.EmptyStructureException;
import analyzer.util.InputHelper;
import analyzer.util.ResultLog;

/** Console submenu for the Linked List component. */
public class LinkedListMenu {

    private final SinglyLinkedList list;

    public LinkedListMenu(SinglyLinkedList list) {
        this.list = list;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- LINKED LIST OPERATIONS ---------------");
            System.out.println("1. Insert at Head");
            System.out.println("2. Insert at Tail");
            System.out.println("3. Insert at Position");
            System.out.println("4. Delete First Node");
            System.out.println("5. Delete by Value");
            System.out.println("6. Search for a Value");
            System.out.println("7. Display List");
            System.out.println("8. Reverse List");
            System.out.println("9. Load Sample Data");
            System.out.println("10. Clear List");
            System.out.println("11. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 11);
            try {
                switch (choice) {
                    case 1:
                        list.insertFirst(InputHelper.readInt("Value to insert at head: "));
                        System.out.println("Inserted at head.");
                        break;
                    case 2:
                        list.insertLast(InputHelper.readInt("Value to insert at tail: "));
                        System.out.println("Inserted at tail.");
                        break;
                    case 3:
                        int pos = InputHelper.readInt("Position (0 to " + list.size() + "): ");
                        int val = InputHelper.readInt("Value: ");
                        list.insertAt(pos, val);
                        System.out.println("Inserted at position " + pos + ".");
                        break;
                    case 4:
                        System.out.println("Deleted head value: " + list.deleteFirst());
                        break;
                    case 5:
                        boolean removed = list.deleteByValue(InputHelper.readInt("Value to delete: "));
                        System.out.println(removed ? "Node deleted." : "Value not found - nothing deleted.");
                        break;
                    case 6:
                        if (list.isEmpty()) {
                            System.out.println("Linked list is empty - nothing to search.");
                            break;
                        }
                        int target = InputHelper.readInt("Value to search: ");
                        SearchResult r = list.search(target);
                        System.out.println(r);
                        ResultLog.add("LinkedList", "Search " + target + " -> " + r);
                        break;
                    case 7:
                        list.display();
                        break;
                    case 8:
                        list.reverse();
                        System.out.println("List reversed.");
                        list.display();
                        break;
                    case 9:
                        list.clear();
                        for (int v : new int[] {15, 25, 35, 45, 55}) {
                            list.insertLast(v);
                        }
                        System.out.println("Sample data loaded.");
                        list.display();
                        break;
                    case 10:
                        list.clear();
                        System.out.println("Linked list cleared.");
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
}
