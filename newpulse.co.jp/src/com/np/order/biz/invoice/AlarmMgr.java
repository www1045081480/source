package com.np.order.biz.invoice;

import java.util.List;

import com.np.base.dml.Sortter;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.order.models.PayableBalance;
import com.np.order.models.ReceivableBalance;
import com.np.order.objects.DeliveryInfo;

public class AlarmMgr {

	/*
	 * 翌日配送ある
	 */
	public static boolean hasDeliveries() throws Exception {
		List<DeliveryInfo> deliveries = DeliveryMgr.getCurrentMonthDeliveryInfos();

		String nextDay = UDate.getDateByAdjustDay(1);
		for (DeliveryInfo delivery : deliveries) {
			String deliveryDate = delivery.getDeliveryDate();
			if (deliveryDate.compareTo(nextDay) <= 0)
				return true;
		}
		return false;
	}

	/*
	 * 四個月以上未納金
	 */
	public static boolean hasNoReceivableAmounts() {
		String limitDay = UDate.getDateByAdjustMonth(-4);

		ReceivableBalance firstUnbalance = getFirstUnbalanceReceivableAmounts();
		if (firstUnbalance != null) {
			String balanceDay = firstUnbalance.getBalanceMonth();
			return balanceDay.compareTo(limitDay) <= 0;
		}
		return false;
	}

	/*
	 * 二個月以上未支払
	 */
	public static boolean hasNoPayableAmounts() {
		String limitDay = UDate.getDateByAdjustMonth(-2);

		PayableBalance firstUnbalance = getFirstUnbalancePayableAmounts();
		if (firstUnbalance != null) {
			String balanceDay = firstUnbalance.getBalanceMonth();
			return balanceDay.compareTo(limitDay) <= 0;
		}

		return false;
	}

	/*
	 * 残高
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ReceivableBalance getFirstUnbalanceReceivableAmounts() {
		Criteria<ReceivableBalance> criteria = new Criteria(ReceivableBalance.class);
		// criteria.and(Restrictions.in(OrderSheet.OrderId, orderIds));

		List<ReceivableBalance> balances = criteria.list();

		String[] sortKeys = { ReceivableBalance.AccountMonth, ReceivableBalance.BalanceMonth };
		Sortter.sort(balances, sortKeys, Sortter.SORT_DSC);

		ReceivableBalance firstUnbalance = null;
		for (ReceivableBalance balance : balances) {
			long amount = balance.getBalanceJpAmount();
			amount += balance.getBalanceCnAmount();
			amount += balance.getBalanceUsAmount();
			if (amount > 0) {
				firstUnbalance = balance;
			} else {
				return firstUnbalance;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PayableBalance getFirstUnbalancePayableAmounts() {
		Criteria<PayableBalance> criteria = new Criteria(PayableBalance.class);
		// criteria.and(Restrictions.in(OrderSheet.OrderId, orderIds));

		List<PayableBalance> balances = criteria.list();

		String[] sortKeys = { PayableBalance.AccountMonth, PayableBalance.BalanceMonth };
		Sortter.sort(balances, sortKeys, Sortter.SORT_DSC);

		PayableBalance firstUnbalance = null;
		for (PayableBalance balance : balances) {
			long amount = balance.getBalanceJpAmount();
			amount += balance.getBalanceCnAmount();
			amount += balance.getBalanceUsAmount();
			if (amount > 0) {
				firstUnbalance = balance;
			} else {
				return firstUnbalance;
			}

		}

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ReceivableBalance getLasstBalancedReceivableAmounts(Long customerId) {
		if (customerId == null)
			return null;
		Criteria<ReceivableBalance> criteria = new Criteria(ReceivableBalance.class);
		criteria.and(Restrictions.eq(ReceivableBalance.CustomerIｄ, customerId));

		List<ReceivableBalance> balances = criteria.list();

		String[] sortKeys = { ReceivableBalance.AccountMonth, ReceivableBalance.BalanceMonth };
		Sortter.sort(balances, sortKeys, Sortter.SORT_DSC);

		for (ReceivableBalance balance : balances) {
			long amount = balance.getBalanceJpAmount();
			amount += balance.getBalanceCnAmount();
			amount += balance.getBalanceUsAmount();
			if (amount == 0) {
				return balance;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PayableBalance getLastBalancedPayableAmounts(Long supplierId) {
		if (supplierId == null)
			return null;

		Criteria<PayableBalance> criteria = new Criteria(PayableBalance.class);
		criteria.and(Restrictions.eq(PayableBalance.SupplierId, supplierId));

		List<PayableBalance> balances = criteria.list();

		String[] sortKeys = { PayableBalance.AccountMonth, PayableBalance.BalanceMonth };
		Sortter.sort(balances, sortKeys, Sortter.SORT_DSC);

		for (PayableBalance balance : balances) {
			long amount = balance.getBalanceJpAmount();
			amount += balance.getBalanceCnAmount();
			amount += balance.getBalanceUsAmount();
			if (amount == 0) {
				return balance;
			}

		}

		return null;
	}

}
