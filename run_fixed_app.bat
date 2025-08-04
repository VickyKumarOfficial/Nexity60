@echo off
echo Running Nexity60 Application with Fixed Bindings...
echo Version: Latest build with binding errors resolved
echo.

cd /d "d:\KL\2nd year\SEM 1\OOPs\Nexity60"

echo Ensuring latest build...
mvn clean compile -q

echo Starting JavaFX Application via Maven Plugin...
echo (This ensures latest code is used)
echo.
mvn javafx:run

echo.
echo Application execution completed.
pause
