@echo off
echo ========================================
echo Starting Nexity60 Backend
echo ========================================
echo.

cd /d "%~dp0"

echo Checking for Maven...
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Maven found! Building and running...
    mvn clean spring-boot:run
) else (
    echo Maven not found. Trying alternative method...
    echo.
    echo Please install Maven or use VS Code:
    echo 1. Open Nexity60Application.java
    echo 2. Look for "Run | Debug" above the main method
    echo 3. Click "Run"
    echo.
    echo Or install Maven from: https://maven.apache.org/download.cgi
    pause
)
