import edu.princeton.cs.algs4.Point2D;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Utils {

    public static Collection<Point2D> readFile(File path) {
        List<Point2D> points = new ArrayList<>();

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

    public static void saveToFile(Iterable<Point2D> points, File target) throws IOException {
        if (!target.getParentFile().exists()) {
            throw new IOException();
        }
        target.delete();
        target.createNewFile();

        FileWriter fileWriter = new FileWriter(target);
        for (Point2D p : points) {
            fileWriter.write(p.x() + " " + p.y());
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.close();
    }

    public static void unorderedEquals(Iterable<Point2D> one, Iterable<Point2D> two) {
        List<Point2D> list1 = copyToList(one);
        List<Point2D> list2 = copyToList(two);

        Assertions.assertEquals(list1.size(), list2.size());

    }

    public static Collection<Point2D> getDifference(Collection<Point2D> one, Collection<Point2D> two) {
        List<Point2D> copy = new ArrayList<>(one);
        one.removeIf(two::contains);
        two.removeIf(copy::contains);

        List<Point2D> remaining = new ArrayList<>();
        remaining.addAll(one);
        remaining.addAll(two);

        return remaining;
    }

    public static <T> List<T> copyToList(Iterable<T> iterable) {
        List<T> out = new ArrayList<>();
        iterable.forEach(out::add);

        return out;
    }

    public static <T> Set<T> copyToSet(Iterable<T> iterable) {
        Set<T> out = new HashSet<>();
        iterable.forEach(out::add);

        return out;
    }


    public static String[] getAllInputFiles() {
        File inputDir = new File(BehaviourTest.class.getResource("input").getFile());

        if (!(inputDir.exists() && inputDir.isDirectory())) {
            return new String[0];
        }

        return inputDir.list();
    }
}
