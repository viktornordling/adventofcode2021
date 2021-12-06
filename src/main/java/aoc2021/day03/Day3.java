package aoc2021.day03;

import aoc2021.day02.Day2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static aoc2021.util.Util.charsToString;

public class Day3 {

    public static void solveA() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day03/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

        List<List<Character>> bits = new ArrayList<>();
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                char bit = line.charAt(i);
                if (i >= bits.size()) {
                    bits.add(new ArrayList<>());
                }
                bits.get(i).add(bit);
            }
        }
        List<Character> gammaBits = new ArrayList<>();
        for (List<Character> bitList : bits) {
            int zeroes = (int) bitList.stream().filter(character -> character == '0').count();
            int ones = (int) bitList.stream().filter(character -> character == '1').count();
            if (zeroes > ones) {
                gammaBits.add('0');
            } else {
                gammaBits.add('1');
            }
        }
        List<Character> epsilonBits = new ArrayList<>();
        for (List<Character> bitList : bits) {
            int zeroes = (int) bitList.stream().filter(character -> character == '0').count();
            int ones = (int) bitList.stream().filter(character -> character == '1').count();
            if (zeroes > ones) {
                epsilonBits.add('1');
            } else {
                epsilonBits.add('0');
            }
        }
        int gamma = Integer.parseInt(charsToString(gammaBits), 2);
        int epsilon = Integer.parseInt(charsToString(epsilonBits), 2);
        System.out.println(gamma * epsilon);
    }

    public static void solveB() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day03/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

        List<List<Character>> bits = new ArrayList<>();
        for (String line : lines) {
            List<Character> chars = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                chars.add(line.charAt(i));
            }
            bits.add(chars);
        }
        int oxygenRating = findOxygenRating(bits);
        int scrubberRating = findScrubberRating(bits);
        System.out.println(oxygenRating * scrubberRating);
    }

    private static int findOxygenRating(List<List<Character>> bits) {
        List<List<Character>> copy = new ArrayList<>(bits);
        int index = 0;
        while (copy.size() > 1) {
            int finalIndex = index;
            int zeroes = (int) copy.stream().filter(characters -> characters.get(finalIndex) == '0').count();
            int ones = (int) copy.stream().filter(characters -> characters.get(finalIndex) == '1').count();
            if (zeroes > ones) {
                copy = copy.stream().filter(characters -> characters.get(finalIndex) == '0').collect(Collectors.toList());
            } else {
                copy = copy.stream().filter(characters -> characters.get(finalIndex) == '1').collect(Collectors.toList());
            }
            index++;
        }
        return Integer.parseInt(charsToString(copy.get(0)), 2);
    }

    private static int findScrubberRating(List<List<Character>> bits) {
        List<List<Character>> copy = new ArrayList<>(bits);
        int index = 0;
        while (copy.size() > 1) {
            int finalIndex = index;
            int zeroes = (int) copy.stream().filter(characters -> characters.get(finalIndex) == '0').count();
            int ones = (int) copy.stream().filter(characters -> characters.get(finalIndex) == '1').count();
            if (zeroes > ones) {
                copy = copy.stream().filter(characters -> characters.get(finalIndex) == '1').collect(Collectors.toList());
            } else {
                copy = copy.stream().filter(characters -> characters.get(finalIndex) == '0').collect(Collectors.toList());
            }
            index++;
        }
        return Integer.parseInt(charsToString(copy.get(0)), 2);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        solveA();
        solveB();
    }
}
