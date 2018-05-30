package ehub_benefits;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.*;


//import com.google.common.collect.HashBasedTable;
//import com.google.common.collect.Table;


public class ExcelOps  {
	
	public static Map<String, String> xlsFetchSQLpair() throws IOException {
		Map<String, String> ReturnMap = new HashMap<>();
		String excelFilePath = "src/test/resources/eHub_Benefits_Input.xlsx";
     	String SheetNm = "ETL_SQL_Pair";
     	FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
     	Workbook workbook = new XSSFWorkbook(inputStream);
     	Sheet sheet = workbook.getSheet(SheetNm);
     	Iterator<Row> RowIter = sheet.iterator();
     	String[] col_val = new String[100];
     	Row row = null;
     	Cell cell = null;
     	
     	int rowCnt = sheet.getLastRowNum() + 1;
        int colCnt = sheet.getRow(0).getLastCellNum();
        System.out.println("XLS Row Count: " + rowCnt);
        
        /* first row data for column names and index */
        Map<String, Integer> colMapByName = new HashMap<String, Integer>();
        if (sheet.getRow(0).cellIterator().hasNext()) {
            for (int j = 0; j < colCnt; j++) {
                colMapByName.put(sheet.getRow(0).getCell(j).toString(), j);
            }
        }
        System.out.println("Column Headers in Excel: " + colMapByName);//shows the indexes of columns populated by traversing first row
        /* first row data */
     	
        int i = 0;
     	while (RowIter.hasNext()) {
     		System.out.println("loop iteration: " + i);
     		row = RowIter.next();
     		//Iterator<Cell> ColIter = row.cellIterator();
     		col_val[0] = sheet.getRow(i).getCell(0).toString();  //Check first column/To_Run is Y or not
     		System.out.println("SQL To be run from Excel Y/N: " + col_val[0]);
     		if ( col_val[0].equals("Y") || col_val[0].equals("y") ) {
     			col_val[1] = sheet.getRow(i).getCell(1).toString(); //ColIndex 1=B
     			col_val[2] = sheet.getRow(i).getCell(2).toString(); //ColIndex 2=C
     			System.out.println("SRC & TRG SQLs are: " + col_val[1] + col_val[2]);
     			ReturnMap.put(col_val[1], col_val[2]);
     		}
     		i++;
     	}
     	
     	return ReturnMap;
	} //xlsFunc method ends

	
	public static String[][] FetchSelTD(String sheetNm, String TCnm) throws IOException {
		String[][] retTD = new String[1000][10] ;

		String excelFilePath = "src/test/resources/eHub_Benefits_Input.xlsx";
     	FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
     	Workbook workbook = new XSSFWorkbook(inputStream);
     	Sheet sheet = workbook.getSheet(sheetNm);
     	Iterator<Row> RowIter = sheet.iterator();
     	String[] col_val = new String[100];
     	Row row = null;
     	Cell cell = null;
     	
     	int rowCnt = sheet.getLastRowNum() + 1;
        int colCnt = sheet.getRow(0).getLastCellNum();
        System.out.println("XLS Row Count: " + rowCnt);
     	
        int i = 0; //While loop counter
        int c = 0; //returnTD counter
     	while (RowIter.hasNext()) {
     		//System.out.println("loop iteration: " + i);
     		row = RowIter.next();
     		//Iterator<Cell> ColIter = row.cellIterator();
     		//col_val[0] = sheet.getRow(i).getCell(2).toString();  //Check first column/To_Run is Y or not
     		//System.out.println("SQL To be run from Excel Y/N: " + col_val[0]);
     		try {
     			if ( i > 0 && (sheet.getRow(i).getCell(0).toString().equals("Y") || sheet.getRow(i).getCell(0).toString().equals("y")) && sheet.getRow(i).getCell(1).toString().equals(TCnm) ) {
     				
     				col_val[0] = sheet.getRow(i).getCell(2).toString(); //ColIndex 2=C
     				col_val[1] = sheet.getRow(i).getCell(3).toString(); //ColIndex 3=D
     				col_val[2] = sheet.getRow(i).getCell(4).toString(); //ColIndex 4=E
     				//System.out.println("Test Data is: " + col_val[0]);
     				//System.out.println("SQ is: " + col_val[1]);
     				//System.out.println("TQ is: " + col_val[2]);
     				//returnTD.add(col_val[0]);
     				retTD[c][0] = col_val[0];
     				retTD[c][1] = col_val[1];
     				retTD[c][2] = col_val[2];
     				c++;
     				//System.out.println("assigned 2D array");
     			}
     			
     		}
     		catch (NullPointerException e) {
     			System.out.println("Looks like " + sheetNm + " sheet in the Test Input Excel has some Rows with NULL values. Please review " );
     		}
     		i++;
     	}
     	//System.out.println("returnTD >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + retTD.length);
     	return retTD;
	} //xlsFunc method ends

	
	
	public static List<String> FetchSQLTD(String sql) throws IOException {   //WIP
		List<String> returnTD = new ArrayList<String>();
		String excelFilePath = "src/test/resources/eHub_Benefits_Input.xlsx";
     	String SheetNm = "ETL_SQL_Pair"; //need to pass from StepDefinition to database.sql_exec and then here, for now Hard-coded
     	FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
     	Workbook workbook = new XSSFWorkbook(inputStream);
     	Sheet sheet = workbook.getSheet(SheetNm);
     	Iterator<Row> RowIter = sheet.iterator();
     	//String[] col_val = new String[100];
     	String TDsheet = "";
     	String TDrange = "";
     	Row row = null;
     	Cell cell = null;
     	
     	int rowCnt = sheet.getLastRowNum() + 1;
        int colCnt = sheet.getRow(0).getLastCellNum();
        System.out.println("XLS Row Count: " + rowCnt);

        int i = 0;
     	while (RowIter.hasNext()) {
     		System.out.println("loop iteration: " + i);
     		row = RowIter.next();
     		//Iterator<Cell> ColIter = row.cellIterator();
     		//col_val[0] = sheet.getRow(i).getCell(2).toString();  //Check first column/To_Run is Y or not
     		//System.out.println("SQL To be run from Excel Y/N: " + col_val[0]);
     		if ( sheet.getRow(i).getCell(3).toString().equals(sql) ) {  //If col 3=D contains the SQL, then fetch B & C
     			TDsheet = sheet.getRow(i).getCell(1).toString(); //ColIndex 1=B
     			TDrange = sheet.getRow(i).getCell(2).toString(); //ColIndex 2=C
     			System.out.println("=================================");
     			System.out.println("=================================");
     			System.out.println("=================================");
     			System.out.println("Sheet & Test Data Row Range are: " + TDsheet + TDrange);
     			break;
     		}
     		i++;
     	}
     	
     	
     	return returnTD;
	} //xlsFunc method ends
	



}     

