package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GenericUtils;

public class LoginPage {
    public WebDriver driver;
    GenericUtils genericUtils;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
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
        return driver.findElement(accessPointText).getText();
    }

    public void getAllEmpMainTabs() {
        String appText=driver.findElement(AllEmp).getText();
        System.out.println(appText);
          driver.findElement(AllEmp);
    }

    public void getAppMainTab(){
        driver.findElement(applicatonsMainTab).click();
    }
}
