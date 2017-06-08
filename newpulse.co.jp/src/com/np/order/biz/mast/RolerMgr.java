package com.np.order.biz.mast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.Sortter;
import com.np.base.orm.Criteria;
import com.np.base.utils.UTrace;
import com.np.order.models.Rolers;

public class RolerMgr {
	/*
	 * TODO:Cache
	 */
	private static List<Rolers> rolers;
	private static Integer maxPriority = -1;
	private static Rolers president;
	private static Rolers vicePresident;
	private static Rolers account;
	private static Rolers seller;
	private static Rolers sellerDistribution;

	private static Rolers distribution;
	private static Rolers administrator;

	public static boolean registerUser(Rolers roler) throws Exception {
		/*
		 * TODO:存在チェック
		 */

		/*
		 * 自動採番
		 */
		Long rolerId = SequenceMgr.nextSeq(Rolers.class);
		roler.setRolerId(rolerId);

		/*
		 * DBにInsert
		 */
		TxMgr.insert(roler);
		return true;
	}

	public static boolean modifyUser(Rolers roler) throws Exception {
		TxMgr.update(roler);
		return true;
	}

	public static boolean removeUser(Long rolerId) throws Exception {
		Rolers roler = new Rolers();
		roler.setRolerId(rolerId);
		TxMgr.delete(roler);
		return true;
	}

	public static List<Rolers> searchRolers(Rolers filter) {
		init();
		List<Rolers> result = new ArrayList<Rolers>();
		for (Rolers roler : rolers) {
			if (roler.getRolerId().equals(filter.getRolerId()))
				result.add(roler);
			else if (roler.getCnName().equals(filter.getCnName()))
				result.add(roler);
			else if (roler.getJpName().equals(filter.getJpName()))
				result.add(roler);
			else if (roler.getEnName().equals(filter.getEnName()))
				result.add(roler);
		}

		return result;

	}

	public static List<Rolers> searchRolers(List<Long> ids) {
		init();
		List<Rolers> result = new ArrayList<Rolers>();
		for (Rolers roler : rolers) {
			if (ids.contains(roler.getRolerId()))
				result.add(roler);
		}

		return result;
	}

	public static String searchRolersAsString(List<Long> ids) {
		List<Rolers> rolers = RolerMgr.searchRolers(ids);
		Sortter.sort(rolers, Rolers.Priority);

		StringBuffer status = new StringBuffer();
		for (Rolers roler : rolers) {
			if (status.length() > 0)
				status.append(" ");
			status.append(roler.getJpName());
		}

		return status.toString();
	}

	public static Rolers searchRoler(Long rolerId) {
		init();
		for (Rolers roler : rolers) {
			if (roler.getRolerId().equals(rolerId))
				return roler;
		}
		return null;
	}

	public static List<Long> getPresidentIds() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(RolerMgr.getRolerForVicePresident().getRolerId());
		ids.add(RolerMgr.getRolerForPresident().getRolerId());
		return ids;
	}

	public static Integer getMaxPriorityRolerId() {
		init();
		return maxPriority;
	}

	public static Rolers getRolerForPresident() {
		init();
		return president;
	}

	public static Rolers getRolerForVicePresident() {
		init();
		return vicePresident;
	}

	public static Rolers getRolerForAccount() {
		init();
		return account;
	}

	public static Rolers getRolerForSeller() {
		init();
		return seller;
	}

	public static Rolers getRolerForDistribution() {
		init();
		return distribution;
	}

	public static Rolers getRolerForSellerDistribution() {
		init();
		return sellerDistribution;
	}

	public static Rolers getRolerForAdministrator() {
		init();
		return administrator;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static synchronized void init() {
		if (rolers != null)
			return;
		try {
			Criteria<Rolers> criteria = new Criteria(Rolers.class);
			rolers = criteria.list();

			if (rolers.isEmpty()) {
				long id = 1L;
				Rolers roler;

				roler = create("営業", 1);
				roler.setRolerId(id++);
				rolers.add(roler);
				seller = roler;

				roler = create("物流", 1);
				roler.setRolerId(id++);
				rolers.add(roler);
				distribution = roler;

				roler = create("財務", 1);
				roler.setRolerId(id++);
				rolers.add(roler);
				account = roler;

				roler = create("副社長", 888);
				roler.setRolerId(id++);
				rolers.add(roler);

				roler = create("社長", 999);
				roler.setRolerId(id++);
				rolers.add(roler);

				administrator = create("管理员", 777);
				administrator.setRolerId(id++);

				roler = create("営業物流", 1);
				roler.setRolerId(id++);
				rolers.add(roler);
				sellerDistribution = roler;

			}

			Collections.sort(rolers,
					(Rolers roler1, Rolers roler2) -> roler1.getPriority().compareTo(roler2.getPriority()));
			president = rolers.get(rolers.size() - 1);
			vicePresident = rolers.get(rolers.size() - 2);

			maxPriority = 0;
			for (Rolers roler : rolers) {
				int priority = roler.getPriority();
				maxPriority = Math.max(maxPriority, priority);
			}
		} catch (Exception e) {
			UTrace.info(e);
		}
	}

	private static Rolers create(String name, int priority) {
		Rolers roler = new Rolers();
		roler.setCnName(name);
		roler.setJpName(name);
		roler.setEnName(name);
		roler.setPriority(priority);

		return roler;
	}
}
