package com.np.base.gen;

import com.np.base.utils.USchema;

public class SchemaColumn {
	private int seq;
	private String name;
	private String viewName;
	private String modelName;
	private String variableName;
	private boolean primaryKey;
	private boolean must;
	private boolean binary;

	private String type;
	private String modelType;
	private int size;

	private String refTable;
	private String refColumn;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (USchema.isSchemaName(name) == false)
			name = USchema.toSchemaName(name);
		this.name = name;

		setModelName(USchema.toJavaName(name));
		setVariableName(USchema.toJavaVariableName(name));
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isMust() {
		return must;
	}

	public void setMust(boolean must) {
		this.must = must;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type.contains("VARCHAR"))
			setModelType("String");
		else if (type.equalsIgnoreCase("LONG") || type.equalsIgnoreCase("BIGINT"))
			setModelType("Long");
		else if (type.equalsIgnoreCase("Integer") || type.equalsIgnoreCase("INT"))
			setModelType("Integer");
		else if (type.equalsIgnoreCase("double") || type.equalsIgnoreCase("float"))
			setModelType("Double");
		else if (type.equalsIgnoreCase("BINARY") || type.equalsIgnoreCase("BLOB"))
			setModelType("byte[]");
		else if (type.equalsIgnoreCase("MONEY"))
			setModelType("Money");

		if (type.equalsIgnoreCase("LONG"))
			type = "BIGINT";
		else if (type.equalsIgnoreCase("MONEY"))
			type = "INTEGER";
		else if (type.equalsIgnoreCase("BINARY")) {
			/*
			 * Postgres
			 * 
			 * bytea: 1G
			 */
			type = "bytea";

			/*
			 * MySQL
			 * 
			 * BLOB = L + 2 bytes (max size is 2^16 - 1 or 65,535 bytes, 65KB)
			 * 
			 * MEDIUMBLOB = L + 3 bytes (max size is 2^24 - 1 or 16,777,215
			 * bytes, 16MB)
			 * 
			 * LONGBLOB = L + 4 bytes (max size is 2^32 - 1 or 4,294,967,295
			 * bytes, 4GB)
			 */
			// type = "MEDIUMBLOB";
		}

		this.type = type;

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getRefTable() {
		return refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public String getRefColumn() {
		return refColumn;
	}

	public void setRefColumn(String refColumn) {
		this.refColumn = refColumn;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return name + ":" + viewName + ":" + modelName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public boolean isForeignKey() {
		return refTable != null && refColumn != null;
	}

	public boolean isNullable() {
		return (isPrimaryKey() || isMust()) == false;
	}

	public boolean isBinary() {
		return binary;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
	}
}
