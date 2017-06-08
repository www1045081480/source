package com.np.base.dml.cmn;

import java.util.Comparator;

import com.np.base.models.JoinedModels;
import com.np.base.reflect.PojoReflector;

public class ObjectLinkComparator implements IArrayComparator {
	private String keyTra;
	private String keyMas;
	protected AccessMethod accessTra;
	protected AccessMethod accessMas;
	@SuppressWarnings({ "rawtypes", "unused" })
	private Class classTra;
	@SuppressWarnings({ "rawtypes", "unused" })
	private Class classMas;
	protected int diffPoint;
	@SuppressWarnings("rawtypes")
	protected Comparator comparator;

	public ObjectLinkComparator(String keyTra, String keyMas) {
		this.keyTra = keyTra;
		this.keyMas = keyMas;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compare(Object o1, Object o2) {
		int comp = 0;
		diffPoint = 0;
		try {
			// logger.info("compare: " + keys[i] + " => " +
			// accessMethods[i].comparator.getClass()) ;
			Object v1 = getTar(o1);
			Object v2 = getMas(o2);

			if (comparator == null) {
				Class returnType = null;
				if (o1 != null)
					returnType = o1.getClass();
				else if (o2 != null)
					returnType = o2.getClass();

				if (returnType == null)
					return 0;
				initComparator(returnType);
			}

			comp = comparator.compare(v1, v2);
			return comp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object getTar(Object o1) {
		if (JoinedModels.class.isInstance(o1)) {
			return JoinedModels.class.cast(o1).getValue(keyTra);
		}
		if (accessTra == null) {
			accessTra = initAccesses(o1, keyTra);
			classTra = o1.getClass();
		}
		try {
			// logger.info("compare: " + keys[i] + " => " +
			// accessMethods[i].comparator.getClass()) ;
			Object v1 = accessTra.invoker.invoke(o1, ObjectComparator.NULL_ARGS);
			return v1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Object getMas(Object o2) {
		if (JoinedModels.class.isInstance(o2)) {
			return JoinedModels.class.cast(o2).getValue(keyMas);
		}

		if (accessMas == null) {
			accessMas = initAccesses(o2, keyMas);
			classMas = o2.getClass();
		}
		try {
			// logger.info("compare: " + keys[i] + " => " +
			// accessMethods[i].comparator.getClass()) ;
			Object v2 = accessMas.invoker.invoke(o2, ObjectComparator.NULL_ARGS);
			return v2;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int getDiffPoint() {
		return diffPoint;
	}

	@SuppressWarnings("rawtypes")
	protected AccessMethod initAccesses(Object o, String key) {
		AccessMethod accessMethod = new AccessMethod();
		Class clazz = o.getClass();

		accessMethod.invoker = PojoReflector.getPropertyGetter(clazz, key);
		Class returnType = accessMethod.invoker.getReturnType();
		accessMethod.comparator = initComparator(returnType);
		return accessMethod;
	}

	@SuppressWarnings("rawtypes")
	private Comparator initComparator(Class returnType) {
		if (returnType.isPrimitive()) {

			if (ObjectComparator.PRIMITIVE_COMPARATOS.containsKey(returnType))
				comparator = (Comparator) ObjectComparator.PRIMITIVE_COMPARATOS.get(returnType);
			else
				throw new RuntimeException("Not Supported : " + returnType);
		} else if (returnType.isArray()) {
			if (returnType.getComponentType().isPrimitive()) {
				if (ObjectComparator.PRIMITIVE_ARRAY_COMPARATOS.containsKey(returnType.getComponentType()))
					comparator = (Comparator) ObjectComparator.PRIMITIVE_ARRAY_COMPARATOS
							.get(returnType.getComponentType());
				else
					comparator = null;
			} else
				throw new RuntimeException("Not Supported : " + returnType);
		} else
			comparator = new ClassComparator();

		return comparator;
	}
}
