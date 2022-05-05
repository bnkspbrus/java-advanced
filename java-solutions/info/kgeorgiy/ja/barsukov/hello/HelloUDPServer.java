package info.kgeorgiy.ja.barsukov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class HelloUDPServer implements HelloServer {

    private ExecutorService distributor, workers;

    private static boolean hasNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    Phaser phaser;

    private static final String USAGE = "Usage:\njava HelloUDPServer port threads";

    private DatagramSocket socket;

    public static void main(String[] args) {
        if (args == null || args.length != 2 || hasNull(args)) {
            System.err.println(USAGE);
            return;
        }
        try (HelloServer server = new HelloUDPServer()) {
            server.start(parseInt(args[0]), parseInt(args[1]));
        }
    }

    @Override
    public void start(int port, int threads) {
        try {
            socket = new DatagramSocket(port);
            workers = Executors.newFixedThreadPool(threads);
            distributor = Executors.newSingleThreadExecutor();
            final int receiveBufferSize = socket.getReceiveBufferSize();
            phaser = new Phaser(1);
            distributor.execute(() -> {
                try {
                    while (!Thread.interrupted()) {
                        final DatagramPacket request = new DatagramPacket(new byte[receiveBufferSize],
                                receiveBufferSize);
                        socket.receive(request);
                        phaser.register();
                        workers.submit(new ResponseWorker(request));
                    }
                } catch (IOException ignored) {
                }
            });
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }

    class ResponseWorker implements Runnable {
        final DatagramPacket request;

        ResponseWorker(DatagramPacket request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                String requestMessage = new String(request.getData(), request.getOffset(), request.getLength(),
                        StandardCharsets.UTF_8);
                System.out.printf("Received: %s%n", requestMessage);
                String responseMessage = String.format("Hello, %s", requestMessage);
                byte[] buf = responseMessage.getBytes(StandardCharsets.UTF_8);
                DatagramPacket response = new DatagramPacket(buf, buf.length, request.getAddress(), request.getPort());
                try {
                    socket.send(response);
                    System.out.printf("Sent: %s%n", responseMessage);
                } catch (IOException ignored) {
                }
            } finally {
                phaser.arriveAndDeregister();
            }
        }
    }

    @Override
    public void close() {
        if (socket == null) {
            return;
        }
        socket.close();
        distributor.shutdown();
        workers.shutdown();
        phaser.arriveAndAwaitAdvance();
    }
}
