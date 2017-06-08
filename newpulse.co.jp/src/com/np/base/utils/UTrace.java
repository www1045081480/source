package com.np.base.utils;

import org.apache.log4j.Logger;

public class UTrace {
	private static final Logger logger = Logger.getLogger(UTrace.class);

	public static void debug(Object o) {
		if (Throwable.class.isInstance(o)) {
			logger.debug(UDebugger.trace(Throwable.class.cast(o)));
		} else {
			logger.debug(String.valueOf(o));
		}
	}

	public static void info(Object o) {
		if (Throwable.class.isInstance(o)) {
			logger.info(UDebugger.trace(Throwable.class.cast(o)));
		} else {
			logger.info(String.valueOf(o));
		}
	}

}
