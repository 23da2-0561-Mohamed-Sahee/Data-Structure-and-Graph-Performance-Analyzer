package analyzer;

import analyzer.array.ArrayStructure;
import analyzer.graph.Graph;
import analyzer.linkedlist.SinglyLinkedList;
import analyzer.queue.ArrayQueue;
import analyzer.search.Searcher;
import analyzer.stack.ArrayStack;
import analyzer.util.EmptyStructureException;
import java.util.Arrays;

/**
 * Lightweight automated tests (no external library needed).
 * Run:  java -cp out analyzer.SelfTest
 */
public class SelfTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testArray();
        testStack();
        testQueue();
        testLinkedList();
        testSearching();
        testGraph();
        System.out.println("\nTests passed: " + passed + " | failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  PASS  " + name);
        } else {
            failed++;
            System.out.println("  FAIL  " + name);
        }
    }

    private static void testArray() {
        ArrayStructure a = new ArrayStructure(4);
        for (int v : new int[] {5, 3, 9, 1, 7}) a.insertAtEnd(v);   // forces resize
        check("Array insert + resize", a.getSize() == 5);
        a.insertAt(1, 100);
        check("Array insertAt shifts", a.get(1) == 100 && a.getSize() == 6);
        check("Array deleteAt returns value", a.deleteAt(1) == 100);
        check("Array deleteByValue", a.deleteByValue(9) == 2 && a.getSize() == 4);
        a.sort();
        check("Array sort", Arrays.equals(a.toArray(), new int[] {1, 3, 5, 7}));
        boolean thrown = false;
        try { a.get(10); } catch (IndexOutOfBoundsException e) { thrown = true; }
        check("Array invalid index rejected", thrown);
        a.clear();
        thrown = false;
        try { a.deleteAt(0); } catch (EmptyStructureException e) { thrown = true; }
        check("Array delete on empty handled", thrown);
    }

    private static void testStack() {
        ArrayStack s = new ArrayStack(2);
        s.push(1);
        s.push(2);
        check("Stack LIFO pop", s.pop() == 2 && s.peek() == 1);
        s.push(3);
        boolean overflow = false;
        try { s.push(4); } catch (IllegalStateException e) { overflow = true; }
        check("Stack overflow handled", overflow);
        s.clear();
        boolean underflow = false;
        try { s.pop(); } catch (EmptyStructureException e) { underflow = true; }
        check("Stack pop on empty handled", underflow);
    }

    private static void testQueue() {
        ArrayQueue q = new ArrayQueue(3);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        check("Queue FIFO dequeue", q.dequeue() == 1 && q.peek() == 2);
        q.enqueue(4);   // wraps around the circular array
        check("Queue circular wrap-around", q.isFull() && q.dequeue() == 2);
        q.clear();
        boolean underflow = false;
        try { q.dequeue(); } catch (EmptyStructureException e) { underflow = true; }
        check("Queue dequeue on empty handled", underflow);
    }

    private static void testLinkedList() {
        SinglyLinkedList l = new SinglyLinkedList();
        l.insertLast(2);
        l.insertFirst(1);
        l.insertAt(2, 3);
        check("LinkedList insert head/tail/position", l.size() == 3 && l.search(3).getIndex() == 2);
        check("LinkedList search steps", l.search(3).getSteps() == 3);
        check("LinkedList deleteByValue", l.deleteByValue(2) && !l.deleteByValue(99) && l.size() == 2);
        l.reverse();
        check("LinkedList reverse", l.search(3).getIndex() == 0);
        l.clear();
        boolean thrown = false;
        try { l.deleteFirst(); } catch (EmptyStructureException e) { thrown = true; }
        check("LinkedList delete on empty handled", thrown);
    }

    private static void testSearching() {
        int[] data = {2, 4, 6, 8, 10, 12, 14, 16};
        check("Linear search found", Searcher.linearSearch(data, 10).getIndex() == 4);
        check("Linear search missing = n steps", Searcher.linearSearch(data, 5).getSteps() == 8);
        check("Binary search found", Searcher.binarySearch(data, 14).getIndex() == 6);
        check("Binary search missing <= log2(n)+1 steps", Searcher.binarySearch(data, 5).getSteps() <= 4);
    }

    private static void testGraph() {
        Graph g = new Graph();
        for (String v : new String[] {"A", "B", "C", "D", "E"}) g.addVertex(v);
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("C", "D");
        check("Graph counts", g.vertexCount() == 5 && g.edgeCount() == 4);
        check("Graph duplicate vertex rejected", !g.addVertex("A"));
        check("Graph BFS order", g.bfs("A").getOrder().equals(Arrays.asList("A", "B", "C", "D")));
        check("Graph DFS order", g.dfs("A").getOrder().equals(Arrays.asList("A", "B", "D", "C")));
        check("Graph unreachable vertex not visited", !g.bfs("A").getOrder().contains("E"));
        check("Graph shortest path", g.findPath("A", "D").size() == 3);
        check("Graph no path to isolated vertex", g.findPath("A", "E").isEmpty());
        boolean thrown = false;
        try { g.addEdge("A", "Z"); } catch (IllegalArgumentException e) { thrown = true; }
        check("Graph edge to missing vertex rejected", thrown);
    }
}
