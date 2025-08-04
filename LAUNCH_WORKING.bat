@echo off
title Nexity60 - Working Launcher
echo ================================================================
echo                     Nexity60 Application                      
echo                   WORKING LAUNCHER                            
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Starting Nexity60 application (no-module mode)...
echo.

java -cp "javafx-sdk-17.0.2\lib\*;lib\*;." --add-exports java.desktop/sun.awt=ALL-UNNAMED OriginalNewsApp

echo.
echo Application finished.
pause
