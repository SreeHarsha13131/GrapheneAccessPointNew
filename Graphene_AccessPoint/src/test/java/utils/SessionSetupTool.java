package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObject.CiplaContractManagementPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Scanner;

/**
 * SessionSetupTool is a one-time utility to perform initial authentication 
 * and save the session state for future test runs.
 * 
 * This is equivalent to the Playwright save_storage_state() function you referenced.
 * 
 * Usage:
 * 1. Run this class as a standalone Java application
 * 2. Complete the Microsoft authentication with mobile PIN
 * 3. Press Enter when authentication is complete
 * 4. Session will be saved for future test runs
 * 
 * @author SreeHarsha
 * @version 1.0
 */
public class SessionSetupTool {
    
    private static WebDriver driver;
    private static CiplaContractManagementPage loginPage;
    
    public static void main(String[] args) {
        System.out.println("🔧 Session Setup Tool - One-time Authentication");
        System.out.println("================================================");
        
        try {
            // Initialize WebDriver
            initializeDriver();
            
            // Navigate to login page
            navigateToApplication();
            
            // Perform manual authentication
            performManualAuthentication();
            
            // Save session
            saveSession();
            
            System.out.println("✅ Session setup completed successfully!");
            System.out.println("You can now run your tests without mobile PIN verification.");
            
        } catch (Exception e) {
            System.out.println("❌ Session setup failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
    
    /**
     * Initializes Chrome WebDriver with session-friendly options.
     */
    private static void initializeDriver() throws IOException {
        System.out.println("🚀 Initializing Chrome WebDriver...");
        
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        // Initialize driver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src/test//resource//chromedriver.exe");
        driver = new ChromeDriver(options);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        loginPage = new CiplaContractManagementPage(driver);
        
        System.out.println("✅ WebDriver initialized successfully");
    }
    
    /**
     * Navigates to the application URL from properties file.
     */
    private static void navigateToApplication() throws IOException, InterruptedException {
        System.out.println("🌐 Navigating to application...");
        
        // Load properties
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//resources//global.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        String url = properties.getProperty("Testurl");
        
        driver.get(url);
        Thread.sleep(2000);
        
        System.out.println("✅ Navigated to: " + url);
    }
    
    /**
     * Guides user through manual authentication process.
     */
    private static void performManualAuthentication() throws InterruptedException {
        System.out.println("\n🔐 MANUAL AUTHENTICATION REQUIRED");
        System.out.println("==================================");
        System.out.println("Please complete the following steps manually in the browser:");
        System.out.println("1. Click on 'Graphene User' if not already selected");
        System.out.println("2. Enter your Microsoft username");
        System.out.println("3. Enter your Microsoft password");
        System.out.println("4. Complete mobile PIN verification on your phone");
        System.out.println("5. Handle any additional Microsoft authentication dialogs");
        System.out.println("6. Wait until you see the 'Access Point' homepage");
        System.out.println("\n⏳ When authentication is COMPLETE and you're on the Access Point homepage,");
        System.out.println("   press ENTER in this console to save the session...");
        
        // Wait for user input
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        
        // Verify authentication was successful
        try {
            if (loginPage.accessPointText().contains("Access Point")) {
                System.out.println("✅ Authentication verified successfully!");
            } else {
                System.out.println("⚠️ Warning: Could not verify authentication. Proceeding anyway...");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Could not verify authentication: " + e.getMessage());
            System.out.println("Proceeding with session save anyway...");
        }
    }
    
    /**
     * Saves the current session state.
     */
    private static void saveSession() {
        System.out.println("💾 Saving session state...");
        
        try {
            SessionManager.saveSessionState(driver);
            System.out.println("✅ Session saved successfully!");
            System.out.println("📁 Session file location: " + System.getProperty("user.dir") + "/src/test/java/resources/auth-session.json");
        } catch (IOException e) {
            System.out.println("❌ Failed to save session: " + e.getMessage());
            throw new RuntimeException("Session save failed", e);
        }
    }
    
    /**
     * Alternative method for automated authentication (if you want to use it).
     * This requires username and password to be provided.
     */
    public static void performAutomatedAuthentication(String username, String password) throws InterruptedException {
        System.out.println("🤖 Performing automated authentication...");
        
        try {
            // Verify we're on the login page
            if (!loginPage.getGrapheneUserText().contains("Graphene User")) {
                throw new RuntimeException("Not on expected login page");
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
            System.out.println("🔐 Please complete mobile PIN verification on your phone...");
            System.out.println("⏳ Waiting 30 seconds for authentication to complete...");
            Thread.sleep(30000);
            
            // Handle post-authentication dialogs
            try {
                loginPage.microsoftDoNotShowAgain();
                Thread.sleep(200);
                loginPage.microsoftYesBtn();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Post-auth dialogs may not be present: " + e.getMessage());
            }
            
            System.out.println("✅ Automated authentication completed");
            
        } catch (Exception e) {
            System.out.println("❌ Automated authentication failed: " + e.getMessage());
            throw new RuntimeException("Automated authentication failed", e);
        }
    }
}
