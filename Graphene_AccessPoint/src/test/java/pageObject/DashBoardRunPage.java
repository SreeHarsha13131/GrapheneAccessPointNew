package pageObject;


import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.Hooks;

import java.time.Duration;
import java.util.List;

import static org.testng.Reporter.log;

public class DashBoardRunPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public Scenario scenario;
    public String currentAppName;


    public DashBoardRunPage(WebDriver driver) {

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

    public WebElement microsoftSignInBtn() {

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

         String accessPointTexts=driver.findElement(accessPointText).getText();
        return accessPointTexts;
    }
    public void getAllEmpMainTabs() {
        String appText=driver.findElement(AllEmp).getText();
        System.out.println(appText);
        driver.findElement(AllEmp);

    }
    public void getAppMainTab(){
        driver.findElement(applicatonsMainTab).click();
    }

public void clickAndValidateSites(List<String> siteNames) {
    if (siteNames == null || siteNames.isEmpty()) {
//        Hooks.logInfo("No site names provided for validation");
        return;
    }

//    Hooks.logInfo("Starting validation for " + siteNames.size() + " applications");

    for (String siteName : siteNames) {
//        Hooks.logInfo("====================================================================");
//        Hooks.logInfo("Starting validation for application: " + siteName);
        System.out.println("Starting validation for application: " + siteName + siteNames );
//        Hooks.logInfo("Starting validation for application: " + siteName+siteNames);


        try {
            // Step 1: Search for the site
            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchApplication));
            searchField.clear();
            searchField.sendKeys(siteName);
//            Hooks.logInfo("Successfully entered application name in search field: " + siteName);

            // Wait for search results
            Thread.sleep(1000);

            // Step 2: Click on the site link
            String siteTileXpath = String.format(tileXpathTemplate, siteName);
            WebElement siteTile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(siteTileXpath)));
            siteTile.click();
//            Hooks.logInfo("Successfully clicked on application tile: " + siteName);

            // Wait for the dashboard to load
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            Thread.sleep(3000);

            // Step 3: Validate the header
            String headerXpath = String.format(dashBoardCompanyName, siteName);
            WebElement headerElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(headerXpath)));
            String headerText = headerElement.getText();
//            Hooks.logInfo("Header text found: " + headerText);

            if (!headerText.contains(siteName)) {
                Hooks.logInfo("Validation FAILED - Expected: " + siteName + ", Actual: " + headerText);
            } else {
                Hooks.logInfo("Validation PASSED - Header matches application name: " + siteName);
            }

            // Step 4: Validate Dashboard is loaded
            WebElement hiddenFrame = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(hiddenFrame);
            Thread.sleep(5000);
//            Hooks.logInfo("Switched to hidden dashboard frame for: " + siteName);

            boolean isDashboardLoaded = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(containerLayout))).isDisplayed();

            if (!isDashboardLoaded) {
                Hooks.logInfo("Dashboard NOT loaded properly for: " + siteName);
            } else {
                Hooks.logInfo("Dashboard loaded successfully for: " + siteName);
            }

            // Step 5: Return to parent frame and click home icon
            driver.switchTo().defaultContent();
            WebElement homeIcons = wait.until(ExpectedConditions.elementToBeClickable(homeIcon));
            homeIcons.click();

            // Wait to return to appMain tab
            wait.until(ExpectedConditions.presenceOfElementLocated(searchApplication));
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
        } finally {
//            Hooks.logInfo("====================================================================");
        }
    }

}
}
