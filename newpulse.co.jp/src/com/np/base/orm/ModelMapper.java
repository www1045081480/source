package com.np.base.orm;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.models.JoinKey;
import com.np.base.models.JoinedModels;
import com.np.base.models.UncaseList;
import com.np.base.models.UncaseMap;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UDebugger;
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.Money;

/*
 * Entity <---> Model 
 */
public class ModelMapper {
	private static Log logger = LogFactory.getLog(ModelMapper.class);
	/*
	 * Update Timestamp
	 */
	public static final String RegTime = "RegTime";
	public static final String UpdTime = "UpdTime";

	private final String tableName;

	/*
	 * Model Class
	 */
	private final Class<?> type;

	/*
	 * Key List （モジュール）
	 */
	private List<String> primaryKeys;

	/*
	 * Entity Names
	 */
	private List<String> columnNames;

	/*
	 * モジュール Names
	 */
	private List<String> propertyNames;

	/*
	 * Model Name -> Model-Entity Info
	 */
	private Map<String, ColumnField> fields;

	public ModelMapper(String tableName, Class<?> type) {
		this.tableName = tableName;
		this.type = type;
		primaryKeys = new UncaseList();
		columnNames = new UncaseList();

		fields = new UncaseMap<ColumnField>();
		propertyNames = new UncaseList();
	}

	public ModelMapper(JoinedModels joinedModels) {
		this.tableName = null;
		this.type = null;
		primaryKeys = new ArrayList<String>();
		columnNames = Arrays.asList(joinedModels.getNames());

		fields = new UncaseMap<ColumnField>();
		propertyNames = Arrays.asList(joinedModels.getNames());
	}

	public Class<Object>[] getJoinTables() {
		return null;
	}

	public JoinKey getJoinKey(ModelMapper master) {
		// List<String> joinKeys = new ArrayList<String>();
		for (ColumnField field : fields.values()) {
			if (field.getJoinTable() == null)
				continue;

			if (master.getTablename().equalsIgnoreCase(field.getJoinTable())) {
				JoinKey joinKey = new JoinKey();
				joinKey.setLeftKey(field.getName());
				joinKey.setRightKey(master.toPropertyName(field.getJoinColumn()));
				return joinKey;
			} else if (master.getType().getSimpleName().equalsIgnoreCase(field.getJoinTable())) {
				JoinKey joinKey = new JoinKey();
				joinKey.setLeftKey(field.getName());
				joinKey.setRightKey(field.getJoinColumn());
				return joinKey;
			}
		}
		return null;
		// return joinKeys.toArray(new String[0]);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(tableName);
		sb.append("\n");
		sb.append(primaryKeys);
		sb.append("\n");
		sb.append(propertyNames);
		sb.append("\n");
		return sb.toString();
	}

	public int getSqlType(String name) {
		if (isString(name))
			return Types.VARCHAR;
		else if (isBinary(name))
			return Types.BLOB;
		else if (isNumber(name)) {
			Class<?> type = getColumnType(name);
			if (type == Long.class)
				return Types.BIGINT;
			if (type == Double.class)
				return Types.DOUBLE;
			if (type == Integer.class)
				return Types.INTEGER;
			return Types.DOUBLE;

		}
		return Types.VARCHAR;
	}

	public ColumnField add(Field field, String column, boolean isPrimaryKey, int length, boolean isNullable) {
		if (columnNames.contains(column))
			return fields.get(field.getName());

		ColumnField colField = new ColumnField(field, column, length, isNullable);
		fields.put(colField.getName(), colField);
		colField.setPrimaryKey(isPrimaryKey);
		field.setAccessible(true);

		/*
		 * テーブル情報
		 */
		columnNames.add(column);

		/*
		 * モジュール情報
		 */
		propertyNames.add(field.getName());
		if (isPrimaryKey && (field.getDeclaringClass() == this.type))
			primaryKeys.add(field.getName());

		return colField;
	}

	public String toColumnName(String name) {
		if (fields.containsKey(name))
			return fields.get(name).getColumn();
		else
			return name;
	}

