package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import pageObject.CoronisPerformancePage;
import pageObject.CostManagementReportPage;
import utils.TestContextSetUp;

import java.util.List;

public class CostManagementReportStepDefination {
    TestContextSetUp testContextSetUp;
    CostManagementReportPage costManagementReportPage;

    public CostManagementReportStepDefination(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.costManagementReportPage=testContextSetUp.pageObjectManager.getcostManagementReportPage();
    }

    @Then("I click on the following Applications and Validate the Cost Management Report DashBoard Loadings:")
    public void clickAndValidateSites(DataTable table) throws InterruptedException {
        List<String> siteNames = table.asList();
        Thread.sleep(200);
        costManagementReportPage.clickAndValidateSites(siteNames);
    }
}
