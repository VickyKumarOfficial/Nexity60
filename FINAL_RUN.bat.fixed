@echo off
title Nexity60 - JavaFX News Application
echo ================================================================
echo                     Nexity60 Application                      
echo             FINAL VERSION - ALL BINDING ERRORS FIXED          
echo ================================================================
echo.

REM Change to the correct directory
cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

REM Check if Maven is available
mvn --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

echo [1/3] Ensuring clean build...
mvn clean compile -q
if errorlevel 1 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo [2/3] Starting JavaFX Application...
echo.
echo *** The application window should open now ***
echo *** Click on any category to fetch news ***
echo *** Look for summaries below each headline ***
echo *** Try switching between different categories ***
echo.

REM Use Maven JavaFX plugin for guaranteed compatibility
mvn javafx:run

echo.
echo [3/3] Application has closed.
echo.
echo If you saw any errors above, please copy them and report the issue.
echo Otherwise, the application ran successfully!
echo.
pause
