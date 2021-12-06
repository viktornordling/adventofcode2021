package aoc2021.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ArrayQueue<T> extends ArrayDeque<T> implements Queue<T> {

    public ArrayQueue(List<T> input) {
        super(input);
    }

    @Override
    public T take() {
        return poll();
    }

    @Override
    public List<T> take(int n) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < n && !isEmpty(); i++) {
            result.add(poll());
        }
        return result;
    }
}
