package info.kgeorgiy.ja.barsukov.hello;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.*;

public class HelloUDPNonblockingServer extends AbstractHelloUDPServer {

    public static void main(String[] args) {
        parseAndRunArgs(new HelloUDPNonblockingServer(), args);
    }

    private static final int MAX_UNSENT_MSG_COUNT = Integer.MAX_VALUE >> 1;

    private static int TIMEOUT = 200;

    private Queue<RequestData> requests;

    private record RequestData(String msg, SocketAddress address) {
    }

    private void opRead(SelectionKey key) throws ClosedChannelException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        try {
            SocketAddress address = channel.receive(buffer.clear());
            String msg = StandardCharsets.UTF_8.decode(buffer.flip()).toString();
            requests.add(new RequestData(msg, address));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            channel.register(selector, SelectionKey.OP_READ);
        }
    }

    private void opWrite(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        workers.execute(() -> {
            try {
                RequestData data = requests.poll();
                if (data != null) {
                    String response = getResponseMessage(data.msg);
                    ByteBuffer buf = StandardCharsets.UTF_8.encode(response);
                    channel.send(buf, data.address);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    channel.register(selector, SelectionKey.OP_WRITE);
                } catch (ClosedChannelException ignored) {;
                }
            }
        });
    }

    private Selector selector;

    private DatagramChannel listener;

    private ExecutorService single, workers;

    private ByteBuffer buffer;

    @Override
    public void start(int port, int threads) {
        SocketAddress address = new InetSocketAddress(port);
        try {
            selector = Selector.open();
            listener = bindChannel(address);
            listener.configureBlocking(false);
            listener.register(selector, SelectionKey.OP_READ);
            for (int i = 0; i < threads; i++) {
                DatagramChannel channel = DatagramChannel.open();
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_WRITE);
            }
            buffer = ByteBuffer.allocate(listener.socket().getReceiveBufferSize());
            single = Executors.newSingleThreadExecutor();
            workers = Executors.newFixedThreadPool(threads);
            requests = new ConcurrentLinkedQueue<>();
            single.execute(() -> {
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
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (ConcurrentModificationException | ClosedSelectorException ignored) {

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
        selector.keys().forEach(key -> {
            try {
                key.channel().close();
            } catch (IOException e) {
                System.out.println("Unable to close channel");
            }
        });
        try {
            selector.close();
        } catch (IOException e) {
            System.out.println("Unable to close selector");
        }
        awaitTermination(single);
        awaitTermination(workers);
    }
}
