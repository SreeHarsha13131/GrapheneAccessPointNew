package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import pageObject.AllSitePage;
import utils.TestContextSetUp;

import java.util.List;

public class AllSiteStepDefinitions {
    AllSitePage allSitePage;
    TestContextSetUp testContextSetUp;
    public WebDriver driver;
//    private SitePage sitePage;

    public AllSiteStepDefinitions(TestContextSetUp testContextSetUp) {
//        this.driver = DriverFactory.getDriver(); // your existing setup
//        this.sitePage = new SitePage(driver);
        this.testContextSetUp=testContextSetUp;
        this.allSitePage=testContextSetUp.pageObjectManager.getAllSitePage();
    }

    @Then("I click on the following site tiles and validate loading:")
    public void clickSiteTiles(DataTable table) {
        List<String> siteNames = table.asList();
        allSitePage.clickAndValidateSites(siteNames);
    }

}
