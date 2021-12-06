package aoc2021.util;

import java.util.Objects;

public class Point {
    int x1;
    int y1;

    public Point(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x1 == point.x1 && y1 == point.y1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, y1);
    }
}
