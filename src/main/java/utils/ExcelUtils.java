package utils;

import entities.Worker;
import exceptions.TimeoutException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.SystemConstants.DOWNLOADS_DIRECTORY_PATH;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;
import static utils.FileUtils.getLatestFileFromFolderByExtension;
import static utils.FileUtils.getNumberOfFilesByExtensionFrom;
import static utils.TimeoutUtils.isMaximumTimeInFunctionPassed;

public class ExcelUtils {
    private static int MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP = 500;
    private final static int FIRST_SHEET_INDEX = 0;
    private static int HEADLINES_ROW_INDEX = 0;
    private final static String excelFileExtension = "xlsx";

    public static List<List<String>> getExcelFileAsStringLists(File file) throws IOException, InvalidFormatException {
        List<List<String>> excelTable = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        XSSFSheet excelSheet = new XSSFWorkbook(file).getSheetAt(FIRST_SHEET_INDEX);

        int lastColumnIndex = excelSheet.getRow(HEADLINES_ROW_INDEX).getLastCellNum() - 1;
// Can it be converted to stream

        for (Row row : excelSheet) {
            excelTable.add(new ArrayList<>());
            for (int cellIndex = 0; cellIndex <= lastColumnIndex; cellIndex++) {
                String cellAsString = formatter.formatCellValue(row.getCell(cellIndex));
                excelTable.get(row.getRowNum()).add(cellAsString);
            }
        }

        return excelTable;
    }

    public static List<Worker> getExcelFileAsWorkersList(File file) throws IOException, InvalidFormatException {
        return getExcelFileAsStringLists(file).stream().map(Worker::new).collect(toList());
    }

    public static File getLatestExcelFileDownloaded() {
        return getLatestFileFromFolderByExtension(DOWNLOADS_DIRECTORY_PATH, excelFileExtension);
    }

    public static void waitUntilExcelFileIsDownloaded(int numberOfExcelFilesInDownloadsDirectoryBeforeDownload,
                                                      int maximumSecondsToWait) throws InterruptedException, TimeoutException {
        long timeWhenEnteredFunctionInMillis = currentTimeMillis();

        while (!isExcelFileDownloadFinished(numberOfExcelFilesInDownloadsDirectoryBeforeDownload) &&
                !isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            Thread.sleep(MILLISECONDS_TO_WAIT_BETWEEN_EACH_LOOP);
        }

        if (isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
            throw new TimeoutException("Maximum time timed out in waitUntilExcelFileIsDownloaded");
        }
    }

    private static boolean isExcelFileDownloadFinished(int numberOfExcelFilesBeforeDownload) {
        return getNumberOfExcelFilesInDownloadsDirectory() > numberOfExcelFilesBeforeDownload;
    }

    public static int getNumberOfExcelFilesInDownloadsDirectory() {
        return getNumberOfFilesByExtensionFrom(DOWNLOADS_DIRECTORY_PATH, excelFileExtension);
    }
}