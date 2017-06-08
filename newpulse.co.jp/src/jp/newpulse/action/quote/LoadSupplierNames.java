package jp.newpulse.action.quote;

import java.util.List;

import com.np.base.db.Query;
import com.np.order.models.Supplier;
import com.np.order.objects.SupplierNames;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class LoadSupplierNames extends BaseJsonAction {
	private String supplierName;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String execute() throws Exception {
		List<Supplier> list = Query.query(new Supplier());

		SupplierNames names = new SupplierNames();
		for (Supplier supplier : list) {
			names.setCnName(supplier.getCnName());
			names.setJpName(supplier.getJpName());
			names.setEnName(supplier.getEnName());
			names.setEnShortName(supplier.getEnShortName());
		}
		response(names);
		return "success";
	}

}
