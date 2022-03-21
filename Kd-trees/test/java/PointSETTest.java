import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PointSETTest {

    @Test
    void construct10() {
        PointSET set = new PointSET();

        assertTrue(set.isEmpty());
        assertEquals(0, set.size());

    }

    @org.junit.jupiter.api.Test
    void insert() {
        PointSET set = new PointSET();

        for (Point2D p : TestUtils.readFile(new File("test/resources/input10.txt"))) {
            set.insert(p);
        }

        assertEquals(10, set.size());
        assertTrue(set.contains(new Point2D(0.372, 0.497)));
        assertFalse(set.contains(new Point2D(0.599, 0.208)));
    }

    @org.junit.jupiter.api.Test
    void contains() {
        PointSET set = new PointSET();
        set.insert(new Point2D(0.5, 0.7));
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.3, 0.6));

        assertEquals(3, set.size());
        assertTrue(set.contains(new Point2D(0.1, 0.1)));
        assertFalse(set.contains(new Point2D(0.4, 0.1)));
    }

    @org.junit.jupiter.api.Test
    void range() {
        PointSET set = new PointSET();
        Iterable<Point2D> input = TestUtils.readFile(new File("test/resources/input10.txt"));

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
        PointSET set = new PointSET();
        Point2D a = new Point2D(1, 1);
        Point2D b = new Point2D(0, -1);

        set.insert(a);
        set.insert(b);
        set.insert(new Point2D(2, 3));
        set.insert(new Point2D(3, 2));

        assertEquals(b, set.nearest(a));
    }
}