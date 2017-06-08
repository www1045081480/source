package com.np.order.objects;

public enum Currency {
	Japan("JP", "日本円"), US("US", "ドル"), China("CN", "人民元");

	private String code;
	private String title;

	private Currency(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
