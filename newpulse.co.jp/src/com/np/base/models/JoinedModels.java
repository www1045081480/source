package com.np.base.models;

public class JoinedModels {
	private String[] names;
	private Object[] values;

	public JoinedModels(String[] names) {
		this.names = names;
		this.values = new Object[names.length];
	}

	public void appendName(String name) {
		String[] newNames = new String[names.length + 1];
		System.arraycopy(names, 0, newNames, 0, names.length);
		newNames[names.length] = name;
		names = newNames;

		Object[] newValues = new Object[values.length + 1];
		System.arraycopy(values, 0, newValues, 0, values.length);
		values = newValues;
	}

	public String[] getNames() {
		return names;
	}

	public Object[] getValues() {
		return values;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String name) {
		int index = 0;
		Object value = null;
		for (String n : names) {
			if (name.equalsIgnoreCase(n)) {
				// if (values[index] != null)
				value = values[index];
				return (T) value;
			}
			index++;
		}
		return (T) value;
		// throw new IllegalArgumentException(name);
	}

	public void setValue(String name, Object value) {
		int index = 0;
		for (String n : names) {
			if (name.equalsIgnoreCase(n)) {
				/*
				 * TODO
				 */
				// if (values[index] == null) {
				values[index] = value;
				return;
				// }
			}
			index++;
		}
		// throw new IllegalArgumentException(name);
	}

	public void setValue(int index, Object value) {
		// values[index] = value;
		setValue(names[index], value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (String name : names) {
			sb.append("\t");
			sb.append(name);
			sb.append("=");
			sb.append(values[index]);
			sb.append("\n");
			index++;
		}
		return sb.toString();
	}

}
