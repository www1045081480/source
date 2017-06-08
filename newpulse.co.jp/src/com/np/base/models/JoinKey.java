package com.np.base.models;

public class JoinKey {
	private String leftKey;
	private String rightKey;

	public JoinKey() {
	}

	public JoinKey(String key) {
		this.leftKey = key;
		this.rightKey = key;
	}

	public String getLeftKey() {
		return leftKey;
	}

	public void setLeftKey(String leftKey) {
		this.leftKey = leftKey;
	}

	public String getRightKey() {
		return rightKey;
	}

	public void setRightKey(String rightKey) {
		this.rightKey = rightKey;
	}

}
