import edu.princeton.cs.algs4.Digraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class HypernymGraph {
    private final Digraph graph;
    private final TopologicalSort topological;

    public HypernymGraph(int size, File edges) throws FileNotFoundException {
        graph = new Digraph(size);
        readFile(edges);

        DirectedCycle cycleChecker = new DirectedCycle(graph);
        if (cycleChecker.hasCycle()) {
            throw new IllegalArgumentException("Hypernyms must be a directed acyclic graph (DAG).");
        }

        topological = new TopologicalSort(graph);
    }

    private void readFile(File edges) throws FileNotFoundException {
        Scanner scanner = new Scanner(edges);

        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(",");
            for (int i = 1; i < values.length; i++) {
                graph.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[i]));
            }
        }
    }

    public Digraph getGraph() {
        return graph;
    }

    public int getRoot() {
        return topological.getFirst();
    }

    private static class TopologicalSort {
        private boolean[] marked;
        private Deque<Integer> stack;

        public TopologicalSort(Digraph graph) {
            marked = new boolean[graph.V()];
            stack = new ArrayDeque<>();

            for (int i = 0; i < marked.length; i++) {
                if (!marked[i]) {
                    dfs(graph, i);
                }
            }
        }

        private void dfs(Digraph graph, int v) {
            marked[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    dfs(graph, w);
                }
            }
            stack.addFirst(v);
        }

        private Iterable<Integer> order() {
            return stack;
        }

        private int getFirst() {
            return stack.getFirst();
        }
    }

    private static class DirectedCycle {
        private boolean hasCycle;
        private boolean[] marked;
        private boolean[] onStack;

        public DirectedCycle(Digraph graph) {
            marked = new boolean[graph.V()];
            onStack = new boolean[graph.V()];

            for (int i = 0; i < marked.length; i++) {
                if (!marked[i]) {
                    dfs(graph, i);
                }
            }
        }

        public boolean hasCycle() {
            return hasCycle;
        }

        private void dfs(Digraph graph, int v) {
            if (hasCycle) {
                return;
            }

            marked[v] = true;
            onStack[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    if (onStack[w]) {
                        hasCycle = true;
                    }
                    dfs(graph, w);
                }
            }
            onStack[v] = false;
        }
    }
}
