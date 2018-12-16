/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author acer
 */
public class ExcelHandler {
    
    private final String baseDir = System.getProperty("user.dir");
    
    public ExcelHandler() {
        
    }
    
    public List<Patient> read(String path, int sheetIndex) throws FileNotFoundException, IOException {
        File excelFile = new File(baseDir + path);
        FileInputStream fis = new FileInputStream(excelFile);
        
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        List<Patient> result = new ArrayList<>();
        
        Iterator<Row> rowIt = sheet.iterator();
        if (rowIt.hasNext()) {
            rowIt.next();
        }
        
        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            result.add(new Patient(row.getCell(0).toString(),
                    row.getCell(1).toString().charAt(0), 
                    Integer.parseInt(row.getCell(2).toString()), 
                    Integer.parseInt(row.getCell(3).toString()), 
                    Integer.parseInt(row.getCell(4).toString()), 
                    Integer.parseInt(row.getCell(5).toString()), 
                    Integer.parseInt(row.getCell(6).toString()), 
                    Integer.parseInt(row.getCell(7).toString()), 
                    row.getCell(8).toString()));
        }
        
        return result;
    }
}
