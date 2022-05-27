package info.kgeorgiy.ja.barsukov.hello;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.*;

public class HelloUDPClient extends AbstractHelloUDPClient {

    private static final int TIMEOUT = 200;

    private int requests;
    private String prefix;

    private int port;

    private InetAddress address;

    public static void main(String[] args) {
        parseArgsAndRun(new HelloUDPClient(), args);
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
        ExecutorService workers = Executors.newFixedThreadPool(threads);
        IntStream.range(0, threads).forEach(threadId -> workers.execute(new RequestWorker(threadId)));
        awaitTermination(workers);
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

                    String requestMessage = getRequestMessage(prefix, threadId, requestId);
                    DatagramPacket request = newMessageSendPacket(requestMessage, address, port);
                    while (!Thread.interrupted()) {
                        try {
                            socket.send(request);
                            logSent(requestMessage);
                            DatagramPacket response = newEmptyReceivePacket(socket);
                            socket.receive(response);
                            String responseMessage = packetDataToString(response);
                            if (responseMessage.endsWith(requestMessage)) {
                                logReceived(requestMessage);
                                break;
                            }

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } catch (SocketException e) {
                System.err.println("Unable to establish a connection: " + e.getMessage());
            }
        }
    }
}
