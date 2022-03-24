package info.kgeorgiy.ja.barsukov.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;
import org.junit.Assert;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public class Implementor implements Impler, JarImpler {

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

    private static final String USAGE_INFORMATION = "Usage: java Implementor [-jar] <full interface name> <class root>";

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        checkClassToken(token);
        Path implPath;
        try {
            implPath = getFullImplPath(token, root);
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

    private static String packageStringPath(Class<?> token) {
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
        checkArgs(args);
        try {
            if (args.length == 2) {
                new Implementor().implement(Class.forName(args[0]), Path.of(args[1]));
            } else if (args.length == 3 && args[0].equals("-jar")) {
                new Implementor().implementJar(Class.forName(args[1]), Path.of(args[2]));
            } else {
                throw new ImplerException(USAGE_INFORMATION);
            }
        } catch (ClassNotFoundException e) {
            throw new ImplerException("Given interface isn't found");
        }
    }

    private static void checkArgs(String[] args) throws ImplerException {
        if (args == null || args.length < 2 || args.length > 3 || anyMatchNull(args)) {
            throw new ImplerException(USAGE_INFORMATION);
        }
    }

    private static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    /**
     * Generates name for interface implementation.
     *
     * @param token given interface token.
     * @return generated name for class.
     */
    private static String className(Class<?> token) {
        return token.getSimpleName() + IMPL;
    }

    public static void compile(final Class<?> token, final Path root) {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Assert.assertNotNull("Could not find java compiler, include tools.jar to classpath", compiler);
        final int exitCode = compiler.run(null, null, null, getFullImplPath(token, root).toString(), "-cp",
                getClassPath(token));
        Assert.assertEquals("Compiler exit code", 0, exitCode);
    }

    private static Path getFullImplPath(final Class<?> token, final Path root) {
        return root.resolve(packageStringPath(token) + FILE_SEPARATOR + className(token) + ".java");
    }

    private static String getClassPath(Class<?> token) {
        try {
            return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    private void createJar(Class<?> token, Path jarFile, Path temp) throws ImplerException {
        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            String pathFromTemp = packageStringPath(token) + FILE_SEPARATOR + className(token) + ".class";
            jarOutputStream.putNextEntry(new JarEntry(pathFromTemp));
            Files.copy(Path.of(temp.toString(), pathFromTemp), jarOutputStream);
        } catch (IOException e) {
            throw new ImplerException(e.getMessage());
        }
    }

    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        Path temp;
        try {
            temp = Files.createTempDirectory(jarFile.getParent(), "temp");
            implement(token, temp);
            compile(token, temp);
            createJar(token, jarFile, temp);
            deleteDirectory(temp.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteDirectory(File dir) {
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                deleteDirectory(file);
            }
        }
        return dir.delete();
    }

    // :NOTE:
}