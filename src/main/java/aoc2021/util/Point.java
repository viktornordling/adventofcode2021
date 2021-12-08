package aoc2021.util;

import java.util.Objects;

public class Point {
    int x;
    int y;

    public Point(int x1, int y1) {
        this.x = x1;
        this.y = y1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
