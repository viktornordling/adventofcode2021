package aoc2021.day11;

import aoc2021.day02.Day2;
import aoc2021.util.Point;
import aoc2021.util.Util;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class Day11 {

    private static final Logger log = LogManager.getLogger();

    public static void solveA(Map<Point, Integer> map) {
        int totalFlashes = 0;
        for (int i = 0; i < 100; i++) {
            Pair<Integer, Map<Point, Integer>> result = flash(map);
            totalFlashes += result.getLeft();
            map = result.getRight();
        }
        System.out.println(totalFlashes);
    }

    private static Pair<Integer, Map<Point, Integer>> flash(Map<Point, Integer> map) {
        Queue<Point> flashers = new LinkedList<>();
        List<Point> dirs = List.of(new Point(-1, -1), new Point(0, -1), new Point(1, -1), new Point(-1, 0), new Point(1, 0), new Point(-1, 1), new Point(0, 1), new Point(1, 1));
        int flashes = 0;
        for (Point point : map.keySet()) {
            int value = map.get(point) + 1;
            map.put(point, value);
            if (value > 9) {
                flashes++;
                flashers.add(point);
            }
        }
        while (!flashers.isEmpty()) {
            Point flasher = flashers.poll();
            for (Point dir : dirs) {
                Point neighbor = new Point(flasher.x + dir.x, flasher.y + dir.y);
                if (map.containsKey(neighbor)) {
                    int value = map.get(neighbor) + 1;
                    map.put(neighbor, value);
                    if (value == 10) {
                        flashes++;
                        flashers.add(neighbor);
                    }
                }
            }
        }
        for (Point point : map.keySet()) {
            int value = map.get(point);
            if (value > 9) {
                map.put(point, 0);
            }
        }
        return Pair.of(flashes, map);
    }

    public static void solveB(Map<Point, Integer> map) {
        int i = 0;
        while (true) {
            i++;
            Pair<Integer, Map<Point, Integer>> result = flash(map);
            map = result.getRight();
            if (map.values().stream().allMatch(integer -> integer == 0)) {
                System.out.println(i);
                return;
            }
        }
    }

    public static Map<Point, Integer> parse(List<String> input) {
        Map<Point, Integer> result = new HashMap<>();
        int y = 0;
        for (String line : input) {
            List<Character> characters = Util.stringToCharacters(line);
            int x = 0;
            for (Character character : characters) {
                result.put(new Point(x, y), Integer.parseInt("" + character));
                x++;
            }
            y++;
        }
        return result;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day11/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        solveA(parse(strings));
        solveB(parse(strings));
    }
}
