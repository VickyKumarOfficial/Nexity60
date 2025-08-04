@echo off
title Nexity60 - No Maven Required
echo ================================================================
echo                     Nexity60 Application                      
echo                   NO MAVEN REQUIRED                          
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher from: https://adoptium.net/
    pause
    exit /b 1
)

echo ✓ Java found
echo.

echo Checking for required JAR files...
if exist "target\Nexity60-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo ✓ Found JAR with dependencies
    echo Starting application...
    echo.
    java --module-path "lib" --add-modules javafx.controls,javafx.fxml -jar "target\Nexity60-1.0-SNAPSHOT-jar-with-dependencies.jar"
    goto end
)

if exist "target\Nexity60-1.0-SNAPSHOT.jar" (
    echo ✓ Found main JAR
    echo Starting application with classpath...
    echo.
    java --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp "target\Nexity60-1.0-SNAPSHOT.jar;lib\*" main.Main
    goto end
)

echo No JAR files found. Attempting to compile from source...
echo.

REM Create output directory
if not exist "compiled" mkdir compiled

echo Compiling source files...
echo.

REM Get all Java files
for /r src %%f in (*.java) do (
    echo Compiling %%f
    javac --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp "lib\*;compiled" -d compiled "%%f"
    if errorlevel 1 (
        echo ERROR: Compilation failed for %%f
        pause
        exit /b 1
    )
)

echo.
echo ✓ Compilation successful
echo Starting application...
echo.

java --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp "compiled;lib\*" main.Main

:end
echo.
echo Application finished.
pause
