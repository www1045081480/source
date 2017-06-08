package jp.newpulse.action.invoice.monthly_trading_list;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.invoice.SellOrderAmountMgr;
import com.np.order.objects.SellOrderDetail;
import com.np.order.objects.SellOrderFilter;
import com.np.order.objects.SellOrderInfo;
import com.np.order.objects.SellOrderSummary;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class BeforeAfterLoadAction extends BaseJsonAction {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(BeforeAfterLoadAction.class);
	private String year;
	private String month;
	private SellOrderSummary sell;

	public SellOrderSummary getSell() {
		return sell;
	}

	public void setSell(SellOrderSummary sell) {
		this.sell = sell;
	}

	private List<SellOrderInfo> sellOrderInfo;

	public String execute() throws Exception {
		SellOrderFilter sellOrderFilter = new SellOrderFilter();
		sellOrderFilter.setShipYear(year);
		sellOrderFilter.setShipMonth(month);

		SellOrderDetail result = SellOrderAmountMgr.getSellOrder(sellOrderFilter);

		sellOrderInfo = result.getDetails();
		sell = result.getSummary();
		response(sell);
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

	public List<SellOrderInfo> getSellOrderInfo() {
		return sellOrderInfo;
	}

	public void setSellOrderInfo(List<SellOrderInfo> sellOrderInfo) {
		this.sellOrderInfo = sellOrderInfo;
	}

}
