import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void construct() {
        int[][] tiles = new int[3][3];
        tiles[1][1] = 2;
        Board b = new Board(tiles);
    }

    @Test
    void constructFromFile() {
        Board b = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-04.txt")));
        Board duplicate = new Board(TestUtils.get2dArray(new char[] {0, 1, 2, 4, 5, 3, 7, 8, 6}, 3));
        assertEquals(b, duplicate);
    }

    @Test
    void dimension() {
        Board b = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        assertEquals(3, b.dimension());
    }

    @Test
    void hamming() {
        Board done = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Board notDone = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-04.txt")));

        assertEquals(0, done.hamming());
        assertEquals(4, notDone.hamming());
    }

    @Test
    void isGoal() {
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Board notGoal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-04.txt")));

        assertTrue(goal.isGoal());
        assertFalse(notGoal.isGoal());
    }

    @Test
    void manhattan() {
        Board done = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Board notDone = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-04.txt")));

        assertEquals(0, done.manhattan());
        assertEquals(4, notDone.manhattan());
    }

    @Test
    void testEquals() {
        Board a = new Board(TestUtils.get2dArray(new char[] {0, 1, 2, 4, 5, 3, 7, 8, 6}, 3));
        Board b = new Board(TestUtils.get2dArray(new char[] {0, 1, 2, 4, 5, 3, 7, 8, 6}, 3));
        Board c = new Board(TestUtils.get2dArray(new char[] {0, 1, 2, 3}, 2));

        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(c, a);
    }

    @Test
    void neighborsGoal() {
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Board neigh1 = new Board(TestUtils.get2dArray(new char[] {1, 2, 3, 4, 5, 6, 7, 0, 8}, 3));
        Board neigh2 = new Board(TestUtils.get2dArray(new char[] {1, 2, 3, 4, 5, 0, 7, 8, 6}, 3));

        List<Board> neighbors = (List<Board>) goal.neighbors();
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(neigh1));
        assertTrue(neighbors.contains(neigh2));
    }

    @Test
    void twin() {
        Board goal = new Board(TestUtils.readFile(new File("test/resources/puzzle3x3-00.txt")));
        Board twin = new Board(TestUtils.get2dArray(new char[] {1, 2, 3, 4, 5, 6, 8, 7, 0}, 3));
        assertEquals(twin, goal.twin());
    }
}