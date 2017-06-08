package jp.newpulse.action.invoice.monthly_shipping_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.FinaceMgr;
import com.np.order.models.Users;
import com.np.order.objects.ShipResult;
import com.np.order.objects.ShipResultFilter;

import jp.newpulse.action.BaseExcelAction;

@SuppressWarnings("serial")
public class DownloadMonthlyShippingList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadMonthlyShippingList.class);
	private String year;
	private String month;
	private List<ShipResult> shipResult;

	public String execute() throws Exception {
		/*
		 * 年月
		 */
		ShipResultFilter shipResultFilter = new ShipResultFilter();
		shipResultFilter.setShipYear(year);
		shipResultFilter.setShipMonth(month);
		// 得到出荷済（売掛金）統計一覧集合
		shipResult = FinaceMgr.getShipResult(shipResultFilter);
		logger.debug("-------------------------Find----------------------------");
		logger.debug(year);
		logger.debug(month);
		logger.debug(shipResult);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("shippings", shipResult);

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());
		context.put("yyyymm", year + "年" + month + "月");

		return response("MonthlyShippingList.xml", context);
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

	public List<ShipResult> getShipResult() {
		return shipResult;
	}

	public void setShipResult(List<ShipResult> shipResult) {
		this.shipResult = shipResult;
	}

}
