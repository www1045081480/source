package jp.newpulse.action.item;

import java.util.List;

import com.np.order.biz.mast.ItemMgr;
import com.np.order.models.Item;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class FindItemAction extends BaseJsonAction {
	/**
	 * 
	 */
	private List<Item> items;
	private String jpname;
	private String jpdesc;
	private String cnname;
	private String cndesc;
	private String enname;
	private String endesc;
	private String categorytype;
	private String family;
	private String partscd;
	private String modelcd;

	public String execute() throws Exception {
		Item item = new Item();

		// System.out.println("==========================");
		// System.out.println(jpname);
		// System.out.println(jpdesc);
		// System.out.println(cnname);
		// System.out.println(cndesc);
		// System.out.println(enname);
		// System.out.println(categorytype);
		// System.out.println(family);
		// System.out.println(modelcd);
		// System.out.println("==========================");

		item.setJpName(jpname);
		item.setJpDesc(jpdesc);
		item.setCnName(cnname);
		item.setCnDesc(cndesc);
		item.setEnName(enname);
		item.setEnDesc(endesc);
		item.setCategoryType(categorytype);
		item.setFamily(family);
		item.setPartsCd(partscd);
		item.setModelCd(modelcd);

		items = ItemMgr.search(item);
		response(items);

		return "success";
	}

	public String getJpdesc() {
		return jpdesc;
	}

	public void setJpdesc(String jpdesc) {
		this.jpdesc = jpdesc;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getCndesc() {
		return cndesc;
	}

	public void setCndesc(String cndesc) {
		this.cndesc = cndesc;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getEndesc() {
		return endesc;
	}

	public void setEndesc(String endesc) {
		this.endesc = endesc;
	}

	public String getCategorytype() {
		return categorytype;
	}

	public void setCategorytype(String categorytype) {
		this.categorytype = categorytype;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getPartscd() {
		return partscd;
	}

	public void setPartscd(String partscd) {
		this.partscd = partscd;
	}

	public String getModelcd() {
		return modelcd;
	}

	public void setModelcd(String modelcd) {
		this.modelcd = modelcd;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getJpname() {
		return jpname;
	}

	public void setJpname(String jpname) {
		this.jpname = jpname;
	}
}
