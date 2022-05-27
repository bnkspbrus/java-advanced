package info.kgeorgiy.ja.barsukov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.*;
import static java.lang.Integer.parseInt;

public abstract class AbstractHelloUDPClient implements HelloClient {
    private static final String USAGE = "Usage:\njava HelloUDPClient <name | ip> <port> <prefix> <threads> <requests>";

    static void parseArgsAndRun(AbstractHelloUDPClient client, String... args) {
        if (args == null || args.length != 5 || anyMatchNull(args)) {
            System.err.println(USAGE);
            return;
        }
        client.run(args[0], parseInt(args[1]), args[2], parseInt(args[3]), parseInt(args[4]));
    }

    static void logSent(String msg) {
        System.out.printf("Sent: %s%n", msg);
    }

    static void logReceived(String msg) {
        System.out.printf("Received: %s%n", msg);
    }
}
