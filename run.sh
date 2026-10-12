#!/bin/sh
# Compile and run the application (Linux / macOS)
mkdir -p out
javac -d out $(find src -name "*.java") && java -cp out analyzer.Main
