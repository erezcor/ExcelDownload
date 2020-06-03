package tests;

import exceptions.TimeoutException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.openqa.selenium.By;
import pages.ChromeDownloadsTab;
import pages.HomePage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static constants.systemConstants.DOWNLOADS_DIRECTORY_PATH;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static utils.ExcelUtils.getExcelFileAsStringLists;
import static utils.FileUtils.waitForFileToExist;

public class DownloadAndGetFileNameFromChromeTab extends BaseTest {
    String filePath;
    String fileFolderPath = DOWNLOADS_DIRECTORY_PATH;

    int MAXIMUM_SECONDS_TO_WAIT = 10;
    int HEADLINES_ROW_INDEX = 0;
    int FIRST_NAME_COLUMN_INDEX = 1;

    @Test
    public void downloadExcelAndGetFileNameFromChrome() throws IOException, InvalidFormatException, InterruptedException, TimeoutException {
        driver.findElement(By.cssSelector(DOWNLOAD_BUTTON_SELECTOR)).click();

        HomePage homePage = new HomePage(driver);
        ChromeDownloadsTab chromeDownloadsTab = homePage.openChromeDownloadsTab();
        filePath = fileFolderPath + chromeDownloadsTab.getLatestDownloadFileName();

        File excelFile = waitForFileToExist(filePath, MAXIMUM_SECONDS_TO_WAIT);
        List<List<String>> excelTable = getExcelFileAsStringLists(excelFile);

        assertThat(excelTable.get(HEADLINES_ROW_INDEX).get(FIRST_NAME_COLUMN_INDEX), is("First Name"));

        excelFile.delete();
    }

}
