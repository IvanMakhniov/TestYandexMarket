package steps;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import pages.*;

public class Steps {
    private WebDriver driver;

    public Steps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие страницы Яндекса")
    public void findYandex(){
        driver.get("https://yandex.ru");
    }
    @Step("Переход на яндекс {subSiteName}")
    public void goYandexSubSite(YandexPage yandexPage, String subSiteName){
        yandexPage.getCollectSubSites();
        yandexPage.goSubSite(subSiteName);
    }
    @Step("Выбор раздела {chapterName}, подраздела {chapterSubName}")
    public void goCategories(YandexMarketPage yandex, String chapterName, String chapterSubName){
        yandex.getCategories();
        yandex.clickCategories(chapterName);
        yandex.getSubCategories();
        yandex.clickSubCategories(chapterSubName);
    }
    @Step("Задание параметра 'Ценаб р' от {minPrise} до {maxPrise} рублей")
    public void priseSetting(YandexMarketPage yandex, String minPrise, String maxPrise){
        yandex.setPrise(minPrise, maxPrise);
    }
    @Step("Выбор производителя {firstManufacturer} и {secondManufacturer}")
    public void manufacturerChoice(YandexMarketPage yandex, String firstManufacturer, String secondManufacturer){
        yandex.getManufacturer();
        yandex.clickManufacturer(firstManufacturer);
        yandex.clickManufacturer(secondManufacturer);
    }
    @Step("Выбор производителя {firstManufacturer}")
    public void manufacturerChoice(YandexMarketPage yandex, String firstManufacturer){
        yandex.getManufacturer();
        yandex.clickManufacturer(firstManufacturer);
    }
    @Step("Переключение с 48 элементов на 12")
    public void getAmountPrice(YandexMarketPage yandex){
        yandex.getAmountPrise();
    }
    @Step("Поиск товара на Яндекс Маркет: {name}")
    public void searchFirstElement(YandexMarketPage yandex, String name){
        yandex.goSearch(name);
    }
    @Step("Проверка имени продукции {namePrice}")
    public void examinPrice(YandexMarketPage yandex, String namePrice){
        yandex.setForward();
        int i = 1;
        while (Selenide.$(driver.findElement(By.xpath("//a[@aria-label='Страница "+ i +" (текущая)']/.."))).text().contains("Вперёд")) {
            Assertions.assertTrue(yandex.getTextAllPrice().stream().allMatch(x -> StringUtils.containsIgnoreCase(x, namePrice)), "На странице "+ i +" элемент не соответствует элементу " + namePrice);
            try {
                yandex.clickForward();
            } catch (WebDriverException e){
                break;
            }
            i++;
        }
        Assertions.assertTrue(yandex.getTextAllPrice().stream().allMatch(x -> StringUtils.containsIgnoreCase(x, namePrice)), "На странице "+ i +" элемент не соответствует элементу " + namePrice);
    }
    @Step("Сравнение двух элементов")
    public void comparisonElements(String first, String second){
        Assertions.assertTrue(first.contains(second), "Элементы не равны между собой");
    }
}