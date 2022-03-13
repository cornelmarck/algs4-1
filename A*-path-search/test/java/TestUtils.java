import java.io.File;
import edu.princeton.cs.algs4.In;

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
}
