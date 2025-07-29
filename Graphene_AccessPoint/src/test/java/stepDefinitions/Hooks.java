package stepDefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utils.ExtentReportManager;
import utils.TestContextSetUp;

import java.io.IOException;

public class Hooks {
    public  static ExtentReports extent;
    public static ExtentTest test;
    TestContextSetUp testContextSetUp;
    WebDriver driver;

    public Hooks(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        extent = ExtentReportManager.getInstance();
        test = extent.createTest(scenario.getName());
    }

    @After
    public void AferSecnario() throws IOException {
        testContextSetUp.testBase.WebDriverManager().quit();
        System.out.println("Closing the Browser");
    }
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Closes all browser windows and ends the WebDriver session
        }
    }

    public static void logInfo(String message) {
        test.log(Status.INFO, message);
    }

    public static void logPass(String message) {
        test.log(Status.PASS, message);
    }

    public static void logFail(String message) {
        test.log(Status.FAIL, message);
    }

    public static void addScreenshot(String path, String title) {
        test.addScreenCaptureFromPath(path, title);
    }
}
