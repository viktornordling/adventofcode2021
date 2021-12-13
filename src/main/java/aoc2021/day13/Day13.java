package aoc2021.day13;

import aoc2021.util.ArrayQueue;
import aoc2021.util.Point;
import aoc2021.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static aoc2021.util.Util.printMap;
import static aoc2021.util.Util.toInts;

public class Day13 {

    private static final Logger log = LogManager.getLogger();

    public static void solveA(Queue<String> input) {
        List<Point> dots = readDots(input);
        Map<Point, Character> map = new HashMap<>();
        for (Point dot : dots) {
            map.put(dot, '#');
        }

        Map<Point, Character> folded = performFold(map, new Point(655, 0));
        System.out.println(folded.keySet().size());
    }

    private static Map<Point, Character> performFold(Map<Point, Character> map, Point fold) {
        Map<Point, Character> result = new HashMap<>();
        if (fold.x == 0) {
            // Fold horizontal.
            for (Point point : map.keySet()) {
                if (point.y > fold.y) {
                    result.put(new Point(point.x, fold.y - (point.y - fold.y)), '#');
                } else {
                    result.put(point, '#');
                }
            }
        } else {
            // Fold vertical.
            for (Point point : map.keySet()) {
                if (point.x > fold.x) {
                    result.put(new Point(fold.x - (point.x - fold.x), point.y), '#');
                } else {
                    result.put(point, '#');
                }
            }
        }
        return result;
    }

    private static List<Point> readDots(Queue<String> input) {
        List<Point> result = new ArrayList<>();
        while (!"".equals(input.peek())) {
            String line = input.take();
            int[] ints = toInts(line.split(","));
            result.add(new Point(ints[0], ints[1]));
        }
        input.take();
        return result;
    }

    public static void solveB(Queue<String> input) {
        List<Point> dots = readDots(input);
        Map<Point, Character> map = new HashMap<>();
        for (Point dot : dots) {
            map.put(dot, '#');
        }

        Map<Point, Character> folded = performFold(map, new Point(655, 0));
        List<Point> folds = readFolds(input);
        for (Point fold : folds) {
            map = performFold(map, fold);
        }
        printMap(map);
    }

    private static List<Point> readFolds(Queue<String> input) {
        List<Point> result = new ArrayList<>();
        while (!input.isEmpty() && !"".equals(input.peek())) {
            String line = input.take();
            String[] parts = line.split(" ");
            String[] sub = parts[2].split("=");
            if (Objects.equals(sub[0], "x")) {
                result.add(new Point(Integer.parseInt(sub[1]), 0));
            } else {
                result.add(new Point(0, Integer.parseInt(sub[1])));
            }
        }
        input.take();
        return result;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day13.class.getResource("/day13/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        solveA(new ArrayQueue<>(strings));
        solveB(new ArrayQueue<>(strings));
    }
}
