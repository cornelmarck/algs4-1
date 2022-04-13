import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;

public class SAPTest {

    @Nested
    public class Digraph1 {
        Digraph graph;
        SAP sap;

        @BeforeEach
        public void init() {
            graph = new Digraph(new In(this.getClass().getResource("digraph1.txt").getFile()));
            sap = new SAP(graph);
        }

        @Test
        public void parentAndChildLength() {
            Assertions.assertEquals(1, sap.length(1,0));
        }
        @Test
        public void parentAndChildAncestor() {
            Assertions.assertEquals(0, sap.ancestor(1, 0));
        }

        @Test
        public void twoSiblingsLength() {
            Assertions.assertEquals(2, sap.length(1,2));
        }

        @Test
        public void twoSiblingsAncestor() {
            Assertions.assertEquals(0, sap.ancestor(1, 2));
        }

        @Test
        public void farAwayLength() {
            Assertions.assertEquals(5, sap.length(12, 8));
        }

        @Test
        public void farAwayAncestor() {
            Assertions.assertEquals(1, sap.ancestor(12, 8));
        }

        @Test
        public void notConnectedLength() {
            Assertions.assertEquals(-1, sap.ancestor(2, 6));
        }

        @Test
        public void notConnectedAncestor() {
            Assertions.assertEquals(-1, sap.ancestor(2, 6));
        }
    }

    @Nested
    public class AmbiguousDigraph {
        Digraph graph;
        SAP sap;

        @BeforeEach
        public void init() {
            graph = new Digraph(new In(this.getClass().getResource("digraph-ambiguous.txt").getFile()));
            sap = new SAP(graph);
        }

        @Test
        public void twoPossibleAncestorsLength() {
            Assertions.assertEquals(5, sap.length(0, 6));
        }

        @Test
        public void twoPossibleAncestors() {
            int ancestor = sap.ancestor(0, 6);
            Assertions.assertTrue(ancestor == 2 || ancestor == 7);
        }

    }



}

