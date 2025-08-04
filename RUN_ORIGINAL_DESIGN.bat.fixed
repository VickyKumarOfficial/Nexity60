@echo off
title Nexity60 - Original Design with JavaFX
echo ================================================================
echo                     Nexity60 Application                      
echo                    ORIGINAL DESIGN RESTORED                   
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking JavaFX setup...
if not exist "javafx-sdk-17.0.2\lib\javafx-controls-17.0.2.jar" (
    echo ERROR: JavaFX not found. Running auto-setup...
    echo.
    if exist "downloads\javafx-sdk.zip" (
        echo JavaFX already downloaded. Extracting...
        powershell -Command "Expand-Archive -Path 'downloads\javafx-sdk.zip' -DestinationPath '.' -Force"
    ) else (
        echo Downloading JavaFX SDK...
        if not exist "downloads" mkdir downloads
        powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip' -OutFile 'downloads\javafx-sdk.zip'}"
        powershell -Command "Expand-Archive -Path 'downloads\javafx-sdk.zip' -DestinationPath '.' -Force"
    )
)

if not exist "javafx-sdk-17.0.2\lib\javafx-controls-17.0.2.jar" (
    echo ERROR: JavaFX setup failed. Please run AUTO_SETUP.bat first.
    pause
    exit /b 1
)

echo + JavaFX SDK ready
echo.
echo Compiling original JavaFX application...

javac --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -d . OriginalNewsApp.java

if errorlevel 1 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo + Compilation successful!
echo.
echo Starting Nexity60 with original design...
echo Features:
echo - Left sidebar with categories
echo - Trending news loaded by default  
echo - Category selection (Trending, Technology, World, Business, Sports, Health, Science, Entertainment)
echo - Loading animations
echo - Modern JavaFX design
echo.

java --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml OriginalNewsApp

echo.
echo Application finished.
pause
