import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> result = new ArrayDeque<>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }

        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        double minSquareDistance = Double.POSITIVE_INFINITY;
        Point2D neighbour = null;

        for (Point2D other : points) {
            if (p.distanceSquaredTo(other) < minSquareDistance) {
                neighbour = other;
                minSquareDistance = p.distanceSquaredTo(other);
            }
        }

        return neighbour;
    }

    public static void main(String[] args) {}
}