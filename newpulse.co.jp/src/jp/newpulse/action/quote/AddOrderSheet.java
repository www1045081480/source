package jp.newpulse.action.quote;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.Money;
import com.np.order.biz.invoice.CodeAutoIncrement;
import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.models.OrderSheet;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class AddOrderSheet extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(AddOrderSheet.class);
	private String IssueDate;
	private String supplierName;
	private String supplierId;
	private String PostCode;
	private String Address1;
	private String Address2;
	private String Tel;
	private String Fax;
	private String StaffName;
	private String Amount;
	private String Title;
	private String Note;
	private String DeliveryDate;
	private String DeliveryAddress;
	private String payment_condition;
	private String payment_method;
	private String EstimationId;
	private String package_method;
	private String delivery_method;
	private String validdate;
	private String orderCd;
	private String enShortName;
	private String orderId;
	private String estimationIds;
	private String userId;
	private String currency;
	private String langFlg;
	private String discount;

	public String execute() throws Exception {
		logger.debug("===============-注文添加begin=============");
		OrderSheet orderSheet = new OrderSheet();
		logger.debug("===============" + orderCd + "============");
		if ("".equals(orderCd) || orderCd == null || "undefined".equals(orderCd)) {
			this.setOrderCd(CodeAutoIncrement.nextOrderCode(enShortName));
		} else {
			orderSheet.setOrderCd(orderCd);
		}
		orderSheet.setIssueDate(IssueDate);
		orderSheet.setSupplierName(supplierName);
		if (!("".equals(supplierId) || supplierId == null || "undefined".equals(supplierId)))
			orderSheet.setSupplierId(Long.parseLong(supplierId));
		orderSheet.setPostCode(PostCode);
		orderSheet.setAddress1(Address1);
		orderSheet.setAddress2(Address2);
		orderSheet.setTel(Tel);
		orderSheet.setFax(Fax);
		orderSheet.setStaffName(StaffName);
		orderSheet.setAmount(new Money(currency, Amount));
		orderSheet.setNote(Note);
		orderSheet.setDeliveryDate(DeliveryDate.replace("-", ""));
		orderSheet.setDeliveryAddress(DeliveryAddress);
		orderSheet.setPaymentCondition(payment_condition);
		orderSheet.setPackageMethod(package_method);
		orderSheet.setDeliveryMethod(delivery_method);
		orderSheet.setValidDate(validdate);
		orderSheet.setOrderCd(orderCd);
		orderSheet.setPaymentMethord(payment_method);
		orderSheet.setCurrency(currency);
		orderSheet.setLangFlg(langFlg);
		Money disMoney = new Money(currency, discount);
		orderSheet.setDiscountAmount(disMoney);
		Long Id;
		if ("".equals(orderId) || orderId == null) {
			// Id = SequenceMgr.nextSeq(OrderSheet.class);
			orderSheet.setOrderId(null);
			// TxMgr.insert(orderSheet);
		} else {
			Id = Long.parseLong(orderId);
			orderSheet.setOrderId(Id);
			// OrderSheetMgr.modify(orderSheet);
		}
		if (userId != null && !"undefined".equals(userId) && !"".equals(userId))
			orderSheet.setUserId(Long.parseLong(userId.trim()));

		logger.debug(estimationIds);
		List<Long> list = new ArrayList<Long>();
		if (estimationIds != null && estimationIds != "") {
			String[] str = estimationIds.split(";");

			for (int i = 0; i < str.length; i++) {
				list.add(Long.parseLong(str[i]));
			}
			;
		}

		logger.debug(orderSheet);
		OrderSheetMgr.save(list, orderSheet);

		response(orderSheet);

		return "success";
	}

	public String getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
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

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
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

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getPayment_condition() {
		return payment_condition;
	}

	public void setPayment_condition(String payment_condition) {
		this.payment_condition = payment_condition;
	}

	public String getPackage_method() {
		return package_method;
	}

	public void setPackage_method(String package_method) {
		this.package_method = package_method;
	}

	public String getDelivery_method() {
		return delivery_method;
	}

	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}

	public String getValiddate() {
		return validdate;
	}

	public void setValiddate(String validdate) {
		this.validdate = validdate;
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getEnShortName() {
		return enShortName;
	}

	public void setEnShortName(String enShortName) {
		this.enShortName = enShortName;
	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEstimationIds() {
		return estimationIds;
	}

	public void setEstimationIds(String estimationIds) {
		this.estimationIds = estimationIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
}
