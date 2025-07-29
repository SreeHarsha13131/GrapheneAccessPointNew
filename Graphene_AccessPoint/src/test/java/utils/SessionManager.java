package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * SessionManager handles saving and loading browser session data (cookies, localStorage, sessionStorage)
 * to avoid repeated Microsoft authentication with mobile PIN verification.
 * 
 * This class provides functionality similar to Playwright's storage_state() method.
 * 
 * @author SreeHarsha
 * @version 1.0
 */
public class SessionManager {
    
    private static final String SESSION_FILE_PATH = System.getProperty("user.dir") + "/src/test/java/resources/auth-session.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Saves the current browser session state (cookies, localStorage, sessionStorage) to a JSON file.
     * This should be called after successful authentication.
     * 
     * @param driver WebDriver instance with active session
     * @throws IOException if file operations fail
     */
    public static void saveSessionState(WebDriver driver) throws IOException {
        ObjectNode sessionData = objectMapper.createObjectNode();
        
        // Save cookies
        ArrayNode cookiesArray = objectMapper.createArrayNode();
        Set<Cookie> cookies = driver.manage().getCookies();
        
        for (Cookie cookie : cookies) {
            ObjectNode cookieNode = objectMapper.createObjectNode();
            cookieNode.put("name", cookie.getName());
            cookieNode.put("value", cookie.getValue());
            cookieNode.put("domain", cookie.getDomain());
            cookieNode.put("path", cookie.getPath());
            cookieNode.put("secure", cookie.isSecure());
            cookieNode.put("httpOnly", cookie.isHttpOnly());
            
            if (cookie.getExpiry() != null) {
                cookieNode.put("expiry", cookie.getExpiry().getTime());
            }
            
            cookiesArray.add(cookieNode);
        }
        sessionData.set("cookies", cookiesArray);
        
        // Save localStorage
        try {
            String localStorageScript = 
                "var localStorage = {}; " +
                "for (var i = 0; i < window.localStorage.length; i++) { " +
                "    var key = window.localStorage.key(i); " +
                "    localStorage[key] = window.localStorage.getItem(key); " +
                "} " +
                "return localStorage;";
            
            Object localStorageData = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(localStorageScript);
            sessionData.set("localStorage", objectMapper.valueToTree(localStorageData));
        } catch (Exception e) {
            System.out.println("Could not save localStorage: " + e.getMessage());
        }
        
        // Save sessionStorage
        try {
            String sessionStorageScript = 
                "var sessionStorage = {}; " +
                "for (var i = 0; i < window.sessionStorage.length; i++) { " +
                "    var key = window.sessionStorage.key(i); " +
                "    sessionStorage[key] = window.sessionStorage.getItem(key); " +
                "} " +
                "return sessionStorage;";
            
            Object sessionStorageData = ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(sessionStorageScript);
            sessionData.set("sessionStorage", objectMapper.valueToTree(sessionStorageData));
        } catch (Exception e) {
            System.out.println("Could not save sessionStorage: " + e.getMessage());
        }
        
        // Create directories if they don't exist
        Path sessionFilePath = Paths.get(SESSION_FILE_PATH);
        Files.createDirectories(sessionFilePath.getParent());
        
        // Write session data to file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(SESSION_FILE_PATH), sessionData);
        System.out.println("Session state saved to: " + SESSION_FILE_PATH);
    }
    
