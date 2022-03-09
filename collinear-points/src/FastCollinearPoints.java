import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class FastCollinearPoints {
    private final List<Point> points;
    private List<CandidateSegment> segments;
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        this.points = new ArrayList<>(Arrays.asList(points));
        segments = new LinkedList<>();

        for (Point origin : this.points) {
            List<Point> slopeSorted = new ArrayList<>(this.points);
            slopeSorted.sort(origin.slopeOrder());
            backtrack(slopeSorted, origin, new ArrayList<>(), 0);
        }

        lineSegments = generateLineSegments();
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

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    private void backtrack(List<Point> slopeSortedPoints, Point origin, List<Point> candidates, int start) {
        int lastElement = candidates.size() - 1;
        if (candidates.size() > 0 && candidates.get(lastElement) == origin) {
            return;
        }

        Comparator<Point> bySlope = origin.slopeOrder();
        if (candidates.size() > 1 && bySlope.compare(candidates.get(lastElement),
                candidates.get(lastElement - 1)) != 0) {
            return;
        }

        if (candidates.size() > 2) {
            List<Point> collinearSegment = new ArrayList<>(candidates);
            collinearSegment.add(origin);
            processCandidateSegment(new CandidateSegment(collinearSegment));
        }


        for (int i = start; i < slopeSortedPoints.size(); i++) {
            candidates.add(slopeSortedPoints.get(i));
            backtrack(slopeSortedPoints, origin, candidates, i + 1);
            candidates.remove(candidates.size() - 1);
        }
    }

    private void processCandidateSegment(CandidateSegment potential) {
        boolean collision = false;
        boolean isSuperior = false;

        Iterator<CandidateSegment> iter = segments.iterator();
        while (iter.hasNext()) {
            CandidateSegment existing = iter.next();
            if (existing.getSlope() == potential.getSlope() && existing.hasPointsInCommon(potential)) {
                collision = true;
                if (existing.size() < potential.size()) {
                    iter.remove();
                    isSuperior = true;
                }
            }
        }
        if (!collision || isSuperior) {
            segments.add(potential);
        }
    }

    private List<LineSegment> generateLineSegments() {
        lineSegments = new ArrayList<>();
        for (CandidateSegment s : segments) {
            lineSegments.add(new LineSegment(s.getFirstPoint(), s.getLastPoint()));
        }
        return lineSegments;
    }

    private static class CandidateSegment {
        private final double slope;
        private final List<Point> points;

        public CandidateSegment(List<Point> input) {
            if (input.size() < 2) {
                throw new IllegalArgumentException();
            }
            this.points = new ArrayList<>(input);
            Collections.sort(points);
            slope = points.get(0).slopeTo(points.get(points.size() - 1));
        }

        public int size() {
            return points.size();
        }

        public double getSlope() {
            return slope;
        }

        public Point getFirstPoint() {
            return points.get(0);
        }

        public Point getLastPoint() {
            return points.get(points.size() - 1);
        }

        public boolean isSupersededBy(CandidateSegment other) {
            return (getSlope() == other.getSlope() && hasPointsInCommon(other) && size() < other.size());
        }

        private boolean hasPointsInCommon(CandidateSegment with) {
            for (Point p : this.points) {
                if (with.points.contains(p)) {
                    return true;
                }
            }
            return false;
        }


    }
}