package jp.newpulse.action.invoice.monthly_arrival_list;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.Money;
import com.np.order.biz.invoice.PayReceiveAmountMgr;
import com.np.order.objects.PayableAmount;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class PayMoneySaveAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(PayMoneySaveAction.class);
	private String str;
	List<PayableAmount> payableAmounts;

	public String execute() {
		logger.debug("-------------------------仕入先別納品済（買掛金）一覧SAVE   START----------------------------");
		logger.debug("------------str------------" + str);
		payableAmounts = new ArrayList<PayableAmount>();

		String[] strSub = str.split("FIN");
		for (int i = 0; i < strSub.length; i++) {
			logger.debug("=========i=========" + i);
			PayableAmount payableAmount = new PayableAmount();
			logger.debug("=========strSub.length=========");
			logger.debug(strSub.length);
			logger.debug("=========strSub.length=========");
			String[] strSub1 = strSub[i].split("@");
			logger.debug("=========strSub1.length=========");
			logger.debug(strSub1.length);
			logger.debug("===============Strate==============");
			if (strSub1[2] != "" || strSub1[2] != null) {
				payableAmount.setPayDate(strSub1[0]);
				logger.debug("===============0==============");
				logger.debug("===============setPayDate==============" + strSub1[0]);
				payableAmount.setModelCd(strSub1[1]);
				logger.debug("===============1==============");
				logger.debug("===============setModelCd==============" + strSub1[1]);
			}

			// payableAmount.setOrderNo(strSub1[2]);
			// logger.debug("===============2==============");
			// logger.debug("===============setOrderNo=============="+strSub1[2]);

			payableAmount.setPayAmount(new Money(strSub1[10], strSub1[3]));
			logger.debug("===============3==============");
			logger.debug("===============setPayAmount==============" + strSub1[3]);
			// payableAmount.setBalance(new Money(strSub1[10], strSub1[4]));
			// logger.debug("===============4==============");
			// logger.debug("===============setBalance=============="+strSub1[4]);

			payableAmount.setTradingNo(strSub1[5]);
			logger.debug("===============5==============");
			logger.debug("===============setTradingNo==============" + strSub1[5]);
			payableAmount.setNote(strSub1[6]);
			logger.debug("===============6==============");
			logger.debug("===============setNote==============" + strSub1[6]);
			// logger.debug("===============setOrderId=============="+strSub1[7]);
			// if(strSub1[7] != "" && strSub1[7] != null && strSub1[7] !=
			// "undefined"){
			// payableAmount.setOrderId(Long.parseLong(strSub1[7]));
			// logger.debug("===============7==============");
			// logger.debug("===============setOrderId=============="+strSub1[7]);
			// }
			logger.debug("===============setDetailId==============" + strSub1[8]);
			if (!strSub1[8].equals("") && !strSub1[8].equals(null)) {
				logger.debug("===============8==============");
				payableAmount.setDetailId(Long.parseLong(strSub1[8]));
				logger.debug("===============8==============");
				logger.debug("===============setDetailId==============" + strSub1[8]);
			}
			if (!strSub1[9].equals("") && !strSub1[9].equals(null)) {
				payableAmount.setSupplierId(Long.parseLong(strSub1[9]));
				logger.debug("===============9==============");
				logger.debug("===============setSupplierId==============" + strSub1[9]);
			}
			payableAmount.setCurrency(strSub1[10]);
			logger.debug("===============10==============");
			logger.debug("===============setCurrency==============" + strSub1[10]);
			payableAmounts.add(payableAmount);
		}
		try {
			PayReceiveAmountMgr.registerPayableAmounts(payableAmounts);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		logger.debug("-------------------------仕入先別納品済（買掛金）一覧SAVE   FINSH----------------------------");
		response(true);
		return "success";
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		PayMoneySaveAction.logger = logger;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
