package com.np.base.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class PojoReflector {
	public static final Object[] NULL_ARGS = new Object[0];
	public static final Class NAME_ARGS = String.class;
	public static final Class VALUE_ARGS = Object.class;

	private static Map<String, Map<String, Method>> accessors = new HashMap<String, Map<String, Method>>();

	public static Method getPropertyGetter(Class clazz, String name) {
		if (Character.isLowerCase(name.charAt(0))) {
			name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}
		String methodName = "get" + name;
		Method found = exist(clazz, methodName);
		if (found != null)
			return found;

		Method[] methods = clazz.getMethods();

		for (int j = 0; j < methods.length; j++) {
			if (methodName.equalsIgnoreCase(methods[j].getName())) {
				Class[] params = methods[j].getParameterTypes();
				if ((params == null) || (params.length == 0)) {
					@SuppressWarnings("unused")
					Class returnType = methods[j].getReturnType();
					save(clazz, methodName, methods[j]);
					return methods[j];
				}
			}
		}
		return null;
	}

	public static Method getPropertySetter(Class clazz, String name) {
		char c = name.charAt(0);
		if (Character.isLowerCase(c))
			name = Character.toUpperCase(c) + name.substring(1);

		String methodName = "set" + name;
		Method found = exist(clazz, methodName);
		if (found != null)
			return found;

		Method[] methods = clazz.getMethods();

		for (int j = 0; j < methods.length; j++) {
			if (methodName.equalsIgnoreCase(methods[j].getName())) {
				Class[] params = methods[j].getParameterTypes();
				// if ((params != null) && (params.length == 2) && (params[0] ==
				// NAME_ARGS))
				if ((params != null) && (params.length == 1)) {
					@SuppressWarnings("unused")
					Class returnType = methods[j].getReturnType();
					save(clazz, methodName, methods[j]);
					return methods[j];
				}
			}
		}
		return null;
	}

	public static Method exist(Class clazz, String name) {
		Map<String, Method> propertyAccesss = null;
		if (accessors.get(clazz.getName()) == null) {
			propertyAccesss = new HashMap<String, Method>();
			accessors.put(clazz.getName(), propertyAccesss);
		} else {
			propertyAccesss = accessors.get(clazz.getName());
			if (propertyAccesss.get(name) != null)
				return (Method) propertyAccesss.get(name);
		}

		return null;
	}

	public static void save(Class clazz, String name, Method method) {
		Map<String, Method> propertyAccesss = null;
		if (accessors.get(clazz.getName()) == null) {
			propertyAccesss = new HashMap<String, Method>();
			accessors.put(clazz.getName(), propertyAccesss);
		} else {
			propertyAccesss = accessors.get(clazz.getName());
		}

		propertyAccesss.put(name, method);
	}

	public static boolean isBoolean(Class clazz) {
		return clazz == Boolean.TYPE;
	}

	public static boolean isChar(Class clazz) {
		return clazz == Character.TYPE;
	}

	public static boolean isByte(Class clazz) {
		return clazz == Byte.TYPE;
	}

	public static boolean isShort(Class clazz) {
		return clazz == Short.TYPE;
	}

	public static boolean isInt(Class clazz) {
		return clazz == Integer.TYPE;
	}

	public static boolean isLong(Class clazz) {
		return clazz == Long.TYPE;
	}

	public static boolean isFloat(Class clazz) {
		return clazz == Float.TYPE;
	}

	public static boolean isDouble(Class clazz) {
		return clazz == Double.TYPE;
	}

	public static boolean isVoid(Class clazz) {
		return clazz == Void.TYPE;
	}

}
