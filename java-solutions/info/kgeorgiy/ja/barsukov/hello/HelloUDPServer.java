package info.kgeorgiy.ja.barsukov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.*;
import static java.lang.Integer.parseInt;

public class HelloUDPServer implements HelloServer {

    private ExecutorService receivers, senders;

    private int MAX_UNSENT_MESSAGE_COUNT;
    private DatagramSocket socket;

    private Semaphore semaphore;

    private static final String USAGE = "Usage:\njava HelloUDPServer <port> <threads>";

    public static void main(String[] args) {
        if (args == null || args.length != 2 || anyMatchNull(args)) {
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
            semaphore = new Semaphore(MAX_UNSENT_MESSAGE_COUNT);
            socket = new DatagramSocket(port);
            receivers = Executors.newFixedThreadPool(threads / 2);
            senders = Executors.newFixedThreadPool(threads - threads / 2);
            for (int i = 0; i < threads / 2; i++) {
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
                String requestMessage = convertDataToString(request);
                String responseMessage = String.format("Hello, %s", requestMessage);
                DatagramPacket response = newMessageSendPacket(responseMessage, request.getAddress(),
                        request.getPort());
                socket.send(response);
            } catch (IOException ignored) {

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
    }
}
