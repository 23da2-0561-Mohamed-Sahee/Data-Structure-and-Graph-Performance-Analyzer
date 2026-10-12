package analyzer;

import analyzer.array.ArrayMenu;
import analyzer.array.ArrayStructure;
import analyzer.graph.Graph;
import analyzer.graph.GraphMenu;
import analyzer.linkedlist.LinkedListMenu;
import analyzer.linkedlist.SinglyLinkedList;
import analyzer.performance.PerformanceAnalyzer;
import analyzer.queue.ArrayQueue;
import analyzer.queue.QueueMenu;
import analyzer.search.SearchMenu;
import analyzer.stack.ArrayStack;
import analyzer.stack.StackMenu;
import analyzer.util.InputHelper;
import analyzer.util.ResultLog;


public class Main {

    public static void main(String[] args) {
        // Shared data structures (one instance each, used by every menu)
        ArrayStructure array = new ArrayStructure(16);
        ArrayStack stack = new ArrayStack(10);
        ArrayQueue queue = new ArrayQueue(10);
        SinglyLinkedList list = new SinglyLinkedList();
        Graph graph = new Graph();

        // Component menus
        ArrayMenu arrayMenu = new ArrayMenu(array);
        StackMenu stackMenu = new StackMenu(stack);
        QueueMenu queueMenu = new QueueMenu(queue);
        LinkedListMenu listMenu = new LinkedListMenu(list);
        SearchMenu searchMenu = new SearchMenu(array);
        GraphMenu graphMenu = new GraphMenu(graph);
        PerformanceAnalyzer performance = new PerformanceAnalyzer(graph);

        while (true) {
            printMainMenu();
            int choice = InputHelper.readInt("Enter your choice: ", 1, 9);
            switch (choice) {
                case 1: arrayMenu.run(); break;
                case 2: stackMenu.run(); break;
                case 3: queueMenu.run(); break;
                case 4: listMenu.run(); break;
                case 5: searchMenu.run(); break;
                case 6: graphMenu.run(); break;
                case 7: performance.run(); break;
                case 8:
                    ResultLog.displayAll();
                    InputHelper.pause();
                    break;
                default:
                    System.out.println("\nThank you for using the Data Structure & Graph Analyzer. Goodbye!");
                    return;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=============================================");
        System.out.println(" DATA STRUCTURE & GRAPH ANALYZER");
        System.out.println("=============================================");
        System.out.println("1. Array Operations");
        System.out.println("2. Stack Operations");
        System.out.println("3. Queue Operations");
        System.out.println("4. Linked List Operations");
        System.out.println("5. Searching Operations");
        System.out.println("6. Graph Operations");
        System.out.println("7. Performance Comparison");
        System.out.println("8. Display All Results");
        System.out.println("9. Exit");
    }
}
