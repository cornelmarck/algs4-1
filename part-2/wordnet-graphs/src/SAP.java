import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

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
        private int solutionDistance;

        private IteratingBFS first;
        private IteratingBFS second;

        public LockstepBFS(Iterable<Integer> v, Iterable<Integer> w) {
            first = new IteratingBFS(v);
            second = new IteratingBFS(w);

            while (isActive(first) || isActive(second)) {
                if (isActive(first)) {
                    first.next();
                    if (second.isMarked(first.current)) {
                        updateSolution(first.current);
                    }
                }
                if (isActive(second)) {
                    second.next();
                    if (first.isMarked(second.current)) {
                        updateSolution(second.current);
                    }
                }
            }
        }

        private boolean isActive(IteratingBFS bfs) {
            return bfs.hasNext() && (!hasSolution || bfs.currentDistance <= solutionDistance);
        }

        private void updateSolution(int ancestor) {
            int distance = first.getDistance(ancestor) + second.getDistance(ancestor);

            if (!hasSolution || distance < solutionDistance) {
                hasSolution = true;
                solution = ancestor;
                solutionDistance = distance;
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
            return solutionDistance;
        }

        public Iterable<Integer> getPath() {
            if (!hasSolution) {
                throw new RuntimeException("No solution");
            }
            List<Integer> inOrder = new ArrayList<>(first.getPathTo(solution));
            inOrder.add(solution);

            List<Integer> right = second.getPathTo(solution);
            Collections.reverse(right);
            inOrder.addAll(right);

            return inOrder;
        }
    }

    private class IteratingBFS {
        private Queue<Integer> q;
        private Set<Integer> marked;
        private Map<Integer, Integer> edgeTo;
        private Map<Integer, Integer> distanceTo;

        private int current;
        private int currentDistance;

        public IteratingBFS(Iterable<Integer> vertices) {
            marked = new HashSet<>();
            edgeTo = new HashMap<>();
            distanceTo = new HashMap<>();
            q = new ArrayDeque<>();

            for (int v : vertices) {
                q.add(v);
                marked.add(v);
                edgeTo.put(v, v);
                distanceTo.put(v, 0);
            }
        }

        public boolean hasNext() {
            return !q.isEmpty();
        }

        public void next() {
            if (q.isEmpty()) {
                throw new NoSuchElementException();
            }

            int v = q.remove();
            for (int w : graph.adj(v)) {
                if (!marked.contains(w)) {
                    q.add(w);
                    marked.add(w);
                    edgeTo.put(w, v);
                    distanceTo.put(w, distanceTo.get(v) + 1);
                }
            }

            current = v;
            currentDistance = distanceTo.get(v);
        }

        public boolean isMarked(int vertex) {
            return marked.contains(vertex);
        }

        public int getDistance(int vertex) {
            return distanceTo.get(vertex);
        }

        public List<Integer> getPathTo(int vertex) {
            Deque<Integer> stack = new ArrayDeque<>();

            while (vertex != edgeTo.get(vertex)) {
                vertex = edgeTo.get(vertex);
                stack.addFirst(vertex);
            }

            return new ArrayList<>(stack);
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
