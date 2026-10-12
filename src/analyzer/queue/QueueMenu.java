package analyzer.queue;

import analyzer.util.EmptyStructureException;
import analyzer.util.InputHelper;

/** Console submenu for the Queue component. */
public class QueueMenu {

    private final ArrayQueue queue;

    public QueueMenu(ArrayQueue queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            System.out.println("\n--------------- QUEUE OPERATIONS ---------------");
            System.out.println("1. Enqueue");
            System.out.println("2. Dequeue");
            System.out.println("3. Peek / Front");
            System.out.println("4. Display Queue");
            System.out.println("5. Check Empty / Full");
            System.out.println("6. Load Sample Data");
            System.out.println("7. Clear Queue");
            System.out.println("8. Return to Main Menu");
            int choice = InputHelper.readInt("Enter your choice: ", 1, 8);
            try {
                switch (choice) {
                    case 1:
                        int v = InputHelper.readInt("Value to enqueue: ");
                        queue.enqueue(v);
                        System.out.println("Enqueued " + v + ".");
                        break;
                    case 2:
                        System.out.println("Dequeued: " + queue.dequeue());
                        break;
                    case 3:
                        System.out.println("Front element: " + queue.peek());
                        break;
                    case 4:
                        queue.display();
                        break;
                    case 5:
                        System.out.println("Empty: " + queue.isEmpty() + " | Full: " + queue.isFull()
                                + " | Size: " + queue.size() + "/" + queue.capacity());
                        break;
                    case 6:
                        queue.clear();
                        for (int i = 1; i <= 5; i++) {
                            queue.enqueue(i * 100);
                        }
                        System.out.println("Sample data enqueued: 100, 200, 300, 400, 500.");
                        queue.display();
                        break;
                    case 7:
                        queue.clear();
                        System.out.println("Queue cleared.");
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
