package com.np.base.models;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UncaseMap<V> extends HashMap<String, V> {

	public UncaseMap(UncaseMap<V> base) {
		super();
		putAll(base);
	}

	public UncaseMap() {
		super();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void putAll(Map map) {
		// super.putAll(map);
		for (Map.Entry<String, V> entry : ((UncaseMap<V>) map).entrySet())
			put(entry.getKey().toLowerCase(), entry.getValue());
	}

	@Override
	public V put(String key, V value) {
		// super.put(key, value);
		return super.put(key.toLowerCase(), value);
	}

	@Override
	public V get(Object key) {
		if (containsKey(key))
			return super.get(String.valueOf(key).toLowerCase());
		return super.get(key);
	}

	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(String.valueOf(key).toLowerCase());
	}

}
