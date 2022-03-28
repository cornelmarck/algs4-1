import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final Point[] sortedPoints;
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkInput(points);
        lineSegments = new ArrayList<>();

        sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);

        for (Point pivot : sortedPoints) {
            findCollinear(pivot, sortedPoints);
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
        StdOut.println(collinear.numberOfSegments());
        StdDraw.show();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    private void findCollinear(Point pivot, Point[] sortedPoints) {
        Point[] slopeSorted = Arrays.copyOf(sortedPoints, sortedPoints.length);
        Comparator<Point> bySlope = pivot.slopeOrder();
        Arrays.sort(slopeSorted, bySlope);

        int current = 1;
        while (current < slopeSorted.length) {
            int next = nextNonDuplicate(slopeSorted, slopeSorted[current], bySlope);
            if (next > current + 2) {
                if (pivot.compareTo(slopeSorted[current]) < 0) {
                    lineSegments.add(new LineSegment(pivot, slopeSorted[next - 1]));
                }
            }
            current = next;
        }
    }

    private static int nextNonDuplicate(Point[] points, Point target, Comparator<Point> comp) {
        int hi = points.length;
        int lo = 0;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (comp.compare(points[mid], target) <= 0) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        return lo;
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
}