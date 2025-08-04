@echo off
title Nexity60 - Auto Setup
echo ================================================================
echo                     Nexity60 Application                      
echo                      AUTO SETUP                              
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Checking setup...
echo.

REM Check if JavaFX is already set up
if exist "javafx-sdk-17.0.2\lib\javafx-controls-17.0.2.jar" (
    echo + JavaFX SDK found
    goto compile_and_run
)

echo JavaFX SDK not found. Setting up JavaFX...
echo.

echo OPTION 1: Download JavaFX automatically (requires internet)
echo OPTION 2: Manual setup instructions
echo.
set /p choice="Choose option (1 or 2): "

if "%choice%"=="1" goto auto_download
if "%choice%"=="2" goto manual_setup

:auto_download
echo.
echo Downloading JavaFX SDK...
echo This may take a few minutes...

REM Create downloads directory if it doesn't exist
if not exist "downloads" mkdir downloads

REM Download JavaFX SDK using PowerShell
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip' -OutFile 'downloads\javafx-sdk.zip'}"

if errorlevel 1 (
    echo Download failed. Switching to manual setup...
    goto manual_setup
)

echo + Download complete!
echo.
echo Extracting JavaFX SDK...

REM Extract using PowerShell
powershell -Command "Expand-Archive -Path 'downloads\javafx-sdk.zip' -DestinationPath '.' -Force"

if exist "javafx-sdk-17.0.2\lib\javafx-controls-17.0.2.jar" (
    echo + JavaFX SDK extracted successfully!
    goto compile_and_run
) else (
    echo Extraction failed. Please try manual setup.
    goto manual_setup
)

:manual_setup
echo.
echo MANUAL SETUP INSTRUCTIONS:
echo.
echo 1. Go to: https://gluonhq.com/products/javafx/
echo 2. Download JavaFX SDK 17.0.2 for Windows x64
echo 3. Extract the zip file to this folder
echo 4. Make sure you have: javafx-sdk-17.0.2\lib\javafx-controls-17.0.2.jar
echo 5. Run this script again
echo.
echo Press any key when ready...
pause >nul
exit /b 1

:compile_and_run
echo.
echo Setting up classpath with JavaFX...

if not exist "out" mkdir out

echo Compiling application...
javac --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp "javafx-sdk-17.0.2\lib\*" -d out -sourcepath src src\main\java\main\Main.java

if errorlevel 1 (
    echo.
    echo Compilation failed. Check the errors above.
    pause
    exit /b 1
)

echo + Compilation successful!
echo.
echo Starting Nexity60 application...
echo.

java --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp "out;javafx-sdk-17.0.2\lib\*" main.Main

echo.
echo Application finished.
pause
