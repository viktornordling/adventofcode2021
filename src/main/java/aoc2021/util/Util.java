package aoc2021.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Util {

    public static void printGrid(Map<Point, Integer> points) {
        int minX = points.keySet().stream().min(Comparator.comparingInt(o -> o.x)).get().x;
        int maxX = points.keySet().stream().max(Comparator.comparingInt(o -> o.x)).get().x;
        int minY = points.keySet().stream().min(Comparator.comparingInt(o -> o.y)).get().y;
        int maxY = points.keySet().stream().max(Comparator.comparingInt(o -> o.y)).get().y;

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

    public static String charsToString(List<Character> str) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : str) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public static int[] toInts(String[] strings) {
        int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Integer.parseInt(strings[i].trim());
        }
        return result;
    }

    public static List<Integer> toInts(List<String> lines) {
        List<Integer> result = new ArrayList<>();
        for (String line : lines) {
            result.add(Integer.parseInt(line));
        }
        return result;
    }

    public static List<Integer> toInts(String line) {
        List<Integer> result = new ArrayList<>();
        String[] parts = line.split(",");
        for (String part : parts) {
            result.add(Integer.parseInt(part.trim()));
        }
        return result;
    }

    public static Map<Integer, Long> freq(List<Integer> nums) {
        Map<Integer, Long> result = new HashMap<>();
        for (Integer integer : nums) {
            result.put(integer, result.getOrDefault(integer, 0L) + 1);
        }
        return result;
    }

    public static String sortString(String inputString) {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    public static <T> T onlyElement(Set<T> set) {
        if (set.size() != 1) {
            throw new IllegalArgumentException("Size not 1: " + set.size());
        }
        return set.stream().findFirst().get();
    }
}
