package com.np.base.orm;

import java.lang.reflect.Field;

public class ColumnField {
	private final Field field;
	private final String column;
	private final String name;
	private boolean primaryKey;
	private boolean blob;
	private String joinTable;
	private String joinColumn;
	private int length = 0;
	private boolean isNullable = true;

	public ColumnField(Field field, String column, int length, boolean isNullable) {
		this.field = field;
		this.column = column;
		this.name = field.getName();
		this.length = length;
		this.isNullable = isNullable;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isBlob() {
		return blob;
	}

	public void setBlob(boolean blob) {
		this.blob = blob;
	}

	public String getJoinTable() {
		return joinTable;
	}

	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}

	public String getJoinColumn() {
		return joinColumn;
	}

	public void setJoinColumn(String joinColumn) {
		this.joinColumn = joinColumn;
	}

	public Field getField() {
		return field;
	}

	public String getColumn() {
		return column;
	}

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	public boolean isNullable() {
		return isNullable;
	}
}
