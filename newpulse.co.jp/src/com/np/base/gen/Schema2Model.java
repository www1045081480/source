package com.np.base.gen;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.np.base.orm.ModelMapper;
import com.np.base.utils.UExcel;
import com.np.base.utils.UFile;
import com.np.base.utils.USchema;
import com.np.base.utils.UString;
import com.np.base.utils.UTemplate;

public class Schema2Model {
	private static String TABLE_NAME_TITLE = "テーブル";

	private static String TABLE_LOGIC_NAME = "論理名：";

	private static String TABLE_PHYSICS_NAME = "物理名：";

	private static String SCHEMA_NAME_TITLE = "スキマー";

	private static enum COLUMN_ATTRIBUTE {
		SEQ, カラム, ラベル, 型, 桁数, ＰＫ, 必須, 参照テーブル, 参照カラム
	};

	public static String schemaFilename = "OrderSchema.xlsx";
	public static String outputRoot = "output";
	public static String modelPakage = "com.np.order.models";

	public static void main(String... args) throws Exception {
		List<SchemaTable> tables = importSchema(new File("./schema/" + schemaFilename));

		for (SchemaTable table : tables) {
			for (SchemaColumn col : table.getColumns()) {
				if (col.isForeignKey() == false)
					continue;
				for (SchemaTable refTable : tables) {
					if (col.getRefTable().equals(refTable.getViewName()) == false)
						continue;
					col.setRefTable(refTable.getName());
					for (SchemaColumn refCol : refTable.getColumns()) {
						if (col.getRefColumn().equals(refCol.getViewName()) == false)
							continue;
						col.setRefColumn(refCol.getName());
						break;
					}
					break;
				}
			}
		}

		File javaSrcPath = new File(outputRoot + "/java", modelPakage.replace('.', '/'));
		UFile.validate(javaSrcPath);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("timestamp", new Date().toString());
		context.put("package", modelPakage);
		for (SchemaTable table : tables) {
			String modelName = USchema.toJavaName(table.getName());
			File modelSource = new File(javaSrcPath, modelName + ".java");
			// System.out.println(modelSource);

			context.put("table", table);
			context.put("columns", table.getColumns());

			UTemplate.generate("./templates/tableModel.vm", context, modelSource);

			System.out.println(modelName);
			// System.out.println("DELETE FROM " + table.getName() + ";");
		}

		File sqlFile = new File(outputRoot + "/sql", "createTables.sql");
		context.put("tables", tables);
		UTemplate.generate("./templates/tableSQL.vm", context, sqlFile);
	}

	public static List<SchemaTable> importSchema(File xlsFile) throws Exception {
		InputStream xls = new FileInputStream(xlsFile);
		try {
			return importSchema(xls);
		} finally {
			xls.close();
		}
	}

