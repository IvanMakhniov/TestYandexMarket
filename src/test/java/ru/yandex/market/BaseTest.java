package ru.yandex.market;

import driver.WebDriverManage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import steps.Steps;

public class BaseTest {
    protected WebDriver chromeDriver;
    protected Steps step;

    @BeforeEach
    public void before(){
        WebDriverManage.initChrome();
        chromeDriver = WebDriverManage.getCurrentDriver();
        step = new Steps(chromeDriver);

    }
    @AfterEach
    public void closeBellTest(){
        WebDriverManage.killCurrentDriver();
    }

}
