package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.Money;
import com.np.order.biz.invoice.CodeAutoIncrement;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.Customer;
import com.np.order.models.EstmationSheet;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class AddEstmationSheet extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(AddEstmationSheet.class);
	private String EstimationId;
	private String EstimationCd;
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
	private String EstmationOKDays;
	private String PaymentCondition;
	private String PaymentMethord;
	private String Currency;
	private String Note;
	private String userId;
	private String lang_flg;

	public String execute() throws Exception {
		logger.debug("=============sheet添加 S=============");

		EstmationSheet estmationSheet = new EstmationSheet();
		if (!("".equals(EstimationId)) && !EstimationId.equals(null)) {
			estmationSheet.setEstimationId(Long.parseLong(EstimationId));
		}
		// 発行日
		estmationSheet.setIssueDate(IssueDate);
		// 踩番规约
		// 临时的需要跟客户表关联取出英文简写
		Long customerId = Long.parseLong(CustomerId);
		Customer customer = CustomerMgr.searchCustomer(customerId);
		String enShortName = customer.getEnShortName();
		// System.out.println("1-----------");
		// System.out.println(EstimationCd);
		String estimationCd = "";
		// 客户ID
		estmationSheet.setCustomerId(customerId);
		// estmationSheet.setEstimationCd(estimationCd);
		if ((null == EstimationCd) || EstimationCd.equals("")) {
			estimationCd = CodeAutoIncrement.nextEstmationCode(enShortName);
			estmationSheet.setEstimationCd(estimationCd);
		} else {
			estimationCd = EstimationCd;
			estmationSheet.setEstimationCd(estimationCd);

		}
		;
		// 踩番estimationId并且插入
		// Long estimationId = SequenceMgr.nextSeq(EstmationSheet.class);
		// estmationSheet.setEstimationId(estimationId);
		setEstma(estmationSheet);
		// System.out.println("-----------------------print
		// estmationSheet--------------------");
		// System.out.println(estmationSheet);
		// System.out.println("-----------------------print
		// estmationSheet--------------------");
		EstmationSheetMgr.save(estmationSheet);
		// System.out.println("-----------------------insert
		// finish-----------------------------");
		// 返回EstimationId和estimationCd
		String str = "" + estmationSheet.getEstimationId() + "@" + estimationCd + "@" + estmationSheet.getUserId();
		// System.out.println(str);
		response(str);
		logger.debug("=============sheet添加 E=============");
		return "success";
	}

	public void setEstma(EstmationSheet estmationSheet) {
		// 客户姓名
		estmationSheet.setCustomerName(CustomerName);
		// System.out.println("2-----------");
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
		// System.out.println("3-----------");
		// 电话
		estmationSheet.setTel(Tel);
		// fax email
		estmationSheet.setFax(Fax);
		// 总价Integer amount = Integer.parseInt(Amount);
		estmationSheet.setAmount(new Money(Currency, Amount));
		// System.out.println("4-----------");
		// 交货方式
		estmationSheet.setDeliveryType(DeliveryType);
		// 交货场所
		estmationSheet.setDeliveryAddress(DeliveryAddress);
		// 交货日期
		estmationSheet.setDeliveryLimitDays(DeliveryDate);
		// 报价单有效日
		estmationSheet.setEstimationOkDays(EstmationOKDays);
		// 支払条件
		estmationSheet.setPaymentCondition(PaymentCondition);
		// System.out.println("5-----------");
		// 支付方式
		estmationSheet.setPaymentMethord(PaymentMethord);
		// System.out.println("6-----------");
		// 折扣数额
		// Integer discountAmount = Integer.parseInt(DiscountAmount);
		// estmationSheet.setDiscountAmount(discountAmount);
		// System.out.println("7-----------");
		// 货币类型
		estmationSheet.setCurrency(Currency);
		// 备注
		estmationSheet.setNote(Note);
		estmationSheet.setLangFlg(lang_flg);

		if (userId != null && !"undefined".equals(userId) && !"".equals(userId))
			estmationSheet.setUserId(Long.parseLong(userId.trim()));
		// System.out.println("8-----------");
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getEstimationCd() {
		return EstimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		EstimationCd = estimationCd;
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

	public String getEstmationOKDays() {
		return EstmationOKDays;
	}

	public void setEstmationOKDays(String estmationOKDays) {
		EstmationOKDays = estmationOKDays;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLang_flg() {
		return lang_flg;
	}

	public void setLang_flg(String lang_flg) {
		this.lang_flg = lang_flg;
	}
}
