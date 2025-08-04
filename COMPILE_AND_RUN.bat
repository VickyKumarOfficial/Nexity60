@echo off
title Nexity60 - Compile and Run (No Maven)
echo ================================================================
echo                     Nexity60 Application                      
echo        COMPILE AND RUN VERSION - NO MAVEN REQUIRED            
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher and try again
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

echo Java found! Checking for JavaFX...
echo.

REM Create directories
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes

echo [1/4] Checking for required JAR files...
echo.

REM Check if lib directory exists with JavaFX
if not exist "lib" (
    echo ERROR: lib directory not found!
    echo.
    echo The lib directory should contain JavaFX JAR files.
    echo Please copy JavaFX libraries to the lib folder or use Maven build.
    echo.
    echo Alternative: Download JavaFX from https://openjfx.io/
    echo and extract to lib/ folder
    echo.
    pause
    exit /b 1
)

echo [2/4] Compiling Java source files...
echo.

REM Find all JAR files in lib directory
set CLASSPATH=
for %%i in (lib\*.jar) do call set CLASSPATH=%%CLASSPATH%%;%%i

REM Compile with classpath
javac -cp "%CLASSPATH%" -d build\classes src\main\java\**\*.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    echo Make sure all required JAR files are in the lib directory
    pause
    exit /b 1
)

echo [3/4] Copying resources...
if exist "src\main\resources" (
    xcopy /s /y "src\main\resources\*" "build\classes\" >nul 2>&1
)

echo [4/4] Starting application...
echo.
echo *** The application window should open now ***
echo *** Click on any category to fetch news ***
echo.

REM Run the application
java -cp "build\classes;%CLASSPATH%" ^
     -Dprism.order=sw ^
     -Djava.awt.headless=false ^
     --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED ^
     --add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED ^
     ui.MainWindow

echo.
echo Application finished.
pause
