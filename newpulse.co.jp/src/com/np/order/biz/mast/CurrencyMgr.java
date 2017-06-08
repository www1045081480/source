package com.np.order.biz.mast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UString;
import com.np.order.objects.Currency;

public class CurrencyMgr {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(CurrencyMgr.class);
	private static Currency[] currencies = { Currency.Japan, Currency.US, Currency.China };

	public static Currency[] getCurrencies() {
		return currencies;
	}

	public static boolean isJapanese(String name) {
		if (UString.isEmpty(name)) {
			// logger.trace("currency is NULL", new IllegalArgumentException());
			return true;
		}
		return Currency.Japan.getCode().equalsIgnoreCase(name);
	}

	public static boolean isDoller(String name) {
		return Currency.US.getCode().equalsIgnoreCase(name);
	}

	public static boolean isChinese(String name) {
		return Currency.China.getCode().equalsIgnoreCase(name);
	}
}
