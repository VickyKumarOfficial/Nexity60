@echo off
title Nexity60 - Simple Runner
echo ================================================================
echo                     Nexity60 Application                      
echo                      SIMPLE RUNNER                           
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java not found
    pause
    exit /b 1
)
echo + Java is available

echo.
echo Checking JavaFX libraries...
if not exist "lib\javafx-controls-17.0.2.jar" (
    echo ERROR: JavaFX libraries not found in lib directory
    echo Please ensure JavaFX JAR files are in the lib folder
    pause
    exit /b 1
)
echo + JavaFX libraries found

echo.
echo Compiling application...
if not exist "out" mkdir out

REM Compile with verbose output
javac --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp "lib\*" -d out -sourcepath src src\main\java\main\Main.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    echo Check the error messages above.
    pause
    exit /b 1
)

echo + Compilation successful!
echo.
echo Starting application...

java --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp "out;lib\*" main.Main

echo.
echo Application finished.
pause
