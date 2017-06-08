package jp.newpulse.action.quote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UDate;
import com.np.order.models.EstmationSheet;

import jp.newpulse.action.BaseJsonAction;

public class GetEstmationSheet extends BaseJsonAction {
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(GetEstmationSheet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String EstimationId;
	private List<EstmationSheet> result;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEstimationId() {
		return EstimationId;
	}

	public void setEstimationId(String estimationId) {
		EstimationId = estimationId;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() throws Exception {
		Criteria<EstmationSheet> criteria = new Criteria(EstmationSheet.class);
		criteria.and(Restrictions.eq(EstmationSheet.EstimationId, EstimationId));
		result = criteria.list();

		if (result.isEmpty() == false && "copy".equals(type)) {
			result.get(0).setIssueDate(UDate.getDate());
			result.get(0).setEstimationOkDays(UDate.getDateByAdjustMonth(1));
		}
		response(result);

		return "success";
	}
}
