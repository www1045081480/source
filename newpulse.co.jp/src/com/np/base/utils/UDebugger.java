package com.np.base.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.np.base.orm.ModelMapper;
import com.np.base.reflect.PojoUtils;

public class UDebugger {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UDebugger.class);

	public static String trace(Throwable t) {
		t.printStackTrace();
		StringWriter w = new StringWriter();
		PrintWriter pw = new PrintWriter(w);
		t.printStackTrace(pw);
		pw.close();

		return w.toString();
	}

	public static void print(Object obj) {
		ModelMapper mapper = PojoUtils.getMapper(obj);
		System.out.println(obj);
		for (String name : mapper.getNames()) {
			System.out.println("\t" + name + "=" + mapper.getValue(obj, name));
		}
	}
}
