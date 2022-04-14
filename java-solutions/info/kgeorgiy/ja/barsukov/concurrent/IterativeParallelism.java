package info.kgeorgiy.ja.barsukov.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Class for processing lists in multiple threads.
 */
public class IterativeParallelism implements ScalarIP {

    private ParallelMapper mapper;

    public IterativeParallelism() {
    }

    public IterativeParallelism(final ParallelMapper mapper) {
        this.mapper = mapper;
    }

    private static <T> List<Stream<? extends T>> split(final int threads, final List<? extends T> values) {
        final int partsSize = values.size() / threads;
        int remainder = values.size() % threads;
        final List<Stream<? extends T>> parts = new ArrayList<>(threads);
        int position = 0;
        while (position < values.size()) {
            final int partSize = partsSize + (remainder > 0 ? 1 : 0);
            parts.add(values.subList(position, position + partSize).stream());
            position += partSize;
            if (remainder > 0) {
                remainder--;
            }
        }
        return parts;
    }

    private <T, R> R execute(final List<Stream<? extends T>> parts, final Function<Stream<? extends T>, R> process,
            final Function<Stream<? extends R>, R> reduce) throws InterruptedException {
        final List<R> results;
        if (mapper == null) {
            results = new ArrayList<>(Collections.nCopies(parts.size(), null));
            final List<Thread> threads = createThreads(parts, process, results);
            joinAll(threads);
        } else {
            results = mapper.map(process, parts);
        }
        return reduce.apply(results.stream());
    }

    private static <T, M> List<Thread> createThreads(final List<Stream<? extends T>> parts,
            final Function<Stream<? extends T>, M> process, final List<M> results) {
        return IntStream.range(0, parts.size()).mapToObj(i -> {
            final Thread thread = new Thread(() -> results.set(i, process.apply(parts.get(i))));
            thread.start();
            return thread;
        }).toList();
    }

    /**
     * Joins all given threads.
     *
     * @param threads threads to join.
     * @throws InterruptedException when join is interrupted.
     */
    public static void joinAll(final List<Thread> threads) throws InterruptedException {
        for (final Thread thread : threads) {
            thread.join();
        }
    }

    private <T, R> R execute(final int threads, final List<? extends T> values,
            final Function<Stream<? extends T>, R> process,
            final Function<Stream<? extends R>, R> reduce) throws InterruptedException {
        return execute(split(threads, values), process, reduce);
    }

    private <T> T execute(final int threads, final List<? extends T> values,
            final Function<Stream<? extends T>, T> process) throws InterruptedException {
        return execute(split(threads, values), process, process);
    }

    /**
     * Returns maximum value.
     *
     * @param threads    number or concurrent threads.
     * @param values     values to get maximum of.
     * @param comparator value comparator.
     * @param <T>        value type.
     * @return maximum of given values
     * @throws InterruptedException             if executing thread was interrupted.
     * @throws java.util.NoSuchElementException if no values are given.
     */
    @Override
    public <T> T maximum(final int threads, final List<? extends T> values,
            final Comparator<? super T> comparator) throws InterruptedException {
        return execute(threads, values, stream -> stream.max(comparator).orElse(null));
    }

    /**
     * Returns minimum value.
     *
     * @param threads    number or concurrent threads.
     * @param values     values to get minimum of.
     * @param comparator value comparator.
     * @param <T>        value type.
     * @return minimum of given values
     * @throws InterruptedException             if executing thread was interrupted.
     * @throws java.util.NoSuchElementException if no values are given.
     */
    @Override
    public <T> T minimum(final int threads, final List<? extends T> values,
            final Comparator<? super T> comparator) throws InterruptedException {
        return execute(threads, values, stream -> stream.min(comparator).orElse(null));
    }

    /**
     * Returns whether all values satisfies predicate.
     *
     * @param threads   number or concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return whether all values satisfies predicate or {@code true}, if no values are given
     * @throws InterruptedException if executing thread was interrupted.
     */
    @Override
    public <T> boolean all(final int threads, final List<? extends T> values,
            final Predicate<? super T> predicate) throws InterruptedException {
        return execute(threads, values, stream -> stream.allMatch(predicate),
                stream -> stream.allMatch(Boolean::booleanValue));
    }

    /**
     * Returns whether any of values satisfies predicate.
     *
     * @param threads   number or concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return whether any value satisfies predicate or {@code false}, if no values are given
     * @throws InterruptedException if executing thread was interrupted.
     */
    @Override
    public <T> boolean any(final int threads, final List<? extends T> values,
            final Predicate<? super T> predicate) throws InterruptedException {
        return execute(threads, values, stream -> stream.anyMatch(predicate),
                stream -> stream.anyMatch(Boolean::booleanValue));
    }
}
