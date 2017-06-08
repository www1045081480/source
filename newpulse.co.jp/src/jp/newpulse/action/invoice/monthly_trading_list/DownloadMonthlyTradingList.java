package jp.newpulse.action.invoice.monthly_trading_list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDate;
import com.np.base.utils.UDebugger;
import com.np.order.action.SessionMgr;
import com.np.order.biz.invoice.SellOrderAmountMgr;
import com.np.order.models.Users;
import com.np.order.objects.SellOrderDetail;
import com.np.order.objects.SellOrderFilter;
import com.np.order.objects.SellOrderInfo;
import com.np.order.objects.SellOrderSummary;

import jp.newpulse.action.BaseExcelAction;

@SuppressWarnings("serial")
public class DownloadMonthlyTradingList extends BaseExcelAction {
	private static Log logger = LogFactory.getLog(DownloadMonthlyTradingList.class);
	private String year;
	private String month;
	private List<SellOrderInfo> sellOrderInfo;
	private SellOrderSummary sellOrderSummary;

	public String execute() throws Exception {
		try {
			SellOrderFilter sellOrderFilter = new SellOrderFilter();
			sellOrderFilter.setShipYear(year);
			sellOrderFilter.setShipMonth(month);
			SellOrderDetail sellOrderDetail = SellOrderAmountMgr.getSellOrder(sellOrderFilter);
			sellOrderInfo = sellOrderDetail.getDetails();
			sellOrderSummary = sellOrderDetail.getSummary();

		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("invoices", sellOrderInfo);
		if (sellOrderSummary == null)
			sellOrderSummary = new SellOrderSummary();
		context.put("jpSum", sellOrderSummary.getJapanese());
		context.put("usSum", sellOrderSummary.getDoller());
		context.put("cnSum", sellOrderSummary.getChinese());

		Users user = SessionMgr.getLoginUser();
		context.put("user", user);
		context.put("createDate", UDate.getDate());
		context.put("yyyymm", year + "年" + month + "月");

		return response("MonthlyTradingList.xml", context);
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

	public List<SellOrderInfo> getSellOrderInfo() {
		return sellOrderInfo;
	}

	public void setSellOrderInfo(List<SellOrderInfo> sellOrderInfo) {
		this.sellOrderInfo = sellOrderInfo;
	}

}
