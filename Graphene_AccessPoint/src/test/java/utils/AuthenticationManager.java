package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.CiplaContractManagementPage;

import java.io.IOException;
import java.time.Duration;

/**
 * AuthenticationManager handles the authentication flow with session management.
 * It attempts to reuse existing sessions to avoid repeated mobile PIN verification.
 * 
 * @author SreeHarsha
 * @version 1.0
 */
public class AuthenticationManager {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private CiplaContractManagementPage loginPage;
    
    public AuthenticationManager(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.loginPage = new CiplaContractManagementPage(driver);
    }
    
    /**
     * Performs authentication with session management.
     * First attempts to load existing session, falls back to full login if needed.
     * 
     * @param username Microsoft username
     * @param password Microsoft password
     * @return true if authentication successful, false otherwise
     * @throws InterruptedException if thread is interrupted during wait
     */
    public boolean authenticateWithSessionManagement(String username, String password) throws InterruptedException {
        System.out.println("Starting authentication with session management...");
        
        // First, try to load existing session
        if (SessionManager.sessionExists()) {
            System.out.println("Found existing session, attempting to load...");
            
            if (SessionManager.loadSessionState(driver)) {
                // Navigate to the application URL to test session
                navigateToApplication();
                
                // Check if session is still valid
                if (SessionManager.isSessionValid(driver)) {
                    System.out.println("✅ Existing session is valid! Skipping login.");
                    return true;
                } else {
                    System.out.println("❌ Existing session is invalid, proceeding with fresh login...");
                    SessionManager.clearSession();
                }
            }
        } else {
            System.out.println("No existing session found, proceeding with fresh login...");
        }
        
        // Perform fresh login
        return performFreshLogin(username, password);
    }
    
    /**
     * Performs a fresh login with mobile PIN verification and saves the session.
     * 
     * @param username Microsoft username
     * @param password Microsoft password
     * @return true if login successful, false otherwise
     * @throws InterruptedException if thread is interrupted during wait
     */
    private boolean performFreshLogin(String username, String password) throws InterruptedException {
        System.out.println("Performing fresh login...");
        
        try {
            // Navigate to login page
            navigateToApplication();
            
            // Verify we're on the login page
            if (!loginPage.getGrapheneUserText().contains("Graphene User")) {
                System.out.println("❌ Not on expected login page");
                return false;
            }
            
            // Perform login steps
            loginPage.selectGrapheneUser();
            Thread.sleep(500);
            
            loginPage.searchMicrosoftSignInTextField(username);
            Thread.sleep(200);
            
            loginPage.microsoftSubmitBtn();
            Thread.sleep(200);
            
            loginPage.SendMicrosoftPasswordTestField(password);
            Thread.sleep(2000);
            
            loginPage.microsoftSignInBtn();
            
            // Wait for mobile PIN verification
            System.out.println("🔐 Please complete mobile PIN verification...");
            System.out.println("⏳ Waiting for authentication to complete...");
            
            // Wait longer for mobile PIN verification
            Thread.sleep(15000);
            
            // Handle post-authentication dialogs
            try {
                loginPage.microsoftDoNotShowAgain();
                Thread.sleep(200);
                loginPage.microsoftYesBtn();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Post-auth dialogs may not be present: " + e.getMessage());
            }
            
            // Verify successful login
            if (loginPage.accessPointText().contains("Access Point")) {
                System.out.println("✅ Login successful! Saving session...");
                
                // Save session state for future use
                try {
                    SessionManager.saveSessionState(driver);
                    System.out.println("✅ Session saved successfully!");
                } catch (IOException e) {
                    System.out.println("⚠️ Warning: Could not save session: " + e.getMessage());
                }
                
                return true;
            } else {
                System.out.println("❌ Login verification failed");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Navigates to the application URL based on properties file.
     */
    private void navigateToApplication() {
        try {
            // Get URL from properties (assuming it's already loaded in TestBase)
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl == null || currentUrl.equals("data:,")) {
                // If no URL is set, we need to navigate to the application
                // This assumes TestBase has already set the URL
                driver.navigate().refresh();
            }
        } catch (Exception e) {
            System.out.println("Navigation issue: " + e.getMessage());
        }
    }
    
    /**
     * Forces a fresh login by clearing existing session.
     * Use this when you want to ensure a new authentication.
     * 
     * @param username Microsoft username
     * @param password Microsoft password
     * @return true if login successful, false otherwise
     * @throws InterruptedException if thread is interrupted during wait
     */
    public boolean forceLogin(String username, String password) throws InterruptedException {
        System.out.println("Forcing fresh login (clearing existing session)...");
        SessionManager.clearSession();
        return performFreshLogin(username, password);
    }
    
    /**
     * Checks if user is currently authenticated.
     * 
     * @return true if authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        try {
            return loginPage.accessPointText().contains("Access Point");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Logs out the current user and clears session.
     */
    public void logout() {
        try {
            // Add logout logic here if your application has a logout button
            // For now, we'll just clear the session
            SessionManager.clearSession();
            System.out.println("Session cleared - user logged out");
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
        }
    }
}
