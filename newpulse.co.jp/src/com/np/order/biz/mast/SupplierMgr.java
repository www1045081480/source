package com.np.order.biz.mast;

import java.util.List;

import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UString;
import com.np.order.models.Supplier;

public class SupplierMgr {

	public static boolean register(Supplier supplier) throws Exception {
		if (exists(supplier))
			throw new IllegalStateException("Supplier has exist");

		/*
		 * TODO:存在チェック
		 */

		/*
		 * 自動採番
		 */
		Long supplierId = SequenceMgr.nextSeq(Supplier.class);
		supplier.setSupplierId(supplierId);

		/*
		 * DBにInsert
		 */
		TxMgr.insert(supplier);
		return true;
	}

	public static boolean modify(Supplier supplier) throws Exception {
		if (exists(supplier))
			throw new IllegalStateException("Supplier has exist");

		TxMgr.update(supplier);
		return true;
	}

	public static boolean exists(Supplier supplier) throws Exception {
		long supplierId = (supplier.getSupplierId() == null) ? -1 : supplier.getSupplierId().longValue();

		List<Supplier> suppliers = search(new Supplier());
		for (Supplier existSupplier : suppliers) {
			if (existSupplier.getSupplierId().longValue() == supplierId)
				continue;

			if (UString.equals(existSupplier.getCnName(), supplier.getCnName()))
				return true;
			if (UString.equals(existSupplier.getJpName(), supplier.getJpName()))
				return true;
			if (UString.equals(existSupplier.getEnName(), supplier.getEnName()))
				return true;
		}
		return false;
	}

	public static boolean delete(Long supplierId) throws Exception {
		Supplier supplier = new Supplier();
		supplier.setSupplierId(supplierId);
		TxMgr.delete(supplier);
		return true;
	}

	public static List<Supplier> search(Supplier supplier) throws Exception {
		List<Supplier> list = Query.query(supplier);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Long getId(String name) throws Exception {
		if (UString.isEmpty(name))
			return null;

		Criteria<Supplier> criteria = new Criteria(Supplier.class);
		criteria.and(Restrictions.eq(Supplier.JpName, name));
		criteria.or(Restrictions.eq(Supplier.EnShortName, name));
		Supplier result = criteria.get();
		return (result == null) ? null : result.getSupplierId();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Supplier searchSupplier(Long supplierId) {
		Criteria<Supplier> criteria = new Criteria(Supplier.class);
		criteria.and(Restrictions.eq(Supplier.SupplierId, supplierId));
		return criteria.get();
	}

	public static String getSupplierShortName(Long supplierId) {
		Supplier result = searchSupplier(supplierId);
		return (result == null) ? null : result.getEnShortName();
	}

	public static String getSupplierName(Long supplierId) {
		Supplier result = searchSupplier(supplierId);
		return (result == null) ? null : result.getJpName();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Supplier> getSuppliers(List<Long> supplierIds) {
		Criteria<Supplier> criteria = new Criteria(Supplier.class);
		criteria.and(Restrictions.in(Supplier.SupplierId, supplierIds));
		return criteria.list();
	}

}
