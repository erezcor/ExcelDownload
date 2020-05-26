package pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private String chromeDownloadTabLink = "chrome://downloads/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ChromeDownloadsTab openChromeDownloadsTab() {
        openLinkInNewTab(chromeDownloadTabLink);
        return new ChromeDownloadsTab(driver);
    }
}
