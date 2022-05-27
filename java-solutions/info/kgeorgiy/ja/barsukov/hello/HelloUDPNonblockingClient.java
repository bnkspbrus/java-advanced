package info.kgeorgiy.ja.barsukov.hello;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.getRequestMessage;

public class HelloUDPNonblockingClient extends AbstractHelloUDPClient {

    private static int TIMEOUT = 200;

    public static void main(String[] args) {
        parseArgsAndRun(new HelloUDPNonblockingClient(), args);
    }

    private static class Attachment {
        final int threadId;
        int requestId;

        Attachment(int threadId) {
            this.threadId = threadId;
            requestId = 0;
        }
    }

    private void opWrite(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Attachment att = (Attachment) key.attachment();
        String msg = getRequestMessage(prefix, att.threadId, att.requestId);
        ByteBuffer buf = StandardCharsets.UTF_8.encode(msg);
        try {
            channel.send(buf, address);
            logSent(msg);
            channel.register(selector, SelectionKey.OP_READ, att);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void opRead(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Attachment att = (Attachment) key.attachment();
        String msg = getRequestMessage(prefix, att.threadId, att.requestId);
        try {
            ByteBuffer buf = ByteBuffer.allocate(channel.socket().getReceiveBufferSize());
            channel.receive(buf);
            String response = StandardCharsets.UTF_8.decode(buf.flip()).toString();
            if (response.contains(msg)) {
                logSent(response);
                att.requestId++;
                if (att.requestId == requests) {
                    channel.close();
                } else {
                    channel.register(selector, SelectionKey.OP_WRITE, att);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private SocketAddress address;

    private Selector selector;

    private String prefix;

    private int requests;

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        this.prefix = prefix;
        this.requests = requests;
        address = new InetSocketAddress(host, port);
        try {
            selector = Selector.open();
            for (int threadId = 0; threadId < threads; threadId++) {
                DatagramChannel channel = HelloUDPUtil.bindChannel(null);
                channel.configureBlocking(false);
                channel.connect(address);
                channel.register(selector, SelectionKey.OP_WRITE, new Attachment(threadId));
            }
            while (!Thread.interrupted() && !selector.keys().isEmpty()) {
                selector.select(TIMEOUT);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (selectionKeys.isEmpty()) {
                    selector.keys().forEach(key -> key.interestOps(SelectionKey.OP_WRITE));
                    continue;
                }
                for (final Iterator<SelectionKey> i = selectionKeys.iterator(); i.hasNext(); ) {
                    final SelectionKey key = i.next();
                    try {
                        if (key.isWritable()) {
                            opWrite(key);
                        }
                        if (key.isReadable()) {
                            opRead(key);
                        }
                    } finally {
                        i.remove();
                    }
                }

            }
            // :NOTE: selector.close();
            // :NOTE: закрыть все каналы
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
