package info.kgeorgiy.ja.barsukov.hello;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.awaitTermination;
import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.bindChannel;

public class HelloUDPNonblockingServer extends AbstractHelloUDPServer {

    public static void main(String[] args) {
        parseAndRunArgs(new HelloUDPNonblockingServer(), args);
    }

    private static final int MAX_UNSENT_MSG_COUNT = Integer.MAX_VALUE >> 1;

    private static int TIMEOUT = 200;

    private record Attachment(String msg, SocketAddress address) {
    }

    private void opRead(SelectionKey key) throws ClosedChannelException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        try {
            SocketAddress client = channel.receive(buffer.clear());
            DatagramChannel newChannel = DatagramChannel.open();
            try {
                newChannel.configureBlocking(false);
                newChannel.connect(client);
                String msg = StandardCharsets.UTF_8.decode(buffer.flip()).toString();
                newChannel.register(selector, SelectionKey.OP_WRITE, new Attachment(msg, client));
            } catch (IOException e) {
                newChannel.close();
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            channel.register(selector, SelectionKey.OP_READ);
        }
    }

    private void opWrite(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        try {
            Attachment att = (Attachment) key.attachment();
            String response = String.format("Hello, %s", att.msg);
            ByteBuffer buf = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
            channel.send(buf, att.address);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                System.out.println("Unable to close channel");
            }
        }
    }

    private Selector selector;

    private DatagramChannel listener;

    private ExecutorService executor;

    private ByteBuffer buffer;

    @Override
    public void start(int port, int threads) {
        SocketAddress address = new InetSocketAddress(port);
        try {
            selector = Selector.open();
            listener = bindChannel(address);
            listener.configureBlocking(false);
            listener.register(selector, SelectionKey.OP_READ);
            buffer = ByteBuffer.allocate(listener.socket().getReceiveBufferSize());
            executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    while (!Thread.interrupted() && listener.isOpen()) {
                        selector.select(TIMEOUT);
                        for (final Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext(); ) {
                            final SelectionKey key = i.next();
                            try {
                                if (key.isValid()) {
                                    if (key.isReadable()) {
                                        opRead(key);
                                    }
                                    if (key.isWritable()) {
                                        opWrite(key);
                                    }
                                }
                            } finally {
                                i.remove();
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            listener.close();
        } catch (IOException e) {
            System.out.println("Unable to close channel");
        }
        try {
            selector.close();
        } catch (IOException e) {
            System.out.println("Unable to close selector");
        }
        awaitTermination(executor);
    }
}
