package com.np.base.dml.cmn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.models.JoinedModels;
import com.np.base.reflect.PojoReflector;

@SuppressWarnings("rawtypes")
public class ObjectComparator implements IArrayComparator {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(ObjectComparator.class);

	public static final Object[] NULL_ARGS = new Object[0];

	protected static Map<Class, Comparator> PRIMITIVE_COMPARATOS;

	protected static Map<Class, Comparator> PRIMITIVE_ARRAY_COMPARATOS;

	static {
		PRIMITIVE_COMPARATOS = new HashMap<Class, Comparator>();
		PRIMITIVE_COMPARATOS.put(Integer.TYPE, new IntComparator());
		PRIMITIVE_COMPARATOS.put(Long.TYPE, new LongComparator());
		PRIMITIVE_COMPARATOS.put(Double.TYPE, new DoubleComparator());

		PRIMITIVE_ARRAY_COMPARATOS = new HashMap<Class, Comparator>();
		PRIMITIVE_ARRAY_COMPARATOS.put(Byte.TYPE, new ByteArrayComparator());
	}

	private String[] keys;

	private int[] orders;

	protected Map<Class, AccessMethod[]> accessMethods = new HashMap<Class, AccessMethod[]>();

	protected int diffPoint;

	public ObjectComparator(String[] keys, int[] orders) {
		this.keys = keys;
		this.orders = orders;
	}

	public ObjectComparator(String[] keys) {
		this.keys = keys;
	}

	public String[] getKeys() {
		return this.keys;
	}

	public int[] getOrders() {
		return this.orders;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) {
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;

		if (Comparable.class.isInstance(o1) && Comparable.class.isInstance(o2))
			return Comparable.class.cast(o1).compareTo(Comparable.class.cast(o2));

		if (JoinedModels.class.isInstance(o1) && JoinedModels.class.isInstance(o2))
			return compare(JoinedModels.class.cast(o1), JoinedModels.class.cast(o2));

		Object[] values1 = getValues(o1);
		Object[] values2 = getValues(o2);

		int comp = 0;
		diffPoint = -1;
		ObjectComparator comparator = new ObjectComparator(keys);
		/*
		 * 大分類ー＞小分類
		 */
		for (int i = 0; i < keys.length; i++) {
			try {
				Object v1 = values1[i];
				Object v2 = values2[i];
				// comp = access1[i].comparator.compare(v1, v2);
				comp = comparator.compare(v1, v2);
				if (comp != 0) {
					diffPoint = i;
					if ((orders != null) && (i < orders.length))
						return comp * orders[i];
					else
						return comp;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return comp;
	}

	private Object[] getValues(Object obj) {
		Object[] values = new Object[keys.length];
		if (JoinedModels.class.isInstance(obj)) {
			JoinedModels models = JoinedModels.class.cast(obj);
			for (int i = 0; i < keys.length; i++) {
				values[i] = models.getValue(keys[i]);
			}
		} else {
			AccessMethod[] accesses = initAccesses(obj);
			for (int i = 0; i < keys.length; i++) {
				try {
					values[i] = accesses[i].invoker.invoke(obj, NULL_ARGS);
				} catch (Exception e) {
					// System.out.println("obj=" + obj);
					// System.out.println("keys[" + i + "]=" + keys[i]);
					throw new RuntimeException(e);
				}
			}
		}
		return values;
	}

	private int compare(JoinedModels o1, JoinedModels o2) {
		ObjectComparator comparator = new ObjectComparator(keys);
		int comp = 0;
		diffPoint = -1;
		/*
		 * 大分類ー＞小分類
		 */
		for (int i = 0; i < keys.length; i++) {
			try {
				Object v1 = o1.getValue(keys[i]);
				Object v2 = o2.getValue(keys[i]);
				comp = comparator.compare(v1, v2);
				if (comp != 0) {
					diffPoint = i;
					if ((orders != null) && (i < orders.length))
						return comp * orders[i];
					else
						return comp;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return comp;
	}

	public int getDiffPoint() {
		return diffPoint;
	}

	protected AccessMethod[] initAccesses(Object o) {
		if (JoinedModels.class.isInstance(o))
			return new AccessMethod[0];

		Class type = o.getClass();
		if (accessMethods.containsKey(type) == false) {
			AccessMethod[] accesses = new AccessMethod[keys.length];
			Class clazz = o.getClass();
			// Method[] methods = clazz.getMethods();
			for (int i = 0; i < accesses.length; i++) {
				accesses[i] = new AccessMethod();
				accesses[i].invoker = PojoReflector.getPropertyGetter(clazz, keys[i]);
				if (accesses[i].invoker == null)
					continue;
				Class returnType = accesses[i].invoker.getReturnType();
				if (returnType.isPrimitive()) {
					// accessMethods[i].comparator = new PrimitiveComparator();
					if (PRIMITIVE_COMPARATOS.containsKey(returnType))
						accesses[i].comparator = PRIMITIVE_COMPARATOS.get(returnType);
					else
						throw new RuntimeException("Not Supported : " + returnType);
				} else if (returnType.isArray()) {
					if (returnType.getComponentType().isPrimitive()) {
						if (PRIMITIVE_ARRAY_COMPARATOS.containsKey(returnType.getComponentType()))
							accesses[i].comparator = PRIMITIVE_ARRAY_COMPARATOS.get(returnType.getComponentType());
						else
							accesses[i].comparator = null;
					} else
						throw new RuntimeException("Not Supported : " + returnType);
				} else
					accesses[i].comparator = new ClassComparator();
			}
			accessMethods.put(type, accesses);
		}

		return accessMethods.get(type);
	}

}
