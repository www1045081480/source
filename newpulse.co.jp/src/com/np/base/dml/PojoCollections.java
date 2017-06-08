package com.np.base.dml;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.np.base.models.JoinedModels;
import com.np.base.reflect.PojoReflector;
import com.np.base.utils.UList;

public class PojoCollections {

	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> getKeyList(List<?> data, String keyname) {
		List<T> keys = new ArrayList<T>();

		if (UList.isEmpty(data))
			return keys;

		Method accessor = null;
		for (Object obj : data) {
			T value = null;
			if (JoinedModels.class.isInstance(obj)) {
				value = (T) JoinedModels.class.cast(obj).getValue(keyname);
			} else {
				if (accessor == null)
					accessor = PojoReflector.getPropertyGetter(obj.getClass(), keyname);
				try {
					value = (T) accessor.invoke(obj, PojoReflector.NULL_ARGS);
				} catch (Exception e) {
				}
			}
			if (value == null)
				continue;
			if (keys.contains(value))
				continue;
			keys.add(value);

		}

		return keys;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> getUniqueKeyList(List<?> data, String keyname) {
		List<T> result = new ArrayList<T>();
		if (UList.isEmpty(data))
			return result;

		Method accessor = null;
		for (Object obj : data) {
			T value = null;
			if (JoinedModels.class.isInstance(obj)) {
				value = (T) JoinedModels.class.cast(obj).getValue(keyname);
			} else {
				if (accessor == null)
					accessor = PojoReflector.getPropertyGetter(obj.getClass(), keyname);
				try {
					value = (T) accessor.invoke(obj, PojoReflector.NULL_ARGS);
					// if (!retsult.contains(value))
				} catch (Exception e) {
				}
			}
			if (value != null)
				result.add(value);
		}

		Sortter.sort(result, keyname);
		for (int i = result.size() - 1; i > 0; i--) {
			if (result.get(i).equals(result.get(i - 1)))
				result.remove(i);
		}

		return result;
	}

	public static <T extends Object> List<T> distinct(List<T> data, String keyName) {
		if (UList.isEmpty(data))
			return data;

		Sortter.sort(data, keyName);

		List<T> result = new ArrayList<T>(data.size());
		Method accessor = null;
		Object nextKey = null;
		final int lenData = data.size();
		for (int i = 0; i < lenData; i++) {
			T obj = data.get(i);
			Object key = null;
			if (JoinedModels.class.isInstance(obj)) {
				key = JoinedModels.class.cast(obj).getValue(keyName);
			} else {
			}
			if (accessor == null)
				accessor = PojoReflector.getPropertyGetter(obj.getClass(), keyName);
			try {
				key = accessor.invoke(obj, PojoReflector.NULL_ARGS);
			} catch (Exception e) {
			}

			if ((nextKey == null) || nextKey.equals(key) == false) {
				result.add(obj);
				nextKey = key;
			}

			data.set(i, null);
		}

		data.clear();
		data.addAll(result);

		return data;
	}

	public static <T extends Object> List<T> distinct(List<T> data, String[] keyNames) {
		if (UList.isEmpty(data))
			return data;

		Sortter.sort(data, keyNames);
		List<T> result = new ArrayList<T>(data.size());
		Groupper.controlBreak(data, keyNames, new DistinctBreaker(result));
		data.clear();
		data.addAll(result);

		return data;
	}

	static class DistinctBreaker implements IControlBreakProcedure {
		@SuppressWarnings("rawtypes")
		private List result;
		private Object tra;

		@SuppressWarnings("rawtypes")
		public DistinctBreaker(List result) {
			this.result = result;
			this.tra = null;
		}

		public void doContinue(Object element) {
			this.tra = element;
		}

		@SuppressWarnings("unchecked")
		public void doBreak(String key) {
			if (this.tra != null) {
				result.add(this.tra);
				this.tra = null;
			}
		}
	}

}