    /**
     * Loads previously saved session state and applies it to the current browser session.
     * This should be called before navigating to the application URL.
     * 
     * @param driver WebDriver instance to apply session to
     * @return true if session was loaded successfully, false otherwise
     */
    public static boolean loadSessionState(WebDriver driver) {
        try {
            File sessionFile = new File(SESSION_FILE_PATH);
            if (!sessionFile.exists()) {
                System.out.println("No saved session found at: " + SESSION_FILE_PATH);
                return false;
            }
            
            ObjectNode sessionData = (ObjectNode) objectMapper.readTree(sessionFile);
            
            // First navigate to the domain to set cookies
            driver.get("https://login.microsoftonline.com/");
            
            // Load cookies
            if (sessionData.has("cookies")) {
                ArrayNode cookiesArray = (ArrayNode) sessionData.get("cookies");
                for (int i = 0; i < cookiesArray.size(); i++) {
                    ObjectNode cookieNode = (ObjectNode) cookiesArray.get(i);
                    
                    Cookie.Builder cookieBuilder = new Cookie.Builder(
                        cookieNode.get("name").asText(),
                        cookieNode.get("value").asText()
                    );
                    
                    if (cookieNode.has("domain")) {
                        cookieBuilder.domain(cookieNode.get("domain").asText());
                    }
                    if (cookieNode.has("path")) {
                        cookieBuilder.path(cookieNode.get("path").asText());
                    }
                    if (cookieNode.has("secure")) {
                        cookieBuilder.isSecure(cookieNode.get("secure").asBoolean());
                    }
                    if (cookieNode.has("httpOnly")) {
                        cookieBuilder.isHttpOnly(cookieNode.get("httpOnly").asBoolean());
                    }
                    if (cookieNode.has("expiry")) {
                        cookieBuilder.expiresOn(new java.util.Date(cookieNode.get("expiry").asLong()));
                    }
                    
                    try {
                        driver.manage().addCookie(cookieBuilder.build());
                    } catch (Exception e) {
                        System.out.println("Could not add cookie: " + cookieNode.get("name").asText() + " - " + e.getMessage());
                    }
                }
            }
            
            // Load localStorage
            if (sessionData.has("localStorage")) {
                ObjectNode localStorage = (ObjectNode) sessionData.get("localStorage");
                localStorage.fieldNames().forEachRemaining(key -> {
                    String value = localStorage.get(key).asText();
                    String script = String.format("window.localStorage.setItem('%s', '%s');", 
                        key.replace("'", "\\'"), value.replace("'", "\\'"));
                    try {
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
                    } catch (Exception e) {
                        System.out.println("Could not set localStorage item: " + key + " - " + e.getMessage());
                    }
                });
            }
            
            // Load sessionStorage
            if (sessionData.has("sessionStorage")) {
                ObjectNode sessionStorage = (ObjectNode) sessionData.get("sessionStorage");
                sessionStorage.fieldNames().forEachRemaining(key -> {
                    String value = sessionStorage.get(key).asText();
                    String script = String.format("window.sessionStorage.setItem('%s', '%s');", 
                        key.replace("'", "\\'"), value.replace("'", "\\'"));
                    try {
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
                    } catch (Exception e) {
                        System.out.println("Could not set sessionStorage item: " + key + " - " + e.getMessage());
                    }
                });
            }
            
            System.out.println("Session state loaded successfully from: " + SESSION_FILE_PATH);
            return true;
            
        } catch (Exception e) {
            System.out.println("Failed to load session state: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a saved session file exists.
     * 
     * @return true if session file exists, false otherwise
     */
    public static boolean sessionExists() {
        return new File(SESSION_FILE_PATH).exists();
    }
    
    /**
     * Deletes the saved session file.
     * Use this to force a fresh login.
     */
    public static void clearSession() {
        File sessionFile = new File(SESSION_FILE_PATH);
        if (sessionFile.exists()) {
            sessionFile.delete();
            System.out.println("Session file deleted: " + SESSION_FILE_PATH);
        }
    }
    
    /**
     * Validates if the current session is still valid by checking for authentication indicators.
     * 
     * @param driver WebDriver instance to check
     * @return true if session appears to be valid, false otherwise
     */
    public static boolean isSessionValid(WebDriver driver) {
        try {
            // Check for common authentication indicators
            // You may need to adjust these based on your specific application
            String currentUrl = driver.getCurrentUrl();
            String pageSource = driver.getPageSource();
            
            // If we're still on login page, session is not valid
            if (currentUrl.contains("login.microsoftonline.com") && 
                pageSource.contains("Sign in")) {
                return false;
            }
            
            // Check for presence of authentication cookies
            Set<Cookie> cookies = driver.manage().getCookies();
            boolean hasAuthCookies = cookies.stream()
                .anyMatch(cookie -> cookie.getName().toLowerCase().contains("auth") || 
                                   cookie.getName().toLowerCase().contains("session") ||
                                   cookie.getName().toLowerCase().contains("token"));
            
            return hasAuthCookies;
            
        } catch (Exception e) {
            System.out.println("Error validating session: " + e.getMessage());
            return false;
        }
    }
}
