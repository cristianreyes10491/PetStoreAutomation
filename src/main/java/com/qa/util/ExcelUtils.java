package com.qa.util;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.BOOLEAN;
import static java.sql.Types.NUMERIC;
import static org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType.FORMULA;
import static org.apache.xmlbeans.impl.piccolo.xml.Piccolo.STRING;

//class used to connect excel data to the api request
public class ExcelUtils {
	private Workbook workbook;
	private Sheet sheet;

	public ExcelUtils(String filePath, String sheetName) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fileInputStream);
		sheet = workbook.getSheet(sheetName);
	}

	public List<Map<String, String>> getAllData() {
		List<Map<String, String>> dataList = new ArrayList<>();
		int rowCount = sheet.getPhysicalNumberOfRows();
		Row headerRow = sheet.getRow(0);

		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			Map<String, String> data = new HashMap<>();
			for (Cell cell : row) {
				String cellValue = getCellValue(cell);
				data.put(headerRow.getCell(cell.getColumnIndex()).getStringCellValue(), cellValue);
			}
			dataList.add(data);
		}
		return dataList;
	}

	private String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().toString();
				} else {
					return String.valueOf(cell.getNumericCellValue());
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			default:
				return "";
		}
	}

	public void close() throws IOException {
		//workbook.close;
	}

}


