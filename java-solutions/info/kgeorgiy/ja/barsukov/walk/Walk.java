package info.kgeorgiy.ja.barsukov.walk;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Walk {
    static final String NULL_FILE_HASH = "0000000000000000000000000000000000000000";
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void main(final String[] args) {
        try {
            run(args);
        } catch (final WalkException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createDirectories(final Path path) throws WalkException {
        final Path parent = path.getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (final IOException e) {
            // :NOTE: Не повод
            throw new WalkException("Unable to create parent directories for output file");
        }
    }

    private static String getFileHash(final Path path) throws NoSuchAlgorithmException {
        try (final InputStream is = Files.newInputStream(path)) {
            return hash(is);
        } catch (final IOException e) {
            return NULL_FILE_HASH;
        }
    }

    private static String hash(final InputStream is) throws NoSuchAlgorithmException, IOException {
        // :NOTE: Переиспользовать
        final byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        int size;
        while ((size = is.read(buf)) != -1) {
            messageDigest.update(buf, 0, size);
        }
        final byte[] hash = messageDigest.digest();
        return String.format("%0" + (hash.length << 1) + "x", new BigInteger(1, hash));
    }

    private static boolean validArgs(final String[] args) {
        return args != null && args.length == 2 && args[0] != null && args[1] != null;
    }

    static void run(final String[] args) throws WalkException {
        if (!validArgs(args)) {
//            System.err.println("Usage:\njava Walk <input file> <output file>");
            throw new WalkException("Usage:\njava Walk <input file> <output file>");
        }
//        File in = new File(args[0]), out = new File(args[1]);
        final Path in;
        final Path out;
        try {
            in = Path.of(args[0]);
            out = Path.of(args[1]);
        } catch (final InvalidPathException e) {
            // :NOTE: Какого файла
            throw new WalkException("Invalid path of input/output file " + e.getMessage());
        }

        createDirectories(out);
        // :NOTE: Кодировки
        try (final BufferedReader bufferedReader = Files.newBufferedReader(in, StandardCharsets.UTF_8);
             final BufferedWriter bufferedWriter = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            String path;
            while ((path = bufferedReader.readLine()) != null) {
                String hash;
                try {
                    hash = getFileHash(Path.of(path));
                } catch (final InvalidPathException e) {
                    hash = NULL_FILE_HASH;
                }
                bufferedWriter.write(hash + " " + path);
                // :NOTE: ??
                bufferedWriter.newLine();
            }
        } catch (final IOException e) {
            throw new WalkException("Unable to open input/output file " + e.getMessage());
        } catch (final NoSuchAlgorithmException e) {
            throw new WalkException("Digest error: " + e.getMessage());
        }
    }
}
