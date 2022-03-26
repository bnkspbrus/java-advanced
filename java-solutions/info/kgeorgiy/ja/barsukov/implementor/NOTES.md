commit e0f146cdc8c3cb23e7e8174d759d4cae8d534714
Author: bnkspbrus <bnkspbrus@gmail.com>
Date:   Thu Mar 24 12:46:57 2022 +0300

    new Implementor.java
==================================================
Compiling 1 Java sources
Tests: running
WARNING: A command line option has enabled the Security Manager
WARNING: The Security Manager is deprecated and will be removed in a future release
Running class info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest for info.kgeorgiy.ja.barsukov.implementor.Implementor
=== Running test01_constructor
=== Running test09_encoding
test09_encoding\temp12980049054755410297\info\kgeorgiy\java\advanced\implementor\full\lang\??????InterfaceImpl.java:3: error: illegal character: '\u00b8'
public class ПриветInterfaceImpl implements info.kgeorgiy.java.advanced.implementor.full.lang.ПриветInterface{
                  ^
test09_encoding\temp12980049054755410297\info\kgeorgiy\java\advanced\implementor\full\lang\??????InterfaceImpl.java:3: error: illegal character: '\u00b2'
public class ПриветInterfaceImpl implements info.kgeorgiy.java.advanced.implementor.full.lang.ПриветInterface{
                    ^
test09_encoding\temp12980049054755410297\info\kgeorgiy\java\advanced\implementor\full\lang\??????InterfaceImpl.java:3: error: illegal character: '\u201a'
public class ПриветInterfaceImpl implements info.kgeorgiy.java.advanced.implementor.full.lang.ПриветInterface{
                        ^
test09_encoding\temp12980049054755410297\info\kgeorgiy\java\advanced\implementor\full\lang\??????InterfaceImpl.java:3: error: illegal character: '\u00b8'
public class ПриветInterfaceImpl implements info.kgeorgiy.java.advanced.implementor.full.lang.ПриветInterface{
                                                                                                         ^
test09_encoding\temp12980049054755410297\info\kgeorgiy\java\advanced\implementor\full\lang\??????InterfaceImpl.java:3: error: illegal character: '\u00b2'
public class ПриветInterfaceImpl implements info.kgeorgiy.java.advanced.implementor.full.lang.ПриветInterface{
                                                                                                           ^
test09_encoding\temp12980049054755410297\info\kgeorgiy\java\advanced\implementor\full\lang\??????InterfaceImpl.java:3: error: illegal character: '\u201a'
public class ПриветInterfaceImpl implements info.kgeorgiy.java.advanced.implementor.full.lang.ПриветInterface{
                                                                                                               ^
6 errors
=== Running test03_standardInterfaces
	Loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.AccessibleImpl
=== Running test07_duplicateClasses
	Loading class info.kgeorgiy.java.advanced.implementor.full.interfaces.ProxiesImpl
=== Running test04_extendedInterfaces
	Loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.DescriptorImpl
=== Running test08_nestedInterfaces
	Loading class info.kgeorgiy.java.advanced.implementor.full.interfaces.PublicInterfaceImpl
=== Running test06_java8Interfaces
	Loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithStaticMethodImpl
=== Running test05_standardNonInterfaces
=== Running test02_methodlessInterfaces
	Loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.RandomAccessImpl
Test test09_encoding failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.full.lang.??????Interface
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.full.lang.??????Interface
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.test09_encoding(InterfaceJarImplementorTest.java:31)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Compiler exit code expected:<0> but was:<1>
	at junit@4.11/org.junit.Assert.fail(Assert.java:88)
	at junit@4.11/org.junit.Assert.failNotEquals(Assert.java:743)
	at junit@4.11/org.junit.Assert.assertEquals(Assert.java:118)
	at junit@4.11/org.junit.Assert.assertEquals(Assert.java:555)
	at info.kgeorgiy.ja.barsukov.implementor.Implementor.compile(Implementor.java:208)
	at info.kgeorgiy.ja.barsukov.implementor.Implementor.implementJar(Implementor.java:242)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:42)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Test test03_standardInterfaces failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.Accessible
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.Accessible
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceImplementorTest.test03_standardInterfaces(InterfaceImplementorTest.java:38)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Error loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.AccessibleImpl
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:50)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:45)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Caused by: java.lang.ClassNotFoundException: info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.AccessibleImpl
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:48)
	... 41 more
Test test07_duplicateClasses failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.full.interfaces.Proxies
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.full.interfaces.Proxies
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceImplementorTest.test07_duplicateClasses(InterfaceImplementorTest.java:58)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Error loading class info.kgeorgiy.java.advanced.implementor.full.interfaces.ProxiesImpl
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:50)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:45)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Caused by: java.lang.ClassNotFoundException: info.kgeorgiy.java.advanced.implementor.full.interfaces.ProxiesImpl
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:48)
	... 41 more
