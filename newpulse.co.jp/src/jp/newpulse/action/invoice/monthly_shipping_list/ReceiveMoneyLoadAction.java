package jp.newpulse.action.invoice.monthly_shipping_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.biz.invoice.FinaceMgr;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.ReceivableAmount;
import com.np.order.objects.ReceivableAmountFilter;
import com.np.order.objects.ShipResult;
import com.np.order.objects.ShipResultFilter;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ReceiveMoneyLoadAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(ReceiveMoneyLoadAction.class);
	private String year;
	private String month;
	// 出荷済み一覧
	private List<ShipResult> shipResult;
	// 发货一览表/買掛金一覧
	private List<ReceivableAmount> receivableAmount;

	public String execute() {
		// 出荷済み一覧
		try {
			ShipResultFilter shipResultFilter = new ShipResultFilter();
			shipResultFilter.setShipYear(year);
			shipResultFilter.setShipMonth(month);
			shipResult = FinaceMgr.getShipResult(shipResultFilter);
			logger.debug("------------------------");
			// 发货一览表/買掛金一覧
			ReceivableAmountFilter receivableAmountFilter = new ReceivableAmountFilter();
			receivableAmountFilter.setBalanceYear(year);
			receivableAmountFilter.setBalanceMonth(month);
			receivableAmount = PayReceiveAmountMgr.getReceivableAmounts(receivableAmountFilter);
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

	public List<ShipResult> getShipResult() {
		return shipResult;
	}

	public void setShipResult(List<ShipResult> shipResult) {
		this.shipResult = shipResult;
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		ReceiveMoneyLoadAction.logger = logger;
	}

	public List<ReceivableAmount> getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(List<ReceivableAmount> receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

}
