@echo off
echo ========================================
echo Nexity60 PWA - Quick Start Script
echo ========================================
echo.

echo [1/4] Checking Java installation...
java -version 2>nul
if errorlevel 1 (
    echo ERROR: Java not found! Please install Java 17 or higher.
    pause
    exit /b 1
)
echo Java found!
echo.

echo [2/4] Checking Node.js installation...
node --version 2>nul
if errorlevel 1 (
    echo ERROR: Node.js not found! Please install Node.js 16 or higher.
    pause
    exit /b 1
)
echo Node.js found!
echo.

echo [3/4] Starting Backend (Spring Boot)...
echo This will open in a new window...
start "Nexity60 Backend" cmd /k "cd pwa-backend && mvn spring-boot:run"
echo Waiting for backend to start...
timeout /t 10 /nobreak >nul
echo.

echo [4/4] Starting Frontend (React)...
echo This will open in a new window...
start "Nexity60 Frontend" cmd /k "cd pwa-frontend && npm install && npm start"
echo.

echo ========================================
echo Nexity60 PWA is starting!
echo ========================================
echo.
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo.
echo The app will open automatically in your browser.
echo.
echo Press any key to close this window...
pause >nul
