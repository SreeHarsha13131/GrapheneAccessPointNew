package pageObject;

import org.openqa.selenium.WebDriver;


public class PageObjectManager {
    public WebDriver driver;
    public LoginPage loginPage;
    public AllSitePage allSitePage;
    public DashBoardRunPage dashBoardRunPage;
    public CiplaContractManagementPage ciplaContractManagementPage;
    public ConstellationPriceWatcherPage constellationPriceWatcherPage;
    public CoronisPerformancePage coronisPerformancePage;
    public CostManagementReportPage costManagementReportPage;
    public DBS2018Page dBS2018Page;
//    public BasicSeleniumPage  basicSeleniumPage;

    public PageObjectManager(WebDriver driver) {

        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        loginPage = new LoginPage(driver);
        return loginPage;
    }

    public AllSitePage getAllSitePage() {
        allSitePage = new AllSitePage(driver);
        return allSitePage;
    }

    public DashBoardRunPage getDashBoardRunPage() {
        dashBoardRunPage = new DashBoardRunPage(driver);
        return dashBoardRunPage;
    }

    public CiplaContractManagementPage getCiplaContractManagementPage() {
        ciplaContractManagementPage = new CiplaContractManagementPage(driver);
        return ciplaContractManagementPage;
    }
    public ConstellationPriceWatcherPage getconstellationPriceWatcherPage() {
        constellationPriceWatcherPage = new ConstellationPriceWatcherPage(driver);
        return constellationPriceWatcherPage;
    }
    public CoronisPerformancePage getCoronisPerformancePage() {
        coronisPerformancePage = new CoronisPerformancePage(driver);
        return coronisPerformancePage;
    }
    public CostManagementReportPage getcostManagementReportPage() {
        costManagementReportPage = new CostManagementReportPage(driver);
        return costManagementReportPage;
    }
    public DBS2018Page getDBS2018Page() {
        dBS2018Page = new DBS2018Page(driver);
        return dBS2018Page;
    }
}
