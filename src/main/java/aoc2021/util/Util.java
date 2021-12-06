package aoc2021.util;

import java.util.Comparator;
import java.util.Map;

public class Util {

    public static void printGrid(Map<Point, Integer> points) {
        int minX = points.keySet().stream().min(Comparator.comparingInt(o -> o.x1)).get().x1;
        int maxX = points.keySet().stream().max(Comparator.comparingInt(o -> o.x1)).get().x1;
        int minY = points.keySet().stream().min(Comparator.comparingInt(o -> o.y1)).get().y1;
        int maxY = points.keySet().stream().max(Comparator.comparingInt(o -> o.y1)).get().y1;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point key = new Point(x, y);
                if (points.get(key) == null) {
                    System.out.print(".");
                } else {
                    System.out.print(points.get(key));
                }
            }
            System.out.println();
        }
    }
}
