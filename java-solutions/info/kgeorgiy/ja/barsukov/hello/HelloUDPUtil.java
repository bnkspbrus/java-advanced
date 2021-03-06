package info.kgeorgiy.ja.barsukov.hello;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloUDPUtil {

    /**
     * Checks all elements in {@code args} don't match with null.
     * @param args command prompt arguments for main.
     * @return {@code true} if args doesn't have null,{@code false} otherwise.
     */
    public static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    /**
     * Tries to shutdown {@code workers}.
     * @param workers {@code ExecutorService} to close.
     */
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


    public static String packetDataToString(DatagramPacket response) {
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


    public static String getRequestMessage(String prefix, int threadId, int requestId) {
        return String.format("%s%d_%d", prefix, threadId, requestId);
    }

    public static String getResponseMessage(String requestMessage) {
        return String.format("Hello, %s", requestMessage);
    }

    public static DatagramChannel openChannel() throws IOException {
        return DatagramChannel.open();
    }

    public static DatagramChannel bindChannel(SocketAddress local) throws IOException {
        return openChannel().bind(local);
    }
}
