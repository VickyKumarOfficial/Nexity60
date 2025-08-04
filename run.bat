@echo off
echo Starting Nexity60 Application...
cd /d "%~dp0target"
java -Djava.awt.headless=false -jar Nexity60-1.0.0.jar
pause
