import edu.princeton.cs.algs4.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestUtils {

    public static Iterable<Point2D> readFile(File path) {
        Queue<Point2D> points = new ArrayDeque<>();

        try {
            Scanner s = new Scanner(path);
            while (s.hasNextDouble()) {
                double x = s.nextDouble();
                double y = s.nextDouble();
                points.add(new Point2D(x, y));
            }
        }
        catch (FileNotFoundException ignored) {}

        return points;
    }
}
