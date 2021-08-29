package ru.yandex.market;


import org.junit.jupiter.api.Test;
import pages.*;

public class YandexMarketTestLaptop extends BaseTest {
    @Test
    public void yandexMarketTest() {
        step.findYandex();
        YandexPage yandexPage = new YandexPage(chromeDriver);
        step.goYandexSubSite(yandexPage, "Маркет");
        YandexMarketPage yandex = new YandexMarketPage(chromeDriver);
        step.goCategories(yandex, "Компьютеры", "Ноутбуки");
        step.priseSetting(yandex, "10000", "30000");
        step.manufacturerChoice(yandex, "HP", "Lenovo");
        step.getAmountPrice(yandex);
        step.searchFirstElement(yandex, yandex.getTextFirstPrice("объект поиска"));
        step.comparisonElements(yandex.getTextFirstPrice("объект поиска"), yandex.getTextFirstPrice("результат поиска"));
    }
}
