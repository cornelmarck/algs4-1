import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;

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

        LockstepBFS lockstepBFS = new LockstepBFS(graph, v, w);
        if (lockstepBFS.hasSolution()) {
            return lockstepBFS.getDistance();
        }
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkValidVertex(v);
        checkValidVertex(w);

        LockstepBFS lockstepBFS = new LockstepBFS(graph, v, w);
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
