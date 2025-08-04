@echo off
title Nexity60 - Enhanced with Read Full Article
echo ================================================================
echo                     Nexity60 Application                      
echo                   ENHANCED WITH NEW FEATURE                   
echo ================================================================
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo 🚀 NEW FEATURE ADDED: "Read Full Article" Button!
echo.
echo ✅ ORIGINAL DESIGN FEATURES:
echo    - Left sidebar with category navigation
echo    - Trending news loaded by default
echo    - Modern JavaFX interface with loading animations
echo    - 60-second news summaries
echo.
echo 🆕 NEW ENHANCEMENT:
echo    - "Read Full Article" button on every news card
echo    - Opens original news website in your default browser
echo    - Real news URLs (BBC, Reuters, Nature, NASA, etc.)
echo    - Cross-platform browser support (Windows, Mac, Linux)
echo    - Confirmation dialogs and error handling
echo.
echo 📰 AVAILABLE CATEGORIES:
echo    - Trending (loaded by default)
echo    - Technology, World News, Business
echo    - Sports, Health, Science, Entertainment
echo.
echo Starting enhanced Nexity60 application...
echo.

java --module-path "javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml OriginalNewsApp

echo.
echo Application finished.
pause
