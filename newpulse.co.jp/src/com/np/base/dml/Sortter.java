package com.np.base.dml;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.np.base.dml.cmn.ObjectComparator;

public class Sortter {
	public static final int SORT_ASC = 1;
	public static final int SORT_DSC = -1;

	@SuppressWarnings("rawtypes")
	public static void sort(List data, String[] sortKeys, int[] sortOrders) {
		sort(data, new ObjectComparator(sortKeys, sortOrders));
	}

	@SuppressWarnings("rawtypes")
	public static void sort(List data, String[] sortKeys, int sortOrder) {
		int[] sortOrders = new int[sortKeys.length];
		for (int i = 0; i < sortOrders.length; i++)
			sortOrders[i] = sortOrder;
		sort(data, new ObjectComparator(sortKeys, sortOrders));
	}

	@SuppressWarnings("rawtypes")
	public static void sort(List data, String[] sortKeys) {
		sort(data, new ObjectComparator(sortKeys));
	}

	@SuppressWarnings("rawtypes")
	public static void sort(List data, String sortKey) {
		sort(data, new String[] { sortKey });
	}

	@SuppressWarnings("rawtypes")
	public static void sort(List data, String sortKey, int order) {
		sort(data, new String[] { sortKey }, order);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sort(List data, Comparator comparator) {
		Collections.sort(data, comparator);
	}
}
