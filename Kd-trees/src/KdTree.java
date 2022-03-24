import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {
    private static final int DIMENSIONS = 2;
    private static final List<Comparator<Point2D>> COMPARATORS = List.of(Point2D.X_ORDER, Point2D.Y_ORDER);

    private Node root;

    public KdTree() {}

    public static void main(String[] args) {}

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node root) {
        if (root == null) {
            return 0;
        }
        return root.count;
    }

    public void insert(Point2D p) {
        root = insertInto(p, root, 0);
    }

    private Node insertInto(Point2D p, Node root, int depth) {
        if (root == null) {
            return new Node(p, depth);
        }

        int compare = getComparator(root).compare(p, root.point);
        if (compare <= 0) {
            root.left = insertInto(p, root.left, depth + 1);
        }
        else {
            root.right = insertInto(p, root.right, depth + 1);
        }

        root.count = 1 + size(root.left) + size(root.right);
        return root;
    }

    private Comparator<Point2D> getComparator(Node node) {
        return COMPARATORS.get(node.dimension());
    }

    public boolean contains(Point2D p) {
        return get(root, p) != null;
    }

    private Node get(Node root, Point2D p) {
        if (root == null || p.equals(root.point)) {
            return root;
        }

        int compare = getComparator(root).compare(p, root.point);
        if (compare <= 0) {
            return get(root.left, p);
        }
        else {
            return get(root.right, p);
        }
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        RectHV initial = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        return searchTreeRange(initial, root, rect, new ArrayList<>());
    }

    private List<Point2D> searchTreeRange(RectHV searchArea, Node current, RectHV target, List<Point2D> output) {
        if (current == null || !target.intersects(searchArea)) {
            return output;
        }

        if (target.contains(current.point)) {
            output.add(current.point);
        }

        List<RectHV> split = splitRectangle(searchArea, current.point, current.dimension());
        searchTreeRange(split.get(0), current.left, target, output);
        searchTreeRange(split.get(1), current.right, target, output);

        return output;
    }

    private List<RectHV> splitRectangle(RectHV original, Point2D point, int axis) {
        List<RectHV> split = new ArrayList<>();

        if (axis == 0) {
            double coordinate = point.x();
            split.add(new RectHV(original.xmin(), original.ymin(), coordinate, original.ymax()));
            split.add(new RectHV(coordinate, original.ymin(), original.xmax(), original.ymax()));
        }
        else if (axis == 1) {
            double coordinate = point.y();
            split.add(new RectHV(original.xmin(), original.ymin(), original.xmax(), coordinate));
            split.add(new RectHV(original.xmin(), coordinate, original.xmax(), original.ymax()));
        }

        return split;
    }

    public Point2D nearest(Point2D p) {
        return null;
    }

    private static class Node {
        Node left;
        Node right;

        Point2D point;
        int depth;
        int count;

        public Node(Point2D point, int depth) {
            this.point = point;
            this.depth = depth;
            this.count = 1;
        }

        public int dimension() {
            return depth % DIMENSIONS;
        }
    }
}