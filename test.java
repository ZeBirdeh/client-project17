
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
 



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class test {
     
    public static void main(String[] args) throws IOException {
        String excelFilePath = "H:\\Li_Excel.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.rowIterator();
         
        while (iterator.hasNext()) {
        	XSSFRow nextRow = (XSSFRow) iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
            	XSSFCell cell = (XSSFCell) cellIterator.next();
                 
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }
         
        workbook.close();
        inputStream.close();
    }
 
}