	public String toPropertyName(String column) {
		for (ColumnField colField : fields.values()) {
			if (colField.getColumn().equalsIgnoreCase(column))
				return colField.getName();
		}
		return column;
	}

	/*
	 * Table名称
	 */
	public String getTablename() {
		return tableName;
	}

	/*
	 * モジュール型
	 */
	public Class<?> getType() {
		return type;
	}

	/*
	 * モジュールのキー名称
	 */
	public List<String> getPrimaryKeys() {
		// return Collections.unmodifiableList(primaryKeys);
		return primaryKeys;
	}

	/*
	 * モジュールの非キー名称
	 */
	public List<String> getNonPKNames() {
		List<String> dataColumns = new ArrayList<String>();
		for (String col : getNames()) {
			if (!primaryKeys.contains(col))
				dataColumns.add(col);
		}
		return dataColumns;
	}

	public boolean containsProperty(String name) {
		return getNames().contains(name);
	}

	/*
	 * Javaモジュール名称
	 */
	public List<String> getNames() {
		return propertyNames;
	}

	/*
	 * Table名称
	 */
	public List<String> getColumns() {
		return columnNames;
	}

	public void setColumns(JoinedModels o) {
		if (UList.isEmpty(columnNames) == false)
			return;
		columnNames = Arrays.asList(o.getNames());
	}

	public ColumnField getField(String name) {
		return fields.get(name);
	}

