package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UJson;
import com.np.base.view.ViewModelMapper;
import com.np.order.biz.mast.SupplierMgr;
import com.np.order.models.Supplier;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class LoadSupplierAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(LoadSupplierAction.class);
	@SuppressWarnings("unused")
	private String adress;

	public String execute() throws Exception {
		// 入力条件から、CustomerMasterオブジェクトを作成
		Supplier supplier = ViewModelMapper.toModel(Supplier.class);
		// 検索
		logger.debug("---------------supplier-------------" + supplier);
		logger.debug(supplier);
		List<Supplier> suppliers = SupplierMgr.search(supplier);
		// 必要なら、JSON文字列に変換
		adress = UJson.toJsonString(suppliers);
		// 响应页面
		response(suppliers);
		return "success";
	}

}
