@echo off
echo Starting Nexity60 via Maven JavaFX Plugin...
echo This method runs the application without requiring JAR packaging
echo.

REM Check if Maven is available
where mvn >nul 2>&1
if errorlevel 1 (
    echo Maven not found in PATH. Trying with full path...
    if exist "C:\tools\apache-maven-3.9.4\bin\mvn.cmd" (
        echo Found Maven at C:\tools\apache-maven-3.9.4\bin\
        set "MAVEN_CMD=C:\tools\apache-maven-3.9.4\bin\mvn.cmd"
    ) else (
        echo Error: Maven not found!
        echo Please ensure Maven is installed and in your PATH
        pause
        exit /b 1
    )
) else (
    set "MAVEN_CMD=mvn"
)

echo Using Maven command: %MAVEN_CMD%
echo.

echo Compiling and running application...
%MAVEN_CMD% clean compile javafx:run

echo.
echo Maven execution finished.
pause
