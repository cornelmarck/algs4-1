import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void slopeTo() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 1);
        assertEquals(0.5, a.slopeTo(b));
    }
    @Test
    void slopeToInverse() {
        Point a = new Point(0, 0);
        Point b = new Point(2, 1);
        assertEquals(0.5, b.slopeTo(a));
    }
    @Test
    void slopeToVertical() {
        Point a = new Point(0, 0);
        assertEquals(Double.POSITIVE_INFINITY, a.slopeTo(new Point(0, 1)));
    }
    @Test
    void slopeToItself() {
        Point a = new Point(0, 0);
        assertEquals(Double.NEGATIVE_INFINITY, a.slopeTo(a));
    }
    @Test
    void slopeToPositiveNegativeZero() {
        Point a = new Point(0, 0);
        Point b = new Point(1,2);
    }

    @Test
    void compareTo() {
        Point a = new Point(-20, 4);
        Point b = new Point(100, 2);
        assertEquals(1, a.compareTo(b));
    }
    @Test
    void compareToInverse() {
        Point a = new Point(-20, 4);
        Point b = new Point(100, 2);
        assertEquals(-1, b.compareTo(a));
    }
    @Test
    void compareToSameY() {
        Point a = new Point(0, 1);
        Point b = new Point(1, 1);
        assertEquals(-1, a.compareTo(b));
    }
    @Test
    void compareToItself() {
        Point a = new Point(1, 2);
        assertEquals(0, a.compareTo(a));
    }

    @Test
    void bySlope() {
        Point origin = new Point(0, 0);
        Point p1 = new Point (1, 1);
        Point p2 = new Point (-1, 2);

        Comparator<Point> comp = origin.slopeOrder();
        assertEquals(1, comp.compare(p1, p2));
    }
    @Test
    void bySlopeInverse() {
        Point origin = new Point(0, 0);
        Point p1 = new Point (1, 1);
        Point p2 = new Point (-1, 2);

        Comparator<Point> comp = origin.slopeOrder();
        assertEquals(-1, comp.compare(p2, p1));
    }
    @Test
    void bySlopeEquals() {
        Point origin = new Point(0, 0);
        Point p1 = new Point (1, 1);

        Comparator<Point> comp = origin.slopeOrder();
        assertEquals(0, comp.compare(p1, p1));
    }

}
