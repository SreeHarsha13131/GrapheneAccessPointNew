
package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AllSitePage {

    public WebDriver driver;
    public WebDriverWait wait;
    public String tileXpathTemplate = "//div[contains(@class,'Metaname') and contains(.,'%s')]";
    public static By homeIcon = By.xpath("//a[@class='home-url']");
    public static By applicationsTab = By.xpath("//a[contains(text(),'Applications')]");

    public AllSitePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout
    }

    public void clickAndValidateSites(List<String> siteNames) {
        String parentWindow = driver.getWindowHandle();

        for (String site : siteNames) {
            try {
                String xpath = String.format(tileXpathTemplate, site);
                WebElement tile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                Thread.sleep(1000);
                tile.click();
                Thread.sleep(5000); // Let window load
                Set<String> allWindows = driver.getWindowHandles();
                if (allWindows.size() > 1) {
                    // Handle new window case
                    for (String win : allWindows) {
                        if (!win.equals(parentWindow)) {
                            driver.switchTo().window(win);
                            Thread.sleep(2000);
                            break;
                        }
                    }
                    boolean pageLoaded = wait.until(ExpectedConditions.or(
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dashboard, .site-content")),
                            ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
                    )) != null;
                    if (!pageLoaded) {
                        System.out.println("Site data did not load for: " + site);
                    }
                    driver.close();
                    driver.switchTo().window(parentWindow);
                } else {
                    // Handle same window case - use home icon navigation
                    navigateUsingHomeIcon();
                }
                Thread.sleep(2000);

                // Ensure we're back on Applications Tab
                returnToApplicationsTab();
                Thread.sleep(5000);

            } catch (Exception e) {
                System.out.println("Error handling site: " + site);
                e.printStackTrace();
                recoverToBaseState(parentWindow);
            }
        }
    }

    private void navigateUsingHomeIcon() {
        try {
            WebElement homeIconElement = wait.until(ExpectedConditions.elementToBeClickable(homeIcon));
            Thread.sleep(2000);
            homeIconElement.click();
            System.out.println("Navigated using home icon");
            wait.until(ExpectedConditions.presenceOfElementLocated(applicationsTab));
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Failed to navigate using home icon");
            throw new RuntimeException("Home icon navigation failed", e);
        }
    }

    private void returnToApplicationsTab() {
        try {
            WebElement applicationsTabElement = wait.until(ExpectedConditions.elementToBeClickable(applicationsTab));
            Thread.sleep(2000);
            applicationsTabElement.click();
            System.out.println("Returned to Applications tab");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(tileXpathTemplate.replace("%s", ""))));
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Failed to return to Applications tab");
            throw new RuntimeException("Applications tab navigation failed", e);
        }
    }

    private void recoverToBaseState(String parentWindow) {
        try {
            // Try to switch back to parent window first
            driver.switchTo().window(parentWindow);
            // Then attempt to return to Applications tab
            returnToApplicationsTab();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Base state recovery failed");
            // Final attempt - refresh and try again
            driver.navigate().refresh();
            returnToApplicationsTab();
        }
    }
}
