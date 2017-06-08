package jp.newpulse.action.invoice.monthly_shipping_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.FinaceMgr;
import com.np.order.objects.ShipResult;
import com.np.order.objects.ShipResultFilter;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ReceiveMoneyFindAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(ReceiveMoneyFindAction.class);
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
		responseForView(shipResult);
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

	public List<ShipResult> getShipResult() {
		return shipResult;
	}

	public void setShipResult(List<ShipResult> shipResult) {
		this.shipResult = shipResult;
	}

}
