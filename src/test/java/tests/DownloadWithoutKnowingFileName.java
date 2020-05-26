package tests;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static utils.ExcelUtils.getExcelFileAsStringLists;
import static utils.ExcelUtils.getLatestExcelFileDownloaded;
import static utils.FileUtils.waitForDownloadsToFinish;

public class DownloadWithoutKnowingFileName extends BaseTest {
    int MAXIMUM_SECONDS_TO_WAIT = 10;
    int HEADLINES_ROW_INDEX = 0;
    int FIRST_NAME_COLUMN_INDEX = 1;

    @Test
    public void downloadExcelWithoutKnowingFileName() throws InterruptedException, IOException, InvalidFormatException {
        Thread.sleep(5000);
        waitForDownloadsToFinish(MAXIMUM_SECONDS_TO_WAIT);

        File excelFile = getLatestExcelFileDownloaded();
        List<List<String>> excelTable = getExcelFileAsStringLists(excelFile);

        assertThat(excelTable.get(HEADLINES_ROW_INDEX).get(FIRST_NAME_COLUMN_INDEX), is("First Name"));

        excelFile.deleteOnExit();
    }
}
