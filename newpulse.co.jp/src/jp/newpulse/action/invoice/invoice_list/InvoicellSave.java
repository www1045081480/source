package jp.newpulse.action.invoice.invoice_list;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.np.base.utils.UDebugger;
import com.np.base.utils.UJson;
import com.np.order.Money;
import com.np.order.biz.invoice.InvoiceSheetMgr;
import com.np.order.biz.invoice.ResultListMgr;
import com.np.order.objects.DeliveryResult;

import jp.newpulse.action.BaseExcelAction;

@SuppressWarnings("serial")
public class InvoicellSave extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(InvoicellSave.class);
	private String str;

	@SuppressWarnings("unused")
	public String execute() {

		Long invoiceId = 0L;
		Long estimationId = 0L;
		/*
		 * Invoice番号
		 * 
		 */
		String invoiceCd = "";
		/*
		 * 作成日付
		 */
		String issueDate = "";
		/*
		 * 配送先
		 */
		String customerName = "";
		/*
		 * 見積番号 EstimationInvoice:estimationCd
		 */
		String estimationCd = "";
		/*
		 * お客様注文番号 OrderInvoice : orderCd
		 */
		String orderCd = "";
		/*
		 * 配送方式:EMS/ 港運/ 航空
		 * 
		 */
		String deliveryType = "";
		/*
		 * 渡し条件:FOB/ CIF
		 */
		String shippingType = "";
		/*
		 * 配送会社
		 */
		String shippingCompany = "";
		/*
		 * 梱包数 PackingLists
		 */
		Integer packingNumber = 0;
		String itemName = "";
		/*
		 * 品名/数量 InvoiceDetail : modelCd + quantity
		 */
		String modelCd = "";

		/*
		 * 品名
		 */
		Integer quantity = 0;

		/*
		 * 総金額（JPN/USD）
		 */
		Money amount;
		/*
		 * 到着日付
		 */
		String arrivalDate = "";
		/*
		 * 運賃金額
		 */
		Money carringAmount;
		/*
		 * 承認者
		 */
		Long approvedId = 0L;

		String[] strSub = str.split("@");
		for (int i = 0; i < strSub.length; i++) {
			logger.debug("=========strSub.length");
			logger.debug(strSub.length);
			String[] strSub1 = strSub[i].split(";");
			invoiceId = Long.parseLong(strSub1[0]);
			invoiceCd = strSub1[1];
			// customerName = strSub1[2];
			// orderCd = strSub1[3];
			// logger.debug("==============1---====================");
			// issueDate = strSub1[4];
			// itemName = strSub1[5];
			// modelCd = strSub1[6];
			// logger.debug("==============2---====================");
			String currency = InvoiceSheetMgr.getCurrency(invoiceId);
			logger.debug("==============monery kinds====================");
			logger.debug(currency);
			// logger.debug("==============3---====================");
			// logger.debug(currency);
			// //quantity = Integer.parseInt(strSub1[7]);
			// //logger.debug(quantity);
			// amount = new Money(currency, strSub1[8]);
			// logger.debug(amount);
			// arrivalDate = strSub1[9];
			// logger.debug(arrivalDate);
			// logger.debug("==============4---====================");
			// shippingType = strSub1[10];
			// logger.debug(shippingType);
			carringAmount = new Money(currency, strSub1[2]);
			// logger.debug("==============invoiceId====================");
			// System.out.println(invoiceId);
			//
			// logger.debug("==============invoiceId====================");
			// invoiceCd = strSub1[0];
			// invoiceCd = strSub1[0];
			// invoiceCd = strSub1[0];
			// invoiceCd = strSub1[0];
			// invoiceCd = strSub1[0];
			// invoiceCd = strSub1[0];
			// invoiceCd = strSub1[0];
			DeliveryResult deliveryResult = new DeliveryResult();
			deliveryResult.setInvoiceCd(invoiceCd);
			// deliveryResult.setCustomerName(customerName);
			// deliveryResult.setOrderCd(orderCd);
			// deliveryResult.setIssueDate(issueDate);
			// deliveryResult.setItemName(itemName);
			// deliveryResult.setModelCd(modelCd);
			// deliveryResult.setQuantity(quantity);
			// deliveryResult.setAmount(amount);
			// deliveryResult.setArrivalDate(arrivalDate);
			// deliveryResult.setShippingType(shippingType);
			deliveryResult.setCarringAmount(carringAmount);
			deliveryResult.setInvoiceId(invoiceId);
			System.out.println("==============SAVE Begin====================");
			try {
				ResultListMgr.register(deliveryResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.debug(UDebugger.trace(e));
			}
			response(true);
			System.out.println("==============SAVE End=====================");
		}

		return "success";
	}

	@SuppressWarnings("unused")
	private Object jsonData;

	public void response(Object result) {
		try {
			HttpServletResponse res = ServletActionContext.getResponse();
			res.setCharacterEncoding("application/json;charset=utf-8");
			res.setHeader("Cache-Control", "no-cache");
			res.reset();

			jsonData = UJson.toJsonString(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
