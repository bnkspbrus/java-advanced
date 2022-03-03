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
    private static final byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
    private static MessageDigest messageDigest;

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
        } catch (IOException ignored) {
        }
    }

    private static String getFileHash(final Path path) {
        try (final InputStream is = Files.newInputStream(path)) {
            return hash(is);
        } catch (final IOException e) {
            return NULL_FILE_HASH;
        }
    }

    private static Path createFile(String fileName, boolean isInputFile) throws WalkException {
        try {
            return Path.of(fileName);
        } catch (InvalidPathException e) {
            throw new WalkException(
                    "Invalid path of " + (isInputFile ? "input" : "output") + " file: " + e.getMessage());
        }
    }

    public static String hash(final InputStream is) throws IOException {
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
            throw new WalkException("Usage:\njava Walk <input file> <output file>");
        }
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new WalkException("Digest error: " + e.getMessage());
        }
        final Path inputFile;
        final Path outputFile;
        inputFile = createFile(args[0], true);
        outputFile = createFile(args[1], false);
        createDirectories(outputFile);
        // :NOTE: Кодировки
        try (final BufferedReader bufferedReader = Files.newBufferedReader(inputFile)) {
            try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(outputFile)) {
                String path;
                try {
                    while ((path = bufferedReader.readLine()) != null) {
                        String hash;
                        try {
                            hash = getFileHash(Path.of(path));
                        } catch (final InvalidPathException e) {
                            hash = NULL_FILE_HASH;
                        }
                        try {
                            bufferedWriter.write(hash + " " + path);
                            bufferedWriter.newLine();
                        } catch (final IOException e) {
                            throw new WalkException("Unable to write data to output file: " + e.getMessage());
                        }
                    }
                } catch (final IOException e) {
                    throw new WalkException("Unable to read data from input file: " + e.getMessage());
                }
            } catch (final IOException e) {
                throw new WalkException("Unable to open output file: " + outputFile);
            }
        } catch (final IOException e) {
            throw new WalkException("Unable to open input file: " + inputFile);
        }
    }
}
