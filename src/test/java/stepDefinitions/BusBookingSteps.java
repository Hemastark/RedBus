package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import pages.AvailableBusesPage;
import pages.BusBookingPage;
import utils.BaseUtils;
import utils.DriverFactory;
import utils.ExcelUtils;

import java.util.HashMap;
import java.util.Map;

public class BusBookingSteps {
    BusBookingPage booking = new BusBookingPage(DriverFactory.getDriver());
    AvailableBusesPage bus = new AvailableBusesPage(DriverFactory.getDriver());
    ExcelUtils excel = new ExcelUtils();
    Scenario scenario = Hooks.getScenarioContext();

    private static final Logger log = LogManager.getLogger(BusBookingSteps.class);

    @Given("Navigate to the Redbus portal")
    public void navigate_to_the_redbus_portal() {
        try {
            String url = BaseUtils.readProperties("url");
            DriverFactory.getDriver().get(url);
            String pageTitle = DriverFactory.getDriver().getTitle();
            log.info("Page Title : " + pageTitle);
            Assert.assertTrue(pageTitle.contains("redBus"));
        } catch (Exception e) {
            log.error("Unable to navigate to the Redbus portal" + e.getMessage());
        }
    }

    @Then("verify the Landing page")
    public void verify_the_landing_page() {
        try {
            booking.landingPageDisplay();
            scenario.log("Landing page is displayed as expected");
        } catch (Exception e) {
            log.error("Issue in landing page display" + e.getMessage());
        }
    }

    @Given("^Enter the Source (.+) and Destination (.+)$")
    public void enter_the_source_and_destination(String source, String destination) {
        try {
            booking.selectFrom(source);
            booking.selectTo(destination);
        } catch (Exception e) {
            log.error("Unable to enter the source and destination" + e.getMessage());
        }
    }

    @Given("^Enter Source and Destination from Excel file$")
    public void enterLocationFromExcelFile() {
        try {
            String source = excel.getCellValue("src/test/resources/testData/Booking.xlsx", "Location", 1, 0);
            String destination = excel.getCellValue("src/test/resources/testData/Booking.xlsx", "Location", 1, 1);
            booking.selectFrom(source);
            booking.selectTo(destination);
        } catch (Exception e) {
            log.error("Issue in entering the source and destination from Excel file" + e.getMessage());
        }
    }

    @Given("^Select the required Date (.+)$")
    public void select_the_required_date(int date) {
        try {
            booking.setSelectDate(date);
        } catch (Exception e) {
            log.error("Unable to select the travel date" + e.getMessage());
        }
    }

    @When("Click the Search buses option")
    public void click_the_search_buses_option() {
        try {
            booking.searchBus();
        } catch (Exception e) {
            log.error("Unable to click Search bus option" + e.getMessage());
        }
    }

    @Then("Verify if the results are fetched")
    public void verify_if_the_results_are_fetched() {
        try {
            bus.confirmBusAvailability();
        } catch (Exception e) {
            log.error("Issue in fetching the search results" + e.getMessage());
        }
    }

    @Given("Collect the Bus list")
    public void collect_the_bus_list() {
        try {
            HashMap<String, Integer> busPriceMap = bus.fetchBusNameAndFair();
            log.info(busPriceMap);
            scenario.log("Collected the Bus list");
        } catch (Exception e) {
            log.error("Issue in collecting the bus list" + e.getMessage());
        }
    }

    @Then("Select the Bus with lowest price")
    public void select_the_bus_with_lowest_price() {
        try {
            Map.Entry<String, Integer> minValue = bus.getLeastPrice();
            log.info("Travels '" + minValue.getKey() + "' has the minimum ticket price of " + minValue.getValue());
            scenario.log("Travels '" + minValue.getKey() + "' has the minimum ticket price of " + minValue.getValue());
            excel.setCellValue("target/test-outputs","Sheet1",0,0,minValue.getKey());
            excel.setCellValue("target/test-outputs","Sheet1",0,1,minValue.getValue().toString());
        } catch (Exception e) {
            log.error("Unable to select the Bus with lowest price" + e.getMessage());
        }
    }
}
