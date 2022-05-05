From https://www.kgeorgiy.info/git-students/year2020/Barsukov_Nikita/java-advanced
520eb54..a3238cc  master     -> origin/master
Updating 520eb54..a3238cc
Fast-forward
.../barsukov/concurrent/IterativeParallelism.java  | 157 ++++++++
.../ja/barsukov/concurrent/ParallelMapperImpl.java | 104 +++++
.../kgeorgiy/ja/barsukov/crawler/WebCrawler.java   | 216 +++++++++++
.../kgeorgiy/ja/barsukov/hello/HelloUDPClient.java | 111 ++++++
.../kgeorgiy/ja/barsukov/hello/HelloUDPServer.java | 102 +++++
.../ja/barsukov/implementor/Implementor.java       | 121 +++---
.../info/kgeorgiy/ja/barsukov/implementor/NOTES.md | 417 +--------------------
javadoc/allclasses-index.html                      |   4 +-
javadoc/allpackages-index.html                     |   4 +-
javadoc/constant-values.html                       |   4 +-
javadoc/help-doc.html                              |   4 +-
javadoc/index-all.html                             |  41 +-
javadoc/index.html                                 |   4 +-
.../ja/barsukov/implementor/Implementor.html       | 193 +++++-----
.../ja/barsukov/implementor/package-summary.html   |   4 +-
.../ja/barsukov/implementor/package-tree.html      |   4 +-
javadoc/member-search-index.js                     |   2 +-
javadoc/overview-tree.html                         |   4 +-
scripts/Notes.md                                   |   1 +
19 files changed, 906 insertions(+), 591 deletions(-)
create mode 100644 java-solutions/info/kgeorgiy/ja/barsukov/concurrent/IterativeParallelism.java
create mode 100644 java-solutions/info/kgeorgiy/ja/barsukov/concurrent/ParallelMapperImpl.java
create mode 100644 java-solutions/info/kgeorgiy/ja/barsukov/crawler/WebCrawler.java
create mode 100644 java-solutions/info/kgeorgiy/ja/barsukov/hello/HelloUDPClient.java
create mode 100644 java-solutions/info/kgeorgiy/ja/barsukov/hello/HelloUDPServer.java
create mode 100644 scripts/Notes.md
Removing __current-repo
Copying __local/git/Barsukov_Nikita to __current-repo
commit a3238cc3ff582fb873bde6cc2f67fbdba7f61572
Author: Nikolay Vedernikov <VedernikovNV@gmail.com>
Date:   Thu May 5 16:16:08 2022 +0300

    Checked by an instructor
Compiling 2 Java sources
Tests: running
WARNING: A command line option has enabled the Security Manager
WARNING: The Security Manager is deprecated and will be removed in a future release
Running class info.kgeorgiy.java.advanced.hello.HelloClientTest for info.kgeorgiy.ja.barsukov.hello.HelloUDPClient
=== Running test01_singleRequest
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Socket closed
Test finished in 0.048s
=== Running test02_sequence
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_1
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_1
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_2
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_2
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_3
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_3
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_4
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_4
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_5
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_5
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_6
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_6
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_7
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_7
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_8
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_8
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_9
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_9
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_10
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_10
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_11
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_11
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_12
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_12
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_13
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_13
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_14
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_14
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_15
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_15
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_16
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_16
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_17
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_17
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_18
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_18
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_19
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_19
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_20
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_20
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_21
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_21
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_22
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_22
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_23
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_23
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_24
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_24
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_25
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_25
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_26
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_26
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_27
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_27
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_28
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_28
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_29
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_29
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_30
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_30
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_31
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_31
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_32
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_32
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_33
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_33
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_34
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_34
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_35
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_35
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_36
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_36
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_37
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_37
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_38
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_38
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_39
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_39
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_40
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_40
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_41
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_41
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_42
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_42
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_43
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_43
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_44
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_44
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_45
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_45
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_46
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_46
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_47
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_47
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_48
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_48
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_49
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_49
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_50
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_50
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_51
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_51
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_52
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_52
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_53
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_53
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_54
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_54
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_55
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_55
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_56
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_56
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_57
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_57
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_58
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_58
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_59
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_59
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_60
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_60
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_61
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_61
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_62
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_62
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_63
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_63
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_64
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_64
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_65
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_65
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_66
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_66
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_67
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_67
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_68
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_68
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_69
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_69
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_70
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_70
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_71
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_71
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_72
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_72
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_73
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_73
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_74
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_74
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_75
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_75
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_76
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_76
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_77
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_77
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_78
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_78
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_79
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_79
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_80
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_80
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_81
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_81
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_82
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_82
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_83
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_83
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_84
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_84
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_85
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_85
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_86
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_86
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_87
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_87
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_88
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_88
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_89
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_89
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_90
Test finished in 0.075s
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_90
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_91
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_91
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_92
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_92
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_93
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_93
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_94
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_94
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_95
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_95
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_96
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_96
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_97
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_97
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_98
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_98
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_99
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_99
Socket closed
=== Running test03_singleWithFailures
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Received:
Socket closed
=== Running test04_sequenceWithFailures
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_0
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_1
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest0_1
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_2
Received: Hello, info.kgeorgiy.java.advanced.hello.HelloClientTest00_22
Sent: info.kgeorgiy.java.advanced.hello.HelloClientTest0_3
Exception in thread "Thread-3" java.lang.AssertionError: Invalid or unexpected request info.kgeorgiy.java.advanced.hello.HelloClientTest0_3 expected:<2> but was:<3>
at junit@4.11/org.junit.Assert.fail(Assert.java:88)
at junit@4.11/org.junit.Assert.failNotEquals(Assert.java:743)
at junit@4.11/org.junit.Assert.assertEquals(Assert.java:118)
at junit@4.11/org.junit.Assert.assertEquals(Assert.java:555)
at info.kgeorgiy.java.advanced.hello/info.kgeorgiy.java.advanced.hello.Util.lambda$server$1(Util.java:159)
at java.base/java.lang.Thread.run(Thread.java:833)

