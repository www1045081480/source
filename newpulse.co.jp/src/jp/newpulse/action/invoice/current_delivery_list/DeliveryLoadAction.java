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
 * 当月配送一览初始化
 */
@SuppressWarnings("serial")
public class DeliveryLoadAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(DeliveryLoadAction.class);
	private String date;
	private Long supplierId;
	private String customerOrder;
	private String year;
	private String mon;
	private List<DeliveryInfo> deliveryInfo;

	public String execute() {
		logger.debug("-----------当月配送star-------------");
		DeliveryFilter deliveryFilter = new DeliveryFilter();

		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		deliveryFilter.setDeliveryYear(year);
		deliveryFilter.setDeliveryMonth(month);

		// deliveryFilter.setSupplierName(customerName);
		deliveryFilter.setSupplierId(supplierId);
		deliveryFilter.setOrderNo(customerOrder);
		try {
			deliveryInfo = DeliveryMgr.getCurrentMonthDeliveryInfos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.trace(UDebugger.trace(e));
		}
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
		return "success";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public List<DeliveryInfo> getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(List<DeliveryInfo> deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
