package jp.newpulse.action.invoice.next_delivery_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.DeliveryMgr;
import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.objects.DeliveryFilter;
import com.np.order.objects.DeliveryInfo;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NapinNextFinshAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(NapinNextFinshAction.class);
	// 要处理的字符串
	private String str;
	private String date;
	private String year;
	private String month;
	private List<DeliveryInfo> NextdeliveryInfo;
	private Integer quantity;

	public String execute() throws Exception {
		year = date.substring(0, 4);
		month = date.substring(4, 6);
		String customerOrderNo = "";
		String orderCd = "";
		Long detailId = 0L;
		Long itemId = 0L;
		Long orderId = 0L;
		Long customerId = 0L;

		try {
			String[] strSub = str.split(",");
			for (int i = 0; i < strSub.length; i++) {
				String[] strCh = strSub[i].split(";");
				// customerOrderNo = strCh[0];
				orderCd = strCh[0];
				detailId = Long.parseLong(strCh[1]);
				itemId = Long.parseLong(strCh[2]);
				orderId = Long.parseLong(strCh[3]);
				customerId = Long.parseLong(strCh[4]);

				DeliveryInfo deliveryInfo = new DeliveryInfo();
				deliveryInfo.setCustomerOrderNo(customerOrderNo);
				deliveryInfo.setOrderCd(orderCd);
				deliveryInfo.setDetailId(detailId);
				deliveryInfo.setItemId(itemId);
				deliveryInfo.setOrderId(orderId);
				deliveryInfo.setCustomerId(customerId);
				logger.debug("-----------纳期完了star-------------");
				OrderSheetMgr.over(orderId, quantity);
				// DeliveryMgr.registerDelivery(deliveryInfo);
				logger.debug("-----------纳期完了star-------------");
				logger.debug(customerOrderNo);
				logger.debug(orderCd);
				logger.debug(detailId);
				logger.debug(itemId);
				logger.debug(orderId);
				logger.debug(customerId);
			}
			logger.debug("-----------次月配送star-------------");
			DeliveryFilter deliveryFilter = new DeliveryFilter();
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			deliveryFilter.setDeliveryYear(year);
			deliveryFilter.setDeliveryMonth(month);

			NextdeliveryInfo = DeliveryMgr.getDeliveryInfos(deliveryFilter);
			logger.debug(NextdeliveryInfo);
			logger.debug("-----------次月配送end-------------");
			// for (int i = 0; i < NextdeliveryInfo.size(); i++) {
			// String DeliveryDate =
			// NextdeliveryInfo.get(i).getNewDeliveryDate();
			// String yyyy = DeliveryDate.substring(0, 4);
			// String mm = DeliveryDate.substring(4, 6);
			// String dd = DeliveryDate.substring(6, 8);
			// String ymd = yyyy + "-" + mm + "-" + dd;
			// NextdeliveryInfo.get(i).setNewDeliveryDate(ymd);
			// }
		} catch (NumberFormatException e) {
			logger.trace(UDebugger.trace(e));
		}
		return "success";
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<DeliveryInfo> getNextdeliveryInfo() {
		return NextdeliveryInfo;
	}

	public void setNextdeliveryInfo(List<DeliveryInfo> nextdeliveryInfo) {
		NextdeliveryInfo = nextdeliveryInfo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
