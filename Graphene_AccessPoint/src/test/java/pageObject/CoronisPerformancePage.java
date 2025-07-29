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

public class CoronisPerformancePage {
    public WebDriver driver;
    public WebDriverWait wait;

    public CoronisPerformancePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public static By insuranceStatus = By.xpath("//span[text()='Insurance Status']/parent :: div[@wairole='presentation']");
    public static By patientInsights = By.xpath("//span[text()='Patient Insights']/parent :: div[@wairole='presentation']");
    public static By riskAssessment = By.xpath("//span[text()='Risk Assessment']/parent :: div[@wairole='presentation']");
    public static String insuranceStatusContainer="//div[@class='tab-zone-padding'] / ancestor ::div[@class='tab-zone tab-widget tabZone-layout-basic fade-bg']";
    public static String patientInsightsContainer="//div[@class='tab-zone-padding']/ ancestor::div[@id='tab-dashboard-region']";
    public static String riskAssessmentContainer="//div[@class='tab-zone tab-widget tabZone-layout-basic fade-bg']/ ancestor :: div[@id='tab-dashboard-region']";


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
                Thread.sleep(1000);

                System.out.println("Container is loaded properly " + wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());
                driver.findElement(insuranceStatus).click();
                Thread.sleep(1000);
                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                System.out.println("InsuranceStatus Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(insuranceStatusContainer))).isDisplayed());
                driver.findElement(patientInsights).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                System.out.println("PatientInsights Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(patientInsightsContainer))).isDisplayed());
                Thread.sleep(1000);
                driver.findElement(riskAssessment).click();

                driver.switchTo().defaultContent();

                driver.switchTo().frame(0);
                System.out.println("RiskAssessment Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(riskAssessmentContainer))).isDisplayed());
                driver.switchTo().defaultContent();
                WebElement homeIcons = driver.findElement(homeIcon);
                homeIcons.click();
                Thread.sleep(1000);

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
