package utils;

import entities.Worker;
import exceptions.TimeoutException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static constants.SystemConstants.DOWNLOADS_DIRECTORY_PATH;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;
import static utils.FileUtils.getLatestFileFromFolderByExtension;
import static utils.FileUtils.getNumberOfFilesByExtensionFrom;
import static utils.StreamUtils.getStreamFromIterator;
import static utils.TimeoutUtils.isMaximumTimeInFunctionPassed;

public class ExcelUtils {
    private static int WAIT_MILLISECONDS_WHEN_EXCEL_NOT_DOWNLOADED = 500;
    private final static int FIRST_SHEET_INDEX = 0;
    private static int HEADLINES_ROW_INDEX = 0;
    private final static String excelFileExtension = "xlsx";

    public static List<List<String>> getExcelFileAsStringLists(File file) throws IOException, InvalidFormatException {
        return getDataFromExcelSheet(new XSSFWorkbook(file).getSheetAt(FIRST_SHEET_INDEX));
        // todo make function to get last element from list
        // Todo if use stream make a util convert iterator to stream
        // Todo Extract to method named stringFromCell and function with parameter excelSheet
    }

    private static List<List<String>> getDataFromExcelSheet(XSSFSheet excelSheet) {
        return getStreamFromIterator(excelSheet.rowIterator())
                .map(ExcelUtils::getCellStringsFromRow).collect(toList());
    }

    // todo new function
    private static String getStringFromCell(Cell cell) {
        return new DataFormatter().formatCellValue(cell);
    }

    // todo new function
    private static int getLastColumnIndex(Sheet excelSheet) {
        return excelSheet.getRow(HEADLINES_ROW_INDEX).getLastCellNum() - 1;
    }

    // todo new function
    private static List<String> getCellStringsFromRow(Row row) {
        return getCellsFromRow(row).stream().map(ExcelUtils::getStringFromCell).collect(toList());
    }

    // todo new function
    private static List<Cell> getCellsFromRow(Row row) {
        List<Cell> excelRowCells = new ArrayList<>();;
        for (int cellIndex = 0; cellIndex <= getLastColumnIndex(row.getSheet()); cellIndex++) {
            excelRowCells.add(row.getCell(cellIndex));
        }
        return excelRowCells;
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

        while (!isExcelFileDownloadFinished(numberOfExcelFilesInDownloadsDirectoryBeforeDownload)) {
            sleep(WAIT_MILLISECONDS_WHEN_EXCEL_NOT_DOWNLOADED);

            if (isMaximumTimeInFunctionPassed(timeWhenEnteredFunctionInMillis, maximumSecondsToWait)) {
                throw new TimeoutException("Maximum time timed out in waitUntilExcelFileIsDownloaded");
            }
        }
    }

    private static boolean isExcelFileDownloadFinished(int numberOfExcelFilesBeforeDownload) {
        return getNumberOfExcelFilesInDownloadsDirectory() > numberOfExcelFilesBeforeDownload;
    }

    public static int getNumberOfExcelFilesInDownloadsDirectory() {
        return getNumberOfFilesByExtensionFrom(DOWNLOADS_DIRECTORY_PATH, excelFileExtension);
    }
}