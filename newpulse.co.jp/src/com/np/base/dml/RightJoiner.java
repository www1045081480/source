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

public class RightJoiner extends Matcher {
	private static Log log = LogFactory.getLog(Matcher.class);

	/*
	 * logical conjunction
	 */
	@SuppressWarnings("rawtypes")
	public static List<JoinedModels> rightJoin(List transaction, List master) {
		return rightJoin(transaction, master, JoinedModels.class);
	}

	@SuppressWarnings("rawtypes")
	public static List<JoinedModels> rightJoin(List transaction, List master, JoinKey joinKey) {
		// return innerJoin(transaction, master, JoinedModels.class);
		return rightJoin(transaction, master, joinKey, JoinedModels.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> rightJoin(List transaction, List master, final Class<T> type) {
		if (UList.isEmpty(transaction) || UList.isEmpty(master))
			return new ArrayList<T>();

		ModelMapper traMapper = PojoUtils.getMapper(UList.first(transaction).getClass());
		ModelMapper masterMapper = PojoUtils.getMapper(UList.first(master).getClass());
		JoinKey joinKey = traMapper.getJoinKey(masterMapper);
		if (joinKey == null)
			throw new IllegalStateException();

		return (List<T>) rightJoin(transaction, master, joinKey, type);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> rightJoin(List transaction, List master, JoinKey joinKey, Class<T> type) {
		// return innerJoin(transaction, master, JoinedModels.class);
		IMatchingProcedure mp;
		if (JoinedModels.class == type)
			mp = new JoinModelMatchingProcedure();
		else
			mp = new GeneralMatchingProcedure<T>(type);

		return (List<T>) rightJoin(transaction, joinKey.getLeftKey(), master, joinKey.getRightKey(), mp);
	}

	@SuppressWarnings("rawtypes")
	public static List<JoinedModels> rightJoin(List transaction, List master, String key) {
		return rightJoin(transaction, master, new String[] { key }, JoinedModels.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> rightJoin(List transaction, List master, String[] keys, final Class<T> type) {
		IMatchingProcedure mp;
		if (JoinedModels.class == type)
			mp = new JoinModelMatchingProcedure();
		else
			mp = new GeneralMatchingProcedure<T>(type);

		return (List<T>) rightJoin(transaction, master, keys, mp);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> rightJoin(List transaction, List master, String[] keys, IModelFactory factory) {
		IMatchingProcedure mp = new FactoryMatchingProcedure(factory);
		return (List<T>) rightJoin(transaction, master, keys, mp);
	}

	@SuppressWarnings("rawtypes")
	public static Object rightJoin(List transaction, String keyTra, List master, String keyMas, IMatchingProcedure mp) {
		Comparator comparator = new ObjectLinkComparator(keyTra, keyMas);
		Sortter.sort(transaction, keyTra);
		Sortter.sort(master, keyMas);
		merge(transaction, master, comparator, mp);
		return mp.doFinish();
	}

	@SuppressWarnings("rawtypes")
	public static Object rightJoin(List transaction, List master, String[] keys, IMatchingProcedure mp) {
		Comparator comparator = new ObjectComparator(keys);
		Sortter.sort(transaction, comparator);
		Sortter.sort(master, comparator);
		merge(transaction, master, comparator, mp);
		return mp.doFinish();
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
			return true;
		}

		@Override
		public void doNoMatch(Object tra) {
			if (traMapper == null)
				traMapper = PojoUtils.getMapper(tra);

			try {
				T obj = type.newInstance();
				for (String name : resultMapper.getColumns()) {
					Object value = null;
					if (traMapper.containsProperty(name)) {
						value = traMapper.getValue(tra, name);
					}
					if (value != null) {
						resultMapper.setValue(obj, name, value);
					}
				}
				result.add(obj);
			} catch (Exception e) {
				log.error(e);
			}
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
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void doNoMatch(Object tra) {
			R obj = factory.create((T) tra, null);
			if (obj != null)
				result.add(obj);
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
			return true;
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

	@SuppressWarnings("unused")
	private static class DifferenceProcedure implements IMatchingProcedure {
		private List<Object> result = new ArrayList<Object>();

		@Override
		public boolean doMatching(Object tra, Object mas) {
			return false;
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
