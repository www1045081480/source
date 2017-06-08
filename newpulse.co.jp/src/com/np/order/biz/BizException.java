package com.np.order.biz;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class BizException extends RuntimeException {
	private String erCode;
	private String errMsg;
	private Map<Object, Object> errTarget;

	public BizException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;
		this.errTarget = new HashMap<Object, Object>();
	}

	public BizException(String erCode, String errMsg) {
		super(erCode + ":" + errMsg);
		this.erCode = erCode;
		this.errMsg = errMsg;
		this.errTarget = new HashMap<Object, Object>();
	}

	@Override
	public String getMessage() {
		return (erCode + ":" + errMsg);
	}

	public String getErCode() {
		return erCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public Map<Object, Object> getErrTarget() {
		return errTarget;
	}

}
