package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import pageObject.ConstellationPriceWatcherPage;
import pageObject.CoronisPerformancePage;
import utils.TestContextSetUp;

import java.util.List;

public class CoronisPerformanceStepDefinitions {
    TestContextSetUp testContextSetUp;
    CoronisPerformancePage coronisPerformancePage;
    public CoronisPerformanceStepDefinitions(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.coronisPerformancePage=testContextSetUp.pageObjectManager.getCoronisPerformancePage();
    }

    @Then("I click on the following Applications and Validate the Coronis Performance DashBoard Loadings:")
    public void clickAndValidateSites(DataTable table) throws InterruptedException {
        List<String> siteNames = table.asList();
        Thread.sleep(200);
        coronisPerformancePage.clickAndValidateSites(siteNames);
    }
}
