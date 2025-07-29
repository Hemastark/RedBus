package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {

    private static final Logger log = LogManager.getLogger(ExcelUtils.class);

    public String getCellValue(String filePath, String sheetName, int rowValue, int colValue) {
        String cellValue = "";
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowValue);
            cellValue = row.getCell(colValue).getStringCellValue();
            workbook.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("Issue in fetching the Excel value" + e.getMessage());
        }
        return cellValue;
    }

    public void setCellValue(String filePath, String sheetName, int rowValue, int colValue, String data) {
        try {
            File resultDirectory = new File(filePath);
            if(!resultDirectory.exists()) {
                log.info("Creating the directory");
                resultDirectory.mkdirs();
            }
            File resultFile = new File(filePath + "/result.xlsx");
            XSSFWorkbook workbook;
            Sheet sheet;
            if(resultFile.exists()) {
                FileInputStream inputStream = new FileInputStream(resultFile);
                workbook = new XSSFWorkbook(inputStream);
                sheet = workbook.getSheet(sheetName);
                if(sheet == null) {
                    sheet = workbook.createSheet(sheetName);
                }
                inputStream.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(sheetName);
            }
            Row row = sheet.getRow(rowValue);
            if(row == null) {
                row = sheet.createRow(rowValue);
            }
            Cell cell = row.createCell(colValue);
            cell.setCellValue(data);

            FileOutputStream outputStream = new FileOutputStream(filePath + "/result.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            log.error("Issue in setting the Excel value" + e.getMessage());
        }
    }
}
