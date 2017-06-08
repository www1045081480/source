package jp.newpulse.action.invoice.monthly_arrival_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.base.utils.UDebugger;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.FinaceMgr;
import com.np.order.models.Users;
import com.np.order.objects.ArrivalResult;
import com.np.order.objects.ArrivalResultFilter;

import jp.newpulse.action.BaseExcelAction;;

@SuppressWarnings("serial")
public class DownloadMonthlyArrivalList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadMonthlyArrivalList.class);
	private String year;
	private String month;
	private List<ArrivalResult> arrivalResult;

	public String execute() throws Exception {
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

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("arrivals", arrivalResult);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());
		context.put("yyyymm", year + "年" + month + "月");

		return response("MonthlyArrivalList.xml", context);
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

}
