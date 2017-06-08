package com.np.order.biz.invoice;

import java.text.MessageFormat;
import java.util.List;

import com.np.base.db.SequenceMgr;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.models.EstmationSheet;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.OrderSheet;

/*
 * 自動採番
 * 
 */
public class CodeAutoIncrement {

	public static void main(String... args) throws Exception {
		System.out.println(checkEstmationCode("X15-NPS-1115"));
	}

	/*
	 * 見積コードの自動採番
	 * 
	 * X15-TPRV-0616
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized String nextEstmationCode(String customerShortName) throws Exception {
		String CODE_PATTERN = "X{0}-{1}-{2}";
		String code = MessageFormat.format(CODE_PATTERN, UDate.getYearYY(), customerShortName, UDate.getMonthDay());

		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.like(EstmationSheet.EstimationCd, code));
		criteria.addSelectedColumn(EstmationSheet.EstimationId);
		List<EstmationSheet> list = criteria.list();

		if (UList.isEmpty(list))
			return code;
		else {
			int index = (list.size() + 1);
			return code + "-" + index;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized boolean checkEstmationCode(String code) throws Exception {
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.eq(EstmationSheet.EstimationCd, code));
		criteria.addSelectedColumn(EstmationSheet.EstimationId);
		List<EstmationSheet> list = criteria.list();

		return UList.isEmpty(list);
	}

	/*
	 * 発注コードの自動採番
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized String nextOrderCode(String supplierShortName) throws Exception {
		/*
		 * TODO:要確認
		 */
		String CODE_PATTERN = "X{0}-{1}-{2}";
		String code = MessageFormat.format(CODE_PATTERN, UDate.getYearYY(), supplierShortName, UDate.getMonthDay());

		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.and(Restrictions.like(OrderSheet.OrderCd, code));
		criteria.addSelectedColumn(OrderSheet.OrderId);
		List<OrderSheet> list = criteria.list();

		if (UList.isEmpty(list))
			return code;
		else {
			int index = (list.size() + 1);
			return code + "-" + index;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized boolean checkOrderCode(String code) throws Exception {
		Criteria<OrderSheet> criteria = new Criteria(OrderSheet.class);
		criteria.and(Restrictions.eq(OrderSheet.OrderCd, code));
		criteria.addSelectedColumn(OrderSheet.OrderId);
		List<OrderSheet> list = criteria.list();

		return UList.isEmpty(list);
	}

	/*
	 * Invoiceコードの自動採番
	 * 
	 * XAJ-S15-066
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized String nextInvoiceCode(char deliveryType) throws Exception {
		/*
		 * TODO:要確認
		 */
		String CODE_PATTERN = "XAJ-{0}{1}-{2}";

		String year = UDate.getYearYY();
		Long invoiceCd = SequenceMgr.currentSeq("InvoiceCD" + year);
		String cd;
		if (invoiceCd < 1000) {
			cd = UString.format(invoiceCd.intValue(), 3);
		} else {
			cd = Long.toString(invoiceCd);
		}

		deliveryType = Character.toUpperCase(deliveryType);
		String code = MessageFormat.format(CODE_PATTERN, deliveryType, year, cd);

		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.like(InvoiceSheet.InvoiceCd, code));
		criteria.addSelectedColumn(InvoiceSheet.InvoiceId);
		List<InvoiceSheet> list = criteria.list();

		if (UList.isEmpty(list))
			return code;
		else {
			int index = (list.size() + 1);
			while (true) {
				String nextCode = code + "-" + index;
				if (checkInvoiceCode(nextCode))
					return nextCode;
				index++;
			}
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static synchronized boolean checkInvoiceCode(String code) throws Exception {
		Criteria<InvoiceSheet> criteria = new Criteria(InvoiceSheet.class);
		criteria.and(Restrictions.eq(InvoiceSheet.InvoiceCd, code));
		criteria.addSelectedColumn(InvoiceSheet.InvoiceId);
		List<InvoiceSheet> list = criteria.list();

		return UList.isEmpty(list);
	}

}
