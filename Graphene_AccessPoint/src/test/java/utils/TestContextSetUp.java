package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pageObject.PageObjectManager;
import stepDefinitions.Hooks;

import java.io.IOException;

public class TestContextSetUp {
    public WebDriver driver;
    public TestBase testBase;
    public GenericUtils genericUtils;
    public PageObjectManager pageObjectManager;
    public JsonUtils jsonUtils;
    public Hooks hooks;

    public TestContextSetUp() throws IOException {
        testBase = new TestBase();
        pageObjectManager = new PageObjectManager(testBase.WebDriverManager());
        genericUtils = new GenericUtils(testBase.WebDriverManager());
        jsonUtils = new JsonUtils(testBase.WebDriverManager());
//        hooks = new Hooks((TestContextSetUp) testBase.WebDriverManager());

    }


}
