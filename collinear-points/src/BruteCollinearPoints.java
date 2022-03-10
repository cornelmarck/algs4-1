import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {
    private final List<Point> points;
    private List<List<Point>> collinearPoints;
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 points. Assume that there are no line segments longer than 4.
    public BruteCollinearPoints(Point[] points) {
        checkInput(points);
        this.points = new ArrayList<>(Arrays.asList(points));
        lineSegments = new ArrayList<>();
        collinearPoints = new ArrayList<>();

        backtrack(new ArrayList<>(), 0);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    private void backtrack(List<Point> combination, int start) {
        if (combination.size() > 2) {
            int currentElement = combination.size() - 1;
            double currentSlope = combination.get(0).slopeTo(combination.get(currentElement));
            double prevSlope = combination.get(0).slopeTo(combination.get(currentElement - 1));
            if (currentSlope != prevSlope) {
                return;
            }
        }

        if (combination.size() == 4) {
            collinearPoints.add(new ArrayList<>(combination));
            storeLineSegment(combination);
            return;
        }

        for (int i = start; i < points.size(); i++) {
            combination.add(points.get(i));
            backtrack(combination, i + 1);
            combination.remove(combination.size() - 1);
        }
    }

    private void storeLineSegment(List<Point> combination) {
        List<Point> sorted = new ArrayList<>(combination);
        Collections.sort(sorted);
        lineSegments.add(new LineSegment(sorted.get(0), sorted.get(sorted.size() - 1)));
    }

    private static void checkInput(Point[] input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        List<Point> vals = new ArrayList<>(Arrays.asList(input));
        if (vals.contains(null)) {
            throw new IllegalArgumentException();
        }
        Collections.sort(vals);
        if (vals.size() > 1) {
            for (int i = 1; i < vals.size(); i++) {
                if (vals.get(i-1).compareTo(vals.get(i)) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}