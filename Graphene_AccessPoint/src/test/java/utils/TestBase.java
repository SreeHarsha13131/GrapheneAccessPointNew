package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * TestBase class manages WebDriver initialization with session management support.
 * Updated to support authentication session persistence.
 * 
 * @author SreeHarsha
 * @version 2.0
 */
public class TestBase {

    public WebDriver driver;
    public TestBase DriverManager;
    private AuthenticationManager authManager;

    /**
     * Initializes WebDriver with session management capabilities.
     * Configures Chrome with options that support session persistence.
     * 
     * @return WebDriver instance
     * @throws IOException if properties file cannot be read
     */
    public WebDriver WebDriverManager() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//resources//global.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        String url = properties.getProperty("Testurl");
        
        if (driver == null) {
            if (properties.getProperty("browser").equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver.exe");
                
                // Configure Chrome options for better session handling
                ChromeOptions options = new ChromeOptions();
                
                // Add arguments to improve session persistence
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-extensions");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--disable-features=VizDisplayCompositor");
                
                // Enable persistent sessions
                options.addArguments("--enable-features=NetworkService,NetworkServiceLogging");
                options.addArguments("--disable-features=TranslateUI");
                
                // Set user agent to avoid detection
                options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                
                // Experimental options for better session handling
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                
                driver = new ChromeDriver(options);
                
                System.out.println("✅ Chrome WebDriver initialized with session management support");
            }
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get(url);
            
            // Initialize authentication manager
            authManager = new AuthenticationManager(driver);
        }
        return driver;
    }
    
    /**
     * Gets the authentication manager instance.
     *
     * @return AuthenticationManager instance
     */
    public AuthenticationManager getAuthenticationManager() {
        if (authManager == null) {
            authManager = new AuthenticationManager(driver);
        }
        return authManager;
    }

    /**
     * Performs authentication with session management.
     * This method should be called instead of manual login steps.
     * 
     * @param username Microsoft username
     * @param password Microsoft password
     * @return true if authentication successful
     * @throws InterruptedException if interrupted during authentication
     */
    public boolean authenticateUser(String username, String password) throws InterruptedException {
        if (authManager == null) {
            authManager = new AuthenticationManager(driver);
        }
        return authManager.authenticateWithSessionManagement(username, password);
    }
    
    /**
     * Forces a fresh login, clearing any existing session.
     * Use this when you need to ensure a new authentication.
     * 
     * @param username Microsoft username
     * @param password Microsoft password
     * @return true if authentication successful
     * @throws InterruptedException if interrupted during authentication
     */
    public boolean forceLogin(String username, String password) throws InterruptedException {
        if (authManager == null) {
            authManager = new AuthenticationManager(driver);
        }
        return authManager.forceLogin(username, password);
    }
    
    /**
     * Checks if user is currently authenticated.
     * 
     * @return true if authenticated
     */
    public boolean isUserAuthenticated() {
        if (authManager == null) {
            authManager = new AuthenticationManager(driver);
        }
        return authManager.isAuthenticated();
    }
    
    /**
     * Logs out the current user and clears session.
     */
    public void logoutUser() {
        if (authManager != null) {
            authManager.logout();
        }
    }
}
