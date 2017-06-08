package jp.newpulse.action.invoice.next_delivery_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.DeliveryMgr;
import com.np.order.objects.DeliveryFilter;
import com.np.order.objects.DeliveryInfo;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NextDeliveryLoadAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(NextDeliveryLoadAction.class);

	private String date;
	private Long supplierId;
	private String customerOrder;
	private String year;
	private String month;
	private String customerName;
	private String customerID;
	private List<DeliveryInfo> NextdeliveryInfo;

	public String execute() {
		year = date.substring(0, 4);
		month = date.substring(4, 6);
		logger.debug("-----------next月配送star-------------");
		DeliveryFilter deliveryFilter = new DeliveryFilter();
		deliveryFilter.setDeliveryYear(year);
		deliveryFilter.setDeliveryMonth(month);

		// deliveryFilter.setSupplierName(customerName);
		if (customerID != null && !customerID.trim().equals("")) {
			supplierId = Long.parseLong(customerID);
			deliveryFilter.setSupplierId(supplierId);
		}
		logger.debug("-----------next      supplierId-------------");
		logger.debug(supplierId);
		deliveryFilter.setOrderNo(customerOrder);
		try {
			NextdeliveryInfo = DeliveryMgr.getDeliveryInfos(deliveryFilter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.trace(UDebugger.trace(e));
		}
		logger.debug(NextdeliveryInfo);
		logger.debug("-----------next月配送end-------------");
		// for (int i = 0; i < NextdeliveryInfo.size(); i++) {
		// String DeliveryDate = NextdeliveryInfo.get(i).getNewDeliveryDate();
		// String yyyy = DeliveryDate.substring(0, 4);
		// String mm = DeliveryDate.substring(4, 6);
		// String dd = DeliveryDate.substring(6, 8);
		// String ymd = yyyy + "-" + mm + "-" + dd;
		// NextdeliveryInfo.get(i).setNewDeliveryDate(ymd);
		// }
		return "success";
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public List<DeliveryInfo> getNextdeliveryInfo() {
		return NextdeliveryInfo;
	}

	public void setNextdeliveryInfo(List<DeliveryInfo> nextdeliveryInfo) {
		NextdeliveryInfo = nextdeliveryInfo;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		NextDeliveryLoadAction.logger = logger;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

}
