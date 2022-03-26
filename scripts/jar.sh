#!/bin/bash
package="info/kgeorgiy/ja/barsukov/implementor"
javac -cp "../artifacts/*" ../java-solutions/$package/Implementor.java
curdir=$(pwd)
printf "Main-Class: info.kgeorgiy.ja.barsukov.implementor.Implementor\nClass-Path: $curdir/../artifacts/info.kgeorgiy.java.advanced.implementor.jar\n" >Manifest.txt
# shellcheck disable=SC2164
cd ../java-solutions
jar cfm $package/Implementor.jar "$curdir"/Manifest.txt $package/Implementor.class
