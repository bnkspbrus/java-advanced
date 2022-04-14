package info.kgeorgiy.ja.barsukov.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelMapperImpl implements ParallelMapper {

    private final List<Thread> results;
    private final SynchronizedQueue tasks;

    public ParallelMapperImpl(final int threads) {
        tasks = new SynchronizedQueue();
        final Runnable THREAD_TASK = () -> {
            try {
                while (!Thread.interrupted()) {
                    tasks.pollTask().run();
                }
            } catch (final InterruptedException ignored) {
            } finally {
                Thread.currentThread().interrupt();
            }
        };
        results = IntStream.range(0, threads).mapToObj(i -> {
            final Thread thread = new Thread(THREAD_TASK);
            thread.start();
            return thread;
        }).collect(Collectors.toList());
    }

    private static final class SynchronizedQueue {
        private final Queue<Runnable> tasks;

        SynchronizedQueue() {
            tasks = new ArrayDeque<>();
        }

        synchronized Runnable pollTask() throws InterruptedException {
            while (tasks.isEmpty()) {
                wait();
            }
            final Runnable task = tasks.poll();
            // :NOTE: possibly redundant
//            notifyAll();
            return task;
        }

        synchronized <T, R> void createAndAddTask(final Function<? super T, ? extends R> f,
                final List<? extends T> args, final int i, final SynchronizedList<R> resultsHandler) {
            final Runnable task = () -> {
                final R result = f.apply(args.get(i));
                resultsHandler.set(i, result);
            };
            tasks.add(task);
            // :NOTE: notify?
//            notifyAll();
            notify();
        }
    }

    private static final class SynchronizedList<R> {
        private final List<R> results;
        private int counter = 0;

        SynchronizedList(final int size) {
            results = new ArrayList<>(Collections.nCopies(size, null));
        }

        synchronized void set(final int i, final R value) {
            results.set(i, value);
            counter++;
            if (counter == results.size()) {
                notify();
            }
        }

        synchronized List<R> getList() throws InterruptedException {
            while (counter < results.size()) {
                wait();
            }
            return results;
        }
    }

    @Override
    public <T, R> List<R> map(final Function<? super T, ? extends R> f,
            final List<? extends T> args) throws InterruptedException {
        final SynchronizedList<R> resultsHandler = new SynchronizedList<>(args.size());
        IntStream.range(0, args.size()).forEach(i -> tasks.createAndAddTask(f, args, i, resultsHandler));
        return resultsHandler.getList();
    }

    @Override
    public void close() {
        results.forEach(Thread::interrupt);
        try {
            IterativeParallelism.joinAll(results);
        } catch (final InterruptedException ignored) {
        }
    }
}
