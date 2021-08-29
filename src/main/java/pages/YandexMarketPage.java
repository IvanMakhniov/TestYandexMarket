package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import helpers.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YandexMarketPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement forward;
    private List<WebElement> buffer = new ArrayList<>();
    private List<Map<String, Object>> categories = new ArrayList<>();
    private List<Map<String, Object>> subCategories = new ArrayList<>();
    private List<Map<String, Object>> manufacturer = new ArrayList<>();
    private Map<String, WebElement> amountPrice = new HashMap<>();
    private Map<String, String> titlePrice = new HashMap<>();

    public YandexMarketPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Constants.DEFAULT_TIMEOUT);
    }

    public void goSearch(String search){
        WebElement inputSearch = driver.findElement(By.xpath("//input[@id='header-search']"));
        WebElement buttonSearch = driver.findElement(By.xpath("//button[@data-r='search-button']"));
        inputSearch.sendKeys(search);
        buttonSearch.click();
    }
    private void getCollections(List<WebElement>buffer, List<Map<String, Object>> collection, String xPath){
        buffer = driver.findElements(By.xpath(xPath));
        for (WebElement result : buffer){
            collection.add(Map.of(
                    "WEB_ELEMENT", result,
                    "NAME", result.getText()
            ));
        }
        buffer.clear();
    }
    private void clickElementBuffer(String name, List<Map<String, Object>> collection){
        WebElement shoppingLinc = (WebElement) collection.stream()
                .filter(x -> x.get("NAME").toString().contains(name))
                .findFirst()
                .get().get("WEB_ELEMENT");
        Selenide.$(shoppingLinc).click();
    }
    public void getCategories(){
        getCollections(buffer, this.categories, "//a[@class = '_3Lwc_']");
    }
    public void getSubCategories(){
        getCollections(buffer, subCategories, "//ul[@data-autotest-id='subItems']//a");
    }
    public void getManufacturer(){
        getCollections(buffer, manufacturer, "//legend[text()='Производитель']/ancestor::fieldset//label");
    }
    public void clickCategories(String nameCategories){
        clickElementBuffer(nameCategories, categories);
    }
    public void clickSubCategories(String nameSubCategories){
        clickElementBuffer(nameSubCategories, subCategories);
    }
    public void clickManufacturer(String nameManufacturer){
        clickElementBuffer(nameManufacturer, manufacturer);
    }
    public void clickForward(){
            if (Selenide.$(forward).exists()){
                Selenide.$(forward).click();
            }
    }
    public WebElement getForward() {
        return forward;
    }
    public void setForward(){
        forward = driver.findElement(By.xpath("//a[@aria-label='Следующая страница']"));
    }
    public void setPrise(String minPrice, String maxPrice){
        WebElement minPriceInput = driver.findElement(By.xpath(("//input[@name= 'Цена от']")));
        WebElement maxPriceInput = driver.findElement(By.xpath(("//input[@name= 'Цена до']")));
        minPriceInput.sendKeys(minPrice);
        maxPriceInput.sendKeys(maxPrice);
    }
    public void getAmountPrise(){
        amountPrice = Map.of(
                "Переключатель", driver.findElement(By.xpath("//i[@class='_2CWz-']/ancestor::button")),
                "На 12 элементов", driver.findElement(By.xpath("//button[text() = 'Показывать по 12']")),
                "На 48 элементов", driver.findElement(By.xpath("//button[text() = 'Показывать по 48']"))
        );
        if (amountPrice.get("Переключатель").getAttribute("aria-expanded").contains("false")) {
            Selenide.$(amountPrice.get("Переключатель")).shouldBe(Condition.visible).click();
        }
        Selenide.$(amountPrice.get("На 12 элементов")).shouldBe(Condition.visible).click();
    }
    public String getTextFirstPrice(String key){
        if (titlePrice.get(key) == null){
            titlePrice.put(key, Selenide.$(driver.findElement(By.xpath("//article//h3//a[@target='_blank']"))).waitUntil(Condition.visible, 9000).getText());
        }
        return titlePrice.get(key);
    }
    public List<String> getTextAllPrice(){
        List<String> pur = new ArrayList<>();
        buffer = driver.findElements(By.xpath("//article//h3//a[@target='_blank']/span"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article//h3//a[@target='_blank']/span")));
        for (int i = 0; i < buffer.size(); i++){
            pur.add(buffer.get(i).getText());
        }
        buffer.clear();
        return pur;
    }
}
