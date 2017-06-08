package jp.newpulse.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.np.base.reflect.PojoReflector;
import com.np.base.utils.UDate;
import com.np.base.utils.UDebugger;
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.biz.invoice.SellOrderAmountMgr;

import jp.newpulse.entity.Users;

public class ExcelReport {
	private static Log logger = LogFactory.getLog(SellOrderAmountMgr.class);

	private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	private static String templatePath = "template/reports/";
	private String xlsFilename;
	private String xmlFilename;
	private String sheetName;
	private InputStream xls;
	private Workbook wb;
	private Sheet outputSheet;
	private Map<String, Object> context;

	public static void main(String... args) throws Exception {
		test("noConfirms.xml", null);
		test("noConfirms1.xml", null);
		test("noConfirms.xml", "未承認見積書");
		test("noConfirms.xml", "sheet1");
	}

	private static int no = 1;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void test(String template, String sheetName) throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();
		List list = EstmationSheetMgr.fetchNotApproveList();
		list.addAll(list);
		list.addAll(list);
		context.put("sheets", list);

		Users user = new Users();
		user.setJpName("新安　太郎");
		context.put("user", user);
		context.put("createDate", UDate.getDate());

		byte[] data = createReport(template, sheetName, context);
		FileOutputStream out = new FileOutputStream("logs/test-" + (no++) + ".xlsx");
		out.write(data);
		out.close();
	}

	public ExcelReport() {
		this(null);
	}

	public ExcelReport(String sheetName) {
		this.sheetName = sheetName;
	}

	public static byte[] createReport(String template, Map<String, Object> context) throws Exception {
		return createReport(template, null, context);
	}

	public static byte[] createReport(String template, String sheetName, Map<String, Object> context) {
		if (template.endsWith(".xml") == false)
			throw new IllegalArgumentException(template);

		try {
			ExcelReport report = new ExcelReport(sheetName);
			report.context = context;
			report.xmlFilename = templatePath + template;
			report.evaluate(report.xmlFilename);

			// String xlsxFile = template.substring(0,
			// template.lastIndexOf(".")) +
			// ".xlsx";
			// File sxlTemplate = new File(SessionMgr.getWebRoot(),
			// "templates/reports/" + xlsxFile);

			report.xls.close();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			report.wb.write(out);
			out.close();
			return out.toByteArray();
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
			throw new RuntimeException(e);
		}
	}

	private void readTemplate() throws Exception {
		File file = new File(SessionMgr.getWebRoot(), templatePath + xlsFilename);
		logger.debug("Excel Template :" + file);
		xls = new FileInputStream(file);
		wb = new XSSFWorkbook(xls);
		if (UString.isEmpty(sheetName))
			outputSheet = wb.getSheetAt(0);
		else {
			for (int i = wb.getNumberOfSheets() - 1; i >= 0; i--) {
				Sheet sheet = wb.getSheetAt(i);
				if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
					outputSheet = sheet;
				} else {
					wb.removeSheetAt(i);
				}
			}
		}
		logger.debug("Sheet: " + sheetName + ":" + outputSheet);
	}

	private void evaluateCell(ExcelCell excelCell) throws Exception {
		if (excelCell == null)
			return;
		String valuePattern = excelCell.value;
		if (valuePattern == null)
			return;

		int rowIndex = Integer.valueOf(excelCell.attrs.get("row"));
		rowIndex--;
		char colLablel = excelCell.attrs.get("col").charAt(0);
		int colIndex = Integer.valueOf(Character.toUpperCase(colLablel) - 'A');

		Row row = outputSheet.getRow(rowIndex);
		if (row == null)
			row = outputSheet.createRow(rowIndex);
		Cell cell = row.getCell(colIndex);
		if (cell == null)
			cell = row.createCell(colIndex);
		Object value = getValue(valuePattern);

		setValue(cell, value);

	}

	private void setValue(Cell cell, Object value) throws Exception {
		if (value != null) {
			/*
			 * yyyy/m/d;@ General
			 */
			if (cell.getCellStyle().getDataFormat() == 177) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				// シリアル値変換
				value = format.parse(String.valueOf(value));
				String formatStr = cell.getCellStyle().getDataFormatString();
				int pos = formatStr.indexOf(";");
				if (pos != -1)
					formatStr = formatStr.substring(0, pos);
				CellFormat cf = CellFormat.getInstance(formatStr);
				value = cf.apply(value).text;
				// System.out.println("CellFormat[" + formatStr + "] : " +
				// value);
			}

			if (Double.class.isInstance(value))
				cell.setCellValue(Double.class.cast(value).doubleValue());
			else if (Number.class.isInstance(value))
				cell.setCellValue(Number.class.cast(value).longValue());
			else
				cell.setCellValue(String.valueOf(value));
		}
	}

	private Object getValue(String valuePattern) throws Exception {
		if (valuePattern.startsWith("$")) {
			valuePattern = valuePattern.substring(1);
			int pos = valuePattern.indexOf(".");
			if (pos != -1) {
				String variable = valuePattern.substring(0, pos);
				String value = valuePattern.substring(pos + 1);

				if (context.containsKey(variable)) {
					Object target = context.get(variable);
					if (target != null) {
						Method getter = PojoReflector.getPropertyGetter(target.getClass(), value);
						if (getter != null) {
							Object result = getter.invoke(target, PojoReflector.NULL_ARGS);
							return result;
						} else {
							logger.debug("[ERROR] Cannot found Property: " + value);
						}
					} else {
						logger.debug("[ERROR] Cannot found Object: " + variable);
					}
				}
			} else {
				String variable = valuePattern;
				if (context.containsKey(variable)) {
					return context.get(variable);
				}
			}
		}
		return valuePattern;
	}

	private void evaluate(String xmlFile) throws Exception {
		File file = new File(SessionMgr.getWebRoot(), xmlFile);
		logger.debug("Excel : " + file.getCanonicalPath());
		Document document = parse(file);
		// get root element
		Element rootElement = document.getDocumentElement();
		evaluate(rootElement);
	}

	private void evaluate(Node node) throws Exception {
		ExcelCell excelCell = parse(node);
		if (excelCell == null)
			return;

		if (excelCell.tagName.equals("report")) {
			xlsFilename = excelCell.attrs.get("template");
			readTemplate();

			NodeList nodes = Element.class.cast(node).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				evaluate(node);
			}

			return;
		} else if (excelCell.tagName.equals("cells")) {
			Element element = Element.class.cast(node);
			evaluateCells(element.getChildNodes());
			return;
		} else if (excelCell.tagName.equals("list")) {
			Element element = Element.class.cast(node);
			evaluateList(excelCell, element.getChildNodes());
			return;
		} else if (excelCell.tagName.equals("sheet")) {
			String name = excelCell.attrs.get("name");
			if (UString.notEmpty(this.sheetName) && this.sheetName.equalsIgnoreCase(name) == false) {
				/*
				 * skip this sheet
				 */
			} else {
				NodeList nodes = Element.class.cast(node).getChildNodes();
				for (int i = 0; i < nodes.getLength(); i++) {
					node = nodes.item(i);
					evaluate(node);
				}

			}
			return;
		}

	}

	private void evaluateCells(NodeList nodes) throws Exception {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			ExcelCell excelCell = parse(node);
			// System.out.println(excelCell);
			evaluateCell(excelCell);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void evaluateList(ExcelCell parent, NodeList nodes) throws Exception {
		int beginRow = Integer.valueOf(parent.attrs.get("begin_row"));

		String listName = parent.attrs.get("name");
		List list = (List) context.get(listName);
		if (UList.isEmpty(list))
			return;

		Class type = UList.first(list).getClass();
		int beginCol = -1;
		List<Method> getters = new ArrayList<Method>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			ExcelCell excelCell = parse(node);
			if (excelCell == null)
				continue;

			if (beginCol == -1 && excelCell.attrs.containsKey("col")) {
				beginCol = excelCell.attrs.get("col").charAt(0) - 'A';
			}
			String value = excelCell.value;
			if (value == null) {
				getters.add(null);
				continue;
			}
			if (value.startsWith("$") == false) {
				getters.add(null);
				continue;
			}
			int pos = value.indexOf(".");
			if (pos == -1) {
				getters.add(null);
				continue;
			}
			value = value.substring(pos + 1);
			Method getter = PojoReflector.getPropertyGetter(type, value);
			getters.add(getter);
		}

		int rowIndex = beginRow - 1;
		Row prototype = outputSheet.getRow(rowIndex);
		for (Object values : list) {
			Row row = outputSheet.getRow(rowIndex);
			if (row == null)
				row = outputSheet.createRow(rowIndex);

			int colIndex = beginCol - 1;
			for (Method getter : getters) {
				colIndex++;
				if (getter == null)
					continue;

				Object value = getter.invoke(values, PojoReflector.NULL_ARGS);

				Cell cell = row.getCell(colIndex);
				if (cell == null)
					cell = row.createCell(colIndex);

				/*
				 * TODO: copy styles from
				 */
				if (rowIndex >= beginRow) {
					Cell prototypeCell = prototype.getCell(colIndex);
					CellStyle style = prototypeCell.getCellStyle();
					cell.setCellStyle(style);

					cell.setCellType(prototypeCell.getCellType());
				}

				setValue(cell, value);
			}
			rowIndex++;
		}

	}

	private ExcelCell parse(Node node) {
		if (node.getNodeType() != Node.ELEMENT_NODE)
			return null;

		Element element = Element.class.cast(node);
		ExcelCell excelCell = new ExcelCell();
		excelCell.tagName = element.getTagName();

		NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			excelCell.attrs.put(attr.getNodeName(), attr.getNodeValue());
		}

		if (excelCell.tagName.equals("cell")) {

		}

		// traverse child elements
		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) {
				Text text = Text.class.cast(node);
				String value = text.getNodeValue().trim();
				excelCell.value = value;
				break;
			}
		}

		return excelCell;
	}

	// Load and parse XML file into DOM
	private Document parse(File xmlFile) {
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an XML file into a DOM tree
			document = builder.parse(xmlFile);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	private static class ExcelCell {
		String tagName;
		String value;
		Map<String, String> attrs = new HashMap<String, String>();

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(tagName);
			sb.append("=");
			sb.append(value);
			sb.append("\n");
			if (attrs.isEmpty() == false) {
				sb.append("\t");
				sb.append(attrs);
				sb.append("\n");
			}
			return sb.toString();
		}
	}
}
