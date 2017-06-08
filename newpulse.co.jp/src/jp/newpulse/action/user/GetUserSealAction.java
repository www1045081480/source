package jp.newpulse.action.user;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.np.order.biz.mast.UserMgr;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GetUserSealAction extends ActionSupport {
	private Long userId;
	private String filename;
	private String contentType;
	private InputStream inputStream;
	private int bufferSize;

	@Override
	public String execute() throws Exception {
		byte[] seal = UserMgr.getSeal(userId);
		filename = "seal.gif";

		inputStream = new ByteArrayInputStream(seal);
		bufferSize = seal.length;

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