package jp.newpulse.action.user;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.mast.UserMgr;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetUserSignAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(GetUserSignAction.class);
	private Long userId;
	private String filename;
	private String contentType;
	private InputStream inputStream;
	private int bufferSize;

	@Override
	public String execute() throws Exception {
		logger.debug("GetUserSignAction : " + userId);

		@SuppressWarnings("unused")
		String contentType = "application/octet-stream";

		byte[] sign = UserMgr.getSign(userId);
		filename = "sign.gif";

		inputStream = new ByteArrayInputStream(sign);
		bufferSize = sign.length;

		return SUCCESS;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFilename() {
		return filename;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public int getBufferSize() {
		return bufferSize;
	}
}
