import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
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
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            RectHV initial = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            root = new Node(p, initial, 0);
            return;
        }

        Deque<Node> stack = new ArrayDeque<>();
        Node current = root;
        while (true) {
            if (p.equals(current.point)) {
                return;
            }

            stack.addFirst(current);
            int compare = getComparator(current).compare(p, current.point);

            if (compare < 0) {
                if (current.left == null) {
                    current.left = new Node(p, getChildRectangle(current, false), current.depth + 1);
                    break;
                }
                current = current.left;
            }
            else {
                if (current.right == null) {
                    current.right = new Node(p, getChildRectangle(current, true), current.depth + 1);
                    break;
                }
                current = current.right;
            }
        }
        updateSize(stack);
    }

    private void updateSize(Deque<Node> stack) {
        Node current;
        while (!stack.isEmpty()) {
            current = stack.removeFirst();
            current.count = 1 + size(current.left) + size(current.right);
        }
    }

    private Comparator<Point2D> getComparator(Node node) {
        return COMPARATORS.get(node.dimension());
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return get(root, p) != null;
    }

    private Node get(Node root, Point2D p) {
        if (root == null || p.equals(root.point)) {
            return root;
        }

        int compare = getComparator(root).compare(p, root.point);
        if (compare < 0) {
            return get(root.left, p);
        }
        else {
            return get(root.right, p);
        }
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        return searchTreeRange(root, rect, new ArrayList<>());
    }

    private List<Point2D> searchTreeRange(Node current, RectHV target, List<Point2D> output) {
        if (current == null || !target.intersects(current.enclosing)) {
            return output;
        }

        if (target.contains(current.point)) {
            output.add(current.point);
        }

        searchTreeRange(current.left, target, output);
        searchTreeRange(current.right, target, output);

        return output;
    }

    private RectHV getChildRectangle(Node node, boolean greater) {
        RectHV original = node.enclosing;

        if (node.dimension() == 0) {
            double coordinate = node.point.x();
            if (greater) {
                return new RectHV(coordinate, original.ymin(), original.xmax(), original.ymax());
            }
            return new RectHV(original.xmin(), original.ymin(), coordinate, original.ymax());
        }
        else  {
            double coordinate = node.point.y();
            if (greater) {
                return new RectHV(original.xmin(), coordinate, original.xmax(), original.ymax());
            }
            return new RectHV(original.xmin(), original.ymin(), original.xmax(), coordinate);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return searchForNearest(root, p);
    }

    private Point2D searchForNearest(Node root, Point2D target) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            return root.point;
        }

        int compare = getComparator(root).compare(target, root.point);
        Node primaryChild = (compare < 0) ? root.left : root.right;
        Node secondaryChild = (compare < 0) ? root.right : root.left;

        Point2D best = root.point;
        if (primaryChild != null) {
            Point2D candidate = searchForNearest(primaryChild, target);

            if (target.distanceSquaredTo(candidate) < target.distanceSquaredTo(best)) {
                best = candidate;
            }
        }

        if (secondaryChild != null) {
            if (target.distanceSquaredTo(best) < secondaryChild.enclosing.distanceSquaredTo(target)) {
                return best;
            }

            Point2D candidate = searchForNearest(secondaryChild, target);
            if (target.distanceSquaredTo(candidate) < target.distanceSquaredTo(best)) {
                best = candidate;
            }
        }

        return best;
    }

    private static class Node {
        Node left;
        Node right;

        Point2D point;
        RectHV enclosing;
        int depth;
        int count;

        public Node(Point2D point, RectHV enclosing, int depth) {
            this.point = point;
            this.enclosing = enclosing;
            this.depth = depth;
            this.count = 1;
        }

        public int dimension() {
            return depth % DIMENSIONS;
        }
    }
}