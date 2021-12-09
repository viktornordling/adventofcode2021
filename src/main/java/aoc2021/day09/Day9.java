package aoc2021.day09;

import aoc2021.day02.Day2;
import aoc2021.util.ArrayQueue;
import aoc2021.util.Point;
import aoc2021.util.Queue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day9 {

    public static void solveA(List<String> input) {
        Map<Point, Integer> map = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int i = 0; i < line.length(); i++) {
                int x = i;
                map.put(new Point(x, y), Integer.parseInt("" + line.charAt(i)));
            }
        }
        int risk = 0;
        for (Point point : map.keySet()) {
            List<Point> neighbors = getNeighbors(point);
            Integer integer = map.get(point);
            boolean lowest = true;
            for (Point neighbor : neighbors) {
                if (map.containsKey(neighbor)) {
                    Integer val = map.get(neighbor);
                    if (val <= integer) {
                        lowest = false;
                    }
                }
            }
            if (lowest) {
                risk += integer + 1;
            }
        }
        System.out.println(risk);
    }

    private static List<Point> getNeighbors(Point point) {
        return List.of(new Point(point.x + 1, point.y), new Point(point.x - 1, point.y), new Point(point.x, point.y + 1), new Point(point.x, point.y - 1));
    }

    public static void solveB(List<String> input) {
        Map<Point, Integer> map = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int i = 0; i < line.length(); i++) {
                int x = i;
                map.put(new Point(x, y), Integer.parseInt("" + line.charAt(i)));
            }
        }

        List<Integer> basins = new ArrayList<>();
        for (Point point : map.keySet()) {
            Integer val = map.get(point);
            if (val != 9 && val != -1) {
                basins.add(dfs(map, point));
            }
        }
        List<Integer> sorted = basins.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int prod = new ArrayQueue<>(sorted).take(3).stream().reduce(1, (integer, integer2) -> integer * integer2).intValue();
        System.out.println(prod);
    }

    private static int dfs(Map<Point, Integer> map, Point point) {
        map.put(point, -1);
        List<Point> neighbors = getNeighbors(point);
        int seen = 0;
        for (Point neighbor : neighbors) {
            if (map.containsKey(neighbor)) {
                Integer val = map.get(neighbor);
                if (val != 9 && val != -1) {
                    seen += dfs(map, neighbor);
                }
            }
        }
        return seen + 1;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day09/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        solveA(strings);
        solveB(strings);
    }
}
