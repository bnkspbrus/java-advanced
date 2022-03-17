package info.kgeorgiy.ja.barsukov.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Implementor implements Impler {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String SPACE = " ";

    private static final String EMPTY = "";

    private static final String PARAMETER_SEPARATOR = ", ";

    private static final String TAB = "\t";

    private static final String SEMICOLON = ";";

    private static final String PACKAGE = "package";

    private static final String PUBLIC = "public";

    private static final String CLASS = "class";

    private static final String IMPLEMENTS = "implements";

    private static final String IMPL = "Impl";

    private static final String OPEN_CURLY_BRACKET = "{";

    private static final String CLOSE_CURLY_BRACKET = "}";

    private static final String OPEN_BRACKET = "(";

    private static final String CLOSE_BRACKET = ")";

    private static final String RETURN = "return";

    private static final char FILE_SEPARATOR = File.separatorChar;

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        checkClassToken(token);
        Path implPath;
        try {
            implPath = Path.of(root.toString(), packageStringPath(token), className(token) + ".java");
        // :NOTE: redundant catch
        } catch (InvalidPathException e) {
            throw new ImplerException(e.getMessage());
        }
        createDirectories(implPath);
        try (BufferedWriter writer = Files.newBufferedWriter(implPath)) {
            writer.write(classToString(token));
        } catch (IOException e) {
            throw new ImplerException(e.getMessage());
        }
    }

    private void checkClassToken(Class<?> token) throws ImplerException {
        if (!token.isInterface() || Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Invalid class token given: " + token.getCanonicalName());
        }
    }

    private String classToString(Class<?> token) {
        return String.join(LINE_SEPARATOR, packageToString(token), declarationToString(token) + OPEN_CURLY_BRACKET,
                methodsToString(token), CLOSE_CURLY_BRACKET);
    }

    private String methodsToString(Class<?> token) {
        return mapAndJoin(token.getMethods(), Implementor::methodToString, LINE_SEPARATOR);
    }

    private static <T> String mapAndJoin(T[] elements, Function<? super T, String> mapper, String separator) {
        return Arrays.stream(elements).map(mapper).collect(Collectors.joining(separator));
    }


    private static String methodToString(Method method) {
        return String.join(LINE_SEPARATOR, TAB + signatureToString(method) + OPEN_CURLY_BRACKET,
                TAB + bodyToString(method), TAB + CLOSE_CURLY_BRACKET);
    }

    private static String bodyToString(Method method) {
        return String.join(SPACE, TAB, RETURN, defaultValue(method.getReturnType()), SEMICOLON);
    }

    private static String defaultValue(Class<?> token) {
        if (!token.isPrimitive()) {
            return "null";
        }
        if (token == void.class) {
            return EMPTY;
        }
        if (token == boolean.class) {
            return "false";
        }
        return "0";
    }

    private static String signatureToString(Method method) {
        // :NOTE: private parameters and return type
        return String.join(SPACE, PUBLIC, method.getReturnType().getCanonicalName(),
                method.getName() + OPEN_BRACKET + parametersToString(method) + CLOSE_BRACKET);
    }

    private static String parametersToString(Method method) {
        return mapAndJoin(method.getParameters(),
                parameter -> parameter.getType().getCanonicalName() + SPACE + parameter.getName(), PARAMETER_SEPARATOR);
    }

    private String packageToString(Class<?> token) {
        String packName = token.getPackageName();
        return packName.isEmpty() ? EMPTY : String.join(SPACE, PACKAGE, packName, SEMICOLON, LINE_SEPARATOR);
    }

    private String declarationToString(Class<?> token) {
        return String.join(SPACE, PUBLIC, CLASS, className(token), IMPLEMENTS, token.getCanonicalName());
    }

    private void createDirectories(Path path) {
        Path parent = path.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException ignored) {
            }
        }
    }

    String packageStringPath(Class<?> token) {
        return token.getPackageName().replace('.', FILE_SEPARATOR);
    }

    public static void main(String[] args) {
        try {
            run(args);
        } catch (ImplerException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void run(String[] args) throws ImplerException {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            throw new ImplerException("Usage: java Implementor <full interface name>");
        }
        try {
            new Implementor().implement(Class.forName(args[0]), Path.of(args[1]));
        } catch (ClassNotFoundException e) {
            throw new ImplerException("Given interface isn't found");
        } catch (InvalidPathException e) { // :NOTE: redundant catch
            throw new ImplerException("Given path isn't valid");
        }
    }

    private static String className(Class<?> token) {
        return token.getSimpleName() + IMPL;
    }

    // :NOTE:
//    private class A {}
//
//    public interface I {
//        public A foo(A a);
//    }
}
