package tests;

import entities.Worker;
import exceptions.TimeoutException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static constants.workerTableColumns.FIRST_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static utils.ExcelUtils.*;
import static utils.WorkerUtils.getWorkerFromListByID;
import static utils.WorkerUtils.getWorkersListFromExcelFile;

public class DownloadWithoutKnowingFileName extends BaseTest {
    int MAXIMUM_SECONDS_TO_WAIT_FOR_EXCEL_DOWNLOAD = 15;

    Worker workerInTable = new Worker("1", "Dulce", "Abril", "Female",
            "United States", "32", "15/10/2017", "1562");

    @Test
    public void downloadExcelWithoutKnowingFileName() throws InterruptedException, IOException, InvalidFormatException, TimeoutException {

        int numberOfExcelFilesBeforeDownload = getNumberOfExcelFilesInDownloadsDirectory();
        driver.findElement(By.cssSelector(DOWNLOAD_BUTTON_SELECTOR)).click();

        waitUntilExcelFileIsDownloaded(numberOfExcelFilesBeforeDownload, MAXIMUM_SECONDS_TO_WAIT_FOR_EXCEL_DOWNLOAD);
        File excelFile = getLatestExcelFileDownloaded();
        List<Worker> workersList = getWorkersListFromExcelFile(excelFile);

        assertThat(getHeadlinesRowAsStringList(excelFile).get(FIRST_NAME.INDEX), is("First Name"));
        assertThat(getWorkerFromListByID(workerInTable.getId(), workersList), is(workerInTable));

        excelFile.deleteOnExit();
    }
}
