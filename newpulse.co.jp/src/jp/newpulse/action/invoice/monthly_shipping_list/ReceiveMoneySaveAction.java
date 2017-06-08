package jp.newpulse.action.invoice.monthly_shipping_list;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.Money;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.ReceivableAmount;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class ReceiveMoneySaveAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(ReceiveMoneySaveAction.class);
	private String str;
	List<ReceivableAmount> receivableAmounts;

	public String execute() {
		logger.debug("-------------------------仕入先別納品済（買掛金）一覧SAVE   START----------------------------");
		logger.debug("------------str------------" + str);
		receivableAmounts = new ArrayList<ReceivableAmount>();

		String[] strSub = str.split("FIN");
		for (int i = 0; i < strSub.length; i++) {
			logger.debug("=========i=========" + i);
			ReceivableAmount receivableAmount = new ReceivableAmount();
			// logger.debug("=========strSub.length=========");
			// logger.debug(strSub.length);
			// logger.debug("=========strSub.length=========");
			String[] strSub1 = strSub[i].split("@");
			// logger.debug("===============0=============="+strSub1[0]);
			// logger.debug("===============1=============="+strSub1[1]);
			// logger.debug("===============2=============="+strSub1[2]);
			// logger.debug("===============3=============="+strSub1[3]);
			// logger.debug("===============4=============="+strSub1[4]);
			// logger.debug("===============5=============="+strSub1[5]);
			// logger.debug("===============6=============="+strSub1[6]);
			// logger.debug("===============7=============="+strSub1[7]);
			// logger.debug("===============8=============="+strSub1[8]);
			// logger.debug("===============9=============="+strSub1[9]);
			// logger.debug("===============10=============="+strSub1[10]);
			// logger.debug("=========strSub1.length=========");
			// logger.debug(strSub1.length);
			logger.debug("===============Strate==============");
			receivableAmount.setXinanOrderNo(strSub1[0]);
			logger.debug("===============0==============");
			logger.debug("===============setXinanOrderNo==============" + strSub1[0]);
			// receivableAmount.setEarningAmount(new Money(strSub1[10],
			// strSub1[1]));
			// logger.debug("===============1==============");
			// logger.debug("===============setEarningAmount=============="+strSub1[1]);
			// receivableAmount.setReceivedAmount(new
			// Money(strSub1[10],strSub1[2]));
			// logger.debug("===============2==============");
			// logger.debug("===============setReceivedAmount=============="+strSub1[2]);
			receivableAmount.setReceiveAmount(new Money(strSub1[10], strSub1[3]));
			logger.debug("===============3==============");
			logger.debug("===============setReceiveAmount==============" + strSub1[3]);

			// receivableAmount.setEndingBalance(new Money(strSub1[10],
			// strSub1[4]));
			// logger.debug("===============4==============");
			// logger.debug("===============setEndingBalance=============="+strSub1[4]);

			receivableAmount.setTradingNo(strSub1[5]);
			logger.debug("===============5==============");
			logger.debug("===============setTradingNo==============" + strSub1[5]);
			receivableAmount.setNote(strSub1[6]);
			logger.debug("===============6==============");
			logger.debug("===============setNote==============" + strSub1[6]);
			logger.debug("===============setDetailId==============" + strSub1[8]);
			if (!strSub1[8].equals("") && !strSub1[8].equals(null)) {
				receivableAmount.setDetailId(Long.parseLong(strSub1[8]));
				logger.debug("===============8==============");
				logger.debug("===============setDetailId==============" + strSub1[8]);
			}
			receivableAmount.setCurrency(strSub1[10]);
			logger.debug("===============10==============");
			logger.debug("===============setCurrency==============" + strSub1[10]);
			receivableAmounts.add(receivableAmount);
		}
		try {
			PayReceiveAmountMgr.registerReceivableAmounts(receivableAmounts);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------仕入先別納品済（買掛金）一覧SAVE   FINSH----------------------------");
		response(true);
		return "success";
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
