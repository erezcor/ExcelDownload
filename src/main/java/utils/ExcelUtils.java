package utils;

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

import static constants.systemConstants.DOWNLOADS_DIRECTORY_PATH;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;
import static java.util.stream.Collectors.toList;
import static utils.FileUtils.getLatestFileFromFolderByExtension;
import static utils.FileUtils.getNumberOfFilesByExtensionFrom;
import static utils.StreamUtils.getStreamFromIterator;
import static utils.TimeoutUtils.isMaximumTimeInFunctionPassed;

public class ExcelUtils {
    private static int WAIT_MILLISECONDS_WHEN_EXCEL_NOT_DOWNLOADED = 500;
    private final static int FIRST_SHEET_INDEX = 0;
    private final static int HEADLINES_ROW_INDEX = 0;
    private final static String excelFileExtension = "xlsx";

    public static List<List<String>> getExcelFileAsStringLists(File file) throws IOException, InvalidFormatException {
        return getDataFromExcelSheet(new XSSFWorkbook(file).getSheetAt(FIRST_SHEET_INDEX));
    }

    private static List<List<String>> getDataFromExcelSheet(XSSFSheet excelSheet) {
        return getStreamFromIterator(excelSheet.rowIterator())
                .map(ExcelUtils::getCellStringsFromRow).collect(toList());
    }

    private static List<String> getCellStringsFromRow(Row row) {
        List<String> excelRowCells = new ArrayList<>();;
        for (int cellIndex = 0; cellIndex <= getLastColumnIndex(row.getSheet()); cellIndex++) {
            excelRowCells.add(getStringFromCell(row.getCell(cellIndex)));
        }
        return excelRowCells;
    }

    private static String getStringFromCell(Cell cell) {
        return new DataFormatter().formatCellValue(cell);
    }

    private static int getLastColumnIndex(Sheet excelSheet) {
        return excelSheet.getRow(HEADLINES_ROW_INDEX).getLastCellNum() - 1;
    }

    public static List<String> getHeadlinesRowStrings(File file) throws IOException, InvalidFormatException {
        return getExcelFileAsStringLists(file).get(HEADLINES_ROW_INDEX);
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