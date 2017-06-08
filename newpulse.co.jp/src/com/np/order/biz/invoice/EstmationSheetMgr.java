package com.np.order.biz.invoice;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.ITxProc;
import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.dml.Groupper;
import com.np.base.dml.Matcher;
import com.np.base.dml.PojoCollections;
import com.np.base.dml.Sortter;
import com.np.base.models.JoinedModels;
import com.np.base.orm.Criteria;
import com.np.base.orm.ModelMapper;
import com.np.base.orm.Restrictions;
import com.np.base.reflect.PojoUtils;
import com.np.base.utils.UDate;
import com.np.base.utils.UModel;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.IllegalPriorityEception;
import com.np.order.biz.mast.ItemMgr;
import com.np.order.biz.mast.RolerMgr;
import com.np.order.models.Credit;
import com.np.order.models.EstimationAccept;
import com.np.order.models.EstimationApprove;
import com.np.order.models.EstimationConfirm;
import com.np.order.models.EstimationInvoice;
import com.np.order.models.EstimationOrder;
import com.np.order.models.EstiomationDetail;
import com.np.order.models.EstmationCancel;
import com.np.order.models.EstmationSheet;
import com.np.order.models.Item;
import com.np.order.models.OrderDetail;
import com.np.order.models.OrderSheet;
import com.np.order.models.Rolers;
import com.np.order.objects.EstimationApproves;
import com.np.order.objects.EstimationResultFilter;
import com.np.order.objects.NoApproveEstimation;
import com.np.order.objects.NoOrderEstimation;

/*
 * （1）进行插入操作
 * 
 * （2）返回所有EstimationId
 * 
 * （3）根据EstimationId返回所有表中内容
 * 
 * （4）更新表
 * 
 * （5）根据EstimationId删除表
 */

public class EstmationSheetMgr {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(EstmationSheetMgr.class);

