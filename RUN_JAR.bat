@echo off
title Nexity60 - Direct JAR Launcher
echo ================================================================
echo                     Nexity60 Application                      
echo              DIRECT JAR VERSION (Alternative Method)          
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking JAR file...
if not exist "target\Nexity60-1.0.0.jar" (
    echo JAR file not found. Building...
    mvn clean package -DskipTests -q
)

echo Starting application with JavaFX compatibility flags...
echo.

java -Dprism.order=sw ^
     -Djava.awt.headless=false ^
     -Djavafx.preloader="" ^
     --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED ^
     --add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED ^
     -jar target\Nexity60-1.0.0.jar

echo.
echo Application finished.
pause
