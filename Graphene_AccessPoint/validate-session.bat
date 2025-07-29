@echo off
echo ========================================
echo    Session Validation Test
echo ========================================
echo.
echo This will validate that your session management
echo is working correctly.
echo.
echo Prerequisites:
echo 1. Session setup completed (run setup-session.bat first)
echo 2. Maven dependencies downloaded
echo.
pause

echo.
echo Running session validation test...
echo.

cd /d "%~dp0"

:: Run the validation test using Maven
echo Running TestNG validation test...
mvn test -Dtest=SessionValidationTest

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Validation test failed!
    echo Please check the error messages above.
    echo.
    echo Common issues:
    echo 1. Session not set up (run setup-session.bat first)
    echo 2. Maven dependencies not downloaded (run: mvn clean compile)
    echo 3. Chrome WebDriver not found
    echo.
) else (
    echo.
    echo ========================================
    echo    Session Validation Completed!
    echo ========================================
    echo.
    echo Your session management is working correctly.
    echo You can now run your Cucumber tests without mobile PIN verification.
    echo.
)

pause
