import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest3x3 {

    @Test
    void test3x3_00() {
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Solver s = new Solver(goal);

        assertTrue(s.isSolvable());
        assertEquals(List.of(goal), s.solution());
        assertEquals(0, 0);
    }

    @Test
    void test3x3_04() {
        Board problem = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-04.txt")));
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Solver s = new Solver(problem);
        TestUtils.checkSolution(goal, 4, s);
    }

    @Test
    void test3x3_15() {
        Board problem = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-15.txt")));
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Solver s = new Solver(problem);
        TestUtils.checkSolution(goal, 15, s);
    }

    @Test
    void test3x3_30() {
        Board problem = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-30.txt")));
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Solver s = new Solver(problem);
        TestUtils.checkSolution(goal, 30, s);
    }



}