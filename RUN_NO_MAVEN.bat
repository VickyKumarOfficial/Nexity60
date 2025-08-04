@echo off
title Nexity60 - Direct Java Launcher (No Maven Required)
echo ================================================================
echo                     Nexity60 Application                      
echo           DIRECT JAVA VERSION - NO MAVEN REQUIRED             
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking for pre-built JAR file...
if not exist "target\Nexity60-1.0.0.jar" (
    echo.
    echo ERROR: JAR file not found at target\Nexity60-1.0.0.jar
    echo.
    echo This could mean:
    echo 1. The project hasn't been built yet
    echo 2. Maven build failed previously
    echo 3. Wrong directory
    echo.
    echo SOLUTION: Please build the project first using:
    echo 1. Install Maven if not installed
    echo 2. Run: mvn clean package -DskipTests
    echo.
    echo Alternatively, check if JAR exists in a different location...
    dir /s *.jar 2>nul | findstr "Nexity60"
    echo.
    pause
    exit /b 1
)

echo Found JAR file! Starting application...
echo.
echo *** The application window should open now ***
echo *** Click on any category to fetch news ***
echo *** Look for summaries below each headline ***
echo *** Try switching between different categories ***
echo.

REM Run with JavaFX compatibility flags for Java 11+
java -Dprism.order=sw ^
     -Djava.awt.headless=false ^
     -Dprism.verbose=false ^
     -Djavafx.animation.pulse=60 ^
     --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED ^
     --add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED ^
     --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED ^
     -jar target\Nexity60-1.0.0.jar

echo.
echo Application has closed.
echo.
if errorlevel 1 (
    echo There was an error running the application.
    echo Error code: %errorlevel%
    echo.
    echo Common solutions:
    echo 1. Make sure Java 11 or higher is installed
    echo 2. Check if JavaFX is properly included in the JAR
    echo 3. Try the alternative launcher scripts
    echo.
) else (
    echo Application ran successfully!
)
echo.
pause
