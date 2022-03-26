import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BehaviourTest {

    @ParameterizedTest(name = "[{index}] {arguments}")
    @MethodSource("fileProvider")
    void testRange(File input) {
        KdTree set = new KdTree();
        for (Point2D p : Utils.readFile(input)) {
            set.insert(p);
        }

        RectHV rect = new RectHV(0.3, 0.4, 0.6, 0.7);
        Iterable<Point2D> reference = getRangeSolution(input, rect);
        Iterable<Point2D> solution = set.range(rect);

        Assertions.assertTrue(Utils.getDifference(Utils.copyToSet(reference), Utils.copyToSet(solution)).isEmpty());
    }

    Iterable<Point2D> getRangeSolution(File inputFile, RectHV target) {
        PointSET set = new PointSET();
        for (Point2D p : Utils.readFile(inputFile)) {
            set.insert(p);
        }
        return set.range(target);
    }

    @ParameterizedTest(name = "[{index}] {arguments}")
    @MethodSource("fileProvider")
    void testNearestNeighbor(File input) {
        KdTree set = new KdTree();
        for (Point2D p : Utils.readFile(input)) {
            set.insert(p);
        }

        List<Point2D> neighbors = new ArrayList<>();
        File referencePoints = new File(BehaviourTest.class.getResource("input/input10.txt").getFile());
        for (Point2D p : Utils.readFile(referencePoints)) {
            neighbors.add(set.nearest(p));
        }
        Assertions.assertEquals(getNeighBorSolution(input), neighbors);
    }

    List<Point2D> getNeighBorSolution(File inputFile) {
        PointSET set = new PointSET();
        for (Point2D p : Utils.readFile(inputFile)) {
            set.insert(p);
        }

        List<Point2D> neighbors = new ArrayList<>();
        File referencePoints = new File(BehaviourTest.class.getResource("input/input10.txt").getFile());
        for (Point2D p : Utils.readFile(referencePoints)) {
            neighbors.add(set.nearest(p));
        }
        return neighbors;
    }

    private static Iterable<File> fileProvider() {
        URL link = BehaviourTest.class.getResource("input");
        if (link == null) {
            return new ArrayList<>();
        }

        File path = new File(link.getFile());
        return List.of(path.listFiles());
    }
}
