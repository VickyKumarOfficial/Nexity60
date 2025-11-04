# Nexity60 PWA - Quick Start Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Nexity60 PWA - Quick Start Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host "[1/4] Checking Java installation..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "✓ Java found: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ ERROR: Java not found! Please install Java 17 or higher." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Check Node.js
Write-Host "[2/4] Checking Node.js installation..." -ForegroundColor Yellow
try {
    $nodeVersion = node --version
    Write-Host "✓ Node.js found: $nodeVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ ERROR: Node.js not found! Please install Node.js 16 or higher." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Start Backend
Write-Host "[3/4] Starting Backend (Spring Boot)..." -ForegroundColor Yellow
Write-Host "Opening in new window..." -ForegroundColor Gray
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'pwa-backend'; Write-Host 'Starting Spring Boot Backend...' -ForegroundColor Green; mvn spring-boot:run"
Write-Host "✓ Backend starting at http://localhost:8080" -ForegroundColor Green
Write-Host "Waiting 15 seconds for backend to initialize..." -ForegroundColor Gray
Start-Sleep -Seconds 15
Write-Host ""

# Start Frontend
Write-Host "[4/4] Starting Frontend (React)..." -ForegroundColor Yellow
Write-Host "Opening in new window..." -ForegroundColor Gray
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'pwa-frontend'; Write-Host 'Installing dependencies and starting React...' -ForegroundColor Green; npm install; npm start"
Write-Host "✓ Frontend starting at http://localhost:3000" -ForegroundColor Green
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Nexity60 PWA is starting!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Backend:  http://localhost:8080" -ForegroundColor White
Write-Host "Frontend: http://localhost:3000" -ForegroundColor White
Write-Host ""
Write-Host "The app will open automatically in your browser." -ForegroundColor Gray
Write-Host "Check the opened windows for startup logs." -ForegroundColor Gray
Write-Host ""
Write-Host "Press any key to close this window..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
