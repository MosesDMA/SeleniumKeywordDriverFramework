package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Constants;
import org.apache.commons.compress.archivers.zip.ZipFile;
import executionEngine.DriverScript;

public class ExcelUtil {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	
	//�����ļ�������
	public static void setExcelFile(String Path) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			Log.error("Class Util | Method setExcelFile | Exception desc :" +e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	//��ȡ�ļ���Ԫ������
	public static String getCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			Log.error("Class Utils | Method getCellData | Exception desc: " + e.getMessage());
			DriverScript.bResult = false;
			return "";
		}
	}
	
	//�õ�������������
	public static int getRowCount(String SheetName) throws Exception {
		int number = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			number = ExcelWSheet.getLastRowNum()+1;
		} catch (Exception e) {
			Log.error("Class Util | Method getRowCount | Exception desc:" + e.getMessage());
			DriverScript.bResult = false;
		}
		return number;
	}
	
	//�õ������������к�,sTestCaseName����������
	public static int getRowContains(String sTestCaseName, int colNum, String SheetName) throws Exception {
		int iRowNum = 1;
		try {
			//ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelUtil.getRowCount(SheetName);
			for(;iRowNum<rowCount;iRowNum++) {
				if(ExcelUtil.getCellData(iRowNum, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
					break;	
				}
			}
		} catch (Exception e) {
			Log.error("Class Util | Method getRowContains | Exception desc:" + e.getMessage());
			DriverScript.bResult = false;
		}
		return iRowNum;
	}
	
	//����һ�����������ж��ٸ�����
	public static int getTestStepsCount(String sheetName, String sTestCaseID, int iTestCaseStart) throws Exception {
		int number;
		try {
			for(int i=iTestCaseStart;i<ExcelUtil.getRowCount(sheetName);i++) {
				if(!sTestCaseID.equals(ExcelUtil.getCellData(i, Constants.Col_TestCaseID, sheetName))) {
					number = i;
					return number;
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			number = ExcelWSheet.getLastRowNum()+1;
			return number;
		} catch (Exception e) {
			Log.error("Class Util | Method getTestStepsCount | Exception desc:" + e.getMessage());
			DriverScript.bResult = false;
			return 0;
		}
	}
	
	//����һ������Ԫ��д���ݵķ�������Ҫ������д���pass����fail
	public static void setCellData(String Result, int RowNum, int ColNum, String SheetName) throws Exception {
		
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum);
			if(Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			}else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + Constants.Path_TestData);
			ExcelWBook.write(fileOut);
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir")+Constants.Path_TestData));
		} catch (FileNotFoundException e) {
			DriverScript.bResult = false;
		} catch (IOException e) {
			DriverScript.bResult = false;
		}
	}
}
