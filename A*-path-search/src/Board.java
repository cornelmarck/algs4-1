import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private char[] tiles;
    private int size;
    private int hammingDistance;
    private int manhattanDistance;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copyTo1d(tiles);
        this.size = tiles.length;
        hammingDistance = computeHamming();
        manhattanDistance = computeManhattan();
    }

    private static char[] copyTo1d(int[][] tiles) {
        char[] jaggedTiles = new char[tiles.length * tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                jaggedTiles[i * tiles.length + j] = (char) tiles[i][j];
            }
        }
        return jaggedTiles;
    }

    private Board(char[] tiles, int size) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        this.size = size;
        hammingDistance = computeHamming();
        manhattanDistance = computeManhattan();
    }

    public Board(char[] tiles, int size, int hammingDistance, int manhattanDistance) {
        this.tiles = tiles;
        this.size = size;
        this.hammingDistance = hammingDistance;
        this.manhattanDistance = manhattanDistance;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingDistance;
    }

    private int hammingOfTile(int i) {
        if (tiles[i] == 0 || tiles[i] == i + 1) {
            return 0;
        }
        return 1;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (manhattan() == 0);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDistance;
    }

    private int computeHamming() {
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            count += hammingOfTile(i);
        }
        return count;
    }

    private int computeManhattan() {
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            count += manhattanOfTile(i);
        }
        return count;
    }

    private int manhattanOfTile(int i) {
        if (tiles[i] == 0) {
            return 0;
        }
        int[] found = to2d(i);
        int[] goal = to2d(tiles[i] - 1);
        return Math.abs(goal[0] - found[0]) + Math.abs(goal[1] - found[1]);
    }

    private int[] to2d(int idx) {
        return new int[] {idx / size, idx % size};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(tiles, board.tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder out = new StringBuilder();
        return " ";
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int empty = findEmptyTile();
        int row = to2d(empty)[0];
        int col = to2d(empty)[1];

        List<Board> boards = new ArrayList<>();
        if (row > 0) {
            Board neighbor = new Board(tiles, size);
            neighbor.swap(empty, to1d(row - 1, col));
            boards.add(neighbor);
        }
        if (row < size - 1) {
            Board neighbor = new Board(tiles, size);
            neighbor.swap(empty, to1d(row + 1, col));
            boards.add(neighbor);
        }
        if (col > 0) {
            Board neighbor = new Board(tiles, size);
            neighbor.swap(empty, to1d(row, col - 1));
            boards.add(neighbor);
        }
        if (col < size - 1) {
            Board neighbor = new Board(tiles, size);
            neighbor.swap(empty, to1d(row, col + 1));
            boards.add(neighbor);
        }

        return boards;
    }

    private int findEmptyTile() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                return i;
            }
        }
        throw new RuntimeException();
    }

    private void swap(int idx1, int idx2) {
        hammingDistance -= (hammingOfTile(idx1) + hammingOfTile(idx2));
        manhattanDistance -= (manhattanOfTile(idx1) + manhattanOfTile(idx2));

        char copy = tiles[idx1];
        tiles[idx1] = tiles[idx2];
        tiles[idx2] = copy;

        hammingDistance += (hammingOfTile(idx1) + hammingOfTile(idx2));
        manhattanDistance += (manhattanOfTile(idx1) + manhattanOfTile(idx2));
    }

    private int to1d(int row, int col) {
        return row * size + col;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(tiles, size, hammingDistance, manhattanDistance);

        int empty = twin.findEmptyTile();
        if (empty > 1) {
            twin.swap(empty - 1, empty - 2);
        }
        else {
            twin.swap(empty + 1, empty + 2);
        }

        return twin;
    }

}