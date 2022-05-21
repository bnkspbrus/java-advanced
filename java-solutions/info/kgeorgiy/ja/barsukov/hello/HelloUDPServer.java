package info.kgeorgiy.ja.barsukov.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.*;
import static java.lang.Math.max;

public class HelloUDPServer extends AbstractHelloUDPServer {

    private ExecutorService receivers, senders;

    private static final int MAX_UNSENT_MESSAGE_COUNT = Integer.MAX_VALUE;
    private DatagramSocket socket;

    private Semaphore semaphore;

    private static final String USAGE = "Usage:\njava HelloUDPServer <port> <threads>";

    public static void main(String[] args) {
        parseAndRunArgs(new HelloUDPServer(), args);
    }

    @Override
    public void start(int port, int threads) {
        try {
            semaphore = new Semaphore(MAX_UNSENT_MESSAGE_COUNT);
            socket = new DatagramSocket(port);
            receivers = Executors.newFixedThreadPool(max(threads / 2, 1));
            senders = Executors.newFixedThreadPool(max(threads - threads / 2, 1));
            for (int i = 0; i < max(threads / 2, 1); i++) {
                receivers.execute(new ReceiveWorker());
            }
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }

    class SendWorker implements Runnable {

        private final DatagramPacket request;

        SendWorker(DatagramPacket request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                String requestMessage = packetDataToString(request);
                String responseMessage = String.format("Hello, %s", requestMessage);
                request.setData(responseMessage.getBytes(StandardCharsets.UTF_8));
                socket.send(request);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                semaphore.release();
            }
        }
    }

    class ReceiveWorker implements Runnable {

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    try {
                        semaphore.acquire();
                        DatagramPacket request = newEmptyReceivePacket(socket);
                        try {
                            socket.receive(request);
                            senders.execute(new SendWorker(request));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (InterruptedException e) {
                        System.out.println("ReceiveWorker interrupted");
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (SocketException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        socket.close();
        awaitTermination(receivers);
        awaitTermination(senders);
    }
}
