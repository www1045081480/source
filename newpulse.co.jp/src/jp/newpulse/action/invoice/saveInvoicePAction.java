package jp.newpulse.action.invoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.Money;
import com.np.order.biz.invoice.InvoiceSheetMgr;
import com.np.order.models.InvoiceSheet;
import com.np.order.models.PackingList;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class saveInvoicePAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(saveInvoicePAction.class);

	private String InvoiceID;
	private String PackageID;
	private String PackingListId;
	private String orderId;
	private String InvoiceCD;
	private String IssueDate;
	private String CustomerName;
	private String Address1;
	private String Address2;
	private String Address3;
	private String Tel;
	private String ShippingType;
	private String ShippingDate;
	private String DeliveryType;
	private String ShippingFrom;
	private String OrderNo;
	private String ShippingTo;
	private String Title;
	private String Receiver;
	private String ShippingCompany;
	private String Notify;
	private String Quantity;
	private String Unit;
	private String Amount;
	private String Currency;
	private String PalletQuantity;
	private String QuantityP;
	private String Nw;
	private String Gw;
	private String M3;
	private String MarksI;
	private String MarksP;
	private String CustomerId;
	private String ReceiptNo;

	// ----------package
	private String NGM;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		logger.debug("begin");
		// invoice
		boolean flag = false;
		try {
			InvoiceSheet invoiceSheet = new InvoiceSheet();
			if (!InvoiceID.equals("")) {
				invoiceSheet.setInvoiceId(Long.parseLong(InvoiceID));
			}
			invoiceSheet.setInvoiceCd(InvoiceCD);
			invoiceSheet.setCustomerId(Long.parseLong(CustomerId));
			invoiceSheet.setIssueDate(IssueDate);
			invoiceSheet.setCustomerName(CustomerName);
			invoiceSheet.setAddress1(Address1);
			invoiceSheet.setAddress2(Address2);
			invoiceSheet.setAddress3(Address3);
			invoiceSheet.setTel(Tel);
			invoiceSheet.setReceiptNo(ReceiptNo);
			invoiceSheet.setShippingType(ShippingType);
			invoiceSheet.setShippingDate(ShippingDate);
			invoiceSheet.setDeliveryType(DeliveryType);
			invoiceSheet.setShippingFrom(ShippingFrom);
			invoiceSheet.setOrderNo(OrderNo);
			invoiceSheet.setShippingTo(ShippingTo);
			invoiceSheet.setTitle(Title);
			invoiceSheet.setReceiver(Receiver);
			invoiceSheet.setMarks(MarksI);

			logger.debug("------------------------MarksI--------------------------------");
			logger.debug(MarksI);
			logger.debug("------------------------MarksI--------------------------------");
			logger.debug("------------------------MarksP--------------------------------");
			logger.debug(MarksP);
			logger.debug("------------------------MarksP--------------------------------");
			invoiceSheet.setShippingCompany(ShippingCompany);
			// invoiceSheet.setNotify(Notify);
			int quantity = Integer.parseInt(Quantity.replace(",", ""));
			logger.debug("------------------------Quantity Star--------------------------------");
			logger.debug(Quantity);
			logger.debug("------------------------Quantity end--------------------------------");
			invoiceSheet.setQuantity(quantity);
			invoiceSheet.setUnit(Unit);

			invoiceSheet.setAmount(new Money(Currency, Amount));
			invoiceSheet.setCurrency(Currency);
			// package;
			PackingList packingList = new PackingList();
			if (!PackingListId.equals("")) {
				packingList.setPackingListId(Long.parseLong(PackingListId));
			}
			if (!InvoiceID.equals("")) {
				packingList.setInvoiceId(Long.parseLong(InvoiceID));
			}
			packingList.setCustomerId(Long.parseLong(CustomerId));
			packingList.setIssueDate(IssueDate);
			packingList.setCustomerName(CustomerName);
			packingList.setAddress1(Address1);
			packingList.setAddress2(Address2);
			packingList.setAddress3(Address3);
			packingList.setReceiptNo(ReceiptNo);
			packingList.setTel(Tel);
			packingList.setShippingType(ShippingType);
			packingList.setShippingDate(ShippingDate);
			packingList.setDeliveryType(DeliveryType);
			packingList.setShippingFrom(ShippingFrom);
			packingList.setOrderNo(OrderNo);
			packingList.setShippingTo(ShippingTo);
			packingList.setTitle(Title);
			packingList.setReceiver(Receiver);
			packingList.setCurrency(Currency);
			packingList.setMarks(MarksP);
			packingList.setNotify(NGM);

			packingList.setShippingCompany(ShippingCompany);
			// packingList.setNotify(Notify);
			if (!QuantityP.equals("") && !QuantityP.equals(null)) {
				int quantity1 = Integer.parseInt(QuantityP);
				packingList.setQuantity(quantity1);
			}
			packingList.setUnit(Unit);
			packingList.setAmount(new Money(Currency, Amount));
			packingList.setNw(Nw);
			packingList.setGw(Gw);
			packingList.setM3(M3);
			packingList.setPalletQuantity(PalletQuantity);
			logger.debug("------------------------------------------------------------" + orderId);
			logger.debug(orderId);
			List<Long> orderIdList = new ArrayList<Long>();
			if (!orderId.equals("") && !orderId.equals(null)) {
				String[] strSub = orderId.split("@");
				logger.debug(strSub.length);
				logger.debug(strSub[0]);
				for (int i = 0; i < strSub.length; i++) {
					logger.debug("^^^^^^strSub[i]:" + strSub[i]);
					orderIdList.add(Long.parseLong(strSub[i]));
				}
			}
			flag = InvoiceSheetMgr.save(orderIdList, invoiceSheet, packingList);
			InvoiceID = Long.toString(invoiceSheet.getInvoiceId());
			logger.debug("^^^^^^InvoiceID:" + InvoiceID);
			PackingListId = Long.toString(packingList.getPackingListId());
			logger.debug("^^^^^^PackingListId:" + PackingListId);
		} catch (NumberFormatException e) {
			logger.trace(UDebugger.trace(e));
			flag = false;
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
			flag = false;
		}

		Map result = new HashMap();
		result.put("InvoiceID", InvoiceID);
		result.put("PackingListId", PackingListId);
		response(result);
		return (flag) ? SUCCESS : ERROR;
	}

	public String getInvoiceCD() {
		return InvoiceCD;
	}

	public void setInvoiceCD(String invoiceCD) {
		InvoiceCD = invoiceCD;
	}

	public String getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
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

	public String getShippingType() {
		return ShippingType;
	}

	public void setShippingType(String shippingType) {
		ShippingType = shippingType;
	}

	public String getShippingDate() {
		return ShippingDate;
	}

	public void setShippingDate(String shippingDate) {
		ShippingDate = shippingDate;
	}

	public String getDeliveryType() {
		return DeliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		DeliveryType = deliveryType;
	}

	public String getShippingFrom() {
		return ShippingFrom;
	}

	public void setShippingFrom(String shippingFrom) {
		ShippingFrom = shippingFrom;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getShippingTo() {
		return ShippingTo;
	}

	public void setShippingTo(String shippingTo) {
		ShippingTo = shippingTo;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
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

	public String getNotify() {
		return Notify;
	}

	public void setNotify(String notify) {
		Notify = notify;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getPalletQuantity() {
		return PalletQuantity;
	}

	public void setPalletQuantity(String palletQuantity) {
		PalletQuantity = palletQuantity;
	}

	public String getQuantityP() {
		return QuantityP;
	}

	public void setQuantityP(String quantityP) {
		QuantityP = quantityP;
	}

	public String getNw() {
		return Nw;
	}

	public void setNw(String nw) {
		Nw = nw;
	}

	public String getGw() {
		return Gw;
	}

	public void setGw(String gw) {
		Gw = gw;
	}

	public String getM3() {
		return M3;
	}

	public void setM3(String m3) {
		M3 = m3;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceID() {
		return InvoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		InvoiceID = invoiceID;
	}

	public String getPackageID() {
		return PackageID;
	}

	public void setPackageID(String packageID) {
		PackageID = packageID;
	}

	public String getPackingListId() {
		return PackingListId;
	}

	public void setPackingListId(String packingListId) {
		PackingListId = packingListId;
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		saveInvoicePAction.logger = logger;
	}

	public String getMarksI() {
		return MarksI;
	}

	public void setMarksI(String marksI) {
		MarksI = marksI;
	}

	public String getMarksP() {
		return MarksP;
	}

	public void setMarksP(String marksP) {
		MarksP = marksP;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getReceiptNo() {
		return ReceiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		ReceiptNo = receiptNo;
	}

	public String getNGM() {
		return NGM;
	}

	public void setNGM(String nGM) {
		NGM = nGM;
	}

}
