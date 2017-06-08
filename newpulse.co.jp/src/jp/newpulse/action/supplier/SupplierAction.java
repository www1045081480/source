package jp.newpulse.action.supplier;

import java.util.List;

import com.np.order.biz.mast.SupplierMgr;
import com.np.order.models.Supplier;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SupplierAction extends ActionSupport {
	/**
	 * 
	 */
	private List<Supplier> suppliers;

	public String execute() throws Exception {
		Supplier sup = new Supplier();
		suppliers = SupplierMgr.search(sup);
		return "success";
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

}
