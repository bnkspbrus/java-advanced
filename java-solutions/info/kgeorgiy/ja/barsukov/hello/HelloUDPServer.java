package info.kgeorgiy.ja.barsukov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.*;

import static java.lang.Integer.parseInt;

public class HelloUDPServer implements HelloServer {

    private ExecutorService workers;

    private static boolean hasNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    // :NOTE: опять же, защелка тут не нужна - у ExecutorService есть требуемый функционал
    private CountDownLatch latch;

    private DatagramSocket socket;

    private static final String USAGE = "Usage:\njava HelloUDPServer port threads";

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
            latch = new CountDownLatch(threads);
            for (int i = 0; i < threads; i++) {
                workers.execute(new ResponseWorker(socket));
            }
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }

    class ResponseWorker implements Runnable {

        final DatagramSocket socket;

        ResponseWorker(DatagramSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    DatagramPacket request = HelloUDPClient.newEmptyReceivePacket(socket);;
                    socket.receive(request);
                    String requestMessage = HelloUDPClient.convertDataToString(request);
//                    System.out.printf("Received: %s%n", requestMessage);
                    // :NOTE: Несмотря на то, что текущий способ получения ответа по запросу очень прост,
                    // сервер должен быть рассчитан на ситуацию,
                    // когда этот процесс может требовать много ресурсов и времени.
                    String responseMessage = String.format("Hello, %s", requestMessage);
                    DatagramPacket response = HelloUDPClient.newMessageSendPacket(responseMessage, request.getAddress(),
                            request.getPort());
                    try {
                        socket.send(response);
//                        System.out.printf("Sent: %s%n", responseMessage);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                latch.countDown();
            }
        }
    }

    // :NOTE: повторные вызовы run и одиночный вызов close закроют только последний сокет - утечка
    @Override
    public void close() {
        socket.close();
        workers.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
