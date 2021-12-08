package aoc2021.day07;

import aoc2021.day02.Day2;
import one.util.streamex.EntryStream;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static aoc2021.util.Util.freq;
import static aoc2021.util.Util.toInts;

public class Day7 {

    public static void solveA(String input) {
        List<Integer> positions = toInts(input);
        Map<Integer, Long> posToCounts = freq(positions);
        long minCost = Long.MAX_VALUE;
        for (Integer integer : posToCounts.keySet()) {
            long costToAlign = getCostToAlign(integer, posToCounts);
            if (costToAlign < minCost) {
                minCost = costToAlign;
            }
        }
        System.out.println(minCost);
    }

    public static void solveB(String input) {
        List<Integer> positions = toInts(input);
        Map<Integer, Long> posToCounts = freq(positions);
        long minCost = Long.MAX_VALUE;
        int min = posToCounts.keySet().stream().min(Comparator.naturalOrder()).get();
        int max = posToCounts.keySet().stream().max(Comparator.naturalOrder()).get();
        for (int i = min; i < max; i++) {
            long costToAlign = getCostToAlignB(i, posToCounts);
            if (costToAlign < minCost) {
                minCost = costToAlign;
            }
        }
        System.out.println(minCost);
    }

    private static long getCostToAlign(Integer alignOn, Map<Integer, Long> posToCounts) {
        long totalCost = 0;
        for (Integer pos : posToCounts.keySet()) {
            Long numCrabs = posToCounts.get(pos);
            totalCost += (numCrabs * Math.abs(alignOn - pos));
        }
        return totalCost;
    }

    private static long getCostToAlignB(Integer alignOn, Map<Integer, Long> posToCounts) {
        long totalCost = 0;
        for (Integer pos : posToCounts.keySet()) {
            Long numCrabs = posToCounts.get(pos);
            int diff = Math.abs(alignOn - pos);
            int diffSum = diff * (diff + 1) / 2;
            totalCost += (numCrabs * diffSum);
        }
        return totalCost;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day07/input.txt")).toURI();
        String input = Files.readAllLines(Paths.get(uri), Charset.defaultCharset()).get(0);
        solveA(input);
        solveB(input);
    }
}
