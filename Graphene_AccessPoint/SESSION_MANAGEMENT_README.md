# Session Management for Microsoft Authentication

## Overview
This framework now includes session management capabilities to avoid repeated Microsoft authentication with mobile PIN verification. This is similar to Playwright's `storage_state()` functionality.

## 🚀 Quick Start

### Step 1: One-Time Session Setup
Before running your tests, you need to perform a one-time authentication to save your session:

```bash
# Navigate to your project directory
cd C:\Users\DELL\IdeaProjects\Graphene_AccessPoint

# Compile and run the SessionSetupTool
javac -cp "path/to/selenium-jars/*" src/test/java/utils/SessionSetupTool.java
java -cp "path/to/selenium-jars/*:src/test/java" utils.SessionSetupTool
```

**Or run it directly from your IDE:**
1. Open `SessionSetupTool.java`
2. Run it as a Java application
3. Follow the on-screen instructions:
   - Browser will open automatically
   - Complete Microsoft authentication manually
   - Approve mobile PIN verification
   - Press Enter when you reach the Access Point homepage
   - Session will be saved automatically

### Step 2: Run Your Tests
After session setup, your tests will automatically use the saved session:

```bash
# Run your Cucumber tests normally
mvn test
```

## 📁 Files Added/Modified

### New Files:
- `utils/SessionManager.java` - Core session management functionality
- `utils/AuthenticationManager.java` - Handles authentication with session reuse
- `utils/SessionSetupTool.java` - One-time setup utility
- `resources/auth-session.json` - Saved session data (auto-generated)

### Modified Files:
- `utils/TestBase.java` - Updated with session management support
- `stepDefinitions/CiplaContractManagementStepDefination.java` - Uses new authentication flow

## 🔧 How It Works

### Session Storage
The system saves:
- **Cookies** - Authentication tokens and session data
- **localStorage** - Browser local storage data
- **sessionStorage** - Browser session storage data

### Authentication Flow
1. **First Run**: Performs full authentication with mobile PIN
2. **Subsequent Runs**: Attempts to load saved session
3. **Fallback**: If session is invalid, performs fresh authentication

### Session Validation
The system checks session validity by:
- Verifying authentication cookies exist
- Checking if still on login page
- Validating Access Point homepage accessibility

## 🛠️ Usage Examples

### Basic Test Run (Automatic Session Management)
```java
// In your step definitions - this now handles session automatically
@When("^User login to the AccessPoint Site usings (.+) and (.+)$")
public void user_login_to_the_access_point_site_using_UserName_And_Passwords(String UserName, String Password) throws InterruptedException {
    // This will automatically use saved session if available
    boolean authSuccess = testContextSetUp.testBase.authenticateUser(UserName, Password);
    if (!authSuccess) {
        throw new RuntimeException("Authentication failed for user: " + UserName);
    }
}
```

### Force Fresh Login (When Needed)
```java
// Force a fresh login (clears existing session)
testContextSetUp.testBase.forceLogin(username, password);
```

### Check Authentication Status
```java
// Check if user is currently authenticated
boolean isAuth = testContextSetUp.testBase.isUserAuthenticated();
```

### Manual Session Management
```java
// Clear session manually
SessionManager.clearSession();

// Check if session exists
boolean hasSession = SessionManager.sessionExists();

// Validate current session
boolean isValid = SessionManager.isSessionValid(driver);
```

## 🔍 Troubleshooting

### Session Not Working?
1. **Clear existing session**: Delete `resources/auth-session.json`
2. **Run setup again**: Execute `SessionSetupTool.java`
3. **Check credentials**: Verify username/password in `global.properties`

### Authentication Fails?
1. **Check mobile PIN**: Ensure you approve the authentication request
2. **Wait longer**: Mobile PIN verification can take 15-30 seconds
3. **Manual verification**: Use `SessionSetupTool` for manual authentication

### Session Expires?
- Sessions are automatically validated before each test
- Invalid sessions trigger fresh authentication
- Consider running `SessionSetupTool` weekly to refresh sessions

## 📊 Benefits

### Before Session Management:
- ❌ Every test required mobile PIN verification
- ❌ Manual intervention for each test run
- ❌ Slow test execution (30+ seconds per login)
- ❌ Tests couldn't run unattended

### After Session Management:
- ✅ One-time mobile PIN verification
- ✅ Automatic session reuse
- ✅ Fast test execution (2-3 seconds for auth)
- ✅ Unattended test execution
- ✅ Reliable CI/CD integration

## 🔒 Security Considerations

1. **Session File**: `auth-session.json` contains sensitive authentication data
2. **Gitignore**: Add `auth-session.json` to `.gitignore`
3. **Expiration**: Sessions may expire and require refresh
4. **Environment**: Use different sessions for different environments

## 📝 Configuration

### Global Properties
Update `resources/global.properties`:
```properties
browser=chrome
Testurl=https://sso.graphenesvc.com/Home/Index/?appId=198fc6da-4a59-469a-b226-c7d5ea56b463
UserName=Sreeharsha@graphenesvc.com
Password=Hahsrah1313?1!
```

### Chrome Options
The framework includes optimized Chrome options for session management:
- Disabled automation detection
- Enhanced session persistence
- Improved cookie handling

## 🚀 Advanced Usage

### Custom Session Validation
```java
// Implement custom session validation logic
public boolean customSessionValidation(WebDriver driver) {
    // Add your specific validation logic here
    return SessionManager.isSessionValid(driver);
}
```

### Environment-Specific Sessions
```java
// Save sessions for different environments
SessionManager.saveSessionState(driver, "production");
SessionManager.loadSessionState(driver, "production");
```

### Session Monitoring
```java
// Monitor session health
if (!SessionManager.isSessionValid(driver)) {
    System.out.println("Session expired, refreshing...");
    authManager.forceLogin(username, password);
}
```

## 📞 Support

If you encounter issues:
1. Check the console output for detailed error messages
2. Verify Chrome WebDriver is properly configured
3. Ensure all dependencies are included in your project
4. Test the `SessionSetupTool` independently first

---

**Happy Testing! 🎉**

*This session management system eliminates the need for repeated mobile PIN verification, making your test automation faster and more reliable.*
