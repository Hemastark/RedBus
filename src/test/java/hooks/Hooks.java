package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.DriverFactory;


public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private static Scenario scenarioContext;

    @Before
    public void setScenarioContext(Scenario scenario) {
        scenarioContext = scenario;
    }

    public static Scenario getScenarioContext() {
        return scenarioContext;
    }

    @AfterStep
    public void screenshot(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
        }
    }

    @After
    public void statusLog(Scenario scenario) {
        if (scenario.isFailed()) {
            log.info("Scenario failed : " + scenario.getName());
        } else {
            log.info("Scenario passed : " + scenario.getName());
        }
    }

}
