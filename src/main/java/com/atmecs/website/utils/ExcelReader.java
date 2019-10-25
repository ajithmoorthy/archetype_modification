package com.atmecs.website.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *  This Excel reader class have the method to read the excel file using the apache
 * poi 
 */
public class ExcelReader {
	/**
	 * This excelDataProviderArray method take input as a
	 * @param path. 
	 * using that path to read the value from the .xlsx file 
	 * store the reading values in array 
	 * and @return excelarray as two dimensional array object.
	 * @throws IOException
	 */
	public String[][] excelDataProviderArray(String path) throws IOException {
		File excelfile = new File(path);
		FileInputStream fileInput = new FileInputStream(excelfile);
		XSSFWorkbook excelbook = new XSSFWorkbook(fileInput);
		Sheet excelsheet = excelbook.getSheetAt(0);
		int rowCount=rowCount(excelsheet);
		int columnCount=columnCount(excelsheet);
		String excelarray[][] = new String[rowCount][columnCount];
		int count = 0;
		for (Row row : excelsheet) {
			int count1 = 0;
			for (Cell cell : row) {
				String data = cell.toString();
				excelarray[count][count1] = data;
				count1++;
			}
			count++;
		}
		return excelarray;
	}
	/**
	 * This excelDataProviderArray method take input as a
	 * @param path and
	 * @param sheetIndex
	 * using that path and sheet index to read the values from the .xlsx file sheet what ever we want.
	 * store the reading values in array 
	 * and @return excelarray as two dimensional array object.
	 * @throws IOException
	 */
	public String[][] excelDataProviderArray(String path, int sheetIndex) throws IOException {
		File excelfile = new File(path);
		FileInputStream fileInput = new FileInputStream(excelfile);
		XSSFWorkbook excelbook = new XSSFWorkbook(fileInput);
		Sheet excelsheet = excelbook.getSheetAt(sheetIndex);
		int rowCount=rowCount(excelsheet);
		int columnCount=columnCount(excelsheet);
		String array[][] = new String[rowCount][columnCount];
		int count = 0;
		for (Row row : excelsheet) {
			int count1 = 0;
			for (Cell cell : row) 
			{
				String Data = cell.toString();
				array[count][count1] = Data;
				count1++;
			}
			count++;
		}
		return array;
	}
	/**
	 * This excelDataProviderArray method take input as a
	 * @param path ,
	 * @param sheetIndex, and
	 * @param columnname
	 * using that path, sheet index, and column index to read the values from the .xlsx file sheet what ever we want.
	 * store the reading values in array 
	 *  and finally @return the array.
	 * @throws IOException
	 */
	public String[] excelDataProviderArray(String path, int sheetIndex,String columnname) throws IOException {
		File file = new File(path);
		FileInputStream fileInput = new FileInputStream(file);
		XSSFWorkbook excelbook = new XSSFWorkbook(fileInput);
		Sheet excelsheet = excelbook.getSheetAt(sheetIndex);
		int rowCount=rowCount(excelsheet);
		String array[] = new String[rowCount-1];
		int count = 0,
				matchcolumn=0;
		for (Row row : excelsheet) 
		{
			if(row.getRowNum()==0) 
			{
				for (Cell cell : row) 
				{
					if(row.getRowNum()==0 && cell.toString().contentEquals(columnname)) {
						matchcolumn=cell.getColumnIndex();
					}
				}
			}
			else {
				for (Cell cell : row) 
				{
					if(matchcolumn==cell.getColumnIndex() ) {
						String Data = cell.toString();
						array[count]= Data;
					}
				}
				count++;
			}

		}
		return array;
	}
	/**
	 * This method take the below parameter
	 * @param sheet
	 * and find the row count of the excel sheet.
	 * finally  @return the count as integer.
	 */
	public int rowCount(Sheet excelsheet) {
		int rowCount = excelsheet.getLastRowNum();
		rowCount += 1;
		return rowCount;
	}
	/**
	 * This method take the below parameter
	 * @param sheet
	 * and find the column count of the excel sheet.
	 * finally  @return the count as integer.
	 */
	public int columnCount(Sheet excelsheet) {
		int columnCount = excelsheet.getRow(0).getLastCellNum();
		return columnCount;
	}
}
