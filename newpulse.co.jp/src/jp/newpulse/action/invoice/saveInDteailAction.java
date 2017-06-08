package jp.newpulse.action.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.utils.UDebugger;
import com.np.order.Money;
import com.np.order.biz.invoice.InvoiceSheetMgr;
import com.np.order.models.InvoiceDetail;
import com.np.order.models.PackingListDetail;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class saveInDteailAction extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(saveInDteailAction.class);

	private String InvoiceID;
	private String PackingListId;
	private String str;
	List<InvoiceDetail> invoiceDetail;
	List<PackingListDetail> packingListDetail;

	public String execute() {
		try {
			String currency = null;
			invoiceDetail = new ArrayList<InvoiceDetail>();
			packingListDetail = new ArrayList<PackingListDetail>();
			String[] strSub = str.split("SPLIT");
			for (int i = 0; i < strSub.length; i++) {
				logger.debug("=========strSub.length");
				logger.debug(strSub.length);
				String[] strSub1 = strSub[i].split("FLG");
				logger.debug("=========strSub1.length");
				logger.debug(strSub1.length);
				InvoiceDetail inde = new InvoiceDetail();
				inde.setInvoiceId(Long.parseLong(InvoiceID));
				inde.setName(strSub1[0]);
				inde.setModelCd(strSub1[11]);
				inde.setQuantity(Integer.parseInt(strSub1[1]));

				if (currency == null)
					currency = InvoiceSheetMgr.getCurrency(inde.getInvoiceId());
				inde.setUnitPrice(new Money(currency, strSub1[2]));
				inde.setAmount(new Money(currency, strSub1[3]));
				inde.setItemId(Long.parseLong(strSub1[4]));
				logger.debug(inde.getInvoiceId());
				invoiceDetail.add(inde);

				PackingListDetail pade = new PackingListDetail();
				pade.setPackingListId(Long.parseLong(PackingListId));
				pade.setName(strSub1[5]);
				pade.setModelCd(strSub1[12]);
				pade.setQuantity(Integer.parseInt(strSub1[6]));
				pade.setNw(strSub1[7]);
				pade.setGw(strSub1[8]);
				pade.setM3(strSub1[9]);
				pade.setItemId(Long.parseLong(strSub1[10]));
				packingListDetail.add(pade);
			}

			InvoiceSheetMgr.registerInvoiceDetails(invoiceDetail);
			InvoiceSheetMgr.registerPackingListDetails(packingListDetail);
			response(true);
		} catch (Exception e) {
			logger.trace(UDebugger.trace(e));
		}
		return "success";
	}

	public String getInvoiceID() {
		return InvoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		InvoiceID = invoiceID;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getPackingListId() {
		return PackingListId;
	}

	public void setPackingListId(String packingListId) {
		PackingListId = packingListId;
	}

}
