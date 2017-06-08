package com.np.base.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.models.JoinedModels;
import com.np.order.Money;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UJson {
	private static Log logger = LogFactory.getLog(UJson.class);
	public static boolean TEST_MODE = false;

	public static String toJsonString(Object obj) throws Exception {
		return toJsonString(obj, false);
	}

	public static String toJsonString(Object obj, boolean forView) throws Exception {
		Object json = toJson(obj, forView);
		if (TEST_MODE)
			logger.debug(((JSON) json).toString(4));

		if (JSON.class.isInstance(json))
			return ((JSON) json).toString((TEST_MODE) ? 4 : 0);
		else
			return json.toString();
	}

	public static Object toJson(Object obj) throws Exception {
		return toJson(obj, false);
	}

	@SuppressWarnings("unchecked")
	public static Object toJson(Object obj, boolean forView) throws Exception {
		if (obj == null)
			return null;

		Object json;
		if (List.class.isInstance(obj)) {
			json = toJson(List.class.cast(obj), forView);
		} else if (Map.class.isInstance(obj)) {
			json = toJson(Map.class.cast(obj), forView);
		} else if (JoinedModels.class.isInstance(obj)) {
			json = toJson(JoinedModels.class.cast(obj), forView);
		} else {
			json = pojoToJson(obj, forView);
		}
		return json;
	}

	@SuppressWarnings("rawtypes")
	private static Object pojoToJson(Object javaBean, boolean forView) throws Exception {
		if (javaBean == null) {
			return javaBean;
		}

		Class clazz = javaBean.getClass();
		if (clazz.isPrimitive())
			return javaBean;
		if (clazz == Money.class) {
			Money money = Money.class.cast(javaBean);
			if (money.intValue() == 0)
				return "";
			return (forView) ? money.toString() : money.getValue();
		}

		if (clazz.isArray()) {
			if (clazz.getComponentType().isPrimitive()) {
				if (clazz.getComponentType() == byte.class)
					return null;
				return javaBean;
			}
		}

		String packageName = clazz.getPackage().getName();
		if (packageName.startsWith("java.lang")) {
			return javaBean;
		}

		JSONObject json = new JSONObject();
		Method[] methods = javaBean.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				String field = method.getName();
				field = field.substring(3);
				field = field.toLowerCase().charAt(0) + field.substring(1);
				Object value = method.invoke(javaBean, (Object[]) null);
				if (value == null)
					continue;

				json.put(field, toJson(value, forView));
			}
		}
		return json;
	}

	private static JSON toJson(Map<Object, Object> map, boolean forView) throws Exception {
		JSONObject json = new JSONObject();
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			Object name = entry.getKey();
			Object value = entry.getValue();
			if (value != null)
				json.put(name, toJson(value, forView));
		}
		return json;
	}

	private static JSON toJson(JoinedModels joinModel, boolean forView) throws Exception {
		JSONObject json = new JSONObject();
		for (String name : joinModel.getNames()) {
			Object value = joinModel.getValue(name);
			if (value != null)
				json.put(name, toJson(value, forView));
		}
		return json;
	}

	@SuppressWarnings("rawtypes")
	private static JSON toJson(List list, boolean forView) throws Exception {
		Object[] jsons = new Object[list.size()];
		int index = 0;
		for (Object obj : list) {
			jsons[index++] = toJson(obj, forView);
		}
		JSON result = JSONArray.fromObject(jsons);

		if (TEST_MODE)
			logger.debug((result).toString(4));
		return result;
	}

}
