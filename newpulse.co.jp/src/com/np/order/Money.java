package com.np.order;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UString;
import com.np.order.biz.mast.CurrencyMgr;

@SuppressWarnings("serial")
public class Money extends Number implements Comparable<Money> {
	private static Log logger = LogFactory.getLog(Money.class);
	private String currency;
	private int value;

	/*
	 * From DB
	 */
	public Money(int value) {
		this.value = value;
	}

	public Money(String currency, int value) {
		this.value = value;
		this.currency = currency;
	}

	/*
	 * From View
	 */
	public Money(String currency, String value) {
		this.currency = currency;
		if (UString.isEmpty(value))
			value = "0";
		if (isJapaneseYen() == false) {
			int pos = value.lastIndexOf(".");
			if (pos == -1)
				value += "00";
			else {
				int digits = value.length() - pos - 1;
				if (digits == 0)
					value += "00";
				else if (digits == 1)
					value += "0";
				value = value.substring(0, pos) + value.substring(pos + 1, pos + 3);
			}
		}
		value = value.replaceAll(",", "");
		if (Character.isDigit(value.charAt(0)) == false) {
			value = value.substring(1);
		}
		try {
			this.value = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			logger.trace(value, e);
			throw e;
		}
	}

	public Money minus(Money amount) {
		if (amount != null)
			this.value -= amount.value;
		return this;
	}

	public Money multiply(Money amount) {
		if (amount != null)
			this.value *= amount.value;
		return this;
	}

	public Money multiply(int amount) {
		this.value *= amount;
		return this;
	}

	public Money add(Money amount) {
		if (amount != null)
			this.value += amount.value;
		return this;
	}

	public Money add(int amount) {
		this.value += amount;
		return this;
	}

	public Money copy() {
		return new Money(currency, value);
	}

	public Object getValue() {
		if (isJapaneseYen()) {
			return Integer.valueOf(value);
		} else {
			return Double.valueOf(value / 100.0);
		}
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();

		/*
		 * 幣種
		 */
		if (isDoller())
			str.append("$");
		else if (isChineseYen())
			str.append("R");
		else
			str.append("\\");

		DecimalFormat format = new DecimalFormat("#,###");
		if (isJapaneseYen()) {
			str.append(format.format(value));
		} else {
			str.append(format.format(value / 100));
			str.append(".");
			str.append(format.format(value % 100));
		}
		return str.toString();
	}

	@Override
	public int compareTo(Money o) {
		return this.intValue() - o.intValue();
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isDoller() {
		return CurrencyMgr.isDoller(currency);
	}

	public boolean isJapaneseYen() {
		return CurrencyMgr.isJapanese(currency);
	}

	public boolean isChineseYen() {
		return CurrencyMgr.isChinese(currency);
	}
}
