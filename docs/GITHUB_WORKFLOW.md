# GitHub Collaboration Workflow

The assignment requires **evidence of genuine collaboration**: individual branches,
meaningful commits, pull requests, merging and issue tracking. Follow these steps
so the repository history shows each member's own work.

## 1. One-time setup (Sahee - repository owner)
```
git init
git add README.md .gitignore run.sh run.bat src/analyzer/Main.java src/analyzer/util
git commit -m "Initial project setup: README, main menu, input helper, result log"
git branch -M main
git remote add origin https://github.com/<username>/CIT300-DS-Graph-Analyzer.git
git push -u origin main
```
Then on GitHub: **Settings > Collaborators** and add Safna, Saseen and Ansaf.
Also create 4 Issues (one per member) under the **Issues** tab, e.g. "Implement Graph component (BFS/DFS)".

## 2. Each member works on their own branch
| Member | Branch name | Files |
|---|---|---|
| Sahee | `feature/array-searching` | `array/`, `search/`, `SelfTest.java` |
| Safna | `feature/stack-queue` | `stack/`, `queue/` |
| Saseen | `feature/linkedlist-performance` | `linkedlist/`, `performance/` |
| Ansaf | `feature/graph` | `graph/` |

```
git clone https://github.com/<username>/CIT300-DS-Graph-Analyzer.git
cd CIT300-DS-Graph-Analyzer
git checkout -b feature/<your-branch>
```

## 3. Commit often with meaningful messages (at least 3-5 per member)
Commit your own files in small steps instead of one large commit. Examples:
```
git add src/analyzer/graph/Graph.java
git commit -m "Graph: add adjacency list, addVertex and addEdge"
git commit -m "Graph: implement BFS traversal with step counting"
git commit -m "Graph: implement DFS traversal using explicit stack"
git commit -m "GraphMenu: add submenu with input validation"
git commit -m "Graph: add shortest path search and sample graph loader"
git push -u origin feature/graph
```

## 4. Open a Pull Request for every branch
1. On GitHub click **Compare & pull request** for your branch.
2. Title example: `Add graph component (BFS, DFS, path search)`.
3. Link the Issue in the description (`Closes #4`).
4. Ask another member to review, then **Merge pull request**.

## 5. Update your local copy after merges
```
git checkout main
git pull origin main
```

## 6. Final check before submission
- `main` contains all four merged branches and the README.
- The Commits, Branches and Pull Requests tabs show work from all four members.
- Run `./run.sh` from a fresh clone to confirm the project compiles.
