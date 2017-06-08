package com.np.base.dml;

import java.util.ArrayList;
import java.util.List;

import com.np.base.dml.cmn.IArrayComparator;
import com.np.base.dml.cmn.ObjectComparator;
import com.np.base.orm.LayerKeys;
import com.np.base.orm.ModelMapper;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UModel;

public class Groupper {

	/*
	 * 集計
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<List<T>> group(List<T> objs, String[] keys) {
		List<List<T>> result = new ArrayList<List<T>>();
		GroupBreaker groupBreaker = new GroupBreaker(result, keys);
		controlBreak(objs, keys, groupBreaker);
		return result;
	}

	/*
	 * 合計
	 */
	public static <T> List<T> sum(List<T> objs, String[] keys, String[] sumNames) {
		List<T> result = new ArrayList<T>();
		SumBreaker sumBreaker = new SumBreaker(result, keys, sumNames);
		controlBreak(objs, keys, sumBreaker);
		return result;
	}

	/*
	 * 残高
	 */
	public static <T> List<T> cumulative(List<T> objs, IBalance<T> iBalance, int initValue) {
		int cumulativeValue = initValue;
		for (T obj : objs) {
			cumulativeValue = iBalance.balance(obj, cumulativeValue);
		}
		return objs;
	}

	@SuppressWarnings("rawtypes")
	public static void controlBreak(List data, String[] keys, IControlBreakProcedure proc) {
		controlBreak(data, keys, proc, new ObjectComparator(keys));
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static void controlBreak(List data, String[] keys, IControlBreakProcedure proc,
			IArrayComparator comparator) {

		Sortter.sort(data, keys);

		int numOfRecords = data.size();
		int indexOfLastRecord = numOfRecords - 1;

		for (int r = 0; r < numOfRecords; r++) {
			boolean hasBreak = false;
			Object tra = data.get(r);
			if (r == 0) {
				proc.doContinue(tra);
			} else {
				if (comparator.compare(data.get(r - 1), tra) != 0) {
					doBreaks(keys, comparator.getDiffPoint(), proc);
					hasBreak = true;
				}
				proc.doContinue(tra);
			}

			if (r == indexOfLastRecord) {
				doBreaks(keys, 0, proc);
			}
		}
	}

	private static void doBreaks(String[] breakKeys, int breakPoint, IControlBreakProcedure proc) {
		/*
		 * 小分類ー＞大分類
		 */
		for (int i = breakKeys.length - 1; i >= breakPoint; i--)
			proc.doBreak(breakKeys[i]);
	}

	static class SumBreaker implements IControlBreakProcedure {
		@SuppressWarnings("unused")
		private String[] keys;
		private String[] sumNames;
		private String[] otherNames;
		@SuppressWarnings("rawtypes")
		private List result;
		private Object sum;

		@SuppressWarnings("rawtypes")
		public SumBreaker(List result, String[] keys, String[] sumNames) {
			this.keys = keys;
			this.sumNames = sumNames;
			this.result = result;
		}

		@SuppressWarnings("unused")
		public void doContinue(Object e) {
			if (otherNames == null) {
				List<String> names = new ArrayList<String>();
				ModelMapper mapper = PojoUtils.getMapper(e);
				NEXT: for (String name : mapper.getNames()) {
					if (ModelMapper.RegTime.equals(name))
						continue;
					if (ModelMapper.UpdTime.equals(name))
						continue;

					names.add(name);
				}
				otherNames = names.toArray(new String[0]);
			}
			int index = 0;
			if (sum == null) {
				sum = UModel.copy(e, otherNames);
			} else {
				UModel.sum(sum, e, sumNames);
			}
		}

		@SuppressWarnings("unchecked")
		public void doBreak(String key) {
			/*
			 * 集計結果
			 */
			result.add(sum);

			/*
			 * 集計をReset
			 */
			sum = null;
		}
	}

	static class GroupBreaker<T> implements IControlBreakProcedure {
		private String[] keys;

		/*
		 * TODO:複数グループ
		 */
		private List<List<T>> result;
		@SuppressWarnings("rawtypes")
		private List group;
		@SuppressWarnings("unused")
		private LayerKeys groupKeys;

		public GroupBreaker(List<List<T>> result, String[] keys) {
			this.keys = keys;
			this.result = result;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void doContinue(Object e) {
			if (group == null) {
				Object[] keyValues = new Object[keys.length];
				int index = 0;
				ModelMapper mapper = PojoUtils.getMapper(e);
				for (String key : keys) {
					keyValues[index] = mapper.getValue(e, key);
					index++;
				}
				groupKeys = new LayerKeys(keyValues);
				group = new ArrayList();
			}
			group.add(e);
		}

		@SuppressWarnings({ "unchecked" })
		public void doBreak(String key) {
			for (int index = 0; index < keys.length; index++) {
				/*
				 * キーが変わらない
				 */
				if (key.equals(keys[index]) == false)
					continue;

				result.add(group);
				group = null;
				groupKeys = null;
				break;
			}
		}
	}
}
