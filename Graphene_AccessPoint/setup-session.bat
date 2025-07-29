@echo off
echo ========================================
echo    Session Setup Tool for AccessPoint
echo ========================================
echo.
echo This will help you set up authentication session
echo to avoid repeated mobile PIN verification.
echo.
echo Make sure you have:
echo 1. Your mobile phone ready for PIN approval
echo 2. Valid Microsoft credentials
echo 3. Chrome browser installed
echo.
pause

echo.
echo Starting session setup...
echo.

cd /d "%~dp0"

:: Compile and run the SessionSetupTool
echo Compiling SessionSetupTool...
javac -cp "target/test-classes;target/dependency/*" src/test/java/utils/SessionSetupTool.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    echo Please ensure:
    echo 1. Java is installed and in PATH
    echo 2. Maven dependencies are downloaded (run: mvn clean compile)
    echo 3. All required JAR files are in target/dependency/
    echo.
    pause
    exit /b 1
)

echo.
echo Running SessionSetupTool...
echo.
java -cp "src/test/java;target/test-classes;target/dependency/*" utils.SessionSetupTool

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Session setup failed!
    echo Please check the error messages above.
    echo.
) else (
    echo.
    echo ========================================
    echo    Session Setup Completed Successfully!
    echo ========================================
    echo.
    echo You can now run your tests without mobile PIN verification.
    echo Session file saved to: src/test/java/resources/auth-session.json
    echo.
    echo To run your tests:
    echo   mvn test
    echo.
)

pause
