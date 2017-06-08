package jp.newpulse.action.item;

import java.util.List;

import com.np.order.biz.mast.ItemMgr;
import com.np.order.models.Item;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ItemAction extends ActionSupport {
	/**
	 * 
	 */
	private List<Item> items;

	public String execute() throws Exception {
		Item item = new Item();
		items = ItemMgr.search(item);
		return "success";
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
