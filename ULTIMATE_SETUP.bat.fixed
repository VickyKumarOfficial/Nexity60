@echo off
title Nexity60 - Ultimate Solution
echo ================================================================
echo                     Nexity60 Application                      
echo                    ULTIMATE SOLUTION                          
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking system setup...
echo.

REM Check Java first
echo [JAVA CHECK]
java -version >nul 2>&1
if errorlevel 1 (
    echo X Java is NOT installed
    echo.
    echo CRITICAL: Java is required to run this application
    echo Please download and install Java from: https://adoptium.net/
    echo Choose Java 11 or higher
    echo.
    pause
    exit /b 1
) else (
    echo + Java is installed
    java -version 2>&1 | findstr "version"
)

echo.
echo [MAVEN CHECK]
mvn --version >nul 2>&1
if errorlevel 1 (
    echo X Maven is NOT installed
    echo.
    echo SOLUTION OPTIONS:
    echo.
    echo OPTION 1 - Install Maven RECOMMENDED:
    echo 1. Download Maven from: https://maven.apache.org/download.cgi
    echo 2. Extract to C:\tools\apache-maven-3.x.x
    echo 3. Add C:\tools\apache-maven-3.x.x\bin to PATH
    echo 4. Restart command prompt and run FINAL_RUN.bat
    echo.
    echo OPTION 2 - Use alternative launcher:
    echo 1. Run RUN_NO_MAVEN.bat if JAR exists
    echo 2. Run EMERGENCY_RUN.bat compile from source
    echo.
    echo OPTION 3 - Quick Maven setup:
    set /p install_maven=Would you like me to help set up Maven? y/n: 
    if /i "%install_maven%"=="y" goto maven_setup
    echo.
    echo Please choose one of the options above and try again.
    pause
    exit /b 1
) else (
    echo + Maven is installed
    mvn --version 2>&1 | findstr "Apache Maven"
    echo.
    echo Great! Maven is available. Running the application...
    echo.
    call FINAL_RUN.bat
    goto end
)

:maven_setup
echo.
echo MAVEN QUICK SETUP HELPER
echo.
echo Please follow these steps:
echo.
echo 1. Open https://maven.apache.org/download.cgi in your browser
echo 2. Download "Binary zip archive" (apache-maven-3.x.x-bin.zip)
echo 3. Extract to C:\tools\ (create folder if needed)
echo 4. Add C:\tools\apache-maven-3.x.x\bin to Windows PATH
echo.
echo Steps to add to PATH:
echo 1. Press Win+R, type "sysdm.cpl", press Enter
echo 2. Click "Environment Variables"
echo 3. Under "System Variables", find and select "Path", click "Edit"
echo 4. Click "New" and add: C:\tools\apache-maven-3.x.x\bin
echo 5. Click OK on all dialogs
echo 6. Restart this command prompt
echo 7. Run FINAL_RUN.bat
echo.
echo Press any key when done...
pause >nul

:end
echo.
echo Setup complete!
pause
