Write-Host "Starting Nexity60 Application..." -ForegroundColor Green
Write-Host "Fixed version with binding improvements" -ForegroundColor Yellow
Write-Host ""

# Check if Java is available
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java found: $($javaVersion[0])" -ForegroundColor Green
} catch {
    Write-Host "Error: Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Java and try again" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Starting application..." -ForegroundColor Yellow
Write-Host ""

try {
    # Try with JavaFX module options first
    Write-Host "Attempting to start with JavaFX module options..." -ForegroundColor Cyan
    & java --add-opens javafx.base/com.sun.javafx.event=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.scene.control.inputmap=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.tk.quantum=ALL-UNNAMED --add-opens javafx.graphics/com.sun.glass.ui=ALL-UNNAMED -jar target\Nexity60-1.0.0.jar
} catch {
    Write-Host "Module options failed. Trying basic execution..." -ForegroundColor Yellow
    try {
        & java -jar target\Nexity60-1.0.0.jar
    } catch {
        Write-Host "Failed to start application. Error: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "Application finished." -ForegroundColor Green
Read-Host "Press Enter to exit"
