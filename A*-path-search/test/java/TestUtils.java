import java.io.File;
import java.util.List;

import edu.princeton.cs.algs4.In;
import static org.junit.jupiter.api.Assertions.*;

public class TestUtils {
    public static int[][] get2dArray(char[] tiles, int size) {
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = tiles[i * size + j];
            }
        }
        return board;
    }

    public static int[][] readFile(File f) {
        In in = new In(f);
        int n = in.readInt();

        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        return tiles;
    }

    public static void checkSolution(Board expected, int movesExpected, Solver s) {
        assertTrue(s.isSolvable());
        assertEquals(movesExpected, s.moves());

        List<Board> sol = (List<Board>) s.solution();
        assertEquals(movesExpected + 1, sol.size());
        assertEquals(expected, sol.get(sol.size() - 1));
    }
}
