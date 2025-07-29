package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.Hooks;

import java.time.Duration;
import java.util.List;

import static pageObject.ConstellationPriceWatcherPage.*;
import static pageObject.ConstellationPriceWatcherPage.homeIcon;

public class DBS2018Page {
    public WebDriver driver;
    public WebDriverWait wait;

    public DBS2018Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static By driversAnalysis = By.xpath("//span[text()='Drivers Analysis']/parent :: div[@wairole='presentation']");
    public static By driversTrend = By.xpath("//span[text()='Drivers Trend']/parent :: div[@wairole='presentation']");
    public static By sentimentAnalysis = By.xpath("//span[text()='Sentiment Analysis']/parent :: div[@wairole='presentation']");
    public static By verbatims = By.xpath("//span[text()='Verbatims']/parent :: div[@wairole='presentation']");
    public static By associations = By.xpath("//span[text()='Associations']/parent :: div[@wairole='presentation']");
    public String verbatimsContainer = "(//div[contains(@class,'tab-zone') and contains(@class,'tab-widget')])[1]";

    public void clickAndValidateSites(List<String> siteNames) {
        if (siteNames == null || siteNames.isEmpty()) {
            return;
        }

        for (String siteName : siteNames) {

            System.out.println("Starting validation for application: " + siteName + siteNames);


            try {
                // Step 1: Search for the site
                WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchApplication));
                searchField.clear();
                searchField.sendKeys(siteName);
                Thread.sleep(200);

                // Step 2: Click on the site link
                String siteTileXpath = String.format(tileXpathTemplate, siteName);
                WebElement siteTile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(siteTileXpath)));
                siteTile.click();


                // Wait for the dashboard to load
                wait.until(ExpectedConditions.numberOfWindowsToBe(1));
                // Step 3: Validate the header
                String headerXpath = String.format(dashBoardCompanyName, siteName);
                WebElement headerElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(headerXpath)));
                String headerText = headerElement.getText();

                if (!headerText.contains(siteName)) {
                    Hooks.logInfo("Validation FAILED - Expected: " + siteName + ", Actual: " + headerText);
                } else {
                    Hooks.logInfo("Validation PASSED - Header matches application name: " + siteName);
                }

                // Step 4: Validate Dashboard is loaded
                WebElement hiddenFrame = driver.findElement(hidenFrame);
                driver.switchTo().frame(hiddenFrame);
                Thread.sleep(100);

                System.out.println("Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());

                Thread.sleep(100);
                driver.findElement(driversAnalysis).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                Thread.sleep(3000);

                System.out.println("RiskAssessment Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());

                driver.findElement(driversTrend).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                Thread.sleep(3000);

                System.out.println("RiskAssessment Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());

                driver.switchTo().defaultContent();

                driver.findElement(sentimentAnalysis).click();



                driver.switchTo().frame(0);
                Thread.sleep(3000);

                System.out.println("RiskAssessment Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());

                driver.switchTo().defaultContent();

                driver.findElement(verbatims).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                Thread.sleep(3000);

                System.out.println("RiskAssessment Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(verbatimsContainer))).isDisplayed());

                driver.switchTo().defaultContent();

                driver.findElement(associations).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                Thread.sleep(3000);

                System.out.println("RiskAssessment Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());
                driver.switchTo().defaultContent();

                WebElement homeIcons = driver.findElement(homeIcon);
                Thread.sleep(500);
                homeIcons.click();
                Thread.sleep(100);

            } catch (Exception e) {
                Hooks.logInfo("ERROR processing application: " + siteName + " - " + e.getMessage());
                e.printStackTrace();

                // Recovery attempt
                try {
                    driver.switchTo().defaultContent();
                    WebElement homeIcons = driver.findElement(homeIcon);
                    homeIcons.click();
                    Thread.sleep(1000);

                } catch (Exception recoveryEx) {
                    Hooks.logInfo("Recovery FAILED for: " + siteName + " - " + recoveryEx.getMessage());
                    recoveryEx.printStackTrace();
                }
            }
        }
    }
}
