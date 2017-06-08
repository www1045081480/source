package com.np.order.biz;

@SuppressWarnings("serial")
public class IllegalPriorityEception extends BizException {

	public IllegalPriorityEception() {
		super("Illegal Priority");
	}
}
