package tests;

import entities.Worker;
import exceptions.TimeoutException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static utils.ExcelUtils.*;

public class DownloadWithoutKnowingFileName extends BaseTest {
    int MAXIMUM_SECONDS_TO_WAIT = 10;
    int HEADLINES_ROW_INDEX = 0;
    int FIRST_NAME_COLUMN_INDEX = 1;

    @Test
    public void downloadExcelWithoutKnowingFileName() throws InterruptedException, IOException, InvalidFormatException, TimeoutException {
        int numberOfExcelFilesBeforeDownload = getNumberOfExcelFilesInDownloadsDirectory();
        driver.findElement(By.cssSelector(DOWNLOAD_BUTTON_SELECTOR)).click();

        waitUntilExcelFileIsDownloaded(numberOfExcelFilesBeforeDownload, MAXIMUM_SECONDS_TO_WAIT);

        File excelFile = getLatestExcelFileDownloaded();
        List<List<String>> excelTable = getExcelFileAsStringLists(excelFile);
        List<Worker> workersList = getExcelFileAsWorkersList(excelFile);

        assertThat(excelTable.get(HEADLINES_ROW_INDEX).get(FIRST_NAME_COLUMN_INDEX), is("First Name"));
        assertThat(workersList.get(HEADLINES_ROW_INDEX).getFirstName(), is("First Name"));
    // add assert for soldier details that was created before, make it a method
        excelFile.deleteOnExit();
    }
}
