package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.view.ViewModelMapper;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.Customer;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class AddressQuoteAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(AddressQuoteAction.class);

	public String execute() throws Exception {
		// 入力条件から、CustomerMasterオブジェクトを作成
		Customer customer = ViewModelMapper.toModel(Customer.class);
		// 検索
		List<Customer> customers = CustomerMgr.searchCustomerAddresses(customer);

		logger.debug(customer);
		// 响应页面
		response(customers);
		return "success";
	}

}
