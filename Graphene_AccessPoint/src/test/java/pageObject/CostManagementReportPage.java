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

public class CostManagementReportPage {
    public WebDriver driver;
    public WebDriverWait wait;

    public CostManagementReportPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static By costByProject = By.xpath("//span[text()='CostByProjects(Detailed)']");
    public static String costByProjectContainer = "//div[@class='tab-zone-padding']/ancestor:: div[@class='tab-zone tab-widget tabZone-layout-basic fade-bg']/ancestor :: div[@id='tab-dashboard-region']";

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
                driver.findElement(costByProject).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                Thread.sleep(3000);

                System.out.println("RiskAssessment Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(costByProjectContainer))).isDisplayed());
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
