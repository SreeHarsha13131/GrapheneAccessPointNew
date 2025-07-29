package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.ConstellationPriceWatcherPage;

import utils.TestContextSetUp;

import java.util.List;

public class ConstellationPriceWatcherStepDefinitions {
    TestContextSetUp testContextSetUp;
    ConstellationPriceWatcherPage constellationPriceWatcherPage;
    public ConstellationPriceWatcherStepDefinitions(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.constellationPriceWatcherPage=testContextSetUp.pageObjectManager.getconstellationPriceWatcherPage();
    }
    @Given("Open the Chrome Browsers and Navigate to AccessPoint Login Pages")
    public void open_the_chrome_browser_and_navigate_to_access_point_url_urls() {
        Assert.assertTrue(constellationPriceWatcherPage.getGrapheneUserText().contains("Graphene User"));
        System.out.println(constellationPriceWatcherPage.getGrapheneUserText());
    }
    @When("^User login to the AccessPoint Sites usings (.+) and (.+)$")
    public void user_login_to_the_access_point_site_using_UserName_And_Passwords(String UserName, String Password) throws InterruptedException {
        constellationPriceWatcherPage.selectGrapheneUser();
        constellationPriceWatcherPage.searchMicrosoftSignInTextField(UserName);
        Thread.sleep(200);
        constellationPriceWatcherPage.microsoftSubmitBtn();
        Thread.sleep(1100);
        constellationPriceWatcherPage.SendMicrosoftPasswordTestField(Password);
        Thread.sleep(2000);
//        genericUtils.explicitWait(genericUtils.driver, loginPage.microsoftSignInBtn());
        constellationPriceWatcherPage.microsoftSignInBtn();
        Thread.sleep(10000);
        constellationPriceWatcherPage.microsoftDoNotShowAgain();
        Thread.sleep(200);
        constellationPriceWatcherPage.microsoftYesBtn();
        Thread.sleep(1000);
    }
    @Then("User will land on the Home pages of the AccessPoint Sites")
    public void user_will_land_on_the_home_page_of_the_access_point_sites() throws InterruptedException {
        Assert.assertTrue(constellationPriceWatcherPage.accessPointText().contains("Access Point"));
        Thread.sleep(200);
        System.out.println(constellationPriceWatcherPage.accessPointText());
    }
    @And("Click on the Applications Tabs")
    public void click_On_The_ApplicationTabs() throws InterruptedException {
        constellationPriceWatcherPage.getAllEmpMainTabs();
        Thread.sleep(200);
        constellationPriceWatcherPage.getAppMainTab();
        Thread.sleep(200);
    }
    @Then("I click on the following Applications and Validate the DashBoard Loadings:")
    public void clickAndValidateSites(DataTable table) throws InterruptedException {
        List<String> siteNames = table.asList();
        Thread.sleep(200);
        constellationPriceWatcherPage.clickAndValidateSites(siteNames);
    }
}
