package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.ResultListMgr;
import com.np.order.objects.EstimationResult;
import com.np.order.objects.EstimationResultFilter;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class QuoteallFindAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(QuoteallFindAction.class);
	/*
	 * 見積番号
	 */
	private String estimationCd;
	/*
	 * 得意先
	 */
	private String customerName;
	/*
	 * 商品種別
	 */
	private String categoryType;
	/*
	 * 期間
	 */
	private String dateFrom;
	private String dateTo;
	private List<EstimationResult> estimationResult;

	public String execute() throws Exception {
		EstimationResultFilter estimationResultFilter = new EstimationResultFilter();
		estimationResultFilter.setEstimationCd(estimationCd);
		estimationResultFilter.setCustomerName(customerName);
		estimationResultFilter.setCategoryType(categoryType);
		estimationResultFilter.setDateFrom(dateFrom);
		estimationResultFilter.setDateTo(dateTo);
		logger.debug("====================見積実績一覧 Begin=======================");
		estimationResult = ResultListMgr.getEstimationResult(estimationResultFilter);
		logger.debug("====================見積実績一覧 End=======================");
		response(estimationResult);
		return "success";
	}

	public String getEstimationCd() {
		return estimationCd;
	}

	public void setEstimationCd(String estimationCd) {
		this.estimationCd = estimationCd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public List<EstimationResult> getEstimationResult() {
		return estimationResult;
	}

	public void setEstimationResult(List<EstimationResult> estimationResult) {
		this.estimationResult = estimationResult;
	}

}
