package info.kgeorgiy.ja.barsukov.bank;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public final class Server {
    public final static int DEFAULT_PORT = 8888;

    public static void main(final String... args) {
        final int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        final Bank bank = new RemoteBank(port);
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            UnicastRemoteObject.exportObject(bank, port);
            registry.rebind("//localhost/bank", bank);
            UnicastRemoteObject.unexportObject(registry.lookup("//localhost/bank"), true);
            System.out.println("Server started");
        } catch (final RemoteException e) {
            System.out.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
