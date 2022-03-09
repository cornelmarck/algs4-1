import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import static org.junit.jupiter.api.Assertions.*;

public class TestUtils {
    public static final String ROOT = "test/resources/";

    public static Point[] readPoints(File f) {
        In in = new In(f);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        return points;
    }

    public static Point[] readInput8() {
        return readPoints(new File(ROOT + "input8.txt"));
    }

    public static void assertInput8(List<LineSegment> segments) {
        List<LineSegment> solution = new ArrayList<>();
        solution.add(new LineSegment(new Point(10000, 0), new Point(0, 10000)));
        solution.add(new LineSegment(new Point(3000, 4000), new Point(20000, 21000)));

        assertEquals(solution.size(), segments.size());
        assertTrue(solution.containsAll(segments));
    }


    void testImport() {
        Point[] points = TestUtils.readPoints(new File(ROOT + "input8.txt"));
        for (Point p : points) {
            System.out.println(p);
        }
    }
}
