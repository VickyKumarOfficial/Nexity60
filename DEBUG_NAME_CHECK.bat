@echo off
title DEBUG - Check Nexity60 Name
echo ================================================================
echo                    DEBUG NAME CHECK                           
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo This will help us identify where "Nexity60" is still appearing...
echo.
echo What you should see in the JavaFX window:
echo - Window title: "Nexity60 - Smart Summarized News Reader"
echo - Sidebar header: "Nexity60"
echo.

echo Starting application for debugging...
echo.

java --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml OriginalNewsApp

echo.
echo === DEBUG QUESTION ===
echo Where did you see "Nexity60" that should be different?
echo 1. Window title bar?
echo 2. Inside the app (sidebar, buttons, etc.)?
echo 3. Console output?
echo 4. Other location?
echo.
pause
