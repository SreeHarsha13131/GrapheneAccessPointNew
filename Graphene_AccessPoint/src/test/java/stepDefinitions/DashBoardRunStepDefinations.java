package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pageObject.AllSitePage;
import pageObject.DashBoardRunPage;
import utils.GenericUtils;
import utils.TestContextSetUp;

import java.util.List;

import static org.testng.Reporter.log;

public class DashBoardRunStepDefinations {
    TestContextSetUp testContextSetUp;
    DashBoardRunPage dashBoardRunPage;


    public DashBoardRunStepDefinations(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.dashBoardRunPage=testContextSetUp.pageObjectManager.getDashBoardRunPage();

    }

    @Given("Open the Chrome Browser and Navigate to AccessPoint Login Page")
    public void open_the_chrome_browser_and_navigate_to_access_point_url_url() {
        Assert.assertTrue(dashBoardRunPage.getGrapheneUserText().contains("Graphene User"));
        System.out.println(dashBoardRunPage.getGrapheneUserText());
    }
    @When("^User login to the AccessPoint Site using (.+) and (.+)$")
    public void user_login_to_the_access_point_site_using_UserName_And_Password(String UserName, String Password) throws InterruptedException {
        dashBoardRunPage.selectGrapheneUser();
        dashBoardRunPage.searchMicrosoftSignInTextField(UserName);
        dashBoardRunPage.microsoftSubmitBtn();
        dashBoardRunPage.SendMicrosoftPasswordTestField(Password);
        Thread.sleep(5000);

//        genericUtils.explicitWait(genericUtils.driver, loginPage.microsoftSignInBtn());
        dashBoardRunPage.microsoftSignInBtn();
        Thread.sleep(1000);
        dashBoardRunPage.microsoftDoNotShowAgain();
        Thread.sleep(1000);
        dashBoardRunPage.microsoftYesBtn();
        Thread.sleep(1000);
    }
    @Then("User will land on the Home page of the AccessPoint Site")
    public void user_will_land_on_the_home_page_of_the_access_point_site() throws InterruptedException {
        Assert.assertTrue(dashBoardRunPage.accessPointText().contains("Access Point"));
        Thread.sleep(200);
        System.out.println(dashBoardRunPage.accessPointText());
    }
    @And("Click on the Application Tab")
    public void click_On_The_ApplicationTab() throws InterruptedException {
        dashBoardRunPage.getAllEmpMainTabs();
        Thread.sleep(200);
        dashBoardRunPage.getAppMainTab();
        Thread.sleep(200);
    }
    @Then("I click on the following Application and Validate the DashBoard Loading:")
    public void clickAndValidateSites(DataTable table) throws InterruptedException {
        List<String> siteNames = table.asList();
        Thread.sleep(200);
        dashBoardRunPage.clickAndValidateSites(siteNames);
    }
}
