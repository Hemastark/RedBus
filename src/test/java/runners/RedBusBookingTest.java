package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.DriverFactory;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
public class RedBusBookingTest extends AbstractTestNGCucumberTests {

    @BeforeTest
    public void setUp() {
        DriverFactory.initializeDriver();
    }

    @AfterTest
    public void quitDriver() {
        DriverFactory.quitDriver();
    }
}
