package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailableBusesPage {

    WebDriver driver;

    public AvailableBusesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[starts-with(@class,'busesFoundText_')]")
    WebElement availableBusCount;

    @FindBy(xpath = "//div[starts-with(@class,'travelsName_')]")
    List<WebElement> busName;

    @FindBy(xpath = "//p[starts-with(@class,'finalFare_')]")
    List<WebElement> busFair;

    HashMap<String, Integer> busPriceMap = new HashMap<>();

    public boolean confirmBusAvailability() {
        return availableBusCount.isDisplayed();
    }

    public HashMap<String, Integer> fetchBusNameAndFair() {
        for(int i=0;i<busName.size();i++) {
            busPriceMap.put(busName.get(i).getText(), Integer.parseInt(busFair.get(i).getText()
                    .replace("₹","").replace(",","")));
        }
        return busPriceMap;
    }

    public Map.Entry<String, Integer> getLeastPrice() {
        Map.Entry<String, Integer> minSet = busPriceMap.entrySet().stream()
                .min(Map.Entry.comparingByValue()).orElse(null);
        return minSet;
    }
}
