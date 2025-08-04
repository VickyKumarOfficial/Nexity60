@echo off
title Nexity60 - Final Launcher
echo ================================================================
echo                     Nexity60 Application                      
echo                      FINAL LAUNCHER                          
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking JavaFX setup...
if not exist "javafx-sdk-17.0.2\lib\javafx.controls.jar" (
    echo ERROR: JavaFX not found. Please run AUTO_SETUP.bat first.
    pause
    exit /b 1
)
echo + JavaFX SDK found

echo.
echo Compiling all source files...
if not exist "out" mkdir out

REM Compile all files at once with proper classpath
javac --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp "javafx-sdk-17.0.2\lib\*" -d out src\main\java\config\*.java src\main\java\model\*.java src\main\java\storage\*.java src\main\java\utils\*.java src\main\java\core\*.java src\main\java\ui\*.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo + Compilation successful!
echo.
echo Starting Nexity60 application...
echo.

java --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp "out;javafx-sdk-17.0.2\lib\*" ui.MainWindow

if errorlevel 1 (
    echo.
    echo ERROR: Application failed to start!
    echo Make sure JavaFX is properly configured.
    pause
    exit /b 1
)

echo.
echo Application finished successfully.
pause
