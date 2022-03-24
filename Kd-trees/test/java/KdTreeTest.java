import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {
    @Test
    void construct10() {
        KdTree set = new KdTree();

        assertTrue(set.isEmpty());
        assertEquals(0, set.size());

    }

    @org.junit.jupiter.api.Test
    void insert() {
        KdTree set = new KdTree();

        for (Point2D p : Utils.readFile(new File("test/resources/input10.txt"))) {
            set.insert(p);
        }

        assertEquals(10, set.size());
        assertTrue(set.contains(new Point2D(0.372, 0.497)));
        assertFalse(set.contains(new Point2D(0.599, 0.208)));
    }

    @org.junit.jupiter.api.Test
    void contains() {
        KdTree set = new KdTree();
        set.insert(new Point2D(0.5, 0.7));
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.3, 0.6));

        assertEquals(3, set.size());
        assertTrue(set.contains(new Point2D(0.1, 0.1)));
        assertFalse(set.contains(new Point2D(0.4, 0.1)));
    }

    @org.junit.jupiter.api.Test
    void range() {
        KdTree set = new KdTree();
        Iterable<Point2D> input = Utils.readFile(new File("test/resources/input10.txt"));

        for (Point2D p : input) {
            set.insert(p);
        }

        int count = 0;
        Iterable<Point2D> inRectangle = set.range(new RectHV(0.2, 0.3, 0.4, 0.7));
        for (Point2D p : inRectangle) {
            count++;
        }
        assertEquals(2, count);
    }

    @org.junit.jupiter.api.Test
    void nearest() {
        KdTree set = new KdTree();
        Point2D a = new Point2D(1, 1);
        Point2D b = new Point2D(0, -1);

        set.insert(a);
        set.insert(b);
        set.insert(new Point2D(2, 3));
        set.insert(new Point2D(3, 2));

        assertEquals(b, set.nearest(a));
    }
}