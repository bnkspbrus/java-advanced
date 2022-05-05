package info.kgeorgiy.ja.barsukov.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class WebCrawler implements Crawler, AdvancedCrawler {

    final Set<String> downloaded = ConcurrentHashMap.newKeySet();
    final Set<String> marked = ConcurrentHashMap.newKeySet();

    final Map<String, Semaphore> semaphores = new HashMap<>();

    final Queue<String> newLayer = new ConcurrentLinkedQueue<>();

    final ConcurrentMap<String, IOException> errors = new ConcurrentHashMap<>();

    final Downloader downloader;

    private static final String USAGE = "Usage:\njava WebCrawler url [depth [downloads [extractors [perHost]]]]";

    private final ExecutorService downloaders, extractors;

    private final int perHost;

    private static int EXTRA_THREADS = 5;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        int downloadMaximumPoolSize = Math.max(downloaders - EXTRA_THREADS, 1);
        int extractMaximumPoolSize = Math.max(extractors - EXTRA_THREADS, 1);
        this.downloader = downloader;
        this.downloaders = new ThreadPoolExecutor(downloadMaximumPoolSize, downloadMaximumPoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        this.extractors = new ThreadPoolExecutor(extractMaximumPoolSize, extractMaximumPoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        this.perHost = perHost;
    }

    Phaser phaser = new Phaser(1);

    @Override
    public Result download(String url, int depth) {
        return download(url, depth, true);
    }

    Result download(String url, int depth, boolean mutable) {
        final Queue<String> curLayer = new ArrayDeque<>();
        marked.add(url);
        curLayer.add(url);
//        for (int i = 0; i < depth; i++) {
        IntStream.range(0, depth).forEach(i -> {
            curLayer.forEach(link -> {
                final DownloadWorker worker;
                if ((worker = createDownloadWorker(link, depth - i, mutable)) != null) {
                    phaser.register();
                    downloaders.submit(worker);
                }
            });
            phaser.arriveAndAwaitAdvance();
            curLayer.clear();
            curLayer.addAll(newLayer);
            newLayer.clear();
        });
//        System.out.println(downloaded);
        final List<String> filtered = downloaded.stream().filter(link -> !errors.containsKey(link)).toList();
        return new Result(filtered, errors);
    }

    private DownloadWorker createDownloadWorker(String link, int depth, boolean mutable) {
        try {
            final String host = URLUtils.getHost(link);
            if (mutable) {
                semaphores.putIfAbsent(host, new Semaphore(perHost));
            }
            final Semaphore semaphore = semaphores.get(host);
            if (semaphore == null) {
                return null;
            }
            return new DownloadWorker(link, depth, semaphore);
        } catch (MalformedURLException e) {
            errors.put(link, e);
        }
        return null;
    }

    @Override
    // :NOTE: почитать как закрывать тредпуллы
    public void close() {
        downloaders.shutdown();
        extractors.shutdown();
    }

    @Override
    public Result download(String url, int depth, List<String> hosts) {
        hosts.forEach(link -> semaphores.put(link, new Semaphore(perHost)));
        return download(url, depth, false);
    }

    private static boolean hasNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    public static void main(String[] args) {
        if (args == null || args.length < 1 || args.length > 5 || hasNull(args)) {
            System.err.println(USAGE);
            return;
        }
        int depth, downloaders, extractors, perHost;
        depth = downloaders = extractors = perHost = 1;
        if (args.length >= 2) {
            depth = parseInt(args[1]);
        }
        if (args.length >= 3) {
            downloaders = parseInt(args[2]);
        }
        if (args.length >= 4) {
            extractors = parseInt(args[3]);
        }
        if (args.length == 5) {
            perHost = parseInt(args[4]);
        }
        try (Crawler crawler = new WebCrawler(new CachingDownloader(), downloaders, extractors, perHost)) {
            crawler.download(args[0], depth);
        } catch (IOException ignored) {

        }
    }

    class ExtractWorker implements Runnable, Comparable<Runnable> {
        //        private final WebCrawler2 crawler;
        private final Document doc;
        private final int depth;

        ExtractWorker(Document doc, int depth) {
//            this.crawler = WebCrawler;
            this.doc = doc;
            this.depth = depth;
        }

        @Override
        public void run() {
            try {
//                final var pair = crawler.getDocQueue().take();
                try {
                    doc.extractLinks().stream().filter(marked::add).forEach(newLayer::add);
                } catch (IOException ignored) {

                }
            } finally {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public int compareTo(Runnable o) {
            return -Integer.compare(depth, ((DownloadWorker) o).depth);
        }
    }

    class DownloadWorker implements Runnable, Comparable<Runnable> {
        //        private final WebCrawler2 crawler;
        private final String url;
        private final int depth;

        private final Semaphore semaphore;

        DownloadWorker(String url, int depth, Semaphore semaphore) {
//            this.crawler = WebCrawler;
            this.url = url;
            this.depth = depth;
            this.semaphore = semaphore;

        }

        @Override
        public void run() {
            try {
//                final var pair = crawler.getUrlQueue().take();
                if (downloaded.add(url)) {

//                System.out.println(pair.depth);
                    try {
                        final Document doc;
                        try {
                            semaphore.acquire();
                            doc = downloader.download(url);
                        } finally {
                            semaphore.release();
                        }
                        if (depth > 1) {
                            phaser.register();
                            extractors.submit(new ExtractWorker(doc, depth));
                        }
                    } catch (IOException e) {
                        errors.put(url, e);
                    }
                }
            } catch (InterruptedException ignored) {
                System.out.println("Downloader interrupted");
            } finally {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public int compareTo(Runnable o) {
            return -Integer.compare(depth, ((ExtractWorker) o).depth);
        }
    }
}
