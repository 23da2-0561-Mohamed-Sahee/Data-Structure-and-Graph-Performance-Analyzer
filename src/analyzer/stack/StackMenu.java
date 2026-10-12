package analyzer.stack;

import analyzer.util.EmptyStructureException;
import analyzer.util.InputHelper;

/** Console submenu for the Stack component. */
public class StackMenu {

    private final ArrayStack stack;

    public StackMenu(ArrayStack stack) {
        this.stack = stack;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- STACK OPERATIONS ---------------");
            System.out.println("1. Push");
            System.out.println("2. Pop");
            System.out.println("3. Peek");
            System.out.println("4. Display Stack");
            System.out.println("5. Check Empty / Full");
            System.out.println("6. Load Sample Data");
            System.out.println("7. Clear Stack");
            System.out.println("8. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 8);
            try {
                switch (choice) {
                    case 1:
                        int v = InputHelper.readInt("Value to push: ");
                        stack.push(v);
                        System.out.println("Pushed " + v + ".");
                        break;
                    case 2:
                        System.out.println("Popped: " + stack.pop());
                        break;
                    case 3:
                        System.out.println("Top element: " + stack.peek());
                        break;
                    case 4:
                        stack.display();
                        break;
                    case 5:
                        System.out.println("Empty: " + stack.isEmpty() + " | Full: " + stack.isFull()
                                + " | Size: " + stack.size() + "/" + stack.capacity());
                        break;
                    case 6:
                        stack.clear();
                        for (int i = 10; i <= 50; i += 10) {
                            stack.push(i);
                        }
                        System.out.println("Sample data pushed: 10, 20, 30, 40, 50.");
                        stack.display();
                        break;
                    case 7:
                        stack.clear();
                        System.out.println("Stack cleared.");
                        break;
                    default:
                        return;
                }
            } catch (EmptyStructureException | IllegalStateException e) {
                System.out.println("  [!] " + e.getMessage());
            }
            InputHelper.pause();
        }
    }
}
