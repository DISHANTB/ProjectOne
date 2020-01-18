package com.ProjectOne.ProjectOne.Utilities;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.junit.Assert;

public class ExcelFactory {
	//Excel file objects
	private static XSSFWorkbook workBook = null;
	private static XSSFSheet workSheet = null;
	private static String SysPath = System.getProperty("user.dir") + "//DataSource//";;
	private static Map<String, String> TestDataMap = new HashMap<String, String>();
	
	public static void getWorkbook(String filePath){
		filePath = SysPath + filePath + ".xlsx";
		File excelfile = new File(filePath);
		if(excelfile.exists()){
			try {
				workBook = new XSSFWorkbook(new FileInputStream(excelfile));
			} catch (Exception e) {
				//Need to log the error message
				Assert.assertTrue("Excel Data file is not available at the mentioned location.", false);
				e.printStackTrace();
			}
		}	
	}
	
	public static void getSheet(String sheetName) throws Exception{
		if(workBook ==  null){
			Assert.assertTrue("Workbook is not available at the provided location", false);
			throw new Exception("Workbook is not available at the provided location");
		}else{
			if (workBook.getNumberOfSheets() != 0) {
		        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
		           if (workBook.getSheetName(i).equals(sheetName)) {
		                workSheet = workBook.getSheet(sheetName);
		                break;
		           }
		        }
		    }
		}
	}
	
	public static String getCellValue(int row, int col) throws Exception{
		String cellValue = null;
		if(workSheet == null){
			Assert.assertTrue("Sheet is not available in the provided WorkBook", false);
			throw new Exception("Sheet is not available in the provided WorkBook");
		}else{
			try{
				XSSFRow rowNumber = workSheet.getRow(row);
				cellValue = rowNumber.getCell(col).toString();
			}catch(Exception e){
				Assert.assertTrue("Requested Cell number may have some issue. Please check provided row and column count", false);
				e.printStackTrace();
			}			
		}
		return cellValue;
	}
	
	public static void getTestDataLoaded(String sheetName,String TestIdentifier){
		int DataRow = 0;
		try{
			getSheet(sheetName);
			int rowCount = workSheet.getLastRowNum();
			int colCount = workSheet.getRow(rowCount).getLastCellNum();
			for(int row = 1; row <= rowCount; row++){
				if(getCellValue(row, 0).equals(TestIdentifier)){
					DataRow = row;
					break;
				}  
			}
			if(DataRow != 0){
				for(int col = 0; col < colCount; col++){
					TestDataMap.put(getCellValue(0,col), getCellValue(DataRow,col));
				}
			}else{
				Assert.assertTrue("No Test data available for the provided test identifier", false);
				throw new Exception();
			}			
		}catch(Exception e){
			Assert.assertTrue("Facing some error while loading test data for the test script.", false);
			e.printStackTrace();
		}
		
	}
	
	public static String getValueOf(String keyValue) throws Exception{
		String value = "";
		if(TestDataMap.containsKey(keyValue)){
			value = TestDataMap.get(keyValue);
		}else{
			Assert.assertFalse("Column is not present for the selected sheet.", false);
			throw new Exception("Column is not present for the selected sheet.");
		}
		return value;
	}
	

}


