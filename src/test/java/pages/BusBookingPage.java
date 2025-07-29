package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BusBookingPage {
    WebDriver driver;

    public BusBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[text()='From']")
    WebElement fromSection;

    @FindBy(id = "srcDest")
    WebElement destElement;

    @FindBy(xpath = "(//div[starts-with(@class,'listHeader')])[1]")
    WebElement firstLocationSuggestion;

    @FindBy(xpath = "//div[starts-with(@class,'srcDest_')]")
    List<WebElement> locationElement;

    @FindBy(xpath = "//div[starts-with(@class,'dateInputWrapper')]")
    WebElement dateHolder;

    @FindBy(xpath = "//button[text()='Search buses']")
    WebElement searchBusButton;

    public void landingPageDisplay() {
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait1.until(ExpectedConditions.elementToBeClickable(searchBusButton));
    }

    public void selectFrom(String location) {
        fromSection.click();
        destElement.sendKeys(location);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(firstLocationSuggestion, location));
        firstLocationSuggestion.click();
        wait.until(ExpectedConditions.textToBePresentInElement(locationElement.get(0), location));
    }

    public void selectTo(String location) {
        destElement.sendKeys(location);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(firstLocationSuggestion, location));
        firstLocationSuggestion.click();
        wait.until(ExpectedConditions.textToBePresentInElement(locationElement.get(1), location));
    }

    public void setSelectDate(int date) {
        dateHolder.click();
        driver.findElement(By.xpath("//div[starts-with(@class,'date_')]/child::span[text()='"+ date +"']")).click();
    }

    public void searchBus() {
        searchBusButton.click();
    }
}
