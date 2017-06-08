package jp.newpulse.action.quote;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.db.Query;
import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.models.EstiomationDetail;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class LoadOrderDetail extends BaseJsonAction {
	private static Log logger = LogFactory.getLog(LoadOrderDetail.class);
	private List<EstiomationDetail> list;
	private HttpServletRequest request;
	private String reqStr;
	private String EstimationId;
	private String lanCd = "";

	public String getLanCd() {
		return lanCd;
	}

	public void setLanCd(String lanCd) {
		this.lanCd = lanCd;
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String execute() throws NumberFormatException, Exception {
		logger.debug("|||||||||||||||||||||||");
		logger.debug(EstimationId);
		logger.debug(reqStr);
		logger.debug("begin================");
		List<EstiomationDetail> list = new ArrayList<EstiomationDetail>();
		if ((reqStr == null || "".equals(reqStr)) && (EstimationId != null && EstimationId != "")) {
			logger.debug("2");
			EstiomationDetail es = new EstiomationDetail();
			logger.debug("3");
			es.setEstimationId(Long.parseLong(EstimationId));
			logger.debug("4");
			list = Query.query(es);
			response(list);
		}
		if ((reqStr != null && !"".equals(reqStr)) && (EstimationId == null || "".equals(EstimationId))) {
			String[] str1 = reqStr.split(";");
			logger.debug(str1.length);
			for (int i = 0; i < str1.length; i++) {
				String[] str2 = str1[i].split(",");
				EstiomationDetail ed;
				ed = EstmationSheetMgr.getDetail(Long.parseLong(str2[0]), Long.parseLong(str2[1]), lanCd);
				list.add(ed);
				logger.debug(ed);

			}
			response(list);
		}
		return "success";

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<EstiomationDetail> getList() {
		return list;
	}

	public void setList(List<EstiomationDetail> list) {
		this.list = list;
	}

}
