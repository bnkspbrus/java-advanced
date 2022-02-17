package info.kgeorgiy.ja.barsukov.walk;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Walk {
    static final String NULL_FILE_HASH = "0000000000000000000000000000000000000000";
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        try {
            run(args);
        } catch (WalkException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createDirectories(Path path) throws WalkException {
        Path parent = path.getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (IOException e) {
            throw new WalkException("Unable to create parent directories for output file");
        }
    }

    private static String getFileHash(Path path) throws NoSuchAlgorithmException {
        try (InputStream is = Files.newInputStream(path)) {
            return hash(is);
        } catch (IOException e) {
            return NULL_FILE_HASH;
        }
    }

    private static String hash(InputStream is) throws IOException, NoSuchAlgorithmException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        int size;
        while ((size = is.read(buf)) != -1) {
            messageDigest.update(buf, 0, size);
        }
        byte[] hash = messageDigest.digest();
        return String.format("%0" + (hash.length << 1) + "x", new BigInteger(1, hash));
    }

    private static boolean validArgs(String[] args) {
        return args != null && args.length == 2 && args[0] != null && args[1] != null;
    }

    private static long hash(final byte[] bytes, final int size, long start) {
        for (int i = 0; i < size; i++) {
            start = (start << 8) + (bytes[i] & 0xff);
            final long high = start & 0xff00_0000_0000_0000L;
            if (high != 0) {
                start ^= high >> 48;
                start &= ~high;
            }
        }
        return start;
    }

    static void run(String[] args) throws WalkException {
        if (!validArgs(args)) {
//            System.err.println("Usage:\njava Walk <input file> <output file>");
            throw new WalkException("Usage:\njava Walk <input file> <output file>");
        }
//        File in = new File(args[0]), out = new File(args[1]);
        Path in, out;
        try {
            in = Path.of(args[0]);
            out = Path.of(args[1]);
        } catch (InvalidPathException e) {
            throw new WalkException("Invalid path of input/output file " + e.getMessage());
        }
        createDirectories(out);
        try (BufferedReader bufferedReader = Files.newBufferedReader(
                in); BufferedWriter bufferedWriter = Files.newBufferedWriter(out)) {
            String path;
            while ((path = bufferedReader.readLine()) != null) {
                Path file;
                String hash;
                try {
                    file = Path.of(path);
                    hash = getFileHash(file);
                } catch (InvalidPathException e) {
                    hash = NULL_FILE_HASH;
                }
                bufferedWriter.write(hash + " " + path);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new WalkException("Unable to open input/output file " + e.getMessage());
        }
    }
}