import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest4x4 {

    @Test
    void test4x4_00() {
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle4x4-00.txt")));
        Solver s = new Solver(goal);
        TestUtils.checkSolution(goal, 0, s);
    }

    @Test
    void test4x4_04() {
        Board problem = new Board(TestUtils.readFile(new File("test/resources/puzzle4x4-04.txt")));
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle4x4-00.txt")));
        Solver s = new Solver(problem);
        TestUtils.checkSolution(goal, 4, s);
    }

    @Test
    void test4x4_15() {
        Board problem = new Board(TestUtils.readFile(new File("test/resources/puzzle4x4-15.txt")));
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle4x4-00.txt")));
        Solver s = new Solver(problem);
        TestUtils.checkSolution(goal, 15, s);
    }
}