@echo off
title Nexity60 - Project Name Test
echo ================================================================
echo                 NEXITY60 PROJECT NAME VERIFICATION            
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Cleaning old compiled files...
del *.class 2>nul
del Simple60SecNews*.class 2>nul

echo.
echo Compiling application...
javac --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml OriginalNewsApp.java

if errorlevel 1 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting Nexity60 application...
echo.
echo CHECK THESE IN THE APPLICATION WINDOW:
echo ✓ Window title should show: "Nexity60 - Smart Summarized News Reader"
echo ✓ Sidebar header should show: "Nexity60"
echo ✓ Welcome message should show: "Welcome to Nexity60"
echo ✓ App subtitle should show: "Get smart, summarized news in 60 seconds"
echo.

java --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics OriginalNewsApp

echo.
echo ================================================================
echo Application closed.
echo.
echo VERIFICATION QUESTIONS:
echo 1. Did the window title show "Nexity60"? (Y/N)
echo 2. Did the sidebar header show "Nexity60"? (Y/N)
echo 3. Did you see any references to "60secNews"? (Y/N)
echo.
set /p verification="If you saw any issues, please report them: "
echo.
echo Thank you for testing!
pause
