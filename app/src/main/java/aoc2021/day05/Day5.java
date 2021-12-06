package aoc2021.day05;

import aoc2021.day02.Day2;
import aoc2021.util.Line;
import aoc2021.util.Point;
import aoc2021.util.Util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day5 {

    public static void solveA() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day05/input.txt")).toURI();
        List<String> input = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        List<Line> lines = input.stream().map(Day5::parse).collect(Collectors.toList());
        Map<Point, Integer> points = new HashMap<>();
        for (Line line : lines) {
            if (line.x1 == line.x2) {
                for (int i = Math.min(line.y1, line.y2); i <= Math.max(line.y1, line.y2); i++) {
                    Point key = new Point(line.x1, i);
                    Integer count = points.getOrDefault(key, 0);
                    points.put(key, count + 1);
                }
            } else if (line.y1 == line.y2) {
                for (int i = Math.min(line.x1, line.x2); i <= Math.max(line.x1, line.x2); i++) {
                    Point key = new Point(i, line.y1);
                    Integer count = points.getOrDefault(key, 0);
                    points.put(key, count + 1);
                }
            }
        }
        int result = 0;
        for (Point point : points.keySet()) {
            if (points.get(point) > 1) {
                result++;
            }
        }
        System.out.println(result);
    }

    public static void solveB() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day05/easy.txt")).toURI();
        List<String> input = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        List<Line> lines = input.stream().map(Day5::parse).collect(Collectors.toList());
        Map<Point, Integer> points = new HashMap<>();
        for (Line line : lines) {
            if (line.x1 == line.x2) {
                for (int i = Math.min(line.y1, line.y2); i <= Math.max(line.y1, line.y2); i++) {
                    Point key = new Point(line.x1, i);
                    Integer count = points.getOrDefault(key, 0);
                    points.put(key, count + 1);
                }
            } else if (line.y1 == line.y2) {
                for (int i = Math.min(line.x1, line.x2); i <= Math.max(line.x1, line.x2); i++) {
                    Point key = new Point(i, line.y1);
                    Integer count = points.getOrDefault(key, 0);
                    points.put(key, count + 1);
                }
            } else {
                int xStep = 1;
                if (line.x1 > line.x2) {
                    xStep = -1;
                }
                int yStep = 1;
                if (line.y1 > line.y2) {
                    yStep = -1;
                }
                int curY = line.y1;
                int curX = line.x1;
                while (curX != line.x2) {
                    Point key = new Point(curX, curY);
                    Integer count = points.getOrDefault(key, 0);
                    points.put(key, count + 1);
                    curX += xStep;
                    curY += yStep;
                }
                Point key = new Point(curX, curY);
                Integer count = points.getOrDefault(key, 0);
                points.put(key, count + 1);
            }
        }
        int result = 0;
        for (Point point : points.keySet()) {
            if (points.get(point) > 1) {
                result++;
            }
        }
        Util.printGrid(points);
        System.out.println(result);
    }

    private static Line parse(String line) {
        String[] parts = line.split("->");
        String[] one = parts[0].split(",");
        String[] two = parts[1].split(",");
        return new Line(Integer.parseInt(one[0].trim()), Integer.parseInt(one[1].trim()), Integer.parseInt(two[0].trim()), Integer.parseInt(two[1].trim()));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        solveA();
        solveB();
    }
}
