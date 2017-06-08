package jp.newpulse.action.quote;

import java.util.List;

import com.np.order.biz.mast.ItemMgr;
import com.np.order.models.Item;

import jp.newpulse.action.BaseJsonAction;

//查询中文名字
@SuppressWarnings("serial")
public class FindItemAction extends BaseJsonAction {
	private String cnName;
	private List<Item> itemName;

	public String execute() throws Exception {
		Item item = new Item();
		item = ItemMgr.getSearch(cnName);
		// 响应页面
		response(item);

		return "success";
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public List<Item> getItemName() {
		return itemName;
	}

	public void setItemName(List<Item> itemName) {
		this.itemName = itemName;
	}

}
