package jp.newpulse.action.invoice.current_delivery_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.DeliveryMgr;
import com.np.order.objects.DeliveryFilter;
import com.np.order.objects.DeliveryInfo;
import com.opensymphony.xwork2.ActionSupport;

/*
 * 纳期变更处理
 */
@SuppressWarnings("serial")
public class NaqiChangeAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(NaqiChangeAction.class);
	// 要处理的字符串
	private String str;
	private String date;
	private List<DeliveryInfo> deliveryInfo;

	public String execute() throws Exception {
		// String customerOrderNo = "";
		String orderCd = "";
		String deliveryDate = "";
		String newDeliveryDate = "";
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
				deliveryDate = strCh[1];
				newDeliveryDate = strCh[2];
				detailId = Long.parseLong(strCh[3]);
				itemId = Long.parseLong(strCh[4]);
				orderId = Long.parseLong(strCh[5]);
				customerId = Long.parseLong(strCh[6]);
				DeliveryInfo deliveryInfo = new DeliveryInfo();
				// deliveryInfo.setCustomerOrderNo(customerOrderNo);
				deliveryInfo.setOrderCd(orderCd);
				deliveryInfo.setDeliveryDate(deliveryDate);
				deliveryInfo.setNewDeliveryDate(newDeliveryDate);
				deliveryInfo.setDetailId(detailId);
				deliveryInfo.setItemId(itemId);
				deliveryInfo.setOrderId(orderId);
				deliveryInfo.setCustomerId(customerId);
				logger.debug("-----------变更纳期star-------------");
				DeliveryMgr.registerDelivery(deliveryInfo);
				logger.debug("-----------变更纳期star-------------");
				// logger.debug(customerOrderNo);
				logger.debug(orderCd);
				logger.debug(deliveryDate);
				logger.debug(newDeliveryDate);
				logger.debug(detailId);
				logger.debug(itemId);
				logger.debug(orderId);
				logger.debug(customerId);
			}
			logger.debug("-----------当月配送star-------------");
			DeliveryFilter deliveryFilter = new DeliveryFilter();
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			deliveryFilter.setDeliveryYear(year);
			deliveryFilter.setDeliveryMonth(month);

			deliveryInfo = DeliveryMgr.getCurrentMonthDeliveryInfos();
			logger.debug(deliveryInfo);
			logger.debug("-----------当月配送end-------------");
			// for (int i = 0; i < deliveryInfo.size(); i++) {
			// String DeliveryDate = deliveryInfo.get(i).getNewDeliveryDate();
			// String yyyy = DeliveryDate.substring(0, 4);
			// String mm = DeliveryDate.substring(4, 6);
			// String dd = DeliveryDate.substring(6, 8);
			// String ymd = yyyy + "-" + mm + "-" + dd;
			// deliveryInfo.get(i).setNewDeliveryDate(ymd);
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

	public List<DeliveryInfo> getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(List<DeliveryInfo> deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

}
