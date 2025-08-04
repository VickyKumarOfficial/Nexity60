@echo off
title Nexity60 - Emergency Setup and Run
echo ================================================================
echo                     Nexity60 Application                      
echo            EMERGENCY SETUP - NO MAVEN REQUIRED                
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo This script will:
echo 1. Create necessary directories
echo 2. Compile the application from source
echo 3. Run it directly
echo.

REM Check Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher from https://adoptium.net/
    pause
    exit /b 1
)

echo Java is available! Setting up...
echo.

REM Create build directory
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes

echo [1/3] Compiling Java sources...
echo.

REM Try to compile with minimal classpath first
javac -d build\classes src\main\java\model\*.java
if errorlevel 1 (
    echo ERROR: Could not compile model classes
    pause
    exit /b 1
)

javac -cp build\classes -d build\classes src\main\java\storage\*.java
if errorlevel 1 (
    echo ERROR: Could not compile storage classes  
    pause
    exit /b 1
)

javac -cp build\classes -d build\classes src\main\java\core\*.java
if errorlevel 1 (
    echo ERROR: Could not compile core classes
    echo This might be due to missing JSoup library
    echo.
    echo SOLUTION: Please run this with Maven first:
    echo   mvn clean package -DskipTests
    echo.
    echo Or download JSoup manually from https://jsoup.org/download
    pause
    exit /b 1
)

echo [2/3] Compiling UI classes (this might fail without JavaFX)...
javac -cp build\classes -d build\classes src\main\java\ui\*.java 2>nul
if errorlevel 1 (
    echo.
    echo WARNING: JavaFX classes failed to compile
    echo This is expected if JavaFX is not in the system classpath
    echo.
    echo SOLUTIONS:
    echo 1. Install JavaFX separately
    echo 2. Use Maven build (recommended): mvn clean package -DskipTests  
    echo 3. Download OpenJFX from https://openjfx.io/
    echo.
    pause
    exit /b 1
)

echo [3/3] Starting application...
echo.
echo *** The application window should open now ***
echo.

java -cp build\classes ui.MainWindow

echo.
echo Done!
pause
