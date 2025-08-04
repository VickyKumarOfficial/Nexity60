@echo off
echo Testing Nexity60 Application (Quick Test)...
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Running for 10 seconds to check for binding errors...
timeout /t 10 /nobreak >nul 2>&1 & java -cp target\Nexity60-1.0.0.jar ui.MainWindow

echo.
echo Test completed.
pause
