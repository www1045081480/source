package jp.newpulse.action.quote;

import java.util.List;

import com.np.order.biz.invoice.EstmationSheetMgr;
import com.np.order.objects.NoOrderEstimation;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NoOrderEstimationAction extends ActionSupport {
	private List<NoOrderEstimation> noOrderEstimation;
	private String Form;
	private String To;

	public String execute() throws Exception {
		// 未注文一览
		String FormDate = Form;
		String ToDate = To;
		// if(FormDate != "" && FormDate!= null){
		// FormDate = FormDate.replace("-", "");
		// }
		// if(ToDate != "" && ToDate!= null){
		// ToDate = ToDate.replace("-", "");
		// }
		noOrderEstimation = EstmationSheetMgr.fetchNotOrderList(FormDate, ToDate);
		System.out.println(noOrderEstimation.size());
		return "success";
	}

	public List<NoOrderEstimation> getNoOrderEstimation() {
		return noOrderEstimation;
	}

	public void setNoOrderEstimation(List<NoOrderEstimation> noOrderEstimation) {
		this.noOrderEstimation = noOrderEstimation;
	}

	public String getForm() {
		return Form;
	}

	public void setForm(String form) {
		Form = form;
	}

	public String getTo() {
		return To;
	}

	public void setTo(String to) {
		To = to;
	}

}