	public static List<SchemaTable> importSchema(InputStream xls) throws Exception {
		Workbook wb = new XSSFWorkbook(xls);

		List<SchemaTable> tables = new ArrayList<SchemaTable>();
		int numberOfSheets = wb.getNumberOfSheets();
		for (int idxOfSheet = 0; idxOfSheet < numberOfSheets; idxOfSheet++) {
			Sheet sheet = wb.getSheetAt(idxOfSheet);

			// System.out.println("=================");
			// System.out.println("Sheet : " + sheet.getSheetName());

			// TABLE NAME
			/*
			 * テーブル論理名称
			 */
			SchemaTable table = null;
			for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				if (row == null)
					continue;
				Cell cell = row.getCell(1);
				if (cell == null)
					continue;
				String typeOfRow = UExcel.getStringValue(cell);
				// System.out.println("Row[" + r + "] typeOfRow: ." + typeOfRow
				// + ".");
				if (TABLE_NAME_TITLE.equals(typeOfRow)) {
					// System.out.println("テーブル名称 .");
					/*
					 * テーブル名称
					 */
					table = getTableInfo(sheet, r);
					tables.add(table);
				} else if (SCHEMA_NAME_TITLE.equals(typeOfRow)) {
					// System.out.println("カラム名称 .");
					/*
					 * カラム名称
					 */
					setColumnsInfo(sheet, r, table);
				}
			}

		}
		wb.close();
		return tables;
	}

	public static SchemaTable getTableInfo(Sheet sheet, int numberOfRow) {
		SchemaTable table = new SchemaTable();
		for (int i = 1; i <= 2; i++) {
			Row row = sheet.getRow(numberOfRow + i);
			String type = UExcel.getStringValue(row.getCell(1));
			String value = UExcel.getStringValue(row.getCell(2));
			if (type.equals(TABLE_LOGIC_NAME))
				table.setViewName(value);
			else if (type.equals(TABLE_PHYSICS_NAME)) {
				table.setName(value);
				table.setModelName(USchema.toJavaName(table.getName()));
			}
			// System.out.println(type + "[" + type.equals(TABLE_LOGIC_NAME) +
			// "]:" + value);
		}
		return table;
	}

	public static void setColumnsInfo(Sheet sheet, int numberOfRow, SchemaTable table) {
		if (table == null)
			return;

		int[] indexOfColumns = new int[COLUMN_ATTRIBUTE.values().length];
		{
			Row row = sheet.getRow(numberOfRow + 1);
			for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				String value = UExcel.getStringValue(cell);
				// System.out.print(value + " =>");
				if (UString.isEmpty(value))
					continue;
				COLUMN_ATTRIBUTE v = COLUMN_ATTRIBUTE.valueOf(value);
				for (int i = 0; i < indexOfColumns.length; i++) {
					if (v == COLUMN_ATTRIBUTE.values()[i]) {
						indexOfColumns[i] = c;
						// System.out.print(indexOfColumns[i]);
						break;
					}
				}
				// System.out.println();
			}
		}

		boolean hasRegTime = false;
		boolean hasUpdTime = false;
		// System.out.println();
		// System.out.println(table);
		for (int r = numberOfRow + 2; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			SchemaColumn column = new SchemaColumn();
			for (int i = 0; i < indexOfColumns.length; i++) {
				Cell cell = row.getCell(indexOfColumns[i]);
				String value = UExcel.getStringValue(cell);
				if (UString.isEmpty(value))
					continue;

				System.out.print(COLUMN_ATTRIBUTE.values()[i] + "=" + value);
				if (i == COLUMN_ATTRIBUTE.SEQ.ordinal())
					column.setSeq(Integer.valueOf(value));
				else if (i == COLUMN_ATTRIBUTE.カラム.ordinal()) {
					column.setName(value);
				} else if (i == COLUMN_ATTRIBUTE.ラベル.ordinal())
					column.setViewName(value);
				else if (i == COLUMN_ATTRIBUTE.型.ordinal()) {
					column.setType(value);
				} else if (i == COLUMN_ATTRIBUTE.桁数.ordinal()) {
					if (UString.isEmpty(value))
						column.setSize(-1);
					else
						column.setSize(Integer.valueOf(value));
				} else if (i == COLUMN_ATTRIBUTE.ＰＫ.ordinal()) {
					if (UString.isEmpty(value))
						column.setPrimaryKey(false);
					else
						column.setPrimaryKey(value.equals("○"));
				} else if (i == COLUMN_ATTRIBUTE.必須.ordinal()) {
					if (UString.isEmpty(value))
						column.setMust(false);
					else
						column.setMust(value.equals("○"));
				} else if (i == COLUMN_ATTRIBUTE.参照テーブル.ordinal()) {
					column.setRefTable(value);
				} else if (i == COLUMN_ATTRIBUTE.参照カラム.ordinal()) {
					column.setRefColumn(value);
				}
			}

			if (UString.isEmpty(column.getName()))
				continue;

			table.addColumn(column);
			// System.out.println();

			if (column.getModelName().equals(ModelMapper.RegTime))
				hasRegTime = true;
			if (column.getModelName().equals(ModelMapper.UpdTime))
				hasUpdTime = true;
		}

		if (hasRegTime == false) {
			SchemaColumn column = new SchemaColumn();
			column.setSeq(table.getColumns().size() + 1);
			column.setName(ModelMapper.RegTime);
			column.setType("LONG");
			table.addColumn(column);
		}

		if (hasUpdTime == false) {
			SchemaColumn column = new SchemaColumn();
			column.setSeq(table.getColumns().size() + 1);
			column.setName(ModelMapper.UpdTime);
			column.setType("LONG");
			table.addColumn(column);
		}

		System.out.println(table);
	}

}
