package com.np.order.biz.invoice;

import com.np.base.db.TxMgr;
import com.np.order.models.EstimationOrder;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.OrderInvoice;
import com.np.order.models.OrderSheet;

public class StatusMgr {

	/*
	 * 見積 → 発注
	 */
	public static void toOrder(EstmationSheet from, OrderSheet to) throws Exception {
		EstimationOrder transfer = new EstimationOrder();
		transfer.setEstimationCd(from.getEstimationCd());
		transfer.setOrderCd(to.getOrderCd());
		TxMgr.insert(transfer);
	}

	/*
	 * 発注 → Invoice
	 */
	public static void toInvoice(OrderSheet from, InvoiceSheet to) throws Exception {
		OrderInvoice transfer = new OrderInvoice();
		transfer.setOrderCd(from.getOrderCd());
		transfer.setInvoiceCd(to.getInvoiceCd());

		TxMgr.insert(transfer);
	}

}
