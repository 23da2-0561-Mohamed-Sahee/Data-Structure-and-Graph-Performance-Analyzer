# Data Structure and Graph Performance Analyzer

**Module:** CIT300 Data Structures and Algorithms
**Assignment:** Graded Practical Assignment 2 (Week 12) - 10% of the final grade
**Institution:** Sri Lanka Technology Campus (SLTC Research University)

## Project Description
A Java **console-based** application that demonstrates the practical use of data
structures, searching algorithms, graph traversal and algorithmic complexity.
Users work with an Array, Stack, Queue, Linked List, Searching and a Graph through
menus, and the system compares algorithms by **number of steps** and **execution time**.

## Team Members, Responsibilities and Contributions

### Student Name: MFM. Sahee
**Student ID:** 23DA2-0561
**Assigned Responsibility:** Array, Searching, Main System Integration
**Individual Contribution:**
- Implemented the `ArrayStructure` class (insert at end/index, delete by index/value, display, insertion sort, auto-resize)
- Implemented `Searcher` with Linear Search and Binary Search (steps + execution time)
- Implemented `ArrayMenu`, `SearchMenu` and the Linear vs Binary comparison
- Implemented `Main` (main menu), `InputHelper` (input validation), `ResultLog` (Display All Results)
- Integrated all modules, wrote `SelfTest`, README and managed the GitHub repository

### Student Name: KF. Safna
**Student ID:** 23DA2-0860
**Assigned Responsibility:** Stack and Queue
**Individual Contribution:**
- Implemented `ArrayStack` (push, pop, peek, display, isEmpty, isFull)
- Implemented `ArrayQueue` as a circular queue (enqueue, dequeue, peek/front, display)
- Handled underflow (pop/dequeue on empty) and overflow (push/enqueue on full) conditions
- Implemented `StackMenu` and `QueueMenu` and tested all edge cases
- Integrated both modules with the main application

### Student Name: KM. Saseen
**Student ID:** 23DA2-0861
**Assigned Responsibility:** Linked List and Performance Comparison
**Individual Contribution:**
- Implemented `SinglyLinkedList` (insert head/tail/position, delete first/by value, search, reverse, display)
- Implemented `LinkedListMenu` with empty-list handling
- Implemented `PerformanceAnalyzer` (Linear vs Binary search, BFS vs DFS, Array vs Linked List insertion)
- Recorded steps, execution time and Big-O complexity in the comparison table
- Tested the performance module and integrated it with the main application

### Student Name: MN. Ansaf
**Student ID:** 23DA2-0933
**Assigned Responsibility:** Graph Component
**Individual Contribution:**
- Implemented the `Graph` class using an adjacency list (undirected, unweighted)
- Implemented add vertex, add edge, remove edge and display graph
- Implemented BFS traversal (queue) and DFS traversal (stack) with step counting
- Implemented shortest-path search (BFS) and `TraversalResult`
- Implemented `GraphMenu`, tested graph functionality and integrated it with the main application

## Technologies Used
- Java (JDK 11 or newer; developed and tested on JDK 21)
- Git and GitHub (branches, commits, pull requests)
- No external libraries

## Main System Features
- **Array:** insert (end / index), delete (index / value), search, display, sort
- **Stack:** push, pop, peek, display, empty/full checks (LIFO)
- **Queue:** enqueue, dequeue, peek/front, display, circular array (FIFO)
- **Linked List:** insert (head / tail / position), delete, search, reverse, display
- **Searching:** Linear Search and Binary Search with a side-by-side comparison
- **Graph:** add vertex, add edge, remove edge, display, BFS, DFS, shortest path
- **Performance Comparison:** steps, execution time and complexity table
- **Display All Results:** every search, traversal and performance result recorded
- **Input validation:** invalid numbers, out-of-range choices, empty names, missing vertices
- **Error handling:** pop/dequeue/delete on empty structures, overflow, invalid index

## Project Structure
```
CIT300-DS-Graph-Analyzer/
|-- README.md
|-- .gitignore
|-- run.sh / run.bat
|-- docs/
|   |-- GITHUB_WORKFLOW.md
|   |-- SAMPLE_OUTPUT.txt
|-- src/analyzer/
    |-- Main.java                  (main menu + integration)
    |-- SelfTest.java              (automated tests)
    |-- array/        ArrayStructure, ArrayMenu
    |-- search/       Searcher, SearchResult, SearchMenu
    |-- stack/        ArrayStack, StackMenu
    |-- queue/        ArrayQueue, QueueMenu
    |-- linkedlist/   SinglyLinkedList, LinkedListMenu
    |-- graph/        Graph, TraversalResult, GraphMenu
    |-- performance/  PerformanceAnalyzer
    |-- util/         InputHelper, ResultLog, EmptyStructureException
```

## Object-Oriented Principles Used
- **Encapsulation:** data fields are private; access is through methods
- **Abstraction:** each data structure is a separate class with a clear interface
- **Separation of concerns:** logic classes (e.g. `Graph`) are separate from menu classes (e.g. `GraphMenu`)
- **Custom exception:** `EmptyStructureException` for invalid operations on empty structures

## Complexity Summary
| Operation | Complexity |
|---|---|
| Array insert at end / at index / delete | O(1) amortised / O(n) / O(n) |
| Stack push, pop, peek | O(1) |
| Queue enqueue, dequeue, peek | O(1) |
| Linked list insert head / insert tail / search | O(1) / O(n) / O(n) |
| Linear Search | O(n) |
| Binary Search (sorted data) | O(log n) |
| Graph BFS / DFS (adjacency list) | O(V + E) |

## How to Run
**Requirements:** JDK 11 or newer (`javac -version` to check).

**Option 1 - run script**
- Linux / macOS: `./run.sh`
- Windows: double-click `run.bat` or run it from Command Prompt

**Option 2 - manual**
```
mkdir out
javac -d out $(find src -name "*.java")      (Linux / macOS)
java -cp out analyzer.Main
```
Windows Command Prompt: `dir /s /b src\*.java > sources.txt`, then `javac -d out @sources.txt`.

**Run the automated tests:** `java -cp out analyzer.SelfTest`

**Suggested demo order:** 1 Array > 5 Searching > 2 Stack > 3 Queue > 4 Linked List > 6 Graph > 7 Performance (option 5) > 8 Display All Results.
Each submenu has a **Load Sample Data** option so a demo can start immediately.

## GitHub Collaboration
See `docs/GITHUB_WORKFLOW.md` for the branch, commit and pull-request workflow used by the team.
