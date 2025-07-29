package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import pageObject.DBS2018Page;
import utils.TestContextSetUp;

import java.util.List;

public class DBS2018StepDefinition {
    TestContextSetUp testContextSetUp;
    DBS2018Page dbs2018Page;

    public DBS2018StepDefinition(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.dbs2018Page=testContextSetUp.pageObjectManager.getDBS2018Page();
    }

    @Then("I click on the following Applications and Validate the DBS 2018 DashBoard Loadings:")
    public void clickAndValidateSites(DataTable table) throws InterruptedException {
        List<String> siteNames = table.asList();
        Thread.sleep(200);
        dbs2018Page.clickAndValidateSites(siteNames);
    }
}
