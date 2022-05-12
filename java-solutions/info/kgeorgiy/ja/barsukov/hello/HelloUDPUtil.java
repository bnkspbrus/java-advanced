package info.kgeorgiy.ja.barsukov.hello;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloUDPUtil {

    public static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    public static void awaitTermination(ExecutorService workers) {
        workers.shutdown();
        try {
            if (!workers.awaitTermination(10, TimeUnit.SECONDS)) {
                workers.shutdownNow();
            }
        } catch (InterruptedException e) {
            workers.shutdownNow();
            Thread.currentThread().interrupt();
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


    public static String createMessage(String prefix, int threadId, int requestId) {
        return String.format("%s%d_%d", prefix, threadId, requestId);
    }
}
