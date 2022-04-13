import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.net.URL;

public class TestSAP {

    @Nested
    public class digraph1 {
        Digraph graph;
        SAP sap;

        @BeforeEach
        public void load() {
            graph = new Digraph(new In(this.getClass().getResource("digraph1.txt").getFile()));
            sap = new SAP(graph);
        }

        @Test
        public void ofItself() {
            Assertions.assertEquals(1, sap.length(1,0));
            Assertions.assertEquals(0, sap.ancestor(1, 0));
        }

        @Test
        public void nearby() {
            Assertions.assertEquals(2, sap.length(1,2));
            Assertions.assertEquals(0, sap.ancestor(1, 2));
        }

        @Test
        public void farAway() {
            Assertions.assertEquals(5, sap.length(12, 8));
            Assertions.assertEquals(1, sap.ancestor(12, 8));
        }

        @Test
        public void invalid() {
            Assertions.assertEquals(-1, sap.length(2, 6));
            Assertions.assertEquals(-1, sap.ancestor(2, 6));
        }


    }

}
