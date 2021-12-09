package aoc2021.day08;

import aoc2021.day02.Day2;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static aoc2021.util.Util.onlyElement;
import static aoc2021.util.Util.sortString;
import static aoc2021.util.Util.toInts;

public class Day8 {

    public static void solveA(List<String> input) {
        List<String> rightSide = input.stream().map(line -> line.split("\\|")[1].trim()).collect(Collectors.toList());
        int total = 0;
        Set<Integer> lengths = Set.of(2, 3, 4, 7);
        for (String s : rightSide) {
            List<String> collect = Arrays.stream(s.split(" ")).filter(it -> lengths.contains(it.length())).collect(Collectors.toList());
            total += collect.size();
        }
        System.out.println(total);
    }

    public static void solveB(List<String> input) {
        int sum = 0;
        for (String line : input) {
            String sides[] = line.split("\\|");
            String leftSide = sides[0].trim();
            String rightSide = sides[1].trim();
            List<String> parts = Arrays.stream(leftSide.split(" ")).collect(Collectors.toList());
            Map<Integer, Set<String>> countToString = new HashMap<>();
            for (String part : parts) {
                Set<String> set = countToString.getOrDefault(part.length(), new HashSet<>());
                set.add(part);
                countToString.put(part.length(), set);
            }
            String one = onlyElement(countToString.get(2));
            String four = onlyElement(countToString.get(4));
            String seven = onlyElement(countToString.get(3));
            String eight = onlyElement(countToString.get(7));
            String five = findFive(countToString.get(5), one, four);
            String two = findTwo(countToString.get(5), one, four);
            String three = findThree(countToString.get(5), one, four);
            String six = findSix(countToString.get(6), five, four);
            String nine = findNine(countToString.get(6), five, four);
            String zero = findZero(countToString.get(6), six, nine);

            Map<String, Integer> stringToInt = new HashMap<>();
            stringToInt.put(sortString(zero), 0);
            stringToInt.put(sortString(one), 1);
            stringToInt.put(sortString(two), 2);
            stringToInt.put(sortString(three), 3);
            stringToInt.put(sortString(four), 4);
            stringToInt.put(sortString(five), 5);
            stringToInt.put(sortString(six), 6);
            stringToInt.put(sortString(seven), 7);
            stringToInt.put(sortString(eight), 8);
            stringToInt.put(sortString(nine), 9);

            List<String> rightParts = Arrays.stream(rightSide.split(" ")).collect(Collectors.toList());
            String num = "";
            for (String rightPart : rightParts) {
                String sorted = sortString(rightPart);
                num += stringToInt.get(sorted);
            }
            sum += Integer.parseInt(num);
        }
        System.out.println(sum);
    }

    private static String findZero(Set<String> strings, String six, String nine) {
        strings.remove(six);
        strings.remove(nine);
        return onlyElement(strings);
    }

    private static String findNine(Set<String> strings, String five, String four) {
        Set<Character> fiveC = getCharacters(five);
        Set<Character> fourC = getCharacters(four);
        for (String string : strings) {
            Set<Character> characters = getCharacters(string);
            if (characters.containsAll(fiveC) && characters.containsAll(fourC)) {
                return string;
            }
        }
        throw new IllegalArgumentException("Could find number!");
    }

    private static String findSix(Set<String> strings, String five, String four) {
        Set<Character> fiveC = getCharacters(five);
        Set<Character> fourC = getCharacters(four);
        for (String string : strings) {
            Set<Character> characters = getCharacters(string);
            if (characters.containsAll(fiveC) && !characters.containsAll(fourC)) {
                return string;
            }
        }
        throw new IllegalArgumentException("Could find number!");
    }

    private static String findThree(Set<String> strings, String one, String four) {
        Set<Character> oneC = getCharacters(one);
        Set<Character> fourC = getCharacters(four);
        for (String string : strings) {
            Set<Character> characters = getCharacters(string);
            int oneInt = Sets.intersection(oneC, characters).size();
            int fourInt = Sets.intersection(fourC, characters).size();
            if (oneInt == 2 && fourInt == 3) {
                return string;
            }
        }
        throw new IllegalArgumentException("Could find number!");
    }

    private static String findFive(Set<String> strings, String one, String four) {
        Set<Character> oneC = getCharacters(one);
        Set<Character> fourC = getCharacters(four);
        for (String string : strings) {
            Set<Character> characters = getCharacters(string);
            int oneInt = Sets.intersection(oneC, characters).size();
            int fourInt = Sets.intersection(fourC, characters).size();
            if (oneInt == 1 && fourInt == 3) {
                return string;
            }
        }
        throw new IllegalArgumentException("Could find number!");
    }

    private static String findTwo(Set<String> strings, String one, String four) {
        Set<Character> oneC = getCharacters(one);
        Set<Character> fourC = getCharacters(four);
        for (String string : strings) {
            Set<Character> characters = getCharacters(string);
            int oneInt = Sets.intersection(oneC, characters).size();
            int fourInt = Sets.intersection(fourC, characters).size();
            if (oneInt == 1 && fourInt == 2) {
                return string;
            }
        }
        throw new IllegalArgumentException("Could find number!");
    }

    private static Set<Character> getCharacters(String part) {
        byte[] bytes = part.getBytes(StandardCharsets.UTF_8);
        Set<Character> chars = new HashSet<>();
        for (byte bb : bytes) {
            chars.add((char) bb);
        }
        return chars;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day08/input.txt")).toURI();
        List<String> strings = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        solveA(strings);
        solveB(strings);
    }
}
