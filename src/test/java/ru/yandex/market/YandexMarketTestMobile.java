package ru.yandex.market;

import org.junit.jupiter.api.Test;
import pages.*;

public class YandexMarketTestMobile extends BaseTest{
    @Test
    public void yandexTestMobile(){
        step.findYandex();
        YandexPage yandexPage = new YandexPage(chromeDriver);
        step.goYandexSubSite(yandexPage, "Маркет");
        YandexMarketPage yandex = new YandexMarketPage(chromeDriver);
        step.goCategories(yandex, "Электроника", "Смартфоны");
        step.manufacturerChoice(yandex, "Apple");
        step.getAmountPrice(yandex);
        step.examinPrice(yandex, "Apple");
    }
}
