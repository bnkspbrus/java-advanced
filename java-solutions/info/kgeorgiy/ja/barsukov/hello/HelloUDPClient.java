package info.kgeorgiy.ja.barsukov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class HelloUDPClient implements HelloClient {

    private static final int TIMEOUT = 200;

    private int requests;
    private String prefix;

    private int port;

    private InetAddress address;

    private CountDownLatch latch;


    private static boolean hasNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    private static final String USAGE = "Usage:\njava HelloUDPClient name/ip port prefix threads requests";

    public static void main(String[] args) {
        if (args == null || args.length != 5 || hasNull(args)) {
            System.err.println(USAGE);
            return;
        }
        new HelloUDPClient().run(args[0], parseInt(args[1]), args[2], parseInt(args[3]), parseInt(args[4]));
    }

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        this.requests = requests;
        this.prefix = prefix;
        this.port = port;
        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        latch = new CountDownLatch(threads);
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        IntStream.range(0, threads).forEach(threadId -> exec.execute(new RequestWorker(threadId)));
        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    class RequestWorker implements Runnable {

        private final int threadId;

        RequestWorker(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(TIMEOUT);
                int receiveBufferSize = socket.getReceiveBufferSize();
                for (int requestId = 0; requestId < requests; requestId++) {

                    String requestMessage = createMessage(prefix, threadId, requestId);
                    System.out.printf("Sent: %s%n", requestMessage);
                    byte[] buf = requestMessage.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket request = new DatagramPacket(buf, buf.length, address, port);
                    while (!socket.isClosed() && !Thread.interrupted()) {
                        try {
                            socket.send(request);
                            DatagramPacket response = new DatagramPacket(new byte[receiveBufferSize],
                                    receiveBufferSize);
                            socket.receive(response);
                            String responseMessage = new String(response.getData(), response.getOffset(),
                                    response.getLength(), StandardCharsets.UTF_8);
                            System.out.printf("Received: %s%n", responseMessage);
                            break;

                        } catch (IOException ignored) {
                        }
                    }
                }
            } catch (SocketException e) {
                System.err.println("Unable to establish a connection: " + e.getMessage());
            }
            latch.countDown();
        }
    }

    private static String createMessage(String prefix, int threadId, int requestId) {
        return String.format("%s%d_%d", prefix, threadId, requestId);
    }
}
