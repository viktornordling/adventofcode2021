package aoc2021.day01;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static aoc2021.util.Util.toInts;

public class Day1 {

    public static void solveGog() {

    }

    public static void solveA() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day1.class.getResource("/day01/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        List<Integer> ints = toInts(lines);
        int incs = 0;
        int last = ints.get(0);
        for (Integer i : ints) {
            if (i > last) {
                incs++;
            }
            last = i;
        }
        System.out.println(incs);
    }

    public static void solveB() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day1.class.getResource("/day01/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        List<Integer> ints = toInts(lines);
        int incs = 0;
        int slidingSum = ints.get(0) + ints.get(1) + ints.get(2);
        int last = slidingSum;
        for (int i = 3; i < ints.size(); i++) {
            slidingSum += ints.get(i);
            slidingSum -= ints.get(i - 3);
            if (slidingSum > last) {
                incs++;
            }
            last = slidingSum;
        }
        System.out.println(incs);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        long start = System.currentTimeMillis();
        solveA();
        solveB();
        System.out.println(System.currentTimeMillis() - start);
    }
}
