package com.np.base.reflect;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.np.base.models.JoinedModels;
import com.np.base.orm.ColumnField;
import com.np.base.orm.ModelMapper;
import com.np.base.utils.UString;

public class PojoUtils {
	private static Map<Class<?>, ModelMapper> modelMap = new ConcurrentHashMap<Class<?>, ModelMapper>();

	public static ModelMapper getMapper(Object type) {
		if (JoinedModels.class.isInstance(type))
			return new ModelMapper(JoinedModels.class.cast(type));
		else
			return getMapper(type.getClass());
	}

	public static ModelMapper getMapper(Class<?> type) {
		if (!modelMap.containsKey(type))
			findModel(type);
		return modelMap.get(type);
	}

	public static ModelMapper getMapperByTablename(String tablename) {
		for (ModelMapper mapper : modelMap.values()) {
			if (mapper.getTablename().equalsIgnoreCase(tablename))
				return mapper;
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static void findModel(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			findModel(clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static synchronized void findModel(Class<?> clazz) {
		if (JoinedModels.class.isInstance(clazz)) {
			return;
		}
		ModelMapper schema = findTable(clazz);
		if (schema == null)
			return;

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			findColumn(schema, field);
		}
		findSuperModel(schema, clazz);
		modelMap.put(clazz, schema);
	}

	private static void findSuperModel(ModelMapper schema, Class<?> clazz) {
		ModelMapper parentSchema = findTable(clazz.getSuperclass());
		if (parentSchema == null)
			return;

		Field[] fields = parentSchema.getType().getDeclaredFields();
		for (Field field : fields) {
			findColumn(schema, field);
		}
	}

	private static ModelMapper findTable(Class<?> clazz) {
		String tablename = null;
		boolean isEntity = false;
		for (Annotation annotation : clazz.getAnnotations()) {
			Class<? extends Annotation> type = annotation.annotationType();
			if (type == Table.class) {
				Table table = (Table) annotation;
				tablename = table.name();
			} else if (type == Entity.class) {
				@SuppressWarnings("unused")
				Entity entity = (Entity) annotation;
				isEntity = true;
			}
		}

		if (isEntity && !UString.isEmpty(tablename)) {
			return new ModelMapper(tablename, clazz);
		} else {
			return new ModelMapper(null, clazz);
			// clazz = clazz.getSuperclass();
			// return (clazz == null) ? null : findTable(clazz);
		}
	}

	private static boolean findColumn(ModelMapper schema, Field field) {
		String columnName = null;
		String joinColumnName = null;
		String joinTableName = null;
		boolean isBlob = false;
		boolean isTransient = false;
		boolean isPrimaryKey = false;
		int length = 0;
		boolean isNullable = true;
		for (Annotation annotation : field.getAnnotations()) {
			Class<? extends Annotation> type = annotation.annotationType();
			if (type == Column.class) {
				Column col = (Column) annotation;
				columnName = col.name();
				length = col.length();
				isNullable = col.nullable();
			} else if (type == Id.class) {
				isPrimaryKey = true;
			} else if (type == Transient.class) {
				isTransient = true;
			} else if (type == JoinTable.class) {
				JoinTable col = (JoinTable) annotation;
				joinTableName = col.name();
			} else if (type == JoinColumn.class) {
				JoinColumn col = (JoinColumn) annotation;
				joinColumnName = col.name();
			} else if (type == Blob.class) {
				@SuppressWarnings("unused")
				Blob col = (Blob) annotation;
				isBlob = true;
			}
		}
		if (!isTransient && !UString.isEmpty(columnName)) {
			ColumnField colField = schema.add(field, columnName, isPrimaryKey, length, isNullable);
			colField.setJoinTable(joinTableName);
			colField.setJoinColumn(joinColumnName);
			colField.setBlob(isBlob);
			return true;
		} else {
			if (schema.getTablename() == null) {
				columnName = field.getName();
				ColumnField colField = schema.add(field, columnName, isPrimaryKey, length, isNullable);
				colField.setJoinTable(joinTableName);
				colField.setJoinColumn(joinColumnName);
				colField.setBlob(isBlob);
				return true;
			}
			return false;
		}
	}
}
