package com.np.base.gen;

import java.util.ArrayList;
import java.util.List;

import com.np.base.utils.USchema;

public class SchemaTable {
	private String name;
	private String modelName;
	private String viewName;
	private List<SchemaColumn> columns = new ArrayList<SchemaColumn>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (USchema.isSchemaName(name) == false)
			name = USchema.toSchemaName(name);
		if (name.endsWith("_TBL") == false)
			name += "_TBL";

		this.name = name;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public List<SchemaColumn> getColumns() {
		return columns;
	}

	public void addColumn(SchemaColumn column) {
		this.columns.add(column);
	}

	@Override
	public String toString() {
		return name + ":" + viewName + " " + columns;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public boolean isForeignKey() {
		for (SchemaColumn col : getColumns()) {
			if (col.isForeignKey())
				return true;
		}
		return false;
	}

	public boolean isPrimaryKey() {
		for (SchemaColumn col : getColumns()) {
			if (col.isPrimaryKey())
				return true;
		}
		return false;
	}
}
