package com.np.order.objects;

public class SellOrderSummary {
	private SellOrderTotal japanese;
	private SellOrderTotal doller;
	private SellOrderTotal chinese;

	public SellOrderSummary() {
		japanese = new SellOrderTotal(Currency.Japan.getCode());
		doller = new SellOrderTotal(Currency.US.getCode());
		chinese = new SellOrderTotal(Currency.China.getCode());
	}

	public void sunm(SellOrderInfo obj) {
		SellOrderTotal target;
		if (obj.getRequireAmount().isDoller())
			target = doller;
		else if (obj.getRequireAmount().isDoller())
			target = chinese;
		else
			target = japanese;

		target.sum(obj);
	}

	public SellOrderTotal getJapanese() {
		return japanese;
	}

	public void setJapanese(SellOrderTotal japanese) {
		this.japanese = japanese;
	}

	public SellOrderTotal getDoller() {
		return doller;
	}

	public void setDoller(SellOrderTotal doller) {
		this.doller = doller;
	}

	public SellOrderTotal getChinese() {
		return chinese;
	}

	public void setChinese(SellOrderTotal chinese) {
		this.chinese = chinese;
	}

}
