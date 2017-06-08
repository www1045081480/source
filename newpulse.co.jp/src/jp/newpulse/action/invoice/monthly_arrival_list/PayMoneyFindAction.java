package jp.newpulse.action.invoice.monthly_arrival_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.FinaceMgr;
import com.np.order.objects.ArrivalResult;
import com.np.order.objects.ArrivalResultFilter;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class PayMoneyFindAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(PayMoneyFindAction.class);
	private String year;
	private String month;
	private List<ArrivalResult> arrivalResult;

	public String execute() {
		ArrivalResultFilter shipResultFilter = new ArrivalResultFilter();
		shipResultFilter.setShipYear(year);
		shipResultFilter.setShipMonth(month);
		// 得到入荷済み一覧一覧集合
		try {
			arrivalResult = FinaceMgr.getArrivalResult(shipResultFilter);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------入荷済み一覧Find----------------------------");
		logger.debug(year);
		logger.debug(month);
		logger.debug(arrivalResult);
		responseForView(arrivalResult);
		// response(true);
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

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		PayMoneyFindAction.logger = logger;
	}

	public List<ArrivalResult> getArrivalResult() {
		return arrivalResult;
	}

	public void setArrivalResult(List<ArrivalResult> arrivalResult) {
		this.arrivalResult = arrivalResult;
	}

}
