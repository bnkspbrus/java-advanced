package info.kgeorgiy.ja.barsukov.walk;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Walk {
    static final String NULL_FILE_HASH = "0000000000000000000000000000000000000000";
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (!validArgs(args)) {
            System.err.println("Usage:\njava Walk <input file> <output file>");
            return;
        }
        File in = new File(args[0]), out = new File(args[1]);
        if (!createDirectories(out)) {
            System.err.println("Unable to create parent directories for output file");
            return;
        }
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(in, StandardCharsets.UTF_8)); BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(out, StandardCharsets.UTF_8))) {
            String path;
            while ((path = bufferedReader.readLine()) != null) {
                String hash = getFileHash(new File(path));
                bufferedWriter.write(hash + " " + path);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Unable to open input/output file " + e.getMessage());
//            System.exit(0);
        }
    }

    private static boolean createDirectories(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            return parent.mkdirs();
        }
        return true;
    }

    private static String getFileHash(File file) throws NoSuchAlgorithmException {
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
            return hash(is);
        } catch (IOException e) {
            return NULL_FILE_HASH;
        }
    }

    private static String hash(BufferedInputStream is) throws IOException, NoSuchAlgorithmException {
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
}
