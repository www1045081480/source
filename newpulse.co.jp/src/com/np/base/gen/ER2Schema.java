package com.np.base.gen;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.np.base.utils.UExcel;
import com.np.base.utils.UString;

public class ER2Schema {

	public static void main(String... args) throws Exception {
		parse(new File("schema/提案書_20150722.xls"));
	}

	public static void parse(File file) throws Exception {
		InputStream in = new FileInputStream(file);

		Workbook wb;
		if (file.getName().endsWith(".xlsx"))
			wb = new XSSFWorkbook(in);
		else
			wb = new HSSFWorkbook(in);

		for (int s = 0; s < wb.getNumberOfSheets(); s++) {
			Sheet sheet = wb.getSheetAt(s);
			if (sheet.getSheetName().equals("DB設計") == false)
				continue;

			for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				if (row == null)
					continue;

				for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
					Cell cell = row.getCell(c);
					if (cell == null)
						continue;

					CellStyle style = cell.getCellStyle();
					Color color = style.getFillForegroundColorColor();
					if (color == null)
						continue;

					int rgb = 0;
					if (HSSFColor.class.isInstance(color)) {
						HSSFColor hssfColor = HSSFColor.class.cast(color);

						rgb += hssfColor.getTriplet()[0] & 0x0FF;
						rgb <<= 8;
						rgb += hssfColor.getTriplet()[1] & 0x0FF;
						rgb <<= 8;
						rgb += hssfColor.getTriplet()[2] & 0x0FF;
					} else if (XSSFColor.class.isInstance(color)) {
						XSSFColor xssfColor = XSSFColor.class.cast(color);

						rgb += xssfColor.getRgb()[0] & 0x0FF;
						rgb <<= 8;
						rgb += xssfColor.getRgb()[1] & 0x0FF;
						rgb <<= 8;
						rgb += xssfColor.getRgb()[2] & 0x0FF;
					}

					if (style.getBorderTop() == 0)
						continue;

					/*
					 * Yellow
					 */
					if (rgb != 0x0FFFF00)
						continue;

					if (style.getFillForegroundColor() == IndexedColors.AUTOMATIC.getIndex())
						continue;

					parse(sheet, r, c);
				}
			}
		}
		wb.close();
		in.close();
	}

	public static void parse(Sheet sheet, int startRowIndex, int cellIndex) throws Exception {
		String tableName = UExcel.getStringValue(sheet.getRow(startRowIndex).getCell(cellIndex));
		if (UString.isEmpty(tableName))
			return;

		System.out.println("tableName = " + tableName);
		for (int r = startRowIndex + 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null)
				break;
			Cell cell = row.getCell(cellIndex);
			if (cell == null)
				break;

			String columnName = UExcel.getStringValue(cell);
			if (UString.isEmpty(columnName))
				break;

			System.out.println("   columnName = " + columnName);
		}
	}
}
