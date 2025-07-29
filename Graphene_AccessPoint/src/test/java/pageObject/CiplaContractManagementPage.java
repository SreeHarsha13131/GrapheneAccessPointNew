package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.Hooks;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;

public class CiplaContractManagementPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public String currentAppName;

    public CiplaContractManagementPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static By grapheneUser = By.xpath("//div[@class='media']/descendant::img[@class='media-image']");
    public static By grapheneUserText = By.xpath("//div[@class='media']/descendant::p[@class='login-name']");

    public static By microsoftSignInTextField = By.xpath("//input[@type='email']");
    public static By microsoftSubmitBtn = By.xpath("//input[@type='submit']");
    public static By microsoftPasswordTestField = By.xpath("//input[@name='passwd']");
    public static By microsoftSignInBtn = By.xpath("//input[@type='submit']");
    public static By microsoftDoNotShowAgain = By.name("DontShowAgain");
    public static By microsoftYesBtn = By.xpath("//input[@type='submit']");
    public static By accessPointText = By.xpath("//div[@class='col-md-4 text-center']/descendant::span[contains(text(),'Access Point')]");
    public static By AllEmp = By.xpath("//a[contains(text(),'All Employees')]");
    public static By applicatonsMainTab = By.xpath("//a[contains(text(),'Applications')]");
    public static By searchApplication = By.xpath("//input[@type='text']");
    public static String tileXpathTemplate = "//div[@data-placement='bottom' and contains(.,'%s')]";
    public static String dashBoardCompanyName = "//div[@class='pull-left']/descendant::span[contains(.,'%s')]";
    //    public static String containerLayout = "//div[@id='main-content']/descendant::div[@class='tab-zone-padding' and @style='inset: 0px; position: absolute;']/parent::*/parent::*/parent::*";
//    public static String containerLayout = "//div[contains(@class,'tab-zone') and contains(@class,'tab-widget')]";
    public static String containerLayout = "(//div[contains(@class,'tab-zone') and contains(@class,'tab-widget')])[2]";
    //    public static String containerLayout = "/html/body/div[2]/div[2]/div[2]/div[1]/div/div[2]";
    public static By homeIcon = By.xpath("//a[@class='home-url']");
    public static By licenceDetails = By.xpath("//div[@wairole='presentation']/child::span[text()='Licence Details']");
    public static By licence_AllocationTrendline = By.xpath("//div[@wairole='presentation']/child::span[text()='Licence Allocation Trendline']");
    public static By totallicenceCountOtherMetrics = By.xpath("//div[@wairole='presentation']/child::span[text()='Total Licence Count - Other Metrics']");
    public static By hidenFrame=By.tagName("iframe");
    public void selectGrapheneUser() {
        driver.findElement(grapheneUser).click();
    }

    public String getGrapheneUserText() {
        return driver.findElement(grapheneUserText).getText();
    }

    public void searchMicrosoftSignInTextField(String UserName) {
        driver.findElement(microsoftSignInTextField).sendKeys(UserName);
    }

    public void microsoftSubmitBtn() {
        driver.findElement(microsoftSubmitBtn).click();
    }

    public void SendMicrosoftPasswordTestField(String Password) {
        driver.findElement(microsoftPasswordTestField).sendKeys(Password);
    }

    public WebElement microsoftSignInBtn() throws InterruptedException {

        WebElement ele = driver.findElement(microsoftSignInBtn);
        ele.click();
        return ele;
    }

    public void microsoftDoNotShowAgain() {
        driver.findElement(microsoftDoNotShowAgain).click();

    }

    public void microsoftYesBtn() {
        driver.findElement(microsoftYesBtn).click();
    }

    public String accessPointText() {

        String accessPointTexts = driver.findElement(accessPointText).getText();
        return accessPointTexts;
    }

    public void getAllEmpMainTabs() {
        String appText = driver.findElement(AllEmp).getText();
        System.out.println(appText);
        driver.findElement(AllEmp);

    }

    public void getAppMainTab() {
        driver.findElement(applicatonsMainTab).click();
    }

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
                Thread.sleep(3000);

                boolean isDashboardLoaded = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed();
                System.out.println("Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());

                driver.findElement(licenceDetails).click();
                Thread.sleep(1000);
                driver.switchTo().defaultContent();
                driver.switchTo().frame(0);
                System.out.println("Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());
                Thread.sleep(500);
                driver.findElement(licence_AllocationTrendline).click();
                Thread.sleep(1000);
                driver.switchTo().defaultContent();
                driver.switchTo().frame(0);
                System.out.println("Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());
                Thread.sleep(500);
                driver.findElement(totallicenceCountOtherMetrics).click();
                Thread.sleep(1000);
                driver.switchTo().defaultContent();
                driver.switchTo().frame(0);
                System.out.println("Container is loaded properly "+wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath(containerLayout))).isDisplayed());
                Thread.sleep(3000);
                driver.switchTo().defaultContent();
                WebElement homeIcons = driver.findElement(homeIcon);
                homeIcons.click();
                Thread.sleep(2000);
            } catch (Exception e) {
                Hooks.logInfo("ERROR processing application: " + siteName + " - " + e.getMessage());
                e.printStackTrace();

                // Recovery attempt
                try {
                    driver.switchTo().defaultContent();
                    WebElement homeIcons = driver.findElement(homeIcon);
                    homeIcons.click();
                    Thread.sleep(3000);
                } catch (Exception recoveryEx) {
                    Hooks.logInfo("Recovery FAILED for: " + siteName + " - " + recoveryEx.getMessage());
                    recoveryEx.printStackTrace();
                }
            }
        }
    }
}
