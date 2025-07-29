package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.CiplaContractManagementPage;

import utils.TestContextSetUp;

import java.util.List;

public class CiplaContractManagementStepDefination {
    TestContextSetUp testContextSetUp;
    CiplaContractManagementPage ciplaContractManagementPage;
    public CiplaContractManagementStepDefination(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.ciplaContractManagementPage=testContextSetUp.pageObjectManager.getCiplaContractManagementPage();
    }
    @Given("Open the Chrome Browser and Navigate to AccessPoint Login Pages")
    public void open_the_chrome_browser_and_navigate_to_access_point_url_urls() {
        Assert.assertTrue(ciplaContractManagementPage.getGrapheneUserText().contains("Graphene User"));
        System.out.println(ciplaContractManagementPage.getGrapheneUserText());
    }
    
    /**
     * Updated login step to use AuthenticationManager with session management.
     * This will attempt to reuse existing session to avoid mobile PIN verification.
     */
    @When("^User login to the AccessPoint Site usings (.+) and (.+)$")
    public void user_login_to_the_access_point_site_using_UserName_And_Passwords(String UserName, String Password) throws InterruptedException {
        System.out.println("🔐 Starting authentication with session management...");
        
        // Use the new authentication manager with session support
        boolean authSuccess = testContextSetUp.testBase.authenticateUser(UserName, Password);
        
        if (!authSuccess) {
            System.out.println("❌ Authentication failed!");
            throw new RuntimeException("Authentication failed for user: " + UserName);
        }
        
        System.out.println("✅ Authentication successful!");
        
        // Original manual login code - kept as backup/fallback
        // This code is now handled by AuthenticationManager
        /*
        ciplaContractManagementPage.selectGrapheneUser();
        ciplaContractManagementPage.searchMicrosoftSignInTextField(UserName);
        Thread.sleep(200);
        ciplaContractManagementPage.microsoftSubmitBtn();
        Thread.sleep(200);
        ciplaContractManagementPage.SendMicrosoftPasswordTestField(Password);
        Thread.sleep(2000);
        ciplaContractManagementPage.microsoftSignInBtn();
        Thread.sleep(10000);
        ciplaContractManagementPage.microsoftDoNotShowAgain();
        Thread.sleep(200);
        ciplaContractManagementPage.microsoftYesBtn();
        Thread.sleep(1000);
        */
    }
    
    @Then("User will land on the Home page of the AccessPoint Sites")
    public void user_will_land_on_the_home_page_of_the_access_point_sites() throws InterruptedException {
        Assert.assertTrue(ciplaContractManagementPage.accessPointText().contains("Access Point"));
        Thread.sleep(200);
        System.out.println(ciplaContractManagementPage.accessPointText());
    }
    @And("Click on the Application Tabs")
    public void click_On_The_ApplicationTabs() throws InterruptedException {
        ciplaContractManagementPage.getAllEmpMainTabs();
        Thread.sleep(200);
        ciplaContractManagementPage.getAppMainTab();
        Thread.sleep(200);
    }
    @Then("I click on the following Application and Validate the DashBoard Loadings:")
    public void clickAndValidateSites(DataTable table) throws InterruptedException {
        List<String> siteNames = table.asList();
        Thread.sleep(200);
        ciplaContractManagementPage.clickAndValidateSites(siteNames);
    }
}
