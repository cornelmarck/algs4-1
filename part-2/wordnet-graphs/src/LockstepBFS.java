import edu.princeton.cs.algs4.Digraph;

import java.util.*;

class LockstepBFS {
    private Digraph graph;
    private boolean hasSolution;
    private int solution;
    private int solutionDistance;

    private IteratingBFS first;
    private IteratingBFS second;

    public LockstepBFS(Digraph graph, Iterable<Integer> v, Iterable<Integer> w) {
        this.graph = graph;
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


}
