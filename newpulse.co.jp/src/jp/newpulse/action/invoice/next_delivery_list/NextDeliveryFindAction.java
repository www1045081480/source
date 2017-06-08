package jp.newpulse.action.invoice.next_delivery_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.objects.DeliveryInfo;
import com.opensymphony.xwork2.ActionSupport;

/*
 * 翌月以後配送一覧 中检索查询
 * 
 * 
 * */
@SuppressWarnings("serial")
public class NextDeliveryFindAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(NextDeliveryFindAction.class);
	private String customerName;
	private String customerOrder;
	private String date;
	private List<DeliveryInfo> NextdeliveryInfo;

	public String execute() throws Exception {
		logger.debug("----------NextdeliveryInfo--------------");
		logger.debug(NextdeliveryInfo);
		// for(int i=0;i<NextdeliveryInfo.size();i++){
		// String DeliveryDate = NextdeliveryInfo.get(i).getDeliveryDate();
		// String yyyy = DeliveryDate.substring(0, 4);
		// String mm = DeliveryDate.substring(4, 6);
		// String dd = DeliveryDate.substring(6, 8);
		// String ymd = yyyy+"-"+mm+"-"+dd;
		// NextdeliveryInfo.get(i).setDeliveryDate(ymd);
		// //logger.debug(yyyy);
		// //logger.debug(mm);
		//
		// }

		logger.debug(customerName);
		logger.debug(customerOrder);
		return "success";
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public List<DeliveryInfo> getNextdeliveryInfo() {
		return NextdeliveryInfo;
	}

	public void setNextdeliveryInfo(List<DeliveryInfo> nextdeliveryInfo) {
		NextdeliveryInfo = nextdeliveryInfo;
	}

}
