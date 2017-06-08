package com.np.base.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;

public class UExcel {

	public static String getComment(Cell cell) {
		if (cell == null)
			return null;
		Comment comment = cell.getCellComment();
		if (comment == null)
			return null;
		return comment.getString().getString().trim();
	}

	public static String getStringValue(Cell cell) {
		if (cell == null)
			return null;

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String value = cell.getRichStringCellValue().getString();
			value = value.trim();
			return UString.isEmpty(value) ? null : value;
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			String value = Double.toString(cell.getNumericCellValue());
			if (value.endsWith(".0"))
				value = value.substring(0, value.length() - 2);
			return value;
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			double x = cell.getNumericCellValue();
			if (x == Double.NaN) {
				return cell.getRichStringCellValue().getString();
			} else {
				String value = Double.toString(x);
				if (value.endsWith(".0"))
					value = value.substring(0, value.length() - 2);
				return value;
			}
		} else
			return null;
	}

	public static Object getValue(Cell cell) {
		if (cell == null)
			return null;
		if (cell.getCellType() == Cell.CELL_TYPE_STRING)
			return cell.getRichStringCellValue().getString();
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			return cell.getNumericCellValue();
		else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			double x = cell.getNumericCellValue();
			if (x == Double.NaN) {
				return cell.getRichStringCellValue().getString();
			} else {
				return x;
			}
		} else
			return null;
	}

	public static int getIntValue(Cell cell) {
		if (cell == null)
			return 0;
		if (cell.getCellType() == Cell.CELL_TYPE_STRING)
			return (int) toNumberic(cell.getRichStringCellValue().getString());
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			return (int) cell.getNumericCellValue();
		else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			double x = cell.getNumericCellValue();
			if (x == Double.NaN) {
				return (int) toNumberic(cell.getRichStringCellValue().getString());
			} else {
				return (int) x;
			}
		} else
			return 0;
	}

	private static double toNumberic(String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
