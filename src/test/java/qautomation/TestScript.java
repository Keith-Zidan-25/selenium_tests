package qautomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestScript {
    public static WebDriver driver = null;

    public String browser = System.getProperty("browser", "Chrome");
    public String url = System.getProperty("URL", "https://www.google.com");

    @BeforeTest
    public void beforeTest() {
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--disable-infobars");
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("IE")) {
            WebDriverManager.iedriver().setup();
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.ignoreZoomSettings();
            options.introduceFlakinessByIgnoringSecurityDomains();
            options.setCapability("unexpectedAlertBehaviour", "accept");
            options.requireWindowFocus();
            options.withInitialBrowserUrl("http://www.google.com");
            driver = new InternetExplorerDriver(options);
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TestApplication() {
        driver.get(url);
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        Assert.assertTrue(title.length() > 0, "Page title should not be empty.");
    }

    @AfterTest
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}