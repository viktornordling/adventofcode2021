package aoc2021.day14;

import aoc2021.util.ArrayQueue;
import aoc2021.util.Queue;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static aoc2021.util.Util.charFreq;
import static aoc2021.util.Util.toInts;

public class Day14 {

    public static void solveA(String input, Map<String, Character> pairsInsertions) {
        String cur = input;
        for (int i = 0; i < 10; i++) {
            cur = doInsertions(cur, pairsInsertions);
        }
        Map<Character, Long> frequencies = Util.charFreq(cur);
        Character max = Util.findMax(frequencies);
        Character min = Util.findMin(frequencies);
        System.out.println(frequencies.get(max) - frequencies.get(min));
    }

    public static void solveB(String input, Map<String, Character> pairInsertions) {
        Map<Pair<String, Integer>, Long> dp = new HashMap<>();
        Map<Pair<Character, Integer>, Long> dp2 = new HashMap<>();
        Map<Character, Set<String>> reversePairs = new HashMap<>();
        for (String s : pairInsertions.keySet()) {
            Set<String> set = reversePairs.getOrDefault(pairInsertions.get(s), new HashSet<>());
            set.add(s);
            reversePairs.put(pairInsertions.get(s), set);
        }
        for (int i = 0; i < input.length() - 1; i++) {
            String pair = input.substring(i, i + 2);
            long cur = dp.getOrDefault(Pair.of(pair, 0), 0L);
            dp.put(Pair.of(pair, 0), cur + 1);
        }
        Set<Character> allChars = new HashSet<>();
        for (String s : pairInsertions.keySet()) {
            allChars.add(s.charAt(0));
            allChars.add(s.charAt(1));
            allChars.add(pairInsertions.get(s));
        }
        for (int i = 0; i < input.length() ; i++) {
            char c = input.charAt(i);
            long cur = dp2.getOrDefault(Pair.of(c, 0), 0L);
            dp2.put(Pair.of(c, 0), cur + 1);
        }
        for (int i = 1; i <= 40; i++) {
            for (String s : pairInsertions.keySet()) {
                Long above = dp.get(Pair.of(s, i - 1));
                if (above != null) {
                    Character character = pairInsertions.get(s);
                    String left = Util.charsToString(List.of(s.charAt(0), character));
                    String right = Util.charsToString(List.of(character, s.charAt(1)));
                    Long curL = dp.getOrDefault(Pair.of(left, i), 0L);
                    Long curR = dp.getOrDefault(Pair.of(right, i), 0L);
                    dp.put(Pair.of(left, i), curL + above);
                    dp.put(Pair.of(right, i), curR + above);
                }
            }
            for (Character c : allChars) {
                long above = dp2.getOrDefault(Pair.of(c, i - 1), 0L);
                Set<String> s = reversePairs.get(c);
                long totPairsAbove = 0L;
                for (String s1 : s) {
                    totPairsAbove += dp.getOrDefault(Pair.of(s1, i - 1), 0L);
                }
                dp2.put(Pair.of(c, i), above + totPairsAbove);
            }
        }
        Set<Pair<Character, Integer>> keys = dp2.keySet().stream().filter(stringIntegerPair -> stringIntegerPair.getRight() == 40).collect(Collectors.toSet());
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;
        for (Pair<Character, Integer> key : keys) {
            max = Math.max(max, dp2.get(key));
            min = Math.min(min, dp2.get(key));
        }
        System.out.println(max - min);
    }

    private static String doInsertions(String input, Map<String, Character> pairs) {
        StringBuilder sb = new StringBuilder();
        sb.append(input.charAt(0));
        for (int i = 0; i < input.length() - 1; i++) {
            String pair = input.substring(i, i + 2);
            Character character = pairs.get(pair);
            if (character != null) {
                sb.append(character);
                sb.append(pair.charAt(1));
            } else {
                sb.append(pair.charAt(1));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day14.class.getResource("/day14/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        Queue<String> input = new ArrayQueue<>(strings);
        String polymer = input.take();
        input.take();
        Map<String, Character> pairs = parsePairs(input);
        solveA(polymer, pairs);
        solveB(polymer, pairs);
    }

    private static Map<String, Character> parsePairs(Queue<String> input) {
        Map<String, Character> result = new HashMap<>();
        while (!input.isEmpty()) {
            String line = input.take();
            String[] split = line.split("->");
            result.put(split[0].trim(), split[1].trim().charAt(0));
        }
        return result;
    }
}
