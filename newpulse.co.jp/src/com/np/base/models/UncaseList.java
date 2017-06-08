package com.np.base.models;

import java.util.ArrayList;

public class UncaseList extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean contains(Object o) {
		if (o == null)
			return super.contains(o);
		if (String.class.isInstance(o) == false)
			return super.contains(o);

		String s = String.class.cast(o);
		for (String e : this) {
			if (s.equalsIgnoreCase(e))
				return true;
		}
		return false;
	}
}
