import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class BruteCollinearPointsTest {
    @Test
    public void testInput8() {
        Point[] points = TestUtils.readPoints(new File(TestUtils.ROOT + "input8.txt"));
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        List<LineSegment> segments = Arrays.asList(brute.segments());
        TestUtils.assertInput8(segments);
    }

}