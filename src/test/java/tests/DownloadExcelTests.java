package tests;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pages.ChromeDownloadsTab;
import pages.HomePage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static utils.ExcelUtils.getExcelFileAsStringLists;
import static utils.ExcelUtils.getLatestExcelFileDownloaded;
import static utils.FileUtils.*;

public class DownloadExcelTests extends BaseTest {
    String CSS_SELECTOR_OF_DOWNLOAD_BUTTON = "#table-files > tbody > tr:nth-child(10) > td.text-right.file-link > a.btn.btn-orange.btn-outline.btn-xl.page-scroll.download-button";
    String DOWNLOAD_FOLDER_PATH = "C:\\Users\\Erez Corech\\Downloads\\";
    String FILE_PATH;

    int MAXIMUM_SECONDS_TO_WAIT = 10;
    int HEADLINES_ROW_INDEX = 0;
    int FIRST_NAME_COLUMN_INDEX = 1;

    String id = "dlbutton";
    String selector = "body > div.container > h2 > a";

    @Before
    public void clickDownloadFile() {
        driver.findElement(By.cssSelector(CSS_SELECTOR_OF_DOWNLOAD_BUTTON)).click();
    }

    @Test
    public void downloadExcelTest() throws IOException, InvalidFormatException, InterruptedException {
        HomePage homePage = new HomePage(driver);
        ChromeDownloadsTab chromeDownloadsTab = homePage.openChromeDownloadsTab();

        FILE_PATH = DOWNLOAD_FOLDER_PATH + chromeDownloadsTab.getLatestDownloadFileName();

        waitForFileToExist(FILE_PATH, MAXIMUM_SECONDS_TO_WAIT);
        List<List<String>> excelTable = getExcelFileAsStringLists(FILE_PATH);

        assertThat(excelTable.get(HEADLINES_ROW_INDEX).get(FIRST_NAME_COLUMN_INDEX), is("First Name"));

        deleteFileAt(FILE_PATH);
    }

    @Test
    public void downloadExcel2() throws InterruptedException, IOException, InvalidFormatException {
        //waitForDownloadToStart(MAXIMUM_SECONDS_TO_WAIT);
        waitForDownloadsToFinish(MAXIMUM_SECONDS_TO_WAIT);
        File excelFile = getLatestExcelFileDownloaded(DOWNLOAD_FOLDER_PATH);
        List<List<String>> excelTable = getExcelFileAsStringLists(excelFile);
        assertThat(excelTable.get(HEADLINES_ROW_INDEX).get(FIRST_NAME_COLUMN_INDEX), is("First Name"));
    }
}
