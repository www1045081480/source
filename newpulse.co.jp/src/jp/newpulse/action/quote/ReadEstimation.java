package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.order.models.EstmationSheet;
import com.opensymphony.xwork2.ActionSupport;

public class ReadEstimation extends ActionSupport {
	private static Log logger = LogFactory.getLog(ReadEstimation.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String EstimationId;
	private List<EstmationSheet> result;
	private EstmationSheet estmationSheet;
	private String type;

	private String langFlg;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EstmationSheet getEstmationSheet() {
		return estmationSheet;
	}

	public void setEstmationSheet(EstmationSheet estmationSheet) {
		this.estmationSheet = estmationSheet;
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	public String getLangFlg() {
		return langFlg;
	}

	public void setLangFlg(String langFlg) {
		this.langFlg = langFlg;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() throws Exception {
		logger.debug("==============readEstimation Start================");
		logger.debug("==============EstimationId  " + EstimationId + "================");
		logger.debug("==============type= " + type + "===============");
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.eq(EstmationSheet.EstimationId, EstimationId));
		result = criteria.list();

		if (result.isEmpty() == false && "copy".equals(type)) {
			estmationSheet = result.get(0);
			estmationSheet.setIssueDate(UDate.getDate());
			estmationSheet.setEstimationOkDays(UDate.getDateByAdjustMonth(1));
		}

		if ("JP".equals(langFlg)) {
			return "successJP";
		}
		return "successCN";
	}
}
