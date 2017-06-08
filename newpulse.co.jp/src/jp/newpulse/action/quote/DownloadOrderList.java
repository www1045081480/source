package jp.newpulse.action.quote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.base.view.ViewModelMapper;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.ResultListMgr;
import com.np.order.models.Users;
import com.np.order.objects.OrderResult;
import com.np.order.objects.OrderResultFilter;

import jp.newpulse.action.BaseExcelAction;

@SuppressWarnings("serial")
public class DownloadOrderList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadOrderList.class);
	/*
	 * 見積番号
	 */
	private String orderCd;
	/*
	 * 得意先
	 */
	private String supplierName;
	/*
	 * 商品種別
	 */
	private String categoryType;
	/*
	 * 期間
	 */
	private String dateFrom;
	private String dateTo;
	private List<OrderResult> OrderResult;

	public String execute() throws Exception {
		OrderResultFilter orderResultFilter = ViewModelMapper.toModel(OrderResultFilter.class);
		// orderResultFilter.setCategoryType(categoryType);
		// orderResultFilter.setDateFrom(dateFrom);
		// orderResultFilter.setDateTo(dateTo);
		// orderResultFilter.setOrderCd(orderCd);
		// orderResultFilter.setSupplierName(supplierName);
		logger.debug("====================注文実績一覧 Begin=======================");
		OrderResult = ResultListMgr.getOrderResult(orderResultFilter);
		logger.debug("***************OrderResult size = " + OrderResult.size() + "***************");
		// logger.debug("***************" +
		// UJson.toJsonString(OrderResult.get(0))+"***************");
		logger.debug("====================注文実績一覧 End=======================");

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("orders", OrderResult);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());

		return response("OrderList.xml", context);

	}

	public String getOrderCd() {
		return orderCd;
	}

	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public List<OrderResult> getOrderResult() {
		return OrderResult;
	}

	public void setOrderResult(List<OrderResult> orderResult) {
		OrderResult = orderResult;
	}
}
