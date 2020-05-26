package pages;

import io.github.sukgu.Shadow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChromeDownloadsTab extends BasePage {
    final String SELECTOR = "body downloads-manager #downloadsList #frb0 #file-link";
    Shadow shadowDOMfinder;

    private WebElement firstDownloadInPage;

    @FindBy (tagName = "downloads-manager")
    private WebElement firstShadowDOM;

    @FindBy (tagName = "iron-list")
    private WebElement secondShadowDOM;

    @FindBy (tagName = "downloads-item")
    private WebElement thirdShadowDOM;

    public ChromeDownloadsTab(WebDriver driver) {
        super(driver);
        shadowDOMfinder = new Shadow(driver);
    }

    public String getLatestDownloadFileName() {
        String fileName = shadowDOMfinder.findElement(SELECTOR).getText();
        closeTab();
        return fileName;
    }
}
