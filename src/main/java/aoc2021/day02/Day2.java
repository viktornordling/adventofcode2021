package aoc2021.day02;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day2 {

    public static void solveA() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day02/easy.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        int depth = 0;
        int hor = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");
            int size = Integer.parseInt(parts[1]);
            if (parts[0].equals("down")) {
                depth += size;
            } else if (parts[0].equals("up")) {
                depth -= size;
            } else if (parts[0].equals("forward")) {
                hor += size;
            }
        }
        System.out.println(hor * depth);
    }

    public static void solveB() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day02/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        int depth = 0;
        int hor = 0;
        int aim = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");
            int size = Integer.parseInt(parts[1]);
            if (parts[0].equals("down")) {
                aim += size;
            } else if (parts[0].equals("up")) {
                aim -= size;
            } else if (parts[0].equals("forward")) {
                hor += size;
                depth += aim * size;
            }
        }
        System.out.println(hor * depth);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        solveA();
        solveB();
    }
}
