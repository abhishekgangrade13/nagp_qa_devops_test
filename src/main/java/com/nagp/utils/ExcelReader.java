package com.nagp.utils;

import com.nagp.pages.BasePage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class ExcelReader {
    public static Logger log = LogManager.getLogger(ExcelReader.class);
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
//    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public void setExcelFile(String ExcelPath, String SheetName) throws Exception {
        try {
            File f = new File(ExcelPath);
            wb = WorkbookFactory.create(f);
            sh = wb.getSheet(SheetName);
//            this.excelFilePath = ExcelPath;
            //adding all the column header names to the map 'columns'
            sh.getRow(0).forEach(excCell -> {
                columns.put(excCell.getStringCellValue(), excCell.getColumnIndex());
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String getCellData(int rownum, int colnum) throws IOException {
        try {
            cell = sh.getRow(rownum).getCell(colnum);
            String cellData = null;
            switch (cell.getCellType()) {
                case STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellData = String.valueOf(cell.getDateCellValue());
                    } else {
                        cellData = String.valueOf((long) cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    cellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    cellData = "";
                    break;
                default:
                    break;
            }
            return cellData;
        } catch (Exception e) {
            return "";
        }
    }

    public String getCellData(String columnName, int rownum) throws Exception {
        return getCellData(rownum, columns.get(columnName));
    }

}
