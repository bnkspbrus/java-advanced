#!/bin/bash
package="info/kgeorgiy/ja/barsukov/implementor"
javadoc -version -private -author -cp "../artifacts/*" "../java-solutions/$package/Implementor.java" -d "../javadoc"
