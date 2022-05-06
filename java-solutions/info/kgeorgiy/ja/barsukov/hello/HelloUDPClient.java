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
        ExecutorService workers = Executors.newFixedThreadPool(threads);
        IntStream.range(0, threads).forEach(threadId -> workers.execute(new RequestWorker(threadId)));
        workers.shutdown();
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
                for (int requestId = 0; requestId < requests; requestId++) {

                    String requestMessage = createMessage(prefix, threadId, requestId);
                    System.out.printf("Sent: %s%n", requestMessage);
                    DatagramPacket request = newMessageSendPacket(requestMessage, address, port);
                    while (!Thread.interrupted()) {
                        try {
                            socket.send(request);
                            DatagramPacket response = newEmptyReceivePacket(socket);
                            socket.receive(response);
                            String responseMessage = convertDataToString(response);
                            if (responseMessage.endsWith(requestMessage)) {
                                System.out.printf("Received: %s%n", responseMessage);
                                break;
                            }

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } catch (SocketException e) {
                System.err.println("Unable to establish a connection: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        }
    }
    public static String convertDataToString(DatagramPacket response) {
        return new String(response.getData(), response.getOffset(), response.getLength(), StandardCharsets.UTF_8);
    }

    public static DatagramPacket newMessageSendPacket(String message, InetAddress address, int port) {
        byte[] buf = message.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(buf, buf.length, address, port);
    }

    public static DatagramPacket newEmptyReceivePacket(DatagramSocket socket) throws SocketException {
        byte[] buf = new byte[socket.getReceiveBufferSize()];
        return new DatagramPacket(buf, buf.length);
    }


    private static String createMessage(String prefix, int threadId, int requestId) {
        return String.format("%s%d_%d", prefix, threadId, requestId);
    }
}