Test test04_extendedInterfaces failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.Descriptor
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.Descriptor
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceImplementorTest.test04_extendedInterfaces(InterfaceImplementorTest.java:43)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Error loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.DescriptorImpl
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:50)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:45)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Caused by: java.lang.ClassNotFoundException: info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.DescriptorImpl
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:48)
	... 41 more
Test test08_nestedInterfaces failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.full.interfaces.Interfaces$PublicInterface
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.full.interfaces.Interfaces$PublicInterface
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceImplementorTest.test08_nestedInterfaces(InterfaceImplementorTest.java:63)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Error loading class info.kgeorgiy.java.advanced.implementor.full.interfaces.PublicInterfaceImpl
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:50)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:45)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Caused by: java.lang.ClassNotFoundException: info.kgeorgiy.java.advanced.implementor.full.interfaces.PublicInterfaceImpl
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:48)
	... 41 more
Test test06_java8Interfaces failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithStaticMethod
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithStaticMethod
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceImplementorTest.test06_java8Interfaces(InterfaceImplementorTest.java:53)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Error loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithStaticMethodImpl
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:50)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:45)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Caused by: java.lang.ClassNotFoundException: info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithStaticMethodImpl
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:48)
	... 41 more
Test test02_methodlessInterfaces failed: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.RandomAccess
java.lang.AssertionError: Error implementing interface info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.RandomAccess
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:100)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:154)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.test(BaseImplementorTest.java:176)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceImplementorTest.test02_methodlessInterfaces(InterfaceImplementorTest.java:33)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at junit@4.11/org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at junit@4.11/org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at junit@4.11/org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at junit@4.11/org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at junit@4.11/org.junit.rules.TestWatcher$1.evaluate(TestWatcher.java:55)
	at junit@4.11/org.junit.rules.RunRules.evaluate(RunRules.java:20)
	at junit@4.11/org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at junit@4.11/org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:127)
	at junit@4.11/org.junit.runners.Suite.runChild(Suite.java:26)
	at junit@4.11/org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at junit@4.11/org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at junit@4.11/org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at junit@4.11/org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at junit@4.11/org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at junit@4.11/org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:160)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:138)
	at junit@4.11/org.junit.runner.JUnitCore.run(JUnitCore.java:117)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:55)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.lambda$add$0(BaseTester.java:95)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.test(BaseTester.java:48)
	at info.kgeorgiy.java.advanced.base/info.kgeorgiy.java.advanced.base.BaseTester.run(BaseTester.java:39)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.Tester.main(Tester.java:21)
Caused by: java.lang.AssertionError: Error loading class info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.RandomAccessImpl
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:50)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implementJar(InterfaceJarImplementorTest.java:45)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.InterfaceJarImplementorTest.implement(InterfaceJarImplementorTest.java:37)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.implement(BaseImplementorTest.java:91)
	... 38 more
Caused by: java.lang.ClassNotFoundException: info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.RandomAccessImpl
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:587)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
	at info.kgeorgiy.java.advanced.implementor/info.kgeorgiy.java.advanced.implementor.BaseImplementorTest.check(BaseImplementorTest.java:48)
	... 41 more
ERROR: Tests: failed
