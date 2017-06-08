package jp.newpulse.action.invoice.monthly_arrival_list;

import java.util.List;

import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.PayableAmount;
import com.np.order.objects.PayableAmountFilter;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class GetPayableAmounts extends BaseJsonAction {

	private String supplierId;
	private String year;
	private String month;
	private List<PayableAmount> result;

	public String execute() throws Exception {
		PayableAmountFilter payableAmountFilter = new PayableAmountFilter();
		payableAmountFilter.setBalanceYear(year);
		;
		payableAmountFilter.setBalanceMonth(month);
		payableAmountFilter.setSupplierId(Long.parseLong(supplierId));
		result = PayReceiveAmountMgr.getPayableAmounts(payableAmountFilter);

		response(result);

		return "success";
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<PayableAmount> getResult() {
		return result;
	}

	public void setResult(List<PayableAmount> result) {
		this.result = result;
	}

}
