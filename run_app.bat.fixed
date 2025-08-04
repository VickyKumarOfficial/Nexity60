@echo off
echo Starting Nexity60 Application...
echo Fixed version with binding issues resolved and JavaFX compatibility for Java 24
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java and try again
    pause
    exit /b 1
)

echo Java found. Starting application with JavaFX software rendering...
echo.

REM Use software rendering to avoid JavaFX hardware issues
java -Djava.awt.headless=false ^
     -Dprism.order=sw ^
     -Dprism.verbose=true ^
     -Djavafx.animation.pulse=60 ^
     -jar target\Nexity60-1.0.0.jar

if errorlevel 1 (
    echo.
    echo Application failed. Trying alternative approach...
    echo.
    java -Dprism.order=sw -Dglass.platform=win -jar target\Nexity60-1.0.0.jar
)

echo.
echo Application finished.
pause
