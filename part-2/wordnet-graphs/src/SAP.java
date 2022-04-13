import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Queue;
import java.util.ArrayDeque;

import java.util.*;

public class SAP {
    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(List.of(v), List.of(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(List.of(v), List.of(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkValidVertex(v);
        checkValidVertex(w);

        LockstepBFS lockstepBFS = new LockstepBFS(v, w);
        if (lockstepBFS.hasSolution()) {
            return lockstepBFS.getDistance();
        }
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkValidVertex(v);
        checkValidVertex(w);

        LockstepBFS lockstepBFS = new LockstepBFS(v, w);
        if (lockstepBFS.hasSolution()) {
            return lockstepBFS.getSolution();
        }
        return -1;
    }

    private void checkValidVertex(Integer vertex) {
        if (vertex == null || vertex < 0 || vertex >= graph.V()) {
            throw new IllegalArgumentException("Invalid vertex Id");
        }
    }

    private void checkValidVertex(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Invalid vertex set");
        }
        vertices.forEach(this::checkValidVertex);
    }

    private class LockstepBFS {
        private boolean hasSolution;
        private int solution;

        private IteratingBFS bfs1;
        private IteratingBFS bfs2;

        public LockstepBFS(Iterable<Integer> v, Iterable<Integer> w) {
            bfs1 = new IteratingBFS(v);
            bfs2 = new IteratingBFS(w);

            while (bfs1.hasNext() && bfs2.hasNext()) {
                if (bfs2.isProcessed(bfs1.next())) {
                    hasSolution = true;
                    solution = bfs1.getPrevious();
                    return;
                }
                else if (bfs1.isProcessed(bfs2.next())) {
                    hasSolution = true;
                    solution = bfs2.getPrevious();
                    return;
                }
            }
            if (bfs1.getPrevious() == bfs2.getPrevious()) {
                hasSolution = true;
                solution = bfs1.getPrevious();
            }

        }

        public boolean hasSolution() {
            return hasSolution;
        }

        public int getSolution() {
            if (!hasSolution) {
                throw new RuntimeException("No solution");
            }
            return solution;

        }

        public int getDistance() {
            if (!hasSolution) {
                throw new RuntimeException("No solution");
            }
            return bfs1.getDistance(solution) + bfs2.getDistance(solution);
        }

        public Iterable<Integer> getPath() {
            if (!hasSolution) {
                throw new RuntimeException("No solution");
            }
            List<Integer> inOrder = new ArrayList<>(bfs1.getPathTo(solution));
            inOrder.add(solution);

            List<Integer> right = bfs2.getPathTo(solution);
            Collections.reverse(right);
            inOrder.addAll(right);

            return inOrder;
        }

    }

    private class IteratingBFS {
        private Queue<Integer> toDo;
        private boolean[] marked;
        private boolean[] processed;
        private int[] edgeTo;
        private int[] distanceTo;
        private int previous;

        public IteratingBFS(Iterable<Integer> vertices) {
            marked = new boolean[graph.V()];
            processed = new boolean[graph.V()];
            edgeTo = new int[graph.V()];
            distanceTo = new int[graph.V()];

            toDo = new ArrayDeque<>();
            for (int v : vertices) {
                marked[v] = true;
                edgeTo[v] = v;
                toDo.add(v);
            }
        }

        public boolean hasNext() {
            return !toDo.isEmpty();
        }

        public int next() {
            if (toDo.isEmpty()) {
                throw new NoSuchElementException();
            }

            int v = toDo.remove();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    toDo.add(w);
                    marked[w] = true;
                    edgeTo[w] = v;
                    distanceTo[w] = distanceTo[v] + 1;
                }
            }
            processed[v] = true;
            previous = v;

            return v;
        }

        public boolean isProcessed(int vertex) {
            return processed[vertex];
        }

        public int getDistance(int vertex) {
            return distanceTo[vertex];
        }

        public int getPrevious() {
            return previous;
        }

        public List<Integer> getPathTo(int vertex) {
            Deque<Integer> stack = new ArrayDeque<>();

            while (vertex != edgeTo[vertex]) {
                vertex = edgeTo[vertex];
                stack.addFirst(vertex);
            }

            return stack.stream().toList();
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
