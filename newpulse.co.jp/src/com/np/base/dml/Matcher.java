package com.np.base.dml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.dml.cmn.ObjectComparator;
import com.np.base.dml.cmn.ObjectLinkComparator;
import com.np.base.models.JoinKey;
import com.np.base.models.JoinedModels;
import com.np.base.orm.ModelMapper;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UList;

public class Matcher {
	private static Log log = LogFactory.getLog(Matcher.class);

	/*
	 * 非含意(not implication)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> difference(List<T> transaction, List master) {
		if (UList.isEmpty(transaction) || UList.isEmpty(master))
			return transaction;

		ModelMapper traMapper = PojoUtils.getMapper(UList.first(transaction).getClass());
		ModelMapper masterMapper = PojoUtils.getMapper(UList.first(master).getClass());
		JoinKey joinKey = traMapper.getJoinKey(masterMapper);
		if (joinKey == null)
			throw new IllegalStateException();
		return (List<T>) innerJoin(transaction, joinKey.getLeftKey(), master, joinKey.getRightKey(),
				new DifferenceProcedure());
	}

	/*
	 * 非含意(not implication)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> difference(List<T> transaction, List master, String key) {
		if (UList.isEmpty(transaction) || UList.isEmpty(master))
			return transaction;

		return (List<T>) innerJoin(transaction, key, master, key, new DifferenceProcedure());
	}

	/*
	 * logical conjunction
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List<JoinedModels> innerJoin(List transaction, List master) {
		return innerJoin(transaction, master, JoinedModels.class);
	}

	@SuppressWarnings({ "rawtypes" })
	public static List<JoinedModels> innerJoin(List transaction, List master, JoinKey joinKey) {
		// return innerJoin(transaction, master, JoinedModels.class);
		return innerJoin(transaction, master, joinKey, JoinedModels.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> innerJoin(List transaction, List master, final Class<T> type) {
		if (UList.isEmpty(transaction) || UList.isEmpty(master))
			return new ArrayList<T>();

		ModelMapper traMapper = PojoUtils.getMapper(UList.first(transaction).getClass());
		ModelMapper masterMapper = PojoUtils.getMapper(UList.first(master).getClass());
		JoinKey joinKey = traMapper.getJoinKey(masterMapper);
		if (joinKey == null)
			throw new IllegalStateException();

		return (List<T>) innerJoin(transaction, master, joinKey, type);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> innerJoin(List master, List transaction, JoinKey joinKey, Class<T> type) {
		// return innerJoin(transaction, master, JoinedModels.class);
		IMatchingProcedure mp;
		if (JoinedModels.class == type)
			mp = new JoinModelMatchingProcedure();
		else
			mp = new GeneralMatchingProcedure<T>(type);

		return (List<T>) innerJoin(master, joinKey.getLeftKey(), transaction, joinKey.getRightKey(), mp);
	}

	@SuppressWarnings("rawtypes")
	public static List<JoinedModels> innerJoin(List master, List transaction, String key) {
		return innerJoin(master, transaction, new String[] { key }, JoinedModels.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> innerJoin(List master, List transaction, String[] keys, final Class<T> type) {
		IMatchingProcedure mp;
		if (JoinedModels.class == type)
			mp = new JoinModelMatchingProcedure();
		else
			mp = new GeneralMatchingProcedure<T>(type);

		return (List<T>) innerJoin(master, transaction, keys, mp);
	}

	/*
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> innerJoin(List master, List transaction, String[] keys, IModelFactory factory) {
		IMatchingProcedure mp = new FactoryMatchingProcedure(factory);
		return (List<T>) innerJoin(master, transaction, keys, mp);
	}

	@SuppressWarnings("rawtypes")
	public static Object innerJoin(List master, String keyTra, List transaction, String keyMas, IMatchingProcedure mp) {
		Comparator comparator = new ObjectLinkComparator(keyTra, keyMas);
		Sortter.sort(master, keyTra);
		Sortter.sort(transaction, keyMas);
		merge(master, transaction, comparator, mp);
		return mp.doFinish();
	}

	@SuppressWarnings("rawtypes")
	public static Object innerJoin(List master, List transaction, String[] keys, IMatchingProcedure mp) {
		Comparator comparator = new ObjectComparator(keys);
		Sortter.sort(transaction, comparator);
		Sortter.sort(master, comparator);
		merge(master, transaction, comparator, mp);
		return mp.doFinish();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void merge(List master, List transaction, Comparator comparator, IMatchingProcedure proc) {
		int numOfTra = master.size();
		int numOfMas = transaction.size();

		if (numOfMas == 0) {
			for (Object tra : master) {
				proc.doNoMatch(tra);
			}
			return;
		}

		if (numOfTra == 0) {
			for (Object mas : transaction) {
				proc.doSkipMaster(mas);
			}
			return;
		}

		boolean isMasterCallbacked = false;
		int masPos = 0;
		@SuppressWarnings("unused")
		int xx = 0;
		TRA: for (int t = 0; t < numOfTra; t++) {
			Object tra = master.get(t);
			xx = t;
			MASTER: for (; masPos < numOfMas;) {
				Object mas = transaction.get(masPos);

				int comp = comparator.compare(tra, mas);

				if (comp == 0) {
					boolean readNextTra = proc.doMatching(tra, mas);
					isMasterCallbacked = true;
					if (readNextTra) {
						continue TRA;
					} else {
						masPos++;
						continue MASTER;
					}
				} else if (comp > 0) {
					if (!isMasterCallbacked) {
						proc.doSkipMaster(mas);
						isMasterCallbacked = true;
					}
					masPos++;
					isMasterCallbacked = false;
					continue MASTER;
				} else {
					proc.doNoMatch(tra);
					continue TRA;
				}
			}
			if (masPos == numOfMas) {
				for (; t < numOfTra; t++) {
					tra = master.get(t);
					proc.doNoMatch(tra);
				}
				break TRA;
			}
		}
	}

	private static class GeneralMatchingProcedure<T> implements IMatchingProcedure {
		private Class<T> type;
		private List<T> result = new ArrayList<T>();
		private ModelMapper resultMapper;
		private ModelMapper traMapper;
		private ModelMapper masterMapper;

		public GeneralMatchingProcedure(Class<T> type) {
			this.type = type;
			resultMapper = PojoUtils.getMapper(type);
		}

		@Override
		public boolean doMatching(Object tra, Object mas) {
			if (traMapper == null)
				traMapper = PojoUtils.getMapper(tra);
			if (masterMapper == null)
				masterMapper = PojoUtils.getMapper(mas);

			try {
				T obj = type.newInstance();
				for (String name : resultMapper.getColumns()) {
					Object value = null;
					if (traMapper.containsProperty(name)) {
						value = traMapper.getValue(tra, name);
					} else if (masterMapper.containsProperty(name)) {
						value = masterMapper.getValue(mas, name);
					}
					if (value != null) {
						resultMapper.setValue(obj, name, value);
					}
				}
				result.add(obj);
			} catch (Exception e) {
				log.error(e);
			}
			return false;
		}

		@Override
		public void doNoMatch(Object tra) {
			// TODO Auto-generated method stub
		}

		@Override
		public void doSkipMaster(Object mas) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object doFinish() {
			// TODO Auto-generated method stub
			return result;
		}

	};

	private static class FactoryMatchingProcedure<R, T, M> implements IMatchingProcedure {
		private IModelFactory<R, T, M> factory;
		private List<R> result = new ArrayList<R>();

		public FactoryMatchingProcedure(IModelFactory<R, T, M> factory) {
			this.factory = factory;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean doMatching(Object tra, Object mas) {
			R obj = factory.create((T) tra, (M) mas);
			if (obj != null)
				result.add(obj);
			return false;
		}

		@Override
		public void doNoMatch(Object tra) {
			// TODO Auto-generated method stub
		}

		@Override
		public void doSkipMaster(Object mas) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object doFinish() {
			// TODO Auto-generated method stub
			return result;
		}

	};

	private static class JoinModelMatchingProcedure implements IMatchingProcedure {
		@SuppressWarnings("unused")
		private Class<JoinedModels> type;
		private List<JoinedModels> result = new ArrayList<JoinedModels>();
		private ModelMapper traMapper;
		private ModelMapper masterMapper;
		private String[] joinedNames;

		public JoinModelMatchingProcedure() {
			this.type = JoinedModels.class;
		}

		@Override
		public boolean doMatching(Object tra, Object mas) {
			if (traMapper == null) {
				traMapper = PojoUtils.getMapper(tra);
				masterMapper = PojoUtils.getMapper(mas);

				List<String> names = new ArrayList<String>();
				for (String name : traMapper.getNames()) {
					if (name.equalsIgnoreCase(ModelMapper.RegTime))
						continue;
					if (name.equalsIgnoreCase(ModelMapper.UpdTime))
						continue;
					names.add(name);
				}
				for (String name : masterMapper.getNames()) {
					if (name.equalsIgnoreCase(ModelMapper.RegTime))
						continue;
					if (name.equalsIgnoreCase(ModelMapper.UpdTime))
						continue;
					/*
					 * TODO:重複名称
					 */
					names.add(name);
				}
				joinedNames = names.toArray(new String[0]);
			}

			try {
				JoinedModels obj = new JoinedModels(joinedNames);
				/*
				 * TEST
				 */
				boolean test = true;
				if (test) {
					int index = 0;
					for (String name : traMapper.getNames()) {
						if (name.equalsIgnoreCase(ModelMapper.RegTime))
							continue;
						if (name.equalsIgnoreCase(ModelMapper.UpdTime))
							continue;
						obj.setValue(index++, traMapper.getValue(tra, name));
					}
					for (String name : masterMapper.getNames()) {
						if (name.equalsIgnoreCase(ModelMapper.RegTime))
							continue;
						if (name.equalsIgnoreCase(ModelMapper.UpdTime))
							continue;
						obj.setValue(index++, masterMapper.getValue(mas, name));
					}
				} else {
					for (String name : joinedNames) {
						Object value = null;
						if (traMapper.containsProperty(name)) {
							value = traMapper.getValue(tra, name);
						} else if (masterMapper.containsProperty(name)) {
							value = masterMapper.getValue(mas, name);
						}
						if (value != null) {
							obj.setValue(name, value);
						}
					}
				}
				result.add(obj);
			} catch (Exception e) {
				log.error(e);
			}
			return false;
		}

		@Override
		public void doNoMatch(Object tra) {
			// TODO Auto-generated method stub
		}

		@Override
		public void doSkipMaster(Object mas) {
			// TODO Auto-generated method stub
		}

		@Override
		public Object doFinish() {
			// TODO Auto-generated method stub
			return result;
		}

	};

	private static class DifferenceProcedure implements IMatchingProcedure {
		private List<Object> result = new ArrayList<Object>();

		@Override
		public boolean doMatching(Object tra, Object mas) {
			return true;
		}

		@Override
		public void doNoMatch(Object tra) {
			result.add(tra);
		}

		@Override
		public void doSkipMaster(Object mas) {
		}

		@Override
		public Object doFinish() {
			return result;
		}

	}
}
