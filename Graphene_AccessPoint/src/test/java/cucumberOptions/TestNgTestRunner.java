/**
 * TestNgTestRunner class that integrates Cucumber with TestNG framework for running feature tests.
 *
 * This class extends AbstractTestNGCucumberTests and configures Cucumber with TestNG to:
 * - Execute feature files located in the 'src/test/java/features' directory
 * - Use step definitions from the 'stepDefinitions' package
 * - Filter tests using the '@DashBoardRun' tag
 * - Generate multiple types of reports including:
 *   - Extent Reports
 *   - HTML reports
 *   - JSON reports
 *   - Rerun configuration for failed scenarios
 *
 * @author [Your Name]
 * @version 1.0
 * @since [Date]
 */
package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Cucumber configuration options:
 * - features: Location of feature files
 * - glue: Location of step definitions
 * - tags: Test filter tags
 * - monochrome: Enables monochrome console output
 * - plugin: Multiple reporting plugins enabled
 */
@CucumberOptions(features = "src/test/java/features",
        glue = "stepDefinitions",
        tags = "@DashBoardRun",
        monochrome = true,
        plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:target/failed_scenario.txt",
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",


        })
public class TestNgTestRunner extends AbstractTestNGCucumberTests {
    /**
     * Default constructor for TestNgTestRunner.
     * Initializes the Cucumber test runner with TestNG integration.
     */
    public TestNgTestRunner() {
        super();
    }
//    //tags = "@ApplicationsRun"
//    @Override
//    @DataProvider(parallel = false)
//    public Object[][] scenarios() {
//        return super.scenarios();
//      "junit:target/cucumber-reports/cucumber.xml",
//              "usage:target/cucumber-reports/usage.json",
//              "testng:target/cucumber-reports/cucumber-testng.xml",
//              "tech.grasshopper.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
//
}

