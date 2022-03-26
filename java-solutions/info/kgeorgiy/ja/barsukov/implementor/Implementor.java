package info.kgeorgiy.ja.barsukov.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;
//import org.junit.Assert;

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

/**
 * Class for implementing interfaces.
 */
public class Implementor implements Impler, JarImpler {

    /**
     * String separator for lines in file.
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Space.
     */
    private static final String SPACE = " ";

    /**
     * Empty.
     */
    private static final String EMPTY = "";

    /**
     * Separator for parameters.
     */
    private static final String PARAMETER_SEPARATOR = ", ";

    /**
     * Tab.
     */
    private static final String TAB = "\t";

    /**
     * Semicolon.
     */
    private static final String SEMICOLON = ";";

    /**
     * Package keyword.
     */
    private static final String PACKAGE = "package";

    /**
     * Public keyword.
     */
    private static final String PUBLIC = "public";

    /**
     * Class keyword.
     */
    private static final String CLASS = "class";

    /**
     * Implements keyword.
     */
    private static final String IMPLEMENTS = "implements";

    /**
     * Implementation class suffix.
     */
    private static final String IMPL = "Impl";

    /**
     * Open curly bracket.
     */
    private static final String OPEN_CURLY_BRACKET = "{";

    /**
     * Close curly bracket.
     */
    private static final String CLOSE_CURLY_BRACKET = "}";

    /**
     * Open bracket.
     */
    private static final String OPEN_BRACKET = "(";

    /**
     * Close bracket.
     */
    private static final String CLOSE_BRACKET = ")";

    /**
     * Return keyword.
     */
    private static final String RETURN = "return";

    /**
     * Char separator for lines in file.
     */
    private static final char FILE_SEPARATOR = File.separatorChar;

    /**
     * Usage information.
     */
    private static final String USAGE_INFORMATION = "Usage: java Implementor [-jar] <full interface name> <class root>";


    /**
     * Produces code implementing class or interface specified by provided {@code token}.
     * <p>
     * Generated class classes name should be same as classes name of the type token with {@code Impl} suffix
     * added. Generated source code should be placed in the correct subdirectory of the specified
     * {@code root} directory and have correct file name. For example, the implementation of the
     * interface {@link java.util.List} should go to {@code $root/java/util/ListImpl.java}
     *
     * @param token type token to create implementation for.
     * @param root  root directory.
     * @throws info.kgeorgiy.java.advanced.implementor.ImplerException when implementation cannot be generated.
     */
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

    /**
     * Produces <var>.jar</var> file implementing class or interface specified by provided <var>token</var>.
     * <p>
     * Generated class classes name should be same as classes name of the type token with <var>Impl</var> suffix
     * added.
     *
     * @param token   type token to create implementation for.
     * @param jarFile target <var>.jar</var> file.
     * @throws ImplerException when implementation cannot be generated.
     */
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

    /**
     * Verifies given interface type token.
     *
     * @param token type token or interface.
     * @throws ImplerException if token is invalid.
     */
    private void checkClassToken(Class<?> token) throws ImplerException {
        if (!token.isInterface() || Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Invalid class token given: " + token.getCanonicalName());
        }
    }

    /**
     * Generates string implementation of class.
     *
     * @param token type token of interface.
     * @return string class implementation.
     */
    private String classToString(Class<?> token) {
        return String.join(LINE_SEPARATOR, packageToString(token), declarationToString(token) + OPEN_CURLY_BRACKET,
                methodsToString(token), CLOSE_CURLY_BRACKET);
    }

    /**
     * Generates string implementation of methods for class.
     *
     * @param token type token of interface.
     * @return string methods implementation.
     */
    private String methodsToString(Class<?> token) {
        return mapAndJoin(token.getMethods(), Implementor::methodToString, LINE_SEPARATOR);
    }

    /**
     * Apply given function for every given element and then join them.
     *
     * @param elements  array of elements to map and join with {@link Arrays#stream(Object[])}
     * @param mapper function that map each element in array.
     * @param separator for separate elements in result string.
     * @param <T> type of elements in given array.
     * @return result of mapping and joining.
     */
    private static <T> String mapAndJoin(T[] elements, Function<? super T, String> mapper, String separator) {
        return Arrays.stream(elements).map(mapper).collect(Collectors.joining(separator));
    }

    /**
     * Generates string implementation of one given method.
     *
     * @param method to implement.
     * @return implementation of method.
     */
    private static String methodToString(Method method) {
        return String.join(LINE_SEPARATOR, TAB + signatureToString(method) + OPEN_CURLY_BRACKET,
                TAB + bodyToString(method), TAB + CLOSE_CURLY_BRACKET);
    }

    /**
     * Generates method body implementation for one method.
     *
     * @param method to implement.
     * @return implementation of method body.
     */
    private static String bodyToString(Method method) {
        return String.join(SPACE, TAB, RETURN, defaultValue(method.getReturnType()), SEMICOLON);
    }

    /**
     * Generates default value for given type token.
     *
     * @param token type token to default value.
     * @return default value in string.
     */
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

    /**
     * Generates string method signature implementation.
     *
     * @param method to implement.
     * @return stirng method signature implementation.
     */
    private static String signatureToString(Method method) {
        return String.join(SPACE, PUBLIC, method.getReturnType().getCanonicalName(),
                method.getName() + OPEN_BRACKET + parametersToString(method) + CLOSE_BRACKET);
    }

    /**
     * @param method to implement.
     * @return string method parameters implementation.
     */
    private static String parametersToString(Method method) {
        return mapAndJoin(method.getParameters(),
                parameter -> parameter.getType().getCanonicalName() + SPACE + parameter.getName(), PARAMETER_SEPARATOR);
    }

    /**
     * @param token type token of interface.
     * @return package to string.
     */
    private String packageToString(Class<?> token) {
        String packName = token.getPackageName();
        return packName.isEmpty() ? EMPTY : String.join(SPACE, PACKAGE, packName, SEMICOLON, LINE_SEPARATOR);
    }

    /**
     * @param token type token of interface.
     * @return declaration to string.
     */
    private String declarationToString(Class<?> token) {
        return String.join(SPACE, PUBLIC, CLASS, className(token), IMPLEMENTS, token.getCanonicalName());
    }

    /**
     * @param path to directory to create.
     */
    private void createDirectories(Path path) {
        Path parent = path.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * @param token type token interface.
     * @return package to string.
     */
    private static String packageStringPath(Class<?> token) {
        return token.getPackageName().replace('.', FILE_SEPARATOR);
    }

    /**
     * @param args command prompt args.
     */
    public static void main(String[] args) {
        try {
            run(args);
        } catch (ImplerException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param args command prompt args.
     * @throws ImplerException when implementation cannot be generated. if
     */
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

    /**
     * @param args args for {@link #main(String[])}method
     * @throws ImplerException if arguments are invalid.
     */
    private static void checkArgs(String[] args) throws ImplerException {
        if (args == null || args.length < 2 || args.length > 3 || anyMatchNull(args)) {
            throw new ImplerException(USAGE_INFORMATION);
        }
    }

    /**
     * Verifies that args array doesn't have null.
     *
     * @param args arguments to verify.
     * @return {@code true} if array has {@code null} and {@code false} otherwise.
     */
    private static boolean anyMatchNull(String[] args) {
        return Arrays.stream(args).anyMatch(Objects::isNull);
    }

    /**
     * Generates name for interface class implementation.
     *
     * @param token given interface token.
     * @return generated name for implementation class.
     */
    private static String className(Class<?> token) {
        return token.getSimpleName() + IMPL;
    }

    /**
     *
     * @param s input string.
     * @return output string.
     */
    private String unicodeRepresentation(final String s) {
        return s.chars().mapToObj(c -> String.format("\\u%04X", c)).collect(Collectors.joining());
    }

    /**
     * Compile class that
     *
     * @param token type token of interface.
     * @param root path to class implementation.
     * @throws ImplerException when implementation cannot be generated.
     */
    public static void compile(final Class<?> token, final Path root) throws ImplerException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Could not find java compiler, include tools.jar to classpath");
        }
        final int exitCode = compiler.run(null, null, null, getFullImplPath(token, root).toString(), "-cp",
                getClassPath(token));
        if (exitCode != 0) {
            throw new ImplerException("Compiler exit code isn't 0");
        }
    }

    /**
     * @param token type token of interface.
     * @param root path to class implementation.
     * @return full java class path.
     */
    private static Path getFullImplPath(final Class<?> token, final Path root) {
        return root.resolve(pathFromExecDirWithoutExt(token) + ".java");
    }

    /**
     * @param token type token of interface.
     * @return path from execution directory to java class.
     */
    private static String pathFromExecDirWithoutExt(Class<?> token) {
        return packageStringPath(token) + FILE_SEPARATOR + className(token);
    }

    /**
     * @param token type token of interface.
     * @return class path.
     */
    private static String getClassPath(Class<?> token) {
        try {
            return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * @param token type token of interface.
     * @param jarFile path to .jar file.
     * @param temp temporary directory.
     * @throws ImplerException when implementation cannot be generated.
     */
    private void createJar(Class<?> token, Path jarFile, Path temp) throws ImplerException {
        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            String pathFromTemp = pathFromExecDirWithoutExt(token) + ".class";
            jarOutputStream.putNextEntry(new JarEntry(pathFromTemp));
            Files.copy(Path.of(temp.toString(), pathFromTemp), jarOutputStream);
        } catch (IOException e) {
            throw new ImplerException(e.getMessage());
        }
    }

    /**
     * @param dir directory to delete.
     * @return {@code true} if directory deleted successfully.
     */
    private boolean deleteDirectory(File dir) {
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                deleteDirectory(file);
            }
        }
        return dir.delete();
    }
}