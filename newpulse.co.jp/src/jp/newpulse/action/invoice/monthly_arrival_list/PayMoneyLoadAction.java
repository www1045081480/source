package jp.newpulse.action.invoice.monthly_arrival_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.FinaceMgr;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.ArrivalResult;
import com.np.order.objects.ArrivalResultFilter;
import com.np.order.objects.PayableAmount;
import com.np.order.objects.PayableAmountFilter;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PayMoneyLoadAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(PayMoneyLoadAction.class);
	private String year;
	private String month;
	// 入荷済み一覧(入荷済（買掛金)統計一覧)
	private List<ArrivalResult> arrivalResult;
	// 買掛金一覧
	private List<PayableAmount> payableAmount;

	public String execute() {
		ArrivalResultFilter arrivalResultFilter = new ArrivalResultFilter();
		arrivalResultFilter.setShipYear(year);
		arrivalResultFilter.setShipMonth(month);
		PayableAmountFilter payableAmountFilter = new PayableAmountFilter();
		payableAmountFilter.setBalanceYear(year);
		payableAmountFilter.setBalanceMonth(month);
		try {
			arrivalResult = FinaceMgr.getArrivalResult(arrivalResultFilter);
			payableAmount = PayReceiveAmountMgr.getPayableAmounts(payableAmountFilter);
			// PayableAmount p = new PayableAmount();
			// p.setTradingNo("123");
			// payableAmount.set(0, p);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		return "success";
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

	public List<ArrivalResult> getArrivalResult() {
		return arrivalResult;
	}

	public void setArrivalResult(List<ArrivalResult> arrivalResult) {
		this.arrivalResult = arrivalResult;
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		PayMoneyLoadAction.logger = logger;
	}

	public List<PayableAmount> getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(List<PayableAmount> payableAmount) {
		this.payableAmount = payableAmount;
	}

}
