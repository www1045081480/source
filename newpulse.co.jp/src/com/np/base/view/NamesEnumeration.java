package com.np.base.view;

import java.util.Enumeration;

@SuppressWarnings("rawtypes")
public class NamesEnumeration implements Enumeration {
	private String[] names;
	private int index = 0;

	public NamesEnumeration(String[] names) {
		this.names = names;
	}

	@Override
	public boolean hasMoreElements() {
		return index < names.length;
	}

	@Override
	public Object nextElement() {
		return names[index++];
	}

}
