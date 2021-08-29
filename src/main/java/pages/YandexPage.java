package pages;

import driver.WebDriverManage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexPage {
    private String xPathSubSites = "//a[@data-id]";
    private WebDriver driver;
    private List<WebElement> subSites;
    private Map<String, WebElement> collectSubSites = new HashMap<>();

    public YandexPage(WebDriver driver) {
        this.driver = driver;
        subSites = driver.findElements(By.xpath(xPathSubSites));
    }
    public Map<String, WebElement> getCollectSubSites(){
        for (WebElement result : subSites){
            collectSubSites.put(
                    result.getText(), result
            );
        }
        return collectSubSites;
    }
    public boolean goSubSite(String subSiteName){
        collectSubSites.get(subSiteName).click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab: tabs){
            driver.switchTo().window(tab);
            if (driver.getTitle().contains(subSiteName))
                return true;
        }
        return false;
    }
}
