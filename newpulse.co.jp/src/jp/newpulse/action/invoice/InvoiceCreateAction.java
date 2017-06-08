package jp.newpulse.action.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.CodeAutoIncrement;
import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.models.Customer;
import com.np.order.views.OrderDetailView;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class InvoiceCreateAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(InvoiceCreateAction.class); // CustomerId传回页面
	private String CustomerId;
	// 方式 S，A，E
	private String way;
	// 到达方式 CIF，FOB
	private String arrive;
	// 返回画面踩翻CD
	private String InvoiceCD;
	// 要处理的字符串
	private String str;
	// 货币种类初始化
	// 画面显示客户信息
	private String EnShortName;
	private String EnName;
	private String EnAddress1;
	private String EnAddress2;
	private String EnAddress3;
	private String Tel1;
	private String Fax;
	private String Currency;

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	// 换面入口区分
	private String KIND;
	// orderId用来存invoice和package的用来关联的
	private String orderId;
	private List<OrderDetailView> orderDetail;
	private List<Customer> customer;

	public String execute() {
		Long detailId = 0L;
		Long customerId = 0L;
		List<Long> detailIdList = new ArrayList<Long>();
		String[] strSub = str.split(",");
		orderId = "";
		try {
			for (int i = 0; i < strSub.length; i++) {
				String[] strCh = strSub[i].split(";");
				logger.debug("--------------------------------");
				logger.debug(strSub[i]);
				logger.debug(strCh.length);
				detailId = Long.parseLong(strCh[0]);
				customerId = Long.parseLong(strCh[1]);
				orderId = orderId + Long.parseLong(strCh[2]) + "@";
				// OrderDetail Odetail = new OrderDetail();
				// Odetail.setDetailId(detailId);
				// List<OrderDetail> ORDERDetail = searchOrderDetail(Odetail);
				// ORDERDetail.get(0).setDetailId(detailId);
				detailIdList.add(detailId);
				logger.debug("--------------detailId------------------");
				logger.debug(detailId);
				/*
				 * 通过detailId集合查询到detail明细在invoice明细中展示出来
				 */
				// detailIdList.add(detailId);
			}
			orderDetail = OrderSheetMgr.fetchDetailsForCreateInvoice(detailIdList);
		} catch (NumberFormatException e1) {
			logger.trace(UDebugger.trace(e1));
		} catch (Exception e1) {
			logger.trace(UDebugger.trace(e1));
		}
		logger.debug(orderDetail);
		// 画面显示客户信息处理
		try {
			CustomerId = "" + customerId;
			Customer c = new Customer();
			c.setCustomerId(customerId);
			customer = searchCustomerAddresses(c);
			EnShortName = customer.get(0).getEnShortName();
			EnName = customer.get(0).getEnName();
			EnAddress1 = customer.get(0).getEnAddress1();
			EnAddress2 = customer.get(0).getEnAddress2();
			EnAddress3 = customer.get(0).getEnAddress3();
			Tel1 = customer.get(0).getTel1();
			Fax = customer.get(0).getFax();
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		char chs[] = way.toCharArray();
		logger.debug(chs[0]);
		try {
			InvoiceCD = CodeAutoIncrement.nextInvoiceCode(chs[0]);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug(InvoiceCD);

		return "success";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Customer> searchCustomerAddresses(Customer customer) throws Exception {

		Criteria<Customer> criteria = new Criteria(Customer.class);

		criteria.and(Restrictions.eq(Customer.CustomerId, customer.getCustomerId()));

		criteria.addSelectedColumn(Customer.EnShortName);
		criteria.addSelectedColumn(Customer.EnName);
		criteria.addSelectedColumn(Customer.EnAddress1);
		criteria.addSelectedColumn(Customer.EnAddress2);
		criteria.addSelectedColumn(Customer.EnAddress3);
		criteria.addSelectedColumn(Customer.Tel1);
		criteria.addSelectedColumn(Customer.Fax);

		List<Customer> result = criteria.list();
		return result;
	}

	/*
	 * public static List<OrderDetail> searchOrderDetail(OrderDetail
	 * orderDetail) throws Exception {
	 * 
	 * Criteria<OrderDetail> criteria = new Criteria(OrderDetail.class); //
	 * 输入检索的key值 criteria.and(Restrictions.eq(OrderDetail.DetailId,
	 * orderDetail.getDetailId())); // 检索出来的数据
	 * criteria.addSelectedColumn(OrderDetail.ItemId);
	 * criteria.addSelectedColumn(OrderDetail.Name);
	 * criteria.addSelectedColumn(OrderDetail.PartsCd);
	 * criteria.addSelectedColumn(OrderDetail.OrderId);
	 * criteria.addSelectedColumn(OrderDetail.DeliveryDate);
	 * criteria.addSelectedColumn(OrderDetail.ModelCd);
	 * criteria.addSelectedColumn(OrderDetail.Quantity);
	 * criteria.addSelectedColumn(OrderDetail.Unit);
	 * criteria.addSelectedColumn(OrderDetail.UnitPrice);
	 * criteria.addSelectedColumn(OrderDetail.Amount);
	 * 
	 * List<OrderDetail> result = criteria.list(); return result; }
	 */

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getEnShortName() {
		return EnShortName;
	}

	public void setEnShortName(String enShortName) {
		EnShortName = enShortName;
	}

	public String getEnAddress1() {
		return EnAddress1;
	}

	public void setEnAddress1(String enAddress1) {
		EnAddress1 = enAddress1;
	}

	public String getEnAddress2() {
		return EnAddress2;
	}

	public void setEnAddress2(String enAddress2) {
		EnAddress2 = enAddress2;
	}

	public String getEnAddress3() {
		return EnAddress3;
	}

	public void setEnAddress3(String enAddress3) {
		EnAddress3 = enAddress3;
	}

	public String getTel1() {
		return Tel1;
	}

	public void setTel1(String tel1) {
		Tel1 = tel1;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public String getEnName() {
		return EnName;
	}

	public void setEnName(String enName) {
		EnName = enName;
	}

	public List<OrderDetailView> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetailView> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getKIND() {
		return KIND;
	}

	public void setKIND(String kIND) {
		KIND = kIND;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getInvoiceCD() {
		return InvoiceCD;
	}

	public void setInvoiceCD(String invoiceCD) {
		InvoiceCD = invoiceCD;
	}

}
