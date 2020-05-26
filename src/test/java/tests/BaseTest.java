package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver;
    private final String DRIVER_PATH = "chromedriver.exe";
    private final String PAGE_URL = "https://file-examples.com/index.php/sample-documents-download/sample-xls-download/"; //https://www49.zippyshare.com/v/94ejDw4u/file.html?dl=PPT9iQfF"; //"https://www17.zippyshare.com/v/xZhbrwEe/file.html";
    //https://file-examples.com/index.php/sample-documents-download/sample-xls-download/

    {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(PAGE_URL);
        driver.manage().window().maximize();
    }

    @After
    public void terminate() {
        driver.quit();
    }
}
