package driver;

import helpers.Constants;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverManage {
    private static WebDriver currentDriver;

    public static WebDriver getCurrentDriver(){
        return currentDriver;
    }
    public static void initChrome(){
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(List.of("start-maximized", "disable-infobars", "--no-sandbox"));
        try {
            currentDriver = new ChromeDriver(chromeOptions);
        } catch (SessionNotCreatedException e){
            System.out.println("Отсутствие совместимости драйвера с браузером");
        }
        setDriverDefaultSetting();
    }
    public static void setDriverDefaultSetting(){
        currentDriver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        currentDriver.manage().deleteAllCookies();
    }
    public static void killCurrentDriver(){
        if (currentDriver != null){
            currentDriver.quit();
            currentDriver = null;
        }
    }
}