	public Object getValue(Object obj, String name) {
		if (JoinedModels.class.isInstance(obj)) {
			JoinedModels jm = JoinedModels.class.cast(obj);
			setColumns(jm);
			return jm.getValue(name);
		}

		if (fields.containsKey(name) == false)
			return null;

		Field field = fields.get(name).getField();
		if (field == null)
			return null;
		try {
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object setValue(Object obj, String name, Object value) {
		if (JoinedModels.class.isInstance(obj)) {
			JoinedModels jm = JoinedModels.class.cast(obj);
			setColumns(jm);
			jm.setValue(name, value);
			return value;
		}

		if (fields.containsKey(name) == false)
			return value;

		Field field = fields.get(name).getField();
		if (field == null)
			return value;
		if (value == null)
			return value;
		try {
			if (isBinary(name)) {
				if (value instanceof String)
					field.set(obj, ((String) value).getBytes());
				else if (InputStream.class.isInstance(value)) {
					setBlob(obj, field, InputStream.class.cast(value));
				} else if (Blob.class.isInstance(value)) {
					setBlob(obj, field, Blob.class.cast(value).getBinaryStream());
				} else if (value.getClass() == byte[].class) {
					field.set(obj, (byte[]) value);
				} else
					throw new IllegalArgumentException("set " + value.getClass() + " to " + field.getType());
			} else {
				Class<?> fieldType = field.getType();
				if (isNumber(name) && value instanceof BigDecimal) {
					BigDecimal bd = (BigDecimal) value;
					if (fieldType == Long.class)
						field.set(obj, bd.longValue());
					else if (fieldType == Integer.class)
						field.set(obj, bd.intValue());
					else if (fieldType == Short.class)
						field.set(obj, bd.shortValue());
					else if (fieldType == Byte.class)
						field.set(obj, bd.byteValue());
					else if (fieldType == Float.class)
						field.set(obj, bd.floatValue());
					else if (fieldType == Double.class)
						field.set(obj, bd.doubleValue());
					else if (fieldType == BigDecimal.class)
						field.set(obj, bd);
				} else if (fieldType == String.class) {
					field.set(obj, String.valueOf(value).trim());
				} else if (fieldType == Character.class) {
					String s = String.valueOf(value).trim();
					field.set(obj, Character.valueOf(s.charAt(0)));
				} else {
					// TODO
					if (BufferedReader.class.isInstance(value)) {
						setClob(obj, field, BufferedReader.class.cast(value));
					} else if (Clob.class.isInstance(value)) {
						setClob(obj, field, Clob.class.cast(value).getCharacterStream());
					} else {
						if (fieldType == Money.class) {
							if (Integer.class.isInstance(value))
								value = new Money(Integer.class.cast(value).intValue());
							else if (Long.class.isInstance(value))
								value = new Money(Long.class.cast(value).intValue());
						}
						field.set(obj, value);
					}
				}
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException(obj.getClass() + "/" + name + ":" + e.getMessage(), e);
		}
	}

	public void setValueByString(Object obj, String name, String value, String currency) {
		if (fields.containsKey(name) == false)
			return;

		Field field = fields.get(name).getField();
		if (field == null)
			return;
		if (value == null)
			return;
		if (UString.isEmpty(value))
			return;
		try {
			Class<?> fieldType = field.getType();
			if (isNumber(name)) {
				value = value.trim();
				if (fieldType == Long.class)
					field.set(obj, Long.valueOf(value));
				else if (fieldType == Integer.class)
					field.set(obj, Integer.valueOf(value));
				else if (fieldType == Short.class)
					field.set(obj, Short.valueOf(value));
				else if (fieldType == Byte.class)
					field.set(obj, Byte.valueOf(value));
				else if (fieldType == Float.class)
					field.set(obj, Float.valueOf(value));
				else if (fieldType == Double.class)
					field.set(obj, Double.valueOf(value));
				else if (fieldType == BigDecimal.class)
					field.set(obj, new BigDecimal(value));
				else if (fieldType == Money.class) {
					field.set(obj, new Money(currency, value));
				}
			} else if (fieldType == String.class) {
				field.set(obj, String.valueOf(value));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setBlob(Object obj, Field field, InputStream in) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while (true) {
			int len = in.read(buf);
			if (len == -1)
				break;
			out.write(buf, 0, len);
		}
		out.close();
		in.close();
		field.set(obj, out.toByteArray());
	}

	private void setClob(Object obj, Field field, Reader in) throws Exception {
		char[] buf = new char[8192];
		StringBuilder sb = new StringBuilder();
		while (true) {
			int len = in.read(buf);
			if (len == -1)
				break;
			sb.append(new String(buf, 0, len));
		}
		in.close();
		field.set(obj, sb.toString());
	}

	public boolean isFlag(String name) {
		Class<?> type = getColumnType(name);
		return (Boolean.class.isAssignableFrom(type));
	}

	public boolean isNumber(String name) {
		Class<?> type = getColumnType(name);
		return (Number.class.isAssignableFrom(type));
	}

	public boolean isString(String name) {
		Class<?> type = getColumnType(name);
		return type == String.class;
	}

	public boolean isBinary(String name) {
		Class<?> type = getColumnType(name);
		return type == byte[].class;
	}

	private Class<?> getColumnType(String name) {
		if (fields.containsKey(name) == false)
			throw new IllegalArgumentException("Invalid Name " + name);

		Field field = fields.get(name).getField();
		if (field == null) {
			throw new IllegalArgumentException("Invalid Column Name " + name);
		}
		Class<?> type = field.getType();
		return type;
	}

	public Object toModel(ResultSet rs) throws Exception {
		return toModel(rs, type, getNames());
	}

	public static <R> R toModel(ResultSet rs, Class<R> type, List<String> fetchColumns) throws Exception {
		ModelMapper mapper = PojoUtils.getMapper(type);
		R obj = type.newInstance();
		String currency = null;
		List<Money> amounts = new ArrayList<Money>();
		for (String column : fetchColumns) {
			try {
				Object value = rs.getObject(mapper.toColumnName(column));
				value = mapper.setValue(obj, column, value);
				if (value != null && Money.class.isInstance(value))
					amounts.add(Money.class.cast(value));
				if (column.equalsIgnoreCase("currency"))
					currency = String.class.cast(value);
			} catch (SQLException e) {
				// SQLException if the columnLabel is not valid;
				logger.trace(UDebugger.trace(e));
			}
		}
		for (Money amount : amounts) {
			amount.setCurrency(currency);
		}
		return obj;
	}
}
