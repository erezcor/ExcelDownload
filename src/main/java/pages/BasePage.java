package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.openqa.selenium.support.PageFactory.initElements;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private int SECONDS_TO_WAIT = 10;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, SECONDS_TO_WAIT);
        initElements(new AjaxElementLocatorFactory(driver, SECONDS_TO_WAIT), this);
    }

    protected Select getSelectOf(WebElement selectElement) {
        return new Select(selectElement);
    }

    private Actions getActionsObject() {
        return new Actions(driver);
    }

    protected Actions hoverOverElements(WebElement... elementsToHoverByOrder) {
        Actions actions = getActionsObject();
        Arrays.asList(elementsToHoverByOrder).forEach(actions::moveToElement);
        actions.perform();
        return actions;
    }

    protected void clickAfterHovering(Actions actions) {
        actions.click().perform();
    }

    private void executeJavaScript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }

    protected void openEmptyNewTab() {
        executeJavaScript("window.open()");
        switchToLastTab();
    }

    // can combine lines 1+3 with javascript
    protected void openLinkInNewTab(String link) {
        openEmptyNewTab();
        switchToLastTab();
        driver.navigate().to(link);
    }

    protected void closeTab() {
        executeJavaScript("window.close()");
        switchToLastTab();
    }

    protected void switchToLastTab() {
        List<String> tabNames = new ArrayList<>(driver.getWindowHandles());
        int lastTabIndex = tabNames.size() - 1;
        driver.switchTo().window(tabNames.get(lastTabIndex));
    }
}
