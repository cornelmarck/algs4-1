import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private GameTree original;
    private GameTree twin;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        original = new GameTree(initial, new ManhattanPriority());
        twin = new GameTree(initial.twin(), new ManhattanPriority());

        while (!original.solutionFound() && !twin.solutionFound()) {
            original.searchNext();
            twin.searchNext();
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return original.solutionFound();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (original.solutionFound()) {
            return original.getLast().numberOfMoves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (original.solutionFound()) {
            return original.getSolutionPath();
        }
        return null;
    }

    private static class ManhattanPriority implements Comparator<TreeNode> {
        @Override
        public int compare(TreeNode o1, TreeNode o2) {
            return Integer.compare(o1.numberOfMoves + o1.board.manhattan(),
                    o2.numberOfMoves + o2.board.manhattan());
        }
    }

    private static class HammingPriority implements Comparator<TreeNode> {
        @Override
        public int compare(TreeNode o1, TreeNode o2) {
            return Integer.compare(o1.numberOfMoves + o1.board.hamming(),
                    o2.numberOfMoves + o2.board.hamming());
        }
    }

    private static class TreeNode{
        TreeNode parent;
        Board board;
        int numberOfMoves;

        public TreeNode(TreeNode parent, Board board, int numberOfMoves) {
            this.parent = parent;
            this.board = board;
            this.numberOfMoves = numberOfMoves;
        }

        public boolean hasParent() {
            return (parent != null);
        }
    }

    private class GameTree {
        private MinPQ<TreeNode> queue;
        private TreeNode last;

        public GameTree(Board problem, Comparator<TreeNode> comp) {
            queue = new MinPQ<>(comp);
            queue.insert(new TreeNode(null, problem, 0));
            last = searchNext();
        }

        public TreeNode searchNext() {
            if (!queue.isEmpty()) {
                last = queue.delMin();
                addNeighboursToQueue(last);
            }
            return last;
        }

        private void addNeighboursToQueue(TreeNode node) {
            Iterable<Board> neighbours = node.board.neighbors();
            for (Board adjacentBoard : neighbours) {
                if (node.parent == null || !adjacentBoard.equals(node.parent.board)) {
                    queue.insert(new TreeNode(node, adjacentBoard, node.numberOfMoves + 1));
                }
            }
        }

        public boolean solutionFound() {
            return last.board.isGoal();
        }

        public Iterable<Board> getSolutionPath() {
            List<Board> path = new ArrayList<>();

            TreeNode current = last;
            path.add(current.board);
            while (current.hasParent()) {
                current = current.parent;
                path.add(current.board);
            }

            Collections.reverse(path);
            return path;
        }

        public TreeNode getLast() {
            return last;
        }
    }

}