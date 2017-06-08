package com.np.order.biz.mast;

import java.util.Collections;
import java.util.List;

import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.PojoCollections;
import com.np.base.dml.Sortter;
import com.np.base.orm.Criteria;
import com.np.base.utils.UString;
import com.np.order.models.Item;
import com.np.order.objects.ItemNames;

public class ItemMgr {
	/*
	 * TODO: Cache
	 */
	private static volatile List<Item> cachedItems;

	static {
		init();
	}

	public static boolean register(Item item) throws Exception {
		/*
		 * TODO:存在チェック
		 */

		/*
		 * 自動採番
		 */
		item.setJpNameSearch(item.getJpName() + "/" + item.getJpDesc());
		item.setCnNameSearch(item.getCnName() + "/" + item.getCnDesc());
		item.setEnNameSearch(item.getEnName() + "/" + item.getEnDesc());

		if (exists(item))
			throw new IllegalStateException("Item has exist");

		Long itemId = SequenceMgr.nextSeq(Item.class);
		item.setItemId(itemId);

		/*
		 * DBにInsert
		 */
		TxMgr.insert(item);
		cachedItems = null;
		init();
		return true;
	}

	public static boolean modify(Item item) throws Exception {
		item.setJpNameSearch(item.getJpName() + "/" + item.getJpDesc());
		item.setCnNameSearch(item.getCnName() + "/" + item.getCnDesc());
		item.setEnNameSearch(item.getEnName() + "/" + item.getEnDesc());

		if (exists(item))
			throw new IllegalStateException("Item has exist");

		TxMgr.update(item);
		cachedItems = null;
		init();
		return true;
	}

	public static boolean delete(Long itemId) throws Exception {
		Item item = new Item();
		item.setItemId(itemId);
		TxMgr.delete(item);
		cachedItems = null;
		init();
		return true;
	}

	public static List<Item> search(Item item) throws Exception {
		List<Item> list = Query.query(item);
		/*
		 * 大分類、小分類、更新日時、作成日時
		 */
		String[] sortKeys = { Item.CategoryType, Item.Family, Item.UpdTime, Item.RegTime };
		Sortter.sort(list, sortKeys);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ItemNames searchItemSearchNames() {
		Criteria<Item> criteria = new Criteria(Item.class);

		criteria.addSelectedColumn(Item.CnNameSearch);
		criteria.addSelectedColumn(Item.JpNameSearch);
		criteria.addSelectedColumn(Item.EnNameSearch);

		List<Item> list = criteria.list();

		ItemNames itemNames = new ItemNames();

		List<String> names = PojoCollections.getKeyList(list, Item.CnNameSearch);
		Collections.sort(names);
		itemNames.setItemCnNames(names);

		names = PojoCollections.getKeyList(list, Item.JpNameSearch);
		Collections.sort(names);
		itemNames.setItemJpNames(names);

		names = PojoCollections.getKeyList(list, Item.EnNameSearch);
		Collections.sort(names);
		itemNames.setItemEnNames(names);

		return itemNames;
	}

	public static Item get(Long itemId) {
		init();
		for (Item item : cachedItems) {
			if (item.getItemId().equals(itemId))
				return item;
		}
		return null;
	}

	public static boolean exists(Long itemId) {
		return get(itemId) != null;
	}

	public static Item getByNameAndPartsCd(String itemName, String partsCd) {
		init();

		String name = itemName + "/" + partsCd;
		for (Item item : cachedItems) {
			if (name.equals(item.getCnNameSearch()))
				return item;
			if (name.equals(item.getEnNameSearch()))
				return item;
			if (name.equals(item.getJpNameSearch()))
				return item;
		}
		return null;
	}

	public static Item getByName(String itemName) {
		init();
		for (Item item : cachedItems) {
			if (itemName.equals(item.getCnName()))
				return item;
			if (itemName.equals(item.getEnName()))
				return item;
			if (itemName.equals(item.getJpName()))
				return item;
		}
		return null;
	}

	public static Item getSearch(String itemName) {
		init();
		for (Item item : cachedItems) {
			if (itemName.equals(item.getCnNameSearch()))
				return item;
			if (itemName.equals(item.getEnNameSearch()))
				return item;
			if (itemName.equals(item.getJpNameSearch()))
				return item;
		}
		return null;
	}

	public static boolean exists(Item item) {
		init();

		long itemId = (item.getItemId() == null) ? -1 : item.getItemId().longValue();
		for (Item existItem : cachedItems) {
			if (existItem.getItemId().longValue() == itemId)
				continue;

			if (UString.equals(existItem.getCnNameSearch(), item.getCnNameSearch()))
				return true;
			if (UString.equals(existItem.getEnNameSearch(), item.getEnNameSearch()))
				return true;
			if (UString.equals(existItem.getJpNameSearch(), item.getJpNameSearch()))
				return true;
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static synchronized void init() {
		if (cachedItems != null)
			return;
		Criteria<Item> criteria = new Criteria(Item.class);
		List<Item> list = criteria.list();
		cachedItems = list;
	}
}
