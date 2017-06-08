package jp.newpulse.action.invoice.monthly_trading_list;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.base.utils.UJson;
import com.np.base.utils.UString;
import com.np.order.Money;
import com.np.order.biz.invoice.SellOrderAmountMgr;
import com.np.order.objects.SellOrderInfo;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class BeforeAfterSaveAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(BeforeAfterSaveAction.class);
	private String str;
	List<SellOrderInfo> sellOrderInfo;

	// private List<DeliveryInfo> NextdeliveryInfo;
	public String execute() {
		logger.debug("------------BeforeAfterSaveAction-----------");
		String[] strSub = str.split(";");
		sellOrderInfo = new ArrayList<SellOrderInfo>();
		try {
			for (int i = 0; i < strSub.length; i++) {
				// logger.debug("=====================strSub.length=====================");
				String[] strSub1 = strSub[i].split("@");
				// logger.debug(Arrays.asList(strSub1));
				SellOrderInfo s = new SellOrderInfo();
				// logger.info(strSub1[0] + "========amount");
				// logger.info(strSub1[13] + "========currency");

				// TODO
				if (UString.notEmpty(strSub1[0])) {
					Money adjustAmount = new Money(strSub1[13], strSub1[0]);
					s.setAdjustAmount(adjustAmount);
				}
				// TODO
				if (UString.notEmpty(strSub1[1])) {
					Money deliveryAmount = new Money(strSub1[13], strSub1[1]);
					s.setDeliveryAmount(deliveryAmount);
				}
				// TODO
				if (UString.notEmpty(strSub1[2]))
					s.setPayAmounts(strSub1[2]);
				// TODO
				if (UString.notEmpty(strSub1[3]))
					s.setPayDates(strSub1[3]);

				if (UString.notEmpty(strSub1[4]))
					s.setDeliveryDate(strSub1[4]);

				// TODO
				if (UString.notEmpty(strSub1[5]))
					s.setTradingNo(strSub1[5]);

				s.setOrderDetailId(Long.parseLong(strSub1[6]));
				s.setInvoiceDetailId(Long.parseLong(strSub1[7]));
				s.setOrderId(Long.parseLong(strSub1[9]));
				s.setInvoiceId(Long.parseLong(strSub1[10]));
				s.setGrossMargin(Long.parseLong(strSub1[14]));
				Money grossProfit = new Money(strSub1[13], strSub1[15]);
				s.setGrossProfit(grossProfit);
				s.setSellDate(strSub1[16]);
				sellOrderInfo.add(s);
			}
			logger.info(sellOrderInfo.get(0).getPayAmounts());
			logger.info(UJson.toJson(sellOrderInfo.get(0)));
			SellOrderAmountMgr.register(sellOrderInfo);
			response(true);
			return SUCCESS;
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
			response(false);
			return ERROR;
		}
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
