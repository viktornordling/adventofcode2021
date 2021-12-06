package aoc2021.day06;

import aoc2021.day02.Day2;
import aoc2021.util.Queue;
import aoc2021.util.ArrayQueue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static aoc2021.util.Util.freq;
import static aoc2021.util.Util.toInts;

public class Day6 {

    static class Fish {
        int days;

        public Fish(int days) {
            this.days = days;
        }
    }

    public static void solveA(List<Integer> initial) {
        List<Fish> fishes = initial.stream().map(Fish::new).collect(Collectors.toList());

        for (int i = 0; i < 80; i++) {
            List<Fish> fishToAdd = new ArrayList<>();
            for (Fish fish : fishes) {
                if (fish.days == 0) {
                    fishToAdd.add(new Fish(8));
                    fish.days = 6;
                } else {
                    fish.days--;
                }
            }
            fishes.addAll(fishToAdd);
        }
        System.out.println(fishes.size());
    }

    public static void solveB(List<Integer> initial) {
        Map<Integer, Long> fishToDay = freq(initial);

        for (int i = 0; i < 256; i++) {
            Map<Integer, Long> newMap = new HashMap<>();
            for (Integer days : fishToDay.keySet()) {
                if (days == 0) {
                    newMap.put(6, fishToDay.get(0));
                    newMap.put(8, fishToDay.get(0));
                } else {
                    newMap.put(days - 1, newMap.getOrDefault(days - 1, 0L) + fishToDay.get(days));
                }
            }
            fishToDay = newMap;
        }
        long sum = 0;
        for (Long value : fishToDay.values()) {
            sum += value;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day06/input.txt")).toURI();
        List<Integer> initial = toInts(Files.readAllLines(Paths.get(uri), Charset.defaultCharset()).get(0));
        solveA(initial);
        solveB(initial);
    }
}
