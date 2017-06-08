package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.order.Money;
import com.np.order.biz.invoice.OrderSheetMgr;
import com.np.order.models.OrderDetail;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AddOrderDetail extends ActionSupport {
	private static Log logger = LogFactory.getLog(AddOrderDetail.class);
	private Long detail_id;

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	private String order_id;
	private String item_id;
	private String name;
	private String quantity;
	private String unit;
	private String unit_price;
	private String amount;
	private String deliveryDate;
	private String type;
	private String partsCd;
	private String modelCd;

	public String execute() throws Exception {
		OrderDetail od = new OrderDetail();
		detail_id = SequenceMgr.nextSeq(OrderDetail.class);

		od.setDetailId(detail_id);
		od.setOrderId(Long.parseLong(order_id));
		logger.debug("###########插入orderdetail###########");
		logger.debug(item_id);
		if (item_id == null || "undefined".equals(item_id))
			item_id = "0";
		od.setItemId(Long.parseLong(item_id));
		od.setName(name);
		if (!(quantity == null || "undefined".equals(quantity) || "".equals(quantity)))
			od.setQuantity(Integer.parseInt(quantity));
		od.setUnit(unit);

		String currency = OrderSheetMgr.getCurrency(od.getOrderId());
		if (!(unit_price == null || "undefined".equals(unit_price) || "".equals(unit_price))) {
			od.setUnitPrice(new Money(currency, unit_price));
		}
		od.setAmount(new Money(currency, amount));

		od.setDeliveryDate(deliveryDate.replace("-", ""));
		od.setType(type);
		od.setPartsCd(partsCd);
		od.setModelCd(modelCd);
		TxMgr.insert(od);

		return "success";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(String unit_price) {
		this.unit_price = unit_price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartsCd() {
		return partsCd;
	}

	public void setPartsCd(String partsCd) {
		this.partsCd = partsCd;
	}

	public String getModelCd() {
		return modelCd;
	}

	public void setModelCd(String modelCd) {
		this.modelCd = modelCd;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}
