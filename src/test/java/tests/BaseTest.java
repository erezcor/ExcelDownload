package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver;
    private final String DRIVER_PATH = "chromedriver.exe";
    private final String PAGE_URL = "https://file-examples.com/index.php/sample-documents-download/sample-xls-download/";

    private String DOWNLOAD_BUTTON_SELECTOR = "#table-files > tbody > tr:nth-child(10) > td.text-right.file-link > a.btn.btn-orange.btn-outline.btn-xl.page-scroll.download-button";

    {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(PAGE_URL);
        driver.manage().window().maximize();

        driver.findElement(By.cssSelector(DOWNLOAD_BUTTON_SELECTOR)).click();
    }

    @After
    public void terminate() {
        driver.quit();
    }
}
