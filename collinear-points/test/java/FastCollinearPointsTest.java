import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;


class FastCollinearPointsTest {
    @Test
    public void testInput8() {
        Point[] points = TestUtils.readPoints(new File(TestUtils.ROOT + "input8.txt"));
        FastCollinearPoints fast = new FastCollinearPoints(points);
        List<LineSegment> segments = Arrays.asList(fast.segments());
        TestUtils.assertInput8(segments);
    }

    public void runInput400() {
        Point[] points = TestUtils.readPoints(new File(TestUtils.ROOT + "input400.txt"));
        FastCollinearPoints fast = new FastCollinearPoints(points);
    }

    public void runInput1000() {
        Point[] points = TestUtils.readPoints(new File(TestUtils.ROOT + "input1000.txt"));
        FastCollinearPoints fast = new FastCollinearPoints(points);
    }

}