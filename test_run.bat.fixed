@echo off
echo Testing Java and JAR execution...
echo.

echo Current directory:
cd

echo.
echo Checking if JAR file exists:
if exist "target\Nexity60-1.0.0.jar" (
    echo JAR file found: target\Nexity60-1.0.0.jar
    dir target\Nexity60-1.0.0.jar
) else (
    echo JAR file NOT found!
    echo Looking for JAR files in target directory:
    dir target\*.jar
    pause
    exit /b 1
)

echo.
echo Testing Java version:
java -version

echo.
echo Attempting to run the application with JavaFX fix...
echo Command: java -Djava.awt.headless=false -Dprism.order=sw -jar target\Nexity60-1.0.0.jar
echo.

java -Djava.awt.headless=false -Dprism.order=sw -jar target\Nexity60-1.0.0.jar

echo.
echo Exit code: %ERRORLEVEL%
pause
