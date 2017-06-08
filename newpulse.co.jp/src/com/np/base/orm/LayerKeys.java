package com.np.base.orm;

public class LayerKeys implements Comparable<LayerKeys> {
	private Object[] keys;

	public LayerKeys(Object[] keys) {
		this.keys = keys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(LayerKeys o) {
		for (int index = 0; index < keys.length; index++) {

			if (o.keys[index] == null && keys[index] == null)
				continue;

			if (o.keys[index] == null)
				return 1;
			if (keys[index] == null)
				return -1;

			int comp;
			if (Comparable.class.isInstance(keys[index])) {
				comp = Comparable.class.cast(keys[index]).compareTo(Comparable.class.cast(o.keys[index]));
			} else {
				comp = keys[index].toString().compareTo(o.keys[index].toString());
			}
			if (comp != 0)
				return comp;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Object key : keys) {
			if (sb.length() > 0)
				sb.append("-");
			sb.append(key);
		}
		return sb.toString();
	}
}
