package com.np.base.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.np.base.orm.ModelMapper;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UString;

public class ViewModelMapper {

	/*
	 * 画面の入力からモデルを作成
	 * 
	 * TODO:File Upload
	 * 
	 * TODO:単項目チェック
	 * 
	 */
	public static <T> T toModel(Class<T> type) throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();

		ModelMapper mapper = PojoUtils.getMapper(type);

		T obj = type.newInstance();
		String currency = null;
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = req.getParameter(name);
			if (name.equalsIgnoreCase("currency")) {
				currency = value;
			}
		}

		names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = req.getParameter(name);
			mapper.setValueByString(obj, name, value, currency);
		}
		return obj;
	}

	/*
	 * 画面の入力からリストを作成
	 * 
	 * Naming Ruler：
	 * 
	 * モデル名称 & _ & インデックス（一番目」は1） ー＞ ビュー名称
	 * 
	 * TODO:単項目チェック
	 * 
	 */
	public static <T> List<T> toModels(Class<T> type) throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();

		List<T> list = new ArrayList<T>();

		Enumeration<String> nameEnum = req.getParameterNames();

		Map<Integer, List<String>> names = new HashMap<Integer, List<String>>();
		while (nameEnum.hasMoreElements()) {
			String name = nameEnum.nextElement();
			int pos = name.lastIndexOf('_');
			if (pos == -1)
				continue;
			String index = name.substring(pos + 1);
			try {
				Integer idx = Integer.valueOf(index);
				name = name.substring(0, pos);
				if (names.containsKey(idx) == false)
					names.put(idx, new ArrayList<String>());
				names.get(idx).add(name);
			} catch (Exception e) {
				continue;
			}
		}

		ModelMapper mapper = PojoUtils.getMapper(type);
		Integer[] idxs = names.keySet().toArray(new Integer[0]);
		Arrays.sort(idxs);
		for (Integer idx : idxs) {
			List<String> attrs = names.get(idx);
			T obj = type.newInstance();
			String currency = null;
			for (String name : attrs) {
				String value = req.getParameter(name + "_" + idx);
				if (name.equalsIgnoreCase("currency"))
					currency = value;
			}
			for (String name : attrs) {
				String value = req.getParameter(name + "_" + idx);
				mapper.setValueByString(obj, name, value, currency);
			}
			list.add(obj);
		}

		return list;
	}

	public static Long getLong(String name) throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		String value = req.getParameter(name);
		return (UString.isEmpty(value)) ? null : Long.valueOf(value);
	}

	public static List<Long> getLons(String name) throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		List<Long> result = new ArrayList<Long>();

		Enumeration<String> nameEnum = req.getParameterNames();
		while (nameEnum.hasMoreElements()) {
			String paramName = nameEnum.nextElement();
			if (paramName.startsWith(name + "_")) {
				String value = req.getParameter(name);
				if (UString.notEmpty(value))
					result.add(Long.valueOf(value));
			}
		}
		return result;
	}

}
