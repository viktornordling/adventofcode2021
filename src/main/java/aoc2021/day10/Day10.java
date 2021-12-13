package aoc2021.day10;

import aoc2021.day02.Day2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 {

    public static Optional<Character> findFirstInvalid(String s) {
        Stack<Character> stack = new Stack<>();
        Set<Character> openers = Set.of('(', '{', '[', '<');
        Map<Character, Character> closerToOpener = Map.of(')', '(', '}', '{', ']', '[', '>', '<');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (openers.contains(c)) {
                stack.push(c);
            } else {
                char opener = closerToOpener.getOrDefault(c, c);
                if (stack.isEmpty() || stack.pop() != opener) {
                    return Optional.of(c);
                }
            }
        }
        if (!stack.isEmpty()) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public static boolean isCorrupt(String s) {
        Stack<Character> stack = new Stack<>();
        Set<Character> openers = Set.of('(', '{', '[', '<');
        Map<Character, Character> closerToOpener = Map.of(')', '(', '}', '{', ']', '[', '>', '<');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (openers.contains(c)) {
                stack.push(c);
            } else {
                char opener = closerToOpener.getOrDefault(c, c);
                if (stack.isEmpty() || stack.pop() != opener) {
                    return true;
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return false;
    }

    public static List<Character> getClosers(String s) {
        Stack<Character> stack = new Stack<>();
        Set<Character> openers = Set.of('(', '{', '[', '<');
        Map<Character, Character> closerToOpener = Map.of(')', '(', '}', '{', ']', '[', '>', '<');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (openers.contains(c)) {
                stack.push(c);
            } else {
                char opener = closerToOpener.getOrDefault(c, c);
                if (stack.isEmpty() || stack.pop() != opener) {
                    return List.of();
                }
            }
        }
        if (!stack.isEmpty()) {
            List<Character> results = new ArrayList<>();
            while (!stack.isEmpty()) {
                results.add(stack.pop());
            }
            return results;
        }
        return List.of();
    }

    public static void solveA(List<String> input) {
        System.out.println(input.stream().map(Day10::findFirstInvalid).filter(Optional::isPresent).map(Optional::get).map(Day10::getValue).reduce(0, Integer::sum).intValue());
    }

    public static void solveB(List<String> input) {
        List<List<Character>> collect = input.stream().filter(line -> !isCorrupt(line)).map(Day10::getClosers).collect(Collectors.toList());

        List<Long> vals = new ArrayList<>();
        for (List<Character> line : collect) {
            vals.add(getCloseValueForLine(line));
        }
        List<Long> sorted = vals.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        System.out.println(sorted.get(sorted.size() / 2));
    }

    private static long getCloseValueForLine(List<Character> line) {
        long total = 0;
        for (Character closer : line) {
            total *= 5;
            total += getCloseValue(closer);
        }
        return total;
    }

    private static int getValue(Character character) {
        switch (character) {
            case ')':
                return 3;
            case ']':
                return 57;
            case '}':
                return 1197;
            case '>':
                return 25137;
        }
        throw new IllegalArgumentException("Dunno: " + character);
    }

    private static int getCloseValue(Character character) {
        switch (character) {
            case '(':
                return 1;
            case '[':
                return 2;
            case '{':
                return 3;
            case '<':
                return 4;
        }
        throw new IllegalArgumentException("Dunno: " + character);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day10/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        solveA(strings);
        solveB(strings);
    }
}
