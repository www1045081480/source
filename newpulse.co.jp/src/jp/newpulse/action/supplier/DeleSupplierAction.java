package jp.newpulse.action.supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.mast.SupplierMgr;

public class DeleSupplierAction {
	private static Log logger = LogFactory.getLog(DeleSupplierAction.class);
	private String supplierid;

	public String execute() throws Exception {
		Long id = Long.parseLong(supplierid);
		logger.debug("**************删除supplier****************");
		logger.debug("id=" + id);
		SupplierMgr.delete(id);
		logger.debug("**************删除成功****************");
		return "success";
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
}
