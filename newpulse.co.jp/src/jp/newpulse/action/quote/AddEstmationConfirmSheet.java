package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.Customer;
import com.np.order.models.EstmationSheet;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class AddEstmationConfirmSheet extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(AddEstmationConfirmSheet.class);
	private String IssueDate;
	private String CustomerId;
	private String CustomerName;
	private String PostCode;
	private String Address1;
	private String Address2;
	private String Title;
	private String StaffName;
	private String Tel;
	private String Fax;
	private String Amount;
	private String DeliveryType;
	private String DeliveryAddress;
	private String DeliveryDate;
	private String PaymentCondition;
	private String PaymentMethord;
	private String estimationCd;
	private String estimationId;
	// private String DiscountAmount;
	private String Currency;
	private String Note;
	private String OrderCd;
	private String yxtime;
	private String langFlg;

	public String execute() throws Exception {
		logger.debug("===============AddconfirmsheetBegin=================");
		EstmationSheet estmationSheet = new EstmationSheet();
		// 発行日
		estmationSheet.setIssueDate(IssueDate);
		if (estimationId != null && !"".equals(estimationId) && !"undefined".equals(estimationId))
			estmationSheet.setEstimationId(Long.parseLong(estimationId.trim()));
		// 临时的需要跟客户表关联取出英文简写
		Long customerId = Long.parseLong(CustomerId);
		Customer customer;
		customer = CustomerMgr.searchCustomer(customerId);
		@SuppressWarnings("unused")
		String enShortName = customer.getEnShortName();
		estmationSheet.setLangFlg(langFlg);
		// String estimationCd =
		// CodeAutoIncrement.nextEstmationCode(enShortName);
		estmationSheet.setEstimationCd(estimationCd);
		// 客户ID
		estmationSheet.setCustomerId(customerId);
		// 客户姓名
		estmationSheet.setCustomerName(CustomerName);
		// 客户邮编
		estmationSheet.setPostCode(PostCode);
		// 客户地址1
		estmationSheet.setAddress1(Address1);
		// 客户地址2
		estmationSheet.setAddress2(Address2);
		// 制品名
		estmationSheet.setTitle(Title);
		// 担当者姓名
		estmationSheet.setStaffName(StaffName);
		// 电话
		estmationSheet.setTel(Tel);
		// fax email
		estmationSheet.setFax(Fax);
		// 总价
		estmationSheet.setAmount(new Money(Currency, Amount));
		// 交货方式
		estmationSheet.setDeliveryType(DeliveryType);
		// 交货场所
		estmationSheet.setDeliveryAddress(DeliveryAddress);
		// 交货日期
		estmationSheet.setDeliveryLimitDays(DeliveryDate);
		// 支払条件
		estmationSheet.setPaymentCondition(PaymentCondition);
		// 支付方式
		estmationSheet.setPaymentMethord(PaymentMethord);
		// 折扣数额
		// Integer discountAmount = Integer.parseInt(DiscountAmount);
		// estmationSheet.setDiscountAmount(discountAmount);
		// 货币类型
		estmationSheet.setCurrency(Currency);
		// 备注
		estmationSheet.setNote(Note);

		estmationSheet.setEstimationOkDays(yxtime);

		estmationSheet.setUserId(SessionMgr.getLoginUserId());
		EstmationSheetMgr.confirm(estmationSheet, OrderCd);
		// EstmationSheetMgr.save(estmationSheet);
		logger.debug("===============AddconfirmsheetEnd=================");
		// System.out.println("===============sheetConfirmBegin"+OrderCd+"=================");
		// EstmationSheetMgr.confirm(estmationSheet.getEstimationId(),
		// OrderCd);
		// System.out.println("===============sheetConfirmEnd=================");

		// 返回estimationId
		response(estmationSheet.getEstimationId());
		return "success";
	}

	public String getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getPostCode() {
		return PostCode;
	}

	public void setPostCode(String postCode) {
		PostCode = postCode;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getDeliveryType() {
		return DeliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		DeliveryType = deliveryType;
	}

	public String getDeliveryAddress() {
		return DeliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		DeliveryAddress = deliveryAddress;
	}

	public String getDeliveryDate() {
		return DeliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}

	public String getPaymentCondition() {
		return PaymentCondition;
	}

	public void setPaymentCondition(String paymentCondition) {
		PaymentCondition = paymentCondition;
	}

	public String getPaymentMethord() {
		return PaymentMethord;
	}

	public void setPaymentMethord(String paymentMethord) {
		PaymentMethord = paymentMethord;
	}

	// public String getDiscountAmount() {
	// return DiscountAmount;
	// }
	// public void setDiscountAmount(String discountAmount) {
	// DiscountAmount = discountAmount;
	// }
	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public String getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(String estimationId) {
		this.estimationId = estimationId;
	}

	public String getEstimationCd() {
		return estimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
	}

	public String getOrderCd() {
		return OrderCd;
	}

	public void setOrderCd(String orderCd) {
		OrderCd = orderCd;
	}

	public String getYxtime() {
		return yxtime;
	}

	public void setYxtime(String yxtime) {
		this.yxtime = yxtime;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}
}
