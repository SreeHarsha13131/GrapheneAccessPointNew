package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GenericUtils {
    public WebDriver driver;
    public GenericUtils(WebDriver driver){
        this.driver=driver;
    }
    public void explicitWait(WebDriver driver, WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));// wait for up to 10 seconds
        WebElement myElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        myElement.click();
    }
    public void getApplicatonsMainTab(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(element));
        tab.click();
    }

}
