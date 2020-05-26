package utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.SystemConstants.DOWNLOAD_FOLDER_PATH;
import static utils.FileUtils.getLatestFileFromFolderByExtension;

public class ExcelUtils {
    private final static int FIRST_SHEET_INDEX = 0;
    private static int HEADLINES_ROW_INDEX = 0;
    private final static String excelFileExtension = "xlsx";

    public static List<List<String>> getExcelFileAsStringLists(File file) throws IOException, InvalidFormatException {
        List<List<String>> excelTable = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        XSSFSheet excelSheet = new XSSFWorkbook(file).getSheetAt(FIRST_SHEET_INDEX);

        int lastColumnIndex = excelSheet.getRow(HEADLINES_ROW_INDEX).getLastCellNum() - 1;

        for (Row row : excelSheet) {
            excelTable.add(new ArrayList<>());
            for (int cellIndex = 0; cellIndex <= lastColumnIndex; cellIndex++) {
                String cellAsString = formatter.formatCellValue(row.getCell(cellIndex));
                excelTable.get(row.getRowNum()).add(cellAsString);
            }
        }

        return excelTable;
    }

    public static List<List<String>> getExcelFileAsStringLists(String filePath) throws IOException, InvalidFormatException {
        return getExcelFileAsStringLists(new File(filePath));
    }

    public static File getLatestExcelFileDownloaded() {
        return getLatestFileFromFolderByExtension(DOWNLOAD_FOLDER_PATH, excelFileExtension);
    }
}