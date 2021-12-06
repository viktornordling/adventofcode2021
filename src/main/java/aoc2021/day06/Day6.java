package aoc2021.day06;

import aoc2021.day02.Day2;
import aoc2021.util.AocDeque;
import aoc2021.util.ArrayAocDeque;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day6 {

    static class Thing {

    }

    private static Thing parse(String line) {
        return new Thing();
    }

    public static void solveA(List<String> input){
        AocDeque<String> lines = new ArrayAocDeque<>(input);
        List<Thing> things = new ArrayList<>();
        while (!lines.isEmpty()) {
            things.add(parse(lines.take()));
        }
        for (Thing thing : things) {
            System.out.println(thing);
        }
    }

    public static void solveB(List<String> input){
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day06/input.txt")).toURI();
        List<String> input = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        solveA(input);
        solveB(input);
    }
}
