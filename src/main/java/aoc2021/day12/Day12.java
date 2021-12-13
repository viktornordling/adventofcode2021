package aoc2021.day12;

import aoc2021.util.ArrayQueue;
import aoc2021.util.Queue;
import com.google.common.collect.Sets;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static aoc2021.util.Util.toInts;

public class Day12 {

    private static Set<List<String>> allPaths = new HashSet<>();

    public static void solveA(Map<String, Set<String>> neighbors) {
        dfs(neighbors, "start", "end", Set.of(), List.of("start"));
        System.out.println(allPaths.size());
    }

    public static void solveB(Map<String, Set<String>> neighbors) {
        allPaths.clear();
        Set<String> smallCaves = neighbors.keySet().stream().filter(Day12::isSmallCave).collect(Collectors.toSet());
        smallCaves.remove("start");
        smallCaves.remove("end");
        for (String smallCave : smallCaves) {
            dfsB(neighbors, "start", "end", Map.of("start", 1), List.of("start"), smallCave);
        }
        System.out.println(allPaths.size());
    }

    private static void dfs(Map<String, Set<String>> neighbors, String current, String goal, Set<String> smallCaves, List<String> path) {
        if (current.equals(goal)) {
            allPaths.add(path);
            return;
        }
        Set<String> ns = neighbors.get(current);
        for (String n : ns) {
            if (!n.toLowerCase().equals(n) || (isSmallCave(n) && !path.contains(n))) {
                List<String> newPath = new ArrayList<>(path);
                newPath.add(n);
                Set<String> newSmallCaves = new HashSet<>(smallCaves);
                if (isSmallCave(n)) {
                    newSmallCaves.add(n);
                }
                dfs(neighbors, n, goal, newSmallCaves, newPath);
            }
        }
    }

    private static void dfsB(Map<String, Set<String>> neighbors, String current, String goal, Map<String, Integer> smallCaves, List<String> path, String allowedDouble) {
        if (current.equals(goal)) {
            allPaths.add(path);
            return;
        }
        Set<String> ns = neighbors.get(current);
        for (String n : ns) {
            int smallCaveVisits = smallCaveVisits(smallCaves, n);
            if (!isSmallCave(n) || (isSmallCave(n) && smallCaveVisits < 1) || (allowedDouble.equals(n) && smallCaveVisits < 2)) {
                List<String> newPath = new ArrayList<>(path);
                newPath.add(n);
                Map<String, Integer> newSmallCaves = new HashMap<>(smallCaves);
                if (isSmallCave(n)) {
                    newSmallCaves.put(n, smallCaveVisits + 1);
                }
                dfsB(neighbors, n, goal, newSmallCaves, newPath, allowedDouble);
            }
        }
    }

    private static int smallCaveVisits(Map<String, Integer> smallCaves, String n) {
        return smallCaves.getOrDefault(n, 0);
    }

    private static boolean isSmallCave(String n) {
        return n.toLowerCase().equals(n);
    }

    private static Map<String, Set<String>> parse(Queue<String> input) {
        Map<String, Set<String>> neighbors = new HashMap<>();
        while (!input.isEmpty()) {
            String line = input.take();
            String[] split = line.split("-");
            String left = split[0];
            String right = split[1];
            Set<String> n1 = neighbors.getOrDefault(left, new HashSet<>());
            n1.add(right);
            neighbors.put(left, n1);

            Set<String> n2 = neighbors.getOrDefault(right, new HashSet<>());
            n2.add(left);
            neighbors.put(right, n2);
        }
        return neighbors;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day12.class.getResource("/day12/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        Queue<String> input = new ArrayQueue<>(strings);
        Map<String, Set<String>> neighbors = parse(input);
        solveA(neighbors);
        solveB(neighbors);
    }
}
