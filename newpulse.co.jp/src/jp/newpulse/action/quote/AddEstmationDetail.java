package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.Money;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.models.EstiomationDetail;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AddEstmationDetail extends ActionSupport {
	private static Log logger = LogFactory.getLog(AddEstmationDetail.class);
	private String EstimationId;
	private String ItemId;
	private String Name;
	private String Type;
	private String PartsCd;
	private String ModelCd;
	private String Quantity;
	private String Unit;
	private String UnitPrice;
	private String Amount;
	private String Note;

	public String execute() throws Exception {
		logger.debug("=======AddDetail========S");
		EstiomationDetail detail = new EstiomationDetail();
		// 見積書踩番
		Long estimationId = Long.parseLong(EstimationId.trim());

		String currency = EstmationSheetMgr.getCurrency(estimationId);
		detail.setEstimationId(estimationId);
		// 商品ID
		// Item item = new Item();
		// item.setCnName(Name);
		// List<Item> itemName = ItemMgr.search(item);
		// Long itemId = itemName.get(0).getItemId();
		// System.out.println(itemId);
		// detail.setItemId(itemId);
		// 商品名称
		detail.setName(Name);
		// 商品区分
		detail.setType(Type);
		// 商品说明
		detail.setPartsCd(PartsCd);
		// 商品型番
		detail.setModelCd(ModelCd);
		// 商品单位
		detail.setUnit(Unit);
		// 商品数量
		Integer quantity = Integer.parseInt(Quantity);
		detail.setQuantity(quantity);
		// 商品单价
		detail.setUnitPrice(new Money(currency, UnitPrice));
		// 金额
		detail.setAmount(new Money(currency, Amount));
		// 商品备注
		detail.setNote(Note);
		// 商品ID

		logger.debug("[AddEstDetaill] itemId= " + ItemId);

		if (ItemId != null && !"".equals(ItemId)) {
			detail.setItemId(Long.parseLong(ItemId));
		}

		logger.debug("[AddEstDetaill] itemId= " + detail.getItemId());

		// detailId踩番
		// Long detailId = SequenceMgr.nextSeq(EstiomationDetail.class);
		// detail.setDetailId(detailId);
		// Long itemId = SequenceMgr.nextSeq(Item.class);
		// detail.setItemId(itemId);
		// TxMgr.insert(detail);
		EstmationSheetMgr.save(detail);
		// 返回見積書番号
		// EstmationSheet estmationSheet =
		// EstmationSheetMgr.search(estimationId);
		// String estimationCd = estmationSheet.getEstimationCd();
		// WriterRespone w = new WriterRespone();
		// w.response(estimationCd);
		logger.debug("=======AddDetail========E");
		return "success";
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getItemId() {
		return ItemId;
	}

	public void setItemId(String itemId) {
		ItemId = itemId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getPartsCd() {
		return PartsCd;
	}

	public void setPartsCd(String partsCd) {
		PartsCd = partsCd;
	}

	public String getModelCd() {
		return ModelCd;
	}

	public void setModelCd(String modelCd) {
		ModelCd = modelCd;
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

	public String getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

}
