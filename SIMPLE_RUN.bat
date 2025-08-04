@echo off
title Nexity60 - Simple Launcher
echo ================================================================
echo                     Nexity60 Application                      
echo              SIMPLE LAUNCHER - MULTIPLE OPTIONS               
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Available launch options:
echo.
echo [1] Use pre-built JAR (fastest - if available)
echo [2] Compile and run from source (no Maven needed)
echo [3] Check system requirements
echo [Q] Quit
echo.
set /p choice="Choose an option (1, 2, 3, or Q): "

if /i "%choice%"=="1" goto option1
if /i "%choice%"=="2" goto option2
if /i "%choice%"=="3" goto option3
if /i "%choice%"=="q" goto quit
echo Invalid choice. Please try again.
pause
goto start

:option1
echo.
echo Launching with pre-built JAR...
call RUN_NO_MAVEN.bat
goto end

:option2
echo.
echo Compiling and running from source...
call COMPILE_AND_RUN.bat
goto end

:option3
echo.
echo Checking system requirements...
echo.
echo [Java Version]
java -version 2>&1
echo.
echo [Directory Contents]
dir /b target\*.jar 2>nul
if errorlevel 1 (
    echo No JAR files found in target directory
) else (
    echo JAR files found!
)
echo.
echo [Required Files]
if exist "src\main\java\ui\MainWindow.java" (
    echo ✓ Source files found
) else (
    echo ✗ Source files missing
)
if exist "lib" (
    echo ✓ lib directory exists
    dir /b lib\*.jar 2>nul | find /c ".jar" > temp_count.txt
    set /p jar_count=<temp_count.txt
    del temp_count.txt
    echo   Found JAR files in lib
) else (
    echo ✗ lib directory missing
)
echo.
pause
goto start

:quit
echo Goodbye!
goto end

:end
