package jp.newpulse.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

/*
 * strits.xml設定例：
 * 
 * 		<action name="XXXXreport" class="jp.newpulse.action.XXXXX">
             <result name="success" type="stream">
            <param name="contentType">application/vnd.ms-excel</param>
            <param name="inputName">inputStream</param>
            <param name="contentDisposition">attachment; filename="excelExport.xls"</param>
            <param name="bufferSize">bufferSize</param>
             </result>
		</action>
 */
@SuppressWarnings("serial")
public abstract class BaseExcelAction extends ActionSupport {
	private InputStream inputStream;
	private int bufferSize;

	public String response(String template, String sheetName, Map<String, Object> context) throws Exception {
		byte[] data = ExcelReport.createReport(template, sheetName, context);
		inputStream = new ByteArrayInputStream(data);
		bufferSize = data.length;
		return SUCCESS;
	}

	public String response(String template, Map<String, Object> context) throws Exception {
		byte[] data = ExcelReport.createReport(template, context);
		inputStream = new ByteArrayInputStream(data);
		bufferSize = data.length;
		return response(template, null, context);
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public int getBufferSize() {
		return bufferSize;
	}

}
