package Utilities;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {

    // Method to read Excel data and return it as a list of maps (each row as a map of column name to cell value)
    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {  // Enforce XSSF for .xlsx files

            // Get the desired sheet by name
            Sheet sheet = workbook.getSheet(sheetName);

            // Get the header row (assumed to be the first row)
            Row headerRow = sheet.getRow(0);

            // Loop through all rows starting from the second row (index 1)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowMap = new HashMap<>();
                Row currentRow = sheet.getRow(i);

                // Loop through each cell in the current row based on header row columns
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();  // Column name
                    Cell cell = currentRow.getCell(j);

                    // Convert cell value to string or set as empty if cell is null
                    String value = cell != null ? cell.toString() : "";
                    rowMap.put(key, value);  // Map column name to cell value
                }
                dataList.add(rowMap);  // Add the current row's map to the list
            }

        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace if exception occurs
        }

        return dataList;  // Return list of row data maps
    }
}
