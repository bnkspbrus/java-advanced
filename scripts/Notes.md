/mnt/d/university/java2022/java-advanced-private/test/__current-repo/scripts$ ./jar.sh
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:3: error: package info.kgeorgiy.java.advanced.implementor does not exist
import info.kgeorgiy.java.advanced.implementor.Impler;
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:4: error: package info.kgeorgiy.java.advanced.implementor does not exist
import info.kgeorgiy.java.advanced.implementor.ImplerException;
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:5: error: package info.kgeorgiy.java.advanced.implementor does not exist
import info.kgeorgiy.java.advanced.implementor.JarImpler;
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:30: error: cannot find symbol
public class Implementor implements Impler, JarImpler {
^
symbol: class Impler
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:30: error: cannot find symbol
public class Implementor implements Impler, JarImpler {
^
symbol: class JarImpler
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:136: error: cannot find symbol
public void implement(Class<?> token, Path root) throws ImplerException {
                                                            ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:158: error: cannot find symbol
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:178: error: cannot find symbol
private void checkClassToken(Class<?> token) throws ImplerException {
                                                        ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:336: error: cannot find symbol
    private static void run(String[] args) throws ImplerException {
                                                  ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:355: error: cannot find symbol
    private static void checkArgs(String[] args) throws ImplerException {
                                                        ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:396: error: cannot find symbol
    public static void compile(final Class<?> token, final Path root) throws ImplerException {
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:446: error: cannot find symbol
private void createJar(Class<?> token, Path jarFile, Path temp) throws ImplerException {
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:135: error: method does not override or implement a method from a supertype
@Override
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:143: error: cannot find symbol
throw new ImplerException("Unable to write implementation in the file: " + implPath);
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:157: error: method does not override or implement a method from a supertype
@Override
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:163: error: cannot find symbol
throw new ImplerException(
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:180: error: cannot find symbol
throw new ImplerException("Invalid class token given: " + token.getCanonicalName());
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:325: error: cannot find symbol
} catch (ImplerException e) {
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:344: error: cannot find symbol
throw new ImplerException(USAGE_INFORMATION);
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:347: error: cannot find symbol
throw new ImplerException("Given interface wasn't found");
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:357: error: cannot find symbol
throw new ImplerException(USAGE_INFORMATION);
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:399: error: cannot find symbol
throw new ImplerException("Could not find java compiler, include tools.jar to classpath");
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:404: error: cannot find symbol
throw new ImplerException("Compiler exit code isn't 0");
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:455: error: cannot find symbol
throw new ImplerException("Unable to write implementation in the file");
^
symbol:   class ImplerException
location: class Implementor
24 errors
info/kgeorgiy/ja/barsukov/implementor/Implementor.class : no such file or directory


/mnt/d/university/java2022/java-advanced-private/test/__current-repo/scripts$ ./javadoc.sh
Loading source file ../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java...
Constructing Javadoc information...
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:3: error: package info.kgeorgiy.java.advanced.implementor does not exist
import info.kgeorgiy.java.advanced.implementor.Impler;
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:4: error: package info.kgeorgiy.java.advanced.implementor does not exist
import info.kgeorgiy.java.advanced.implementor.ImplerException;
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:5: error: package info.kgeorgiy.java.advanced.implementor does not exist
import info.kgeorgiy.java.advanced.implementor.JarImpler;
^
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:30: error: cannot find symbol
public class Implementor implements Impler, JarImpler {
^
symbol: class Impler
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:30: error: cannot find symbol
public class Implementor implements Impler, JarImpler {
^
symbol: class JarImpler
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:136: error: cannot find symbol
public void implement(Class<?> token, Path root) throws ImplerException {
                                                            ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:158: error: cannot find symbol
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:178: error: cannot find symbol
private void checkClassToken(Class<?> token) throws ImplerException {
                                                        ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:336: error: cannot find symbol
    private static void run(String[] args) throws ImplerException {
                                                  ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:355: error: cannot find symbol
    private static void checkArgs(String[] args) throws ImplerException {
                                                        ^
  symbol:   class ImplerException
  location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:396: error: cannot find symbol
    public static void compile(final Class<?> token, final Path root) throws ImplerException {
^
symbol:   class ImplerException
location: class Implementor
../java-solutions/info/kgeorgiy/ja/barsukov/implementor/Implementor.java:446: error: cannot find symbol
private void createJar(Class<?> token, Path jarFile, Path temp) throws ImplerException {
^
symbol:   class ImplerException
location: class Implementor
12 errors
