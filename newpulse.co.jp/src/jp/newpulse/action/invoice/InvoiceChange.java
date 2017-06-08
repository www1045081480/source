package jp.newpulse.action.invoice;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.Money;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.InvoiceSheetMgr;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.PackingList;
import com.np.order.views.InvoiceDetailView;
import com.np.order.views.PackingListDetailView;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class InvoiceChange extends ActionSupport {
	private static Log logger = LogFactory.getLog(InvoiceCreateAction.class);
	private String invoiceID;
	private Long packingListId;

	// 增加识别修改标志
	private String InFlgChange = "c";

	public PackingList getPackingList() {
		return packingList;
	}

	public void setPackingList(PackingList packingList) {
		this.packingList = packingList;
	}

	private InvoiceSheet invoiceSheet;
	private PackingList packingList;
	// invoiceSheet中的内容
	private String InvoiceCD;
	private Long CustomerId;
	private String CustomerName;
	private String Address1;
	private String Address2;
	private String Address3;
	private String Tel;
	private String Fax;
	private String Title;
	private Money Amount;
	private String DeliveryType;
	private String ShippingType;
	private String Currency;
	private String OrderNo;
	private String ReceiptNo;
	private String Receiver;
	private String ShippingCompany;
	private String ShippingDate;
	private String ShippingFrom;
	private String ShippingTo;
	private String IssueDate;
	private String Notify;
	private String Marks;
	private Integer Quantity;
	private String Unit;
	private Long UserId;
	private String nw;
	private String gw;
	private String m3;
	private String palletQuantity;
	private String kind;
	// -----------package-------
	private String NGM;

	// invoiceDetail中的内容
	private List<InvoiceDetailView> invoiceDetail;
	// packageDetail中的内容
	private List<PackingListDetailView> packingListDetail;

	public String execute() {
		// ------------------------------invoiceSheet加载star-------------------------
		invoiceSheet = InvoiceSheetMgr.fetchInvoiceSheet(Long.parseLong(invoiceID));
		InvoiceCD = invoiceSheet.getInvoiceCd();
		CustomerId = invoiceSheet.getCustomerId();
		CustomerName = invoiceSheet.getCustomerName();
		Address1 = invoiceSheet.getAddress1();
		Address2 = invoiceSheet.getAddress2();
		Address3 = invoiceSheet.getAddress3();
		Tel = invoiceSheet.getTel();
		Fax = invoiceSheet.getFax();
		Title = invoiceSheet.getTitle();
		Amount = invoiceSheet.getAmount();
		DeliveryType = invoiceSheet.getDeliveryType();
		ShippingType = invoiceSheet.getShippingType();
		ShippingDate = invoiceSheet.getShippingDate();
		Currency = invoiceSheet.getCurrency();
		OrderNo = invoiceSheet.getOrderNo();
		ReceiptNo = invoiceSheet.getReceiptNo();
		Receiver = invoiceSheet.getReceiver();
		ShippingCompany = invoiceSheet.getShippingCompany();
		ShippingFrom = invoiceSheet.getShippingFrom();
		ShippingTo = invoiceSheet.getShippingTo();
		IssueDate = invoiceSheet.getIssueDate();
		Notify = invoiceSheet.getNotify();
		Marks = invoiceSheet.getMarks();
		Quantity = invoiceSheet.getQuantity();
		Unit = invoiceSheet.getUnit();
		UserId = invoiceSheet.getUserId();
		if (UserId == null || "".equals(UserId)) {
			UserId = SessionMgr.getLoginUser().getUserId();
		}
		// ------------------------------invoiceSheet加载end-------------------------
		try {
			packingList = InvoiceSheetMgr.searchPackingList(Long.parseLong(invoiceID));
			nw = packingList.getNw();
			gw = packingList.getGw();
			m3 = packingList.getM3();
			NGM = packingList.getNotify();
			palletQuantity = packingList.getPalletQuantity();

			// ------------------------------invoiceDetail加载star-------------------------

			invoiceDetail = InvoiceSheetMgr.fetchInvoiceDetailsForModify(Long.parseLong(invoiceID));
			if (!invoiceDetail.isEmpty()) {
				logger.debug("UnitPrice:" + invoiceDetail.get(0).getUnitPrice());
			}

			// ------------------------------invoiceDetail加载end-------------------------

			// ------------------------------packageDetail加载star-------------------------
			PackingList packingList = InvoiceSheetMgr.searchPackingList(Long.parseLong(invoiceID));
			packingListId = packingList.getPackingListId();
			packingListDetail = InvoiceSheetMgr.fetchPackingListDetailsForModify(packingListId);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.trace(UDebugger.trace(e));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.trace(UDebugger.trace(e));
		}

		// ------------------------------packageDetail加载star-------------------------

		return "success";
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		InvoiceChange.logger = logger;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public InvoiceSheet getInvoiceSheet() {
		return invoiceSheet;
	}

	public void setInvoiceSheet(InvoiceSheet invoiceSheet) {
		this.invoiceSheet = invoiceSheet;
	}

	public String getInvoiceCD() {
		return InvoiceCD;
	}

	public void setInvoiceCD(String invoiceCD) {
		InvoiceCD = invoiceCD;
	}

	public Long getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(Long customerId) {
		CustomerId = customerId;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
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

	public String getAddress3() {
		return Address3;
	}

	public void setAddress3(String address3) {
		Address3 = address3;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getShippingDate() {
		return ShippingDate;
	}

	public void setShippingDate(String shippingDate) {
		ShippingDate = shippingDate;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Money getAmount() {
		return Amount;
	}

	public void setAmount(Money amount) {
		Amount = amount;
	}

	public String getDeliveryType() {
		return DeliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		DeliveryType = deliveryType;
	}

	public String getShippingType() {
		return ShippingType;
	}

	public void setShippingType(String shippingType) {
		ShippingType = shippingType;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getReceiptNo() {
		return ReceiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		ReceiptNo = receiptNo;
	}

	public String getReceiver() {
		return Receiver;
	}

	public void setReceiver(String receiver) {
		Receiver = receiver;
	}

	public String getShippingCompany() {
		return ShippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		ShippingCompany = shippingCompany;
	}

	public String getShippingFrom() {
		return ShippingFrom;
	}

	public void setShippingFrom(String shippingFrom) {
		ShippingFrom = shippingFrom;
	}

	public String getShippingTo() {
		return ShippingTo;
	}

	public void setShippingTo(String shippingTo) {
		ShippingTo = shippingTo;
	}

	public String getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}

	public String getNotify() {
		return Notify;
	}

	public void setNotify(String notify) {
		Notify = notify;
	}

	public String getMarks() {
		return Marks;
	}

	public void setMarks(String marks) {
		Marks = marks;
	}

	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public Long getUserId() {
		return UserId;
	}

	public void setUserId(Long userId) {
		UserId = userId;
	}

	public List<InvoiceDetailView> getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(List<InvoiceDetailView> invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public List<PackingListDetailView> getPackingListDetail() {
		return packingListDetail;
	}

	public void setPackingListDetail(List<PackingListDetailView> packingListDetail) {
		this.packingListDetail = packingListDetail;
	}

	public String getNw() {
		return nw;
	}

	public void setNw(String nw) {
		this.nw = nw;
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	public String getM3() {
		return m3;
	}

	public void setM3(String m3) {
		this.m3 = m3;
	}

	public String getPalletQuantity() {
		return palletQuantity;
	}

	public void setPalletQuantity(String palletQuantity) {
		this.palletQuantity = palletQuantity;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Long getPackingListId() {
		return packingListId;
	}

	public void setPackingListId(Long packingListId) {
		this.packingListId = packingListId;
	}

	public String getInFlgChange() {
		return InFlgChange;
	}

	public void setInFlgChange(String inFlgChange) {
		InFlgChange = inFlgChange;
	}

	public String getNGM() {
		return NGM;
	}

	public void setNGM(String nGM) {
		NGM = nGM;
	}

}
