import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

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
        public void parentAndChild() {
            Assertions.assertEquals(1, sap.length(1,0));
        }

        @Test
        public void twoSiblings() {
            Assertions.assertEquals(2, sap.length(1,2));
        }

        @Test
        public void farAway() {
            Assertions.assertEquals(5, sap.length(12, 8));
        }

        @Test
        public void notConnected() {
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
    }

    @Nested
    public class Digraph2 {
        Digraph graph;
        SAP sap;

        @BeforeEach
        public void init() {
            graph = new Digraph(new In(this.getClass().getResource("digraph2.txt").getFile()));
            sap = new SAP(graph);
        }

        @Test
        public void LongAndShortPath() {
            Assertions.assertEquals(2, sap.length(1,3));
        }
    }

    @Nested
    public class Tournament {
        Digraph graph;
        SAP sap;

        @BeforeEach
        public void init() {
            graph = new Digraph(4);
            graph.addEdge(0, 1);
            graph.addEdge(0, 3);
            graph.addEdge(1, 3);
            graph.addEdge(2, 0);
            graph.addEdge(2, 1);
            graph.addEdge(3, 2);

            sap = new SAP(graph);
        }

        @Test
        public void t03() {
            Assertions.assertEquals(1, sap.length(3, 0));
        }

        @Test
        public void t01() {
            Assertions.assertEquals(1, sap.length(1, 0));
        }

        @Test
        public void t02() {
            Assertions.assertEquals(1, sap.length(0, 2));
        }
    }

    @Nested
    public class Wordnet {
        SAP sap;

        @BeforeEach
        public void init() {
            SynonymDictionary syn = new SynonymDictionary(new File(this.getClass().getResource("synsets.txt").getFile()));
            HypernymGraph hyper = new HypernymGraph(syn.getNumberOfSynonyms(), new File(this.getClass().getResource("hypernyms.txt").getFile()));
             sap = new SAP(hyper.getGraph());
        }

        @Test
        public void random3_11_length() {
            Assertions.assertEquals(7, sap.length(List.of(15254, 67887, 72560),
                    List.of(2817, 12961, 21985, 27173, 30589, 50212, 50812, 62664, 66221, 71825, 77387)));
        }

        @Test
        public void random3_11() {
            Assertions.assertEquals(58158, sap.ancestor(List.of(15254, 67887, 72560),
                    List.of(2817, 12961, 21985, 27173, 30589, 50212, 50812, 62664, 66221, 71825, 77387)));
        }
    }





}

