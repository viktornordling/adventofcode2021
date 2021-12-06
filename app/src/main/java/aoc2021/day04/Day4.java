package aoc2021.day04;

import aoc2021.day02.Day2;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day4 {

    static class BingoBoard {
        int[][] nums = new int[5][5];

        boolean hasBingo() {
            // check rows;
            for (int i = 0; i < 5; i++) {
                boolean bingo = true;
                for (int j = 0; j < 5; j++) {
                    if (nums[i][j] != -1) {
                        bingo = false;
                    }
                }
                if (bingo) {
                    return true;
                }
            }
            // check columns
            for (int i = 0; i < 5; i++) {
                boolean bingo = true;
                for (int j = 0; j < 5; j++) {
                    if (nums[j][i] != -1) {
                        bingo = false;
                    }
                }
                if (bingo) {
                    return true;
                }
            }
            return false;
        }

        public void set(Integer number) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (nums[j][i] == number) {
                        nums[j][i] = -1;
                    }
                }
            }
        }

        public int calc() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (nums[j][i] != -1) {
                        sum += nums[j][i];
                    }
                }
            }
            return sum;
        }
    }

    public static void solveA() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day04/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        String first = lines.get(0);
        List<Integer> numbers = Arrays.stream(first.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        List<BingoBoard> allBoards = new ArrayList<>();
        List<List<String>> partitions = Lists.partition(lines.subList(1, lines.size()).stream().filter(s -> !s.trim().equals("")).collect(Collectors.toList()), 5);
        for (int i = 0; i < partitions.size(); i++) {
            List<String> board = partitions.get(i);
            BingoBoard bboard = new BingoBoard();
            for (int j = 0; j < board.size(); j++) {
                List<Integer> nums = Arrays.stream(board.get(j).split(" ")).filter(s -> !s.trim().equals("")).map(Integer::parseInt).collect(Collectors.toList());
                for (int k = 0; k < nums.size(); k++) {
                    bboard.nums[j][k] = nums.get(k);
                }
            }
            allBoards.add(bboard);
        }
        for (Integer number : numbers) {
            for (BingoBoard board : allBoards) {
                board.set(number);
                if (board.hasBingo()) {
                    System.out.println("Bingo! " + board.calc() * number);
                }
            }
        }
    }

    public static void solveB() throws IOException, URISyntaxException {
        URI uri = Objects.requireNonNull(Day2.class.getResource("/day04/input.txt")).toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        String first = lines.get(0);
        List<Integer> numbers = Arrays.stream(first.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        List<BingoBoard> allBoards = new ArrayList<>();
        List<List<String>> partitions = Lists.partition(lines.subList(1, lines.size()).stream().filter(s -> !s.trim().equals("")).collect(Collectors.toList()), 5);
        for (int i = 0; i < partitions.size(); i++) {
            List<String> board = partitions.get(i);
            BingoBoard bboard = new BingoBoard();
            for (int j = 0; j < board.size(); j++) {
                List<Integer> nums = Arrays.stream(board.get(j).split(" ")).filter(s -> !s.trim().equals("")).map(Integer::parseInt).collect(Collectors.toList());
                for (int k = 0; k < nums.size(); k++) {
                    bboard.nums[j][k] = nums.get(k);
                }
            }
            allBoards.add(bboard);
        }
        for (Integer number : numbers) {
            for (BingoBoard board : allBoards) {
                board.set(number);
            }
            if (allBoards.size() == 1 && allBoards.get(0).hasBingo()) {
                System.out.println("Bingo! " + allBoards.get(0).calc() * number);
            }
            allBoards = allBoards.stream().filter(bingoBoard -> !bingoBoard.hasBingo()).collect(Collectors.toList());
        }
    }
    public static void main(String[] args) throws IOException, URISyntaxException {
//        solveA();
        solveB();
    }
}
