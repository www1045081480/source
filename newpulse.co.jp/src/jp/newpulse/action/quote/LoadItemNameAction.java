package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.mast.ItemMgr;
import com.np.order.objects.ItemNames;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class LoadItemNameAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(LoadItemNameAction.class);

	public String execute() throws Exception {
		logger.debug("-------------ItemNamesLoad------------------");
		ItemNames names = ItemMgr.searchItemSearchNames();
		names.getItemCnNames().removeAll(names.getItemEnNames());
		names.getItemCnNames().addAll(names.getItemEnNames());
		names.getItemCnNames().removeAll(names.getItemJpNames());
		names.getItemCnNames().addAll(names.getItemJpNames());
		// itemName = UJson.toJsonString(names);
		logger.debug("商品名");
		logger.debug(names);
		// 响应页面
		response(names);

		return "success";
	}

}
