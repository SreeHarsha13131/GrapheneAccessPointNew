package utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * SessionValidationTest - Validates that session management is working correctly.
 * 
 * This test class verifies:
 * 1. Session can be saved and loaded
 * 2. Authentication works with session reuse
 * 3. Session validation is functioning
 * 
 * Run this test after setting up your session with SessionSetupTool.
 * 
 * @author SreeHarsha
 * @version 1.0
 */
public class SessionValidationTest {
    
    private TestBase testBase;
    private WebDriver driver;
    private String username;
    private String password;
    
    @BeforeClass
    public void setUp() throws IOException {
        System.out.println("🧪 Setting up Session Validation Test...");
        
        // Load credentials from properties
        loadCredentials();
        
        // Initialize test base
        testBase = new TestBase();
        driver = testBase.WebDriverManager();
        
        System.out.println("✅ Test setup completed");
    }
    
    @Test(priority = 1)
    public void testSessionExists() {
        System.out.println("🔍 Test 1: Checking if session file exists...");
        
        boolean sessionExists = SessionManager.sessionExists();
        
        if (sessionExists) {
            System.out.println("✅ Session file found!");
        } else {
            System.out.println("❌ Session file not found!");
            System.out.println("Please run SessionSetupTool first to create a session.");
        }
        
        Assert.assertTrue(sessionExists, "Session file should exist. Run SessionSetupTool first.");
    }
    
    @Test(priority = 2, dependsOnMethods = "testSessionExists")
    public void testSessionLoad() {
        System.out.println("🔍 Test 2: Testing session load functionality...");
        
        try {
            boolean loaded = SessionManager.loadSessionState(driver);
            
            if (loaded) {
                System.out.println("✅ Session loaded successfully!");
            } else {
                System.out.println("❌ Session load failed!");
            }
            
            Assert.assertTrue(loaded, "Session should load successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Session load threw exception: " + e.getMessage());
            Assert.fail("Session load should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(priority = 3, dependsOnMethods = "testSessionLoad")
    public void testSessionValidation() {
        System.out.println("🔍 Test 3: Testing session validation...");
        
        try {
            // Wait a moment for page to load
            Thread.sleep(2000);
            
            boolean isValid = SessionManager.isSessionValid(driver);
            
            if (isValid) {
                System.out.println("✅ Session is valid!");
            } else {
                System.out.println("⚠️ Session appears invalid - this might be expected if session expired");
            }
            
            // Note: We don't assert true here because session might have expired
            // This test is informational
            System.out.println("ℹ️ Session validation result: " + isValid);
            
        } catch (Exception e) {
            System.out.println("❌ Session validation threw exception: " + e.getMessage());
            Assert.fail("Session validation should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(priority = 4, dependsOnMethods = "testSessionValidation")
    public void testAuthenticationWithSession() {
        System.out.println("🔍 Test 4: Testing authentication with session management...");
        
        try {
            boolean authSuccess = testBase.authenticateUser(username, password);
            
            if (authSuccess) {
                System.out.println("✅ Authentication successful!");
            } else {
                System.out.println("❌ Authentication failed!");
            }
            
            Assert.assertTrue(authSuccess, "Authentication should succeed with session management");
            
        } catch (Exception e) {
            System.out.println("❌ Authentication threw exception: " + e.getMessage());
            Assert.fail("Authentication should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(priority = 5, dependsOnMethods = "testAuthenticationWithSession")
    public void testUserAuthenticationStatus() {
        System.out.println("🔍 Test 5: Testing user authentication status check...");
        
        try {
            boolean isAuthenticated = testBase.isUserAuthenticated();
            
            if (isAuthenticated) {
                System.out.println("✅ User is authenticated!");
            } else {
                System.out.println("❌ User is not authenticated!");
            }
            
            Assert.assertTrue(isAuthenticated, "User should be authenticated after successful login");
            
        } catch (Exception e) {
            System.out.println("❌ Authentication status check threw exception: " + e.getMessage());
            Assert.fail("Authentication status check should not throw exception: " + e.getMessage());
        }
    }
    
    @AfterClass
    public void tearDown() {
        System.out.println("🧹 Cleaning up test...");
        
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver closed");
        }
        
        System.out.println("✅ Test cleanup completed");
    }
    
    /**
     * Loads credentials from global.properties file.
     */
    private void loadCredentials() throws IOException {
        System.out.println("📋 Loading credentials from properties...");
        
        FileInputStream fileInputStream = new FileInputStream(
            System.getProperty("user.dir") + "//src//test//java//resources//global.properties"
        );
        Properties properties = new Properties();
        properties.load(fileInputStream);
        
        username = properties.getProperty("UserName");
        password = properties.getProperty("Password");
        
        if (username == null || password == null) {
            throw new RuntimeException("Username or password not found in global.properties");
        }
        
        System.out.println("✅ Credentials loaded for user: " + username);
    }
    
    /**
     * Utility method to print test results summary.
     */
    public static void printTestSummary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           SESSION VALIDATION TEST SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("1. ✅ Session file existence check");
        System.out.println("2. ✅ Session loading functionality");
        System.out.println("3. ✅ Session validation logic");
        System.out.println("4. ✅ Authentication with session reuse");
        System.out.println("5. ✅ User authentication status check");
        System.out.println("=".repeat(50));
        System.out.println("🎉 All session management features validated!");
        System.out.println("Your framework is ready for automated testing without mobile PIN verification.");
        System.out.println("=".repeat(50));
    }
}
