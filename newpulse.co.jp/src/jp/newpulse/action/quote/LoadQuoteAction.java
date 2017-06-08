package jp.newpulse.action.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UJson;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.objects.CustomerNames;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class LoadQuoteAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(LoadQuoteAction.class);
	private String coustomerName;

	public String execute() throws Exception {
		CustomerNames names = CustomerMgr.getCustomersNames();
		coustomerName = UJson.toJsonString(names);
		logger.debug(coustomerName);
		// 响应页面
		response(names);

		return "success";
	}

	public String getCoustomerName() {
		return coustomerName;
	}

	public void setCoustomerName(String coustomerName) {
		this.coustomerName = coustomerName;
	}

}
