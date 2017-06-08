package com.np.base.utils;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.models.JoinedModels;
import com.np.base.orm.ModelMapper;
import com.np.base.reflect.PojoReflector;
import com.np.base.reflect.PojoUtils;
import com.np.order.Money;

public class UModel {
	private static Log logger = LogFactory.getLog(UModel.class);

	@SuppressWarnings("rawtypes")
	public static boolean compareModel(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null)
			return false;

		if (obj1.getClass() != obj2.getClass())
			return false;
		try {
			Class type = obj1.getClass();
			ModelMapper mapper = PojoUtils.getMapper(type);
			List<String> names = mapper.getNames();

			for (String name : names) {
				Object value1, value2;
				if (JoinedModels.class == type) {
					value1 = JoinedModels.class.cast(obj1).getValue(name);
					value2 = JoinedModels.class.cast(obj2).getValue(name);
				} else {
					Method getter = PojoReflector.getPropertyGetter(type, name);
					if (getter == null)
						continue;

					value1 = getter.invoke(obj1, PojoReflector.NULL_ARGS);
					value2 = getter.invoke(obj2, PojoReflector.NULL_ARGS);
				}
				int comp = compareObject(value1, value2);
				if (comp != 0)
					return false;
			}

			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static int compareObject(Object value1, Object value2) {
		if (value1 == null && value2 == null)
			return 0;
		if (value1 == null)
			return -1;
		if (value2 == null)
			return 1;

		if (Comparable.class.isInstance(value1) && Comparable.class.isInstance(value2))
			return Comparable.class.cast(value1).compareTo(Comparable.class.cast(value2));
		return String.valueOf(value1).compareTo(String.valueOf(value2));
	}

	@SuppressWarnings("rawtypes")
	public static <T> T copy(Object from, Class<T> toType, String... names) {
		if (from == null)
			return null;

		try {
			T to = toType.newInstance();

			Class fromType = from.getClass();
			if (names == null || names.length == 0) {
				ModelMapper mapper = PojoUtils.getMapper(toType);
				names = mapper.getNames().toArray(new String[0]);
			}
			for (String name : names) {
				Object value;
				if (JoinedModels.class == fromType) {
					value = JoinedModels.class.cast(from).getValue(name);
				} else {
					Method getter = PojoReflector.getPropertyGetter(fromType, name);
					if (getter == null)
						continue;

					value = getter.invoke(from, PojoReflector.NULL_ARGS);
				}
				if (value == null)
					continue;
				Method setter = PojoReflector.getPropertySetter(toType, name);
				if (setter != null)
					try {
						Class paramType = setter.getParameters()[0].getType();
						if (paramType == Money.class) {
							if (Integer.class.isInstance(value)) {
								value = new Money(Integer.class.cast(value));
							} else if (Long.class.isInstance(value)) {
								value = new Money(Long.class.cast(value).intValue());
							}
						}
						setter.invoke(to, new Object[] { value });
					} catch (Exception e) {
						logger.trace(value + " " + setter);
						logger.trace(UDebugger.trace(e));
					}
			}

			return to;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T copy(T obj, String... names) {
		if (obj == null)
			return null;

		try {
			if (JoinedModels.class.isInstance(obj))
				return (T) copy(JoinedModels.class.cast(obj), names);

			Class type = obj.getClass();
			@SuppressWarnings("unused")
			T cloned = (T) type.newInstance();

			return (T) copy(obj, type, names);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static JoinedModels copy(JoinedModels obj, String... names) throws Exception {
		JoinedModels cloned = new JoinedModels(obj.getNames());
		for (String name : names) {
			cloned.setValue(name, obj.getValue(name));
		}
		return cloned;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T sum(T sum, T obj, String... names) {
		if (obj == null)
			return sum;

		try {
			if (JoinedModels.class.isInstance(obj))
				return (T) sum(JoinedModels.class.cast(sum), JoinedModels.class.cast(obj), names);

			Class type = obj.getClass();

			for (String name : names) {
				Method accessor = PojoReflector.getPropertyGetter(type, name);
				Object value = accessor.invoke(obj, PojoReflector.NULL_ARGS);
				if (value == null)
					continue;

				/*
				 * TODO:throw new IllegalArgumentException(name);
				 */
				if (Number.class.isInstance(value) == false)
					continue;

				Object total = accessor.invoke(sum, PojoReflector.NULL_ARGS);
				@SuppressWarnings("unused")
				Object t1 = total;
				total = sum(Number.class.cast(total), Number.class.cast(value));

				PojoReflector.getPropertySetter(type, name).invoke(sum, new Object[] { total });
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return sum;
	}

	public static JoinedModels sum(JoinedModels sum, JoinedModels obj, String... names) throws Exception {
		for (String name : names) {
			Object value = obj.getValue(name);
			if (value == null)
				continue;

			/*
			 * TODO:throw new IllegalArgumentException(name);
			 */
			if (Number.class.isInstance(value) == false)
				continue;

			Object total = sum.getValue(name);
			total = sum(Number.class.cast(total), Number.class.cast(value));

			sum.setValue(name, total);
		}
		return sum;
	}

	public static Number sum(Number total, Number value) {
		if (value == null)
			return total;

		if (total == null)
			return value;

		if (Long.class.isInstance(value)) {
			return Long.valueOf(Long.class.cast(value).longValue() + Long.class.cast(total).longValue());
		}
		if (Integer.class.isInstance(value)) {
			return Integer.valueOf(Integer.class.cast(value).intValue() + Integer.class.cast(total).intValue());
		}

		if (Float.class.isInstance(value)) {
			return Float.valueOf(Float.class.cast(value).floatValue() + Float.class.cast(total).floatValue());
		}
		if (Double.class.isInstance(value)) {
			return Double.valueOf(Double.class.cast(value).doubleValue() + Double.class.cast(total).doubleValue());
		}
		if (Money.class.isInstance(value)) {
			return Money.class.cast(total).add(Money.class.cast(value));
		}

		return total;
	}
}
