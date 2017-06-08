package jp.newpulse.action.item;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.mast.ItemMgr;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DeleItemAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(DeleItemAction.class);
	/**
	 * 
	 */
	private String itemid;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String execute() throws Exception {
		logger.debug("=====delete=" + itemid);
		ItemMgr.delete(Long.parseLong(itemid));
		return "success";
	}
}