	public static boolean save(final EstmationSheet sheet, List<EstiomationDetail> details) throws Exception {
		boolean[] result = new boolean[1];
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {

				result[0] = save(con, sheet);
				if (result[0] == false)
					return;

				try {
					for (EstiomationDetail detail : details) {
						detail.setEstimationId(sheet.getEstimationId());
						save(con, detail);
					}
					result[0] = true;
				} catch (Exception e) {
					result[0] = false;
					throw e;
				}
			}
		});

		return result[0];
	}

	/*
	 * 見積書確認(修正有)
	 * 
	 * １．今の見積書IDを取消テーブルに登録
	 * 
	 * ２．新しい見積書IDを採番
	 * 
	 * ３．見積書を登録
	 * 
	 * ４．EstimationConfirmに登録
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean save(Connection con, final EstmationSheet estimation) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		final Long oldEstimationId = estimation.getEstimationId();
		if (oldEstimationId == null) {
			/*
			 * 作成者
			 */
			estimation.setUserId(SessionMgr.getLoginUserId());
			return register(con, estimation);
		}
		{
			/*
			 * チェック修正
			 */
			Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
			criteria.and(Restrictions.eq(EstmationSheet.EstimationId, oldEstimationId));
			EstmationSheet oldSheet = criteria.get();
			if (UModel.compareModel(oldSheet, estimation)) {
				/*
				 * 変更無し
				 */
				return true;
			}
		}
		/*
		 * 取消
		 */
		EstmationCancel cancel = new EstmationCancel();
		cancel.setEstimationId(oldEstimationId);
		TxMgr.insert(con, cancel);

		Criteria<EstimationConfirm> criteria = new Criteria<EstimationConfirm>(EstimationConfirm.class);
		criteria.and(Restrictions.eq(EstimationConfirm.EstimationId, oldEstimationId));
		EstimationConfirm confirm = criteria.get();

		Long newEstimationId = SequenceMgr.nextSeq(EstmationSheet.class);
		estimation.setEstimationId(newEstimationId);
		TxMgr.insert(con, estimation);

		if (confirm != null) {
			confirm.setEstimationId(newEstimationId);
			TxMgr.update(con, confirm);
		}

		/*
		 * 承認状態
		 */
		Criteria<EstimationApprove> criteriaApprove = new Criteria(EstimationApprove.class);
		criteriaApprove.and(Restrictions.eq(EstimationApprove.EstimationId, oldEstimationId));
		List<EstimationApprove> approves = criteriaApprove.list();
		for (EstimationApprove approve : approves) {
			approve.setEstimationId(newEstimationId);
			TxMgr.update(con, approve);
		}

		/*
		 * TODO:detailのEstimationIdを更新
		 */
		// update(con, oldEstimationId, newEstimationId);

		return true;

	}

	public static boolean save(Connection con, EstiomationDetail detail) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * 自動採番
		 */

		Long detailId = SequenceMgr.nextSeq(EstiomationDetail.class);
		detail.setDetailId(detailId);
		if (detail.getItemId() == null) {
			Item item = ItemMgr.getByNameAndPartsCd(detail.getName(), detail.getPartsCd());
			if (item != null)
				detail.setItemId(item.getItemId());
			else
				detail.setItemId(SequenceMgr.nextSeq(Item.class));
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insert(con, detail);

		return true;
	}

	/*
	 * （1）进行插入操作
	 */
	public static boolean register(Connection con, EstmationSheet estmationt) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * 自動採番
		 */
		Long estimationId = SequenceMgr.nextSeq(EstmationSheet.class);
		estmationt.setEstimationId(estimationId);

		/*
		 * DBにInsert
		 */
		TxMgr.insert(con, estmationt);
		return true;
	}

	/*
	 * 見積書確認(修正有)
	 * 
	 * １．今の見積書IDを取消テーブルに登録
	 * 
	 * ２．新しい見積書IDを採番
	 * 
	 * ３．見積書を登録
	 * 
	 * ４．EstimationConfirmに登録
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean save(final EstmationSheet estimation) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		final Long oldEstimationId = estimation.getEstimationId();
		if (oldEstimationId == null) {
			/*
			 * 作成者
			 */
			estimation.setUserId(SessionMgr.getLoginUserId());
			return register(estimation);
		}
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				{
					/*
					 * チェック修正
					 */
					Criteria<EstmationSheet> criteria = new Criteria<EstmationSheet>(EstmationSheet.class);
					criteria.and(Restrictions.eq(EstmationSheet.EstimationId, oldEstimationId));
					EstmationSheet oldSheet = criteria.get();
					if (UModel.compareModel(oldSheet, estimation)) {
						/*
						 * 変更無し
						 */
						return;
					}
				}
				/*
				 * 取消
				 */
				EstmationCancel cancel = new EstmationCancel();
				cancel.setEstimationId(oldEstimationId);
				TxMgr.insert(con, cancel);

				Criteria<EstimationConfirm> criteria = new Criteria(EstimationConfirm.class);
				criteria.and(Restrictions.eq(EstimationConfirm.EstimationId, oldEstimationId));
				EstimationConfirm confirm = criteria.get();

				Long newEstimationId = SequenceMgr.nextSeq(EstmationSheet.class);
				estimation.setEstimationId(newEstimationId);
				TxMgr.insert(con, estimation);

				if (confirm != null) {
					confirm.setEstimationId(newEstimationId);
					TxMgr.update(con, confirm);
				}

				/*
				 * 承認状態
				 */
				Criteria<EstimationApprove> criteriaApprove = new Criteria(EstimationApprove.class);
				criteriaApprove.and(Restrictions.eq(EstimationApprove.EstimationId, oldEstimationId));
				List<EstimationApprove> approves = criteriaApprove.list();
				for (EstimationApprove approve : approves) {
					approve.setEstimationId(newEstimationId);
					TxMgr.update(con, approve);
				}

				/*
				 * TODO:detailのEstimationIdを更新
				 */
				// update(con, oldEstimationId, newEstimationId);
			}

		});
		return true;
	}

	public static boolean save(EstiomationDetail detail) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * 自動採番
		 */

		Long detailId = SequenceMgr.nextSeq(EstiomationDetail.class);
		detail.setDetailId(detailId);
		if (detail.getItemId() == null) {
			Item item = ItemMgr.getByNameAndPartsCd(detail.getName(), detail.getPartsCd());
			if (item != null)
				detail.setItemId(item.getItemId());
			else
				detail.setItemId(SequenceMgr.nextSeq(Item.class));
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insert(detail);

		return true;
	}

	/*
	 * （1）进行插入操作
	 */

	public static boolean register(List<EstiomationDetail> details) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * 自動採番
		 */

		for (EstiomationDetail detail : details) {
			Long detailId = SequenceMgr.nextSeq(EstiomationDetail.class);
			detail.setDetailId(detailId);
			if (detail.getItemId() == null) {
				detail.setItemId(SequenceMgr.nextSeq(Item.class));
			}
		}

		/*
		 * DBにInsert
		 */
		TxMgr.insertList(details);

		return true;
	}

	/*
	 * （1）进行插入操作
	 */
	public static boolean register(EstmationSheet estmationt) throws Exception {
		if (SessionMgr.isSaller() == false)
			throw new IllegalPriorityEception();

		/*
		 * 自動採番
		 */
		Long estimationId = SequenceMgr.nextSeq(EstmationSheet.class);
		estmationt.setEstimationId(estimationId);

		/*
		 * DBにInsert
		 */
		TxMgr.insert(estmationt);
		return true;
	}

	@Deprecated
	public static boolean modify(final EstmationSheet estmationt, List<EstiomationDetail> details) throws Exception {
		// TxMgr.update(estmationt);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				EstmationCancel cancel = new EstmationCancel();
				cancel.setEstimationId(estmationt.getEstimationId());
				TxMgr.insert(con, cancel);

				Long estimationId = SequenceMgr.nextSeq(EstmationSheet.class);
				estmationt.setEstimationId(estimationId);
				TxMgr.insert(con, estmationt);

				for (EstiomationDetail detail : details) {
					detail.setEstimationId(estimationId);
					Long detailId = SequenceMgr.nextSeq(EstiomationDetail.class);
					detail.setDetailId(detailId);
				}
				TxMgr.insert(con, details);
			}

		});
		return true;
	}

	@Deprecated
	public static boolean modify(List<EstiomationDetail> details) throws Exception {
		TxMgr.updateList(details);
		return true;
	}

	/*
	 * （5）根据EstimationId删除表
	 */
	public static boolean cancel(Long estimationId) throws Exception {
		EstmationCancel cancel = new EstmationCancel();
		cancel.setEstimationId(estimationId);
		TxMgr.insert(cancel);

		return true;
	}

	/*
	 * 検索
	 */
	public static List<EstmationSheet> search(EstmationSheet estmationt) throws Exception {
		List<EstmationSheet> list = Query.query(estmationt);
		return list;
	}

	/*
	 * （2）返回所有EstimationId
	 */
	@Deprecated
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Long> searchEstmationIds() throws Exception {
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.addSelectedColumn(EstmationSheet.EstimationId);
		List<EstmationSheet> list = criteria.list();

		List<Long> ids = PojoCollections.getKeyList(list, EstmationSheet.EstimationId);
		return ids;
	}

	/*
	 * （3）根据EstimationId返回所有表中内容
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EstmationSheet search(Long estimationId) {
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.eq(EstmationSheet.EstimationId, estimationId));
		return criteria.get();
	}

	public static String getCurrency(Long estimationId) {
		EstmationSheet sheet = search(estimationId);
		return (sheet == null) ? null : sheet.getCurrency();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getEstimationCd(Long estimationId) throws Exception {
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.addSelectedColumn(EstmationSheet.EstimationCd);
		criteria.and(Restrictions.eq(EstmationSheet.EstimationId, estimationId));

		EstmationSheet result = criteria.get();
		return (result == null) ? null : result.getEstimationCd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Long getEstimationIdByInvoice(String invoiceCd) throws Exception {
		Criteria<EstimationInvoice> criteriaInvoice = new Criteria(EstimationInvoice.class);
		criteriaInvoice.addSelectedColumn(EstimationInvoice.EstimationCd);
		criteriaInvoice.and(Restrictions.eq(EstimationInvoice.InvoiceCd, invoiceCd));

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.addSelectedColumn(EstmationSheet.EstimationId);
		criteria.and(Restrictions.in(EstmationSheet.EstimationCd, criteriaInvoice));

		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));

		EstmationSheet result = criteria.get();
		return (result == null) ? null : result.getEstimationId();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getEstimationCds(List<String> orderCds) throws Exception {
		Criteria<EstimationOrder> criteria = new Criteria(EstimationOrder.class);
		criteria.addSelectedColumn(EstimationOrder.EstimationCd);
		criteria.and(Restrictions.in(EstimationOrder.OrderCd, orderCds));

		List<EstimationOrder> result = criteria.list();
		List<String> estimationCds = PojoCollections.getKeyList(result, EstimationOrder.EstimationCd);
		return estimationCds;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EstiomationDetail> fetchDetails(Long estimationId) throws Exception {
		EstmationSheet sheet = search(estimationId);

		Criteria<EstiomationDetail> criteria = new Criteria(EstiomationDetail.class);
		criteria.and(Restrictions.eq(EstiomationDetail.EstimationId, estimationId));
		List<EstiomationDetail> details = criteria.list();

		for (EstiomationDetail detail : details) {
			detail.getAmount().setCurrency(sheet.getCurrency());
			detail.getUnitPrice().setCurrency(sheet.getCurrency());
		}

		Sortter.sort(details, EstiomationDetail.DetailId);
		return details;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EstmationSheet> fetchSheets(List<Long> estimationIds) throws Exception {
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.in(EstmationSheet.EstimationId, estimationIds));
		List<EstmationSheet> details = criteria.list();
		return details;
	}

	/*
	 * 承認者ユーザーID
	 * 
	 * 社長、副社長
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EstimationApproves getApproves(Long estimationId) throws Exception {
		Criteria<EstimationApprove> criteria = new Criteria(EstimationApprove.class);
		criteria.and(Restrictions.eq(EstimationApprove.EstimationId, estimationId));
		List<EstimationApprove> result = criteria.list();

		Rolers president = RolerMgr.getRolerForPresident();
		Rolers vicePresident = RolerMgr.getRolerForVicePresident();

		EstimationApproves apprve = new EstimationApproves();
		Long[] userIds = new Long[result.size()];
		int index = 0;
		for (EstimationApprove approve : result) {
			Long userId = approve.getUserId();
			userIds[index++] = userId;

			Long rolerId = approve.getRole();
			if (president.getRolerId().equals(rolerId))
				apprve.setPresident(userId);
			else if (vicePresident.getRolerId().equals(rolerId))
				apprve.setVicePresident(userId);
		}
		apprve.setApprovers(userIds);
		return apprve;
	}

	/*
	 * 見積書を承認
	 */
	public static void approve(Long estimationId) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		Long userId = SessionMgr.getLoginUserId();
		Long role = SessionMgr.getLoginRoleId();
		approve(estimationId, userId, role);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isApproved(Long estimationId) throws Exception {
		Criteria<EstimationApprove> criteria = new Criteria(EstimationApprove.class);
		criteria.and(Restrictions.eq(EstimationApprove.EstimationId, estimationId));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Long> getApproved(List<Long> estimationIds) throws Exception {
		Criteria<EstimationApprove> criteria = new Criteria(EstimationApprove.class);
		criteria.and(Restrictions.in(EstimationApprove.EstimationId, estimationIds));
		// criteria.addSelectedColumn(EstimationApprove.EstimationId);
		List<EstimationApprove> list = criteria.list();

		List<Long> topAccepts = new ArrayList<Long>();
		for (EstimationApprove accept : list) {
			if (accept.getRole().equals(RolerMgr.getRolerForPresident().getRolerId()))
				topAccepts.add(accept.getEstimationId());
		}

		List<Long> result = new ArrayList<Long>();
		for (EstimationApprove accept : list) {
			if (accept.getRole().equals(RolerMgr.getRolerForVicePresident().getRolerId())) {
				if (topAccepts.contains(accept.getEstimationId()))
					result.add(accept.getEstimationId());
			}
		}

		// estimationIds = PojoCollections.getKeyList(list,
		// EstimationApprove.EstimationId);
		return result;
	}

	public static void approve(Long estimationId, Long userId, Long role) throws Exception {
		EstimationApprove approve = new EstimationApprove();
		Long id = SequenceMgr.nextSeq(EstimationApprove.class);
		approve.setApproveId(id);
		approve.setEstimationId(estimationId);
		/*
		 * セッションからuserIdを取得
		 */
		approve.setUserId(userId);
		approve.setRole(role);

		TxMgr.insert(approve);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isAccepted(Long estimationId) {
		Criteria<EstiomationDetail> criteria1 = new Criteria(EstiomationDetail.class);
		criteria1.and(Restrictions.eq(EstiomationDetail.EstimationId, estimationId));
		List<EstiomationDetail> details = criteria1.list();
		List<Long> detailIds = PojoCollections.getKeyList(details, EstiomationDetail.DetailId);

		Criteria<EstimationAccept> criteria = new Criteria(EstimationAccept.class);
		criteria.and(Restrictions.in(EstimationAccept.DetailId, detailIds));
		List<EstimationAccept> accepts = criteria.list();

		return accepts.size() == detailIds.size();
	}

	/*
	 * 見積書確認(修正有)
	 * 
	 * １．今の見積書IDを取消テーブルに登録
	 * 
	 * ２．新しい見積書IDを採番
	 * 
	 * ３．見積書を登録
	 * 
	 * ４．EstimationConfirmに登録
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean confirm(final EstmationSheet estmationt, String customerOrderNo) throws Exception {
		if (SessionMgr.isLogined() == false)
			return false;

		// TxMgr.update(estmationt);
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				Long estimationId = estmationt.getEstimationId();
				{
					/*
					 * チェック修正
					 */
					Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
					criteria.and(Restrictions.eq(EstmationSheet.EstimationId, estimationId));
					EstmationSheet oldSheet = criteria.get();
					if (UModel.compareModel(oldSheet, estmationt) == false) {
						/*
						 * 変更有り
						 */
						EstmationCancel cancel = new EstmationCancel();
						cancel.setEstimationId(estmationt.getEstimationId());
						TxMgr.insert(con, cancel);

						estmationt.setEstimationId(SequenceMgr.nextSeq(EstmationSheet.class));
						TxMgr.insert(con, estmationt);

						// update(con, estimationId,
						// estmationt.getEstimationId());
						estimationId = estmationt.getEstimationId();

						/*
						 * 承認状態
						 */
						Criteria<EstimationApprove> criteriaApprove = new Criteria(EstimationApprove.class);
						criteriaApprove.and(Restrictions.eq(EstimationApprove.EstimationId, cancel.getEstimationId()));
						List<EstimationApprove> approves = criteriaApprove.list();
						for (EstimationApprove approve : approves) {
							approve.setEstimationId(estimationId);
							TxMgr.update(con, approve);
						}
					}
				}

				EstimationConfirm confirm = new EstimationConfirm();
				confirm.setEstimationId(estimationId);
				confirm.setConfirmDate(UDate.getDate());
				confirm.setCustomerOrderNo(customerOrderNo);
				TxMgr.insert(con, confirm);
			}

		});
		return true;
	}

	/*
	 * 見積書を確定（修正無し）
	 */
	@Deprecated
	public static void confirm(Long estimationId, String customerOrderNo) throws Exception {
		if (SessionMgr.isLogined() == false)
			return;

		EstimationConfirm confirm = new EstimationConfirm();
		confirm.setEstimationId(estimationId);
		confirm.setConfirmDate(UDate.getDate());
		confirm.setCustomerOrderNo(customerOrderNo);
		TxMgr.insert(confirm);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isConfirmed(Long estimationId) throws Exception {
		Criteria<EstimationConfirm> criteria = new Criteria(EstimationConfirm.class);
		criteria.and(Restrictions.eq(EstimationConfirm.EstimationId, estimationId));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Long> getConfirmed(List<Long> estimationIds) throws Exception {
		Criteria<EstimationConfirm> criteria = new Criteria(EstimationConfirm.class);
		criteria.and(Restrictions.in(EstimationConfirm.EstimationId, estimationIds));
		criteria.addSelectedColumn(EstimationConfirm.EstimationId);
		List<EstimationConfirm> list = criteria.list();
		estimationIds = PojoCollections.getKeyList(list, EstimationConfirm.EstimationId);
		return estimationIds;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isOrdered(String estimationCd) throws Exception {
		Criteria<EstimationOrder> criteria = new Criteria(EstimationOrder.class);
		criteria.and(Restrictions.eq(EstimationOrder.EstimationCd, estimationCd));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isDeliveried(String estimationCd) throws Exception {
		Criteria<EstimationInvoice> criteria = new Criteria(EstimationInvoice.class);
		criteria.and(Restrictions.eq(EstimationInvoice.EstimationCd, estimationCd));
		return criteria.get() != null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getDeliveried(List<String> estimationCds) throws Exception {
		Criteria<EstimationInvoice> criteria = new Criteria(EstimationInvoice.class);
		criteria.and(Restrictions.in(EstimationInvoice.EstimationCd, estimationCds));
		criteria.addSelectedColumn(EstimationInvoice.EstimationCd);
		List<EstimationInvoice> list = criteria.list();
		estimationCds = PojoCollections.getKeyList(list, EstimationInvoice.EstimationCd);
		return estimationCds;
	}

	/*
	 * 未承認見積書リストを検索
	 */
	public static List<NoApproveEstimation> fetchNotApproveList() throws Exception {
		return fetchNotApproveList(new EstimationResultFilter());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<NoApproveEstimation> fetchNotApproveList(EstimationResultFilter filter) throws Exception {
		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);
		Criteria<EstimationConfirm> confirms = new Criteria(EstimationConfirm.class);
		confirms.addSelectedColumn(EstimationConfirm.EstimationId);

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, confirms));
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, createApproved()));

		if (UString.notEmpty(filter.getEstimationCd()))
			criteria.and(Restrictions.eq(EstmationSheet.EstimationCd, filter.getEstimationCd()));
		if (UString.notEmpty(filter.getCustomerName()))
			criteria.and(Restrictions.eq(EstmationSheet.CustomerName, filter.getCustomerName()));
		if (UString.notEmpty(filter.getDateFrom()) || UString.notEmpty(filter.getDateTo())) {
			if (UString.notEmpty(filter.getDateFrom()) && UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.between(EstmationSheet.IssueDate, filter.getDateFrom(), filter.getDateTo()));
			} else if (UString.notEmpty(filter.getDateFrom())) {
				criteria.and(Restrictions.ge(EstmationSheet.IssueDate, filter.getDateFrom()));
			} else if (UString.notEmpty(filter.getDateTo())) {
				criteria.and(Restrictions.le(EstmationSheet.IssueDate, filter.getDateTo()));
			}
		}

		List<JoinedModels> estimations = Query.queryWithDependencies(criteria, EstiomationDetail.class);

		String[] sumKeys = { EstmationSheet.EstimationId };
		String[] sumNames = { EstiomationDetail.Quantity, EstiomationDetail.Amount };
		estimations = Groupper.sum(estimations, sumKeys, sumNames);

		List<NoApproveEstimation> result = new ArrayList<NoApproveEstimation>(estimations.size());

		ModelMapper mapper = PojoUtils.getMapper(NoApproveEstimation.class);
		String[] names = mapper.getNames().toArray(new String[0]);

		List<String> ids = PojoCollections.getKeyList(estimations, EstmationSheet.EstimationId);
		List<EstimationApprove> approves = fetchApproveStatus(ids);
		for (JoinedModels estimation : estimations) {
			NoApproveEstimation noApprove = UModel.copy(estimation, NoApproveEstimation.class, names);

			noApprove.getAmount().setCurrency((String) estimation.getValue(EstmationSheet.Currency));
			/* 納期 */
			noApprove.setArriveDate((String) estimation.getValue(EstmationSheet.DeliveryLimitDays));
			/* 支払 */

			/* 承認状態 */
			Long estimationId = noApprove.getEstimationId();
			noApprove.setApprovedStates(fetchApproveStatus(approves, estimationId));

			result.add(noApprove);
		}

		return result;
	}

	/*
	 * 承認状態を取得
	 */
	public static String fetchApproveStatus(List<EstimationApprove> approves, Long estimationId) throws Exception {
		List<Long> rolerIds = new ArrayList<Long>();
		for (EstimationApprove approve : approves) {
			if (approve.getEstimationId().equals(estimationId))
				rolerIds.add(approve.getRole());
		}

		return RolerMgr.searchRolersAsString(rolerIds);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EstimationApprove> fetchApproveStatus(List<String> ids) throws Exception {
		Criteria<EstimationApprove> criteria = new Criteria(EstimationApprove.class);
		criteria.and(Restrictions.in(EstimationApprove.EstimationId, ids));
		List<EstimationApprove> approves = criteria.list();
		return approves;
	}

	/*
	 * 未注文見積書リストを検索
	 * 
	 * Create -> Approve -> Confirm -> Order -> Invoice + PackingList
	 */
	public static List<NoOrderEstimation> fetchNotOrderList() throws Exception {
		return fetchNotOrderList(null, null);
	}

	public static List<NoOrderEstimation> fetchNotOrderList(String dateFrom, String dateTo) throws Exception {
		List<JoinedModels> estimations = fetchNotOrderSheets(dateFrom, dateTo);
		estimations.addAll(fetchNotOrderItems(dateFrom, dateTo));

		/*
		 * EstimationConfirm
		 */
		// List<Long> estimationIds =
		// PojoCollections.getUniqueKeyList(estimations,
		// EstmationSheet.EstimationId);
		// Criteria<EstimationConfirm> confirmsCriteria = new
		// Criteria(EstimationConfirm.class);
		// confirmsCriteria.and(Restrictions.in(EstimationConfirm.EstimationId,
		// estimationIds));
		// List<EstimationConfirm> confirms = confirmsCriteria.list();

		List<NoOrderEstimation> result = new ArrayList<NoOrderEstimation>(estimations.size());

		for (JoinedModels estimation : estimations) {
			/*
			 * 商品マスタに存在しない商品を注文しない。
			 * 
			 * 例えば、安装費
			 */
			Long itemId = (Long) estimation.getValue(EstiomationDetail.ItemId);
			if (ItemMgr.exists(itemId) == false)
				continue;

			NoOrderEstimation noOrder = new NoOrderEstimation();

			noOrder.setEstimationId((Long) estimation.getValue(EstiomationDetail.EstimationId));
			noOrder.setDetailId((Long) estimation.getValue(EstiomationDetail.DetailId));
			noOrder.setEstimationCd((String) estimation.getValue(EstmationSheet.EstimationCd));

			noOrder.setSellerName((String) estimation.getValue(EstmationSheet.CustomerName));

			noOrder.setOrderDate((String) estimation.getValue(EstimationConfirm.ConfirmDate));

			noOrder.setAcceptDate((String) estimation.getValue(EstmationSheet.DeliveryLimitDays));
			noOrder.setItemId((Long) estimation.getValue(EstiomationDetail.ItemId));
			noOrder.setItemName((String) estimation.getValue(EstiomationDetail.Name));
			noOrder.setPartCd((String) estimation.getValue(EstiomationDetail.PartsCd));
			noOrder.setQuantity((Integer) estimation.getValue(EstiomationDetail.Quantity));
			noOrder.setModelCd((String) estimation.getValue(EstiomationDetail.ModelCd));
			noOrder.setAmount((Money) estimation.getValue(EstiomationDetail.Amount));

			noOrder.getAmount().setCurrency((String) estimation.getValue(EstmationSheet.Currency));

			String orderDate = noOrder.getOrderDate();
			if (UString.notEmpty(dateFrom) && dateFrom.compareTo(orderDate) > 0) {
				continue;
			}
			if (UString.notEmpty(dateTo) && dateTo.compareTo(orderDate) < 0) {
				continue;
			}

			result.add(noOrder);
		}

		return result;
	}

	/*
	 * 未注文見積書リストを取得
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<JoinedModels> fetchNotOrderSheets(String dateFrom, String dateTo) throws Exception {
		// 取消した見積
		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);
		// 承認した見積
		Criteria<EstimationConfirm> approveds = new Criteria(EstimationConfirm.class);
		approveds.addSelectedColumn(EstimationConfirm.EstimationId);
		// 注文した見積
		Criteria<EstimationOrder> orders = new Criteria(EstimationOrder.class);
		orders.addSelectedColumn(EstimationOrder.EstimationCd);

		// 未注文見積
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));
		criteria.and(Restrictions.in(EstmationSheet.EstimationId, approveds));
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationCd, orders));

		List<JoinedModels> estimations = Query.queryWithDependencies(criteria, EstiomationDetail.class);

		List<String> estimetionIds = PojoCollections.getUniqueKeyList(estimations, EstmationSheet.EstimationId);
		Criteria<EstimationConfirm> criteriaConfirm = new Criteria(EstimationConfirm.class);
		criteriaConfirm.and(Restrictions.in(EstmationSheet.EstimationId, estimetionIds));
		List<EstimationConfirm> confirms = criteriaConfirm.list();
		estimations = Matcher.innerJoin(confirms, estimations, EstmationSheet.EstimationId);

		return estimations;
	}

	/*
	 * 注文した見積書中の未注文商品リストを取得
	 * 
	 * 注：見積書の一部商品を注文した場合
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<JoinedModels> fetchNotOrderItems(String dateFrom, String dateTo) throws Exception {
		// 取消した見積
		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);
		// 注文した見積
		Criteria<EstimationOrder> orders = new Criteria(EstimationOrder.class);
		orders.addSelectedColumn(EstimationOrder.EstimationCd);
		// 注文確定した見積
		// Criteria<EstimationAccept> accepts = new
		// Criteria(EstimationAccept.class);
		// accepts.addSelectedColumn(EstimationAccept.EstimationId);

		// 未注文見積
		Criteria<EstmationSheet> criteriaSheet = new Criteria(EstmationSheet.class);
		criteriaSheet.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));
		criteriaSheet.and(Restrictions.in(EstmationSheet.EstimationCd, orders));
		// criteriaSheet.and(Restrictions.notIn(EstmationSheet.EstimationId,
		// accepts));

		List<JoinedModels> estimations = Query.queryWithDependencies(criteriaSheet, EstiomationDetail.class);

		List<String> estimetionIds = PojoCollections.getUniqueKeyList(estimations, EstmationSheet.EstimationId);
		Criteria<EstimationConfirm> criteriaConfirm = new Criteria(EstimationConfirm.class);
		criteriaConfirm.and(Restrictions.in(EstmationSheet.EstimationId, estimetionIds));
		List<EstimationConfirm> confirms = criteriaConfirm.list();
		estimations = Matcher.innerJoin(confirms, estimations, EstmationSheet.EstimationId);

		/*
		 * 見積CD
		 */
		List<String> estimetionCds = PojoCollections.getUniqueKeyList(estimations, EstmationSheet.EstimationCd);

		List<JoinedModels> result = new ArrayList<JoinedModels>();
		for (String estimetionCd : estimetionCds) {
			/*
			 * 注文CD
			 */
			Criteria<EstimationOrder> criteriaEstimetionOrder = new Criteria(EstimationOrder.class);
			criteriaEstimetionOrder.and(Restrictions.eq(EstimationOrder.EstimationCd, estimetionCd));
			EstimationOrder estimationOrder = criteriaEstimetionOrder.get();

			// 注文済み見積
			Criteria<OrderSheet> criteriaOrderSheet = new Criteria(OrderSheet.class);
			criteriaOrderSheet.and(Restrictions.eq(OrderSheet.OrderCd, estimationOrder.getOrderCd()));
			criteriaOrderSheet.addSelectedColumn(OrderSheet.OrderId);

			Criteria<OrderDetail> criteriaOrderDetail = new Criteria(OrderDetail.class);
			criteriaOrderDetail.and(Restrictions.in(OrderDetail.OrderId, criteriaOrderSheet));
			List<OrderDetail> orderDetails = criteriaOrderDetail.list();
			/*
			 * 注文した商品を除く
			 */
			for (JoinedModels estimation : estimations) {
				String cd = estimation.getValue(EstmationSheet.EstimationCd);
				if (cd.equals(estimetionCd) == false)
					continue;

				Long itemId = estimation.getValue(EstiomationDetail.ItemId);
				boolean ordered = false;
				for (OrderDetail orderDetail : orderDetails) {
					if (itemId.equals(orderDetail.getItemId())) {
						ordered = true;
						break;
					}
				}
				if (ordered == false)
					result.add(estimation);
			}

		}
		return result;
	}

	/*
	 * 注文済み見積書リストを検索
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EstmationSheet> fetchOrderedList() throws Exception {
		Criteria<EstmationCancel> cancels = new Criteria(EstmationCancel.class);
		cancels.addSelectedColumn(EstmationCancel.EstimationId);
		Criteria<EstimationOrder> orders = new Criteria(EstimationOrder.class);
		orders.addSelectedColumn(EstimationOrder.EstimationCd);

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.notIn(EstmationSheet.EstimationId, cancels));
		criteria.and(Restrictions.in(EstmationSheet.EstimationCd, orders));

		List<EstmationSheet> result = criteria.list();
		return result;
	}

	/*
	 * 見積実績リストを検索
	 */
	public static List<EstmationSheet> fetchFinishedList() throws Exception {
		List<EstmationSheet> result = fetchOrderedList();

		return result;
	}

	/*
	 * 見積書と詳細を検索
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static EstiomationDetail getDetail(Long estiomationId, Long itemId, String lanCd) throws Exception {
		EstmationSheet sheet = search(estiomationId);
		Criteria<EstiomationDetail> criteria = new Criteria(EstiomationDetail.class);
		criteria.and(Restrictions.eq(EstiomationDetail.EstimationId, estiomationId));
		criteria.and(Restrictions.eq(EstiomationDetail.ItemId, itemId));
		EstiomationDetail detail = criteria.get();

		Criteria<Item> critItem = new Criteria(Item.class);
		critItem.and(Restrictions.eq(EstiomationDetail.ItemId, itemId));
		Item aitem = critItem.get();

		if (detail != null && aitem != null) {
			detail.getAmount().setCurrency(sheet.getCurrency());
			detail.getUnitPrice().setCurrency(sheet.getCurrency());
			if (lanCd.equals("Jp")) {
				detail.setName(aitem.getJpName());
				detail.setPartsCd(aitem.getJpDesc());
			}

			if (lanCd.equals("En")) {
				detail.setName(aitem.getEnName());
				detail.setPartsCd(aitem.getEnDesc());
			}
		}
		return detail;
	}

	public static void register(Credit credit) throws Exception {
		credit.setCreditId(SequenceMgr.nextSeq(Credit.class));
		TxMgr.insert(credit);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Criteria<EstimationApprove> createApproved() {
		Criteria<EstimationApprove> approveds = new Criteria(EstimationApprove.class);
		/*
		 * 副社長承認
		 */
		approveds.and(Restrictions.eq(EstimationApprove.Role, RolerMgr.getRolerForVicePresident().getRolerId()));
		approveds.addSelectedColumn(EstimationApprove.EstimationId);

		Criteria<EstimationApprove> approved0 = new Criteria(EstimationApprove.class);
		/*
		 * 社長承認
		 */
		approved0.and(Restrictions.eq(EstimationApprove.Role, RolerMgr.getRolerForPresident().getRolerId()));
		approved0.addSelectedColumn(EstimationApprove.EstimationId);
		approveds.and(Restrictions.in(EstimationApprove.EstimationId, approved0));

		return approveds;
	}
}
