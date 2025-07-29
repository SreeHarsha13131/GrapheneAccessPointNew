package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.AllSitePage;
import utils.GenericUtils;
import utils.TestContextSetUp;

import java.util.List;

public class LoginPage {
    TestContextSetUp testContextSetUp;
    AllSitePage allSitePage;
    pageObject.LoginPage loginPage;
    GenericUtils genericUtils;


    public LoginPage(TestContextSetUp testContextSetUp){
        this.testContextSetUp=testContextSetUp;
        this.loginPage=testContextSetUp.pageObjectManager.getLoginPage();
        this.allSitePage=testContextSetUp.pageObjectManager.getAllSitePage();

    }
}
