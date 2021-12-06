package aoc2021.util;

import java.util.Deque;
import java.util.List;

public interface Queue<T> extends Deque<T> {

    /**
     * Takes one element from the queue.
     */
    T take();

    /**
     * Takes n elements from the queue.
     */
    List<T> take(int n);
}
