@echo off
echo ========================================
echo Maven Installation Script for Nexity60
echo ========================================
echo.

set MAVEN_VERSION=3.9.5
set INSTALL_DIR=%USERPROFILE%\.m2\apache-maven-%MAVEN_VERSION%

echo Checking if Maven is already installed...
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Maven is already installed!
    mvn -version
    pause
    exit /b 0
)

echo.
echo Maven will be installed to: %INSTALL_DIR%
echo.
echo Please follow these steps:
echo.
echo 1. Download Maven from: https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip
echo 2. Extract the ZIP file to: %USERPROFILE%\.m2\
echo 3. Run this script again to add Maven to PATH
echo.
echo OR - Use the quick PowerShell installer below:
echo.

pause

echo.
echo Opening download page in browser...
start https://maven.apache.org/download.cgi

echo.
echo After downloading, I can help you set it up!
pause
