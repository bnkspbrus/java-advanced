package info.kgeorgiy.ja.barsukov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import static info.kgeorgiy.ja.barsukov.hello.HelloUDPUtil.anyMatchNull;
import static java.lang.Integer.parseInt;

public abstract class AbstractHelloUDPServer implements HelloServer {
    private static final String USAGE = "Usage:\njava HelloUDPServer <port> <threads>";

    protected static void parseAndRunArgs(AbstractHelloUDPServer server, String... args) {
        if (args == null || args.length != 2 || anyMatchNull(args)) {
            System.err.println(USAGE);
            return;
        }
        try (server) {
            server.start(parseInt(args[0]), parseInt(args[1]));
        }
    }
}
