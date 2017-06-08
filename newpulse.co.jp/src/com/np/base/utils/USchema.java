package com.np.base.utils;

public class USchema {

	public static String toJavaName(String name) {
		if (isSchemaName(name) == false)
			return name;

		if (name.toUpperCase().endsWith("_TBL"))
			name = name.substring(0, name.length() - 4);

		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < name.length(); idx++) {
			char c = name.charAt(idx);
			if (idx == 0)
				sb.append(Character.toUpperCase(c));
			else if (c == '_') {
				idx++;
				if (idx < name.length())
					sb.append(Character.toUpperCase(name.charAt(idx)));
			} else
				sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	public static String toJavaVariableName(String name) {
		if (isSchemaName(name) == false) {
			return Character.toLowerCase(name.charAt(0)) + name.substring(1);
		}

		if (name.toUpperCase().endsWith("_TBL"))
			name = name.substring(0, name.length() - 4);

		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < name.length(); idx++) {
			char c = name.charAt(idx);
			if (idx == 0)
				sb.append(Character.toLowerCase(c));
			else if (c == '_') {
				idx++;
				if (idx < name.length())
					sb.append(Character.toUpperCase(name.charAt(idx)));
			} else
				sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	public static String toSchemaName(String name) {
		if (isSchemaName(name))
			return name.toUpperCase();

		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < name.length(); idx++) {
			char c = name.charAt(idx);
			if (idx > 0 && Character.isUpperCase(c)) {
				sb.append('_');
				sb.append(c);
			} else if (c != '_')
				sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}

	public static boolean isSchemaName(String name) {
		if (name.indexOf('_') != -1) {
			return true;
		}
		boolean hasLowerCase = false;
		for (int idx = 0; idx < name.length(); idx++) {
			char c = name.charAt(idx);
			hasLowerCase = hasLowerCase || Character.isLowerCase(c);
		}
		return !hasLowerCase;
	}

}
