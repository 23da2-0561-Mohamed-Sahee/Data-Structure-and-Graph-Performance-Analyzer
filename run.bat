@echo off
REM Compile and run the application (Windows)
if not exist out mkdir out
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
del sources.txt
java -cp out analyzer.Main
