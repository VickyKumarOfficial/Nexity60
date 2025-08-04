@echo off
title Nexity60 - Working Version
echo ================================================================
echo                     Nexity60 Application                      
echo                      WORKING VERSION                          
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Compiling standalone version...
echo.

REM Compile all Java files
javac -d . NewsArticle.java NewsService.java NewsApp.java

if errorlevel 1 (
    echo ERROR: Compilation failed
    echo Please check the error messages above
    pause
    exit /b 1
)

echo + Compilation successful!
echo.
echo Starting Nexity60 application...
echo.

java NewsApp

if errorlevel 1 (
    echo ERROR: Failed to start application
    pause
    exit /b 1
)

echo.
echo Application finished.
pause
