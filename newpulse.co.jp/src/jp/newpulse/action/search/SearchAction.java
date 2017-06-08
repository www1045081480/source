package jp.newpulse.action.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.order.models.EstmationSheet;
import com.np.order.models.NapaStores;
import com.opensymphony.xwork2.ActionContext;

import jp.newpulse.action.BaseAction;

@SuppressWarnings("serial")
public class SearchAction extends BaseAction {
	private static Log logger = LogFactory.getLog(SearchAction.class);
	private String NapaId;
	private String Cname;
	private NapaStores napastores;
	private Map<String, Object> session;
	private List<NapaStores> list = new ArrayList<NapaStores>();
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() throws Exception {
		
		logger.debug("############login#########");
		
		if(NapaId.equals("") && Cname.equals("")){
			Criteria<NapaStores> criteria = new Criteria(NapaStores.class);
			list = criteria.list();
		}else if(NapaId.equals("") && !Cname.equals("")){
			Criteria<NapaStores> criteria = new Criteria(NapaStores.class);
			criteria.and(Restrictions.like(NapaStores.CNAME, Cname));
			list = criteria.list();
		}else if(!NapaId.equals("") && Cname.equals("")){
			Criteria<NapaStores> criteria = new Criteria(NapaStores.class);
			criteria.and(Restrictions.eq(NapaStores.NAPAId, NapaId));
			list = criteria.list();
		}else{
			Criteria<NapaStores> criteria = new Criteria(NapaStores.class);
			criteria.and(Restrictions.eq(NapaStores.NAPAId, NapaId));
			criteria.and(Restrictions.like(NapaStores.CNAME, Cname));
			list = criteria.list();
		}
		ActionContext.getContext().put("list", list);
		
		System.out.println(NapaId);
		System.out.println(Cname) ;
		//登陆画面需要重新做
		return "success";
//		if (accept() == false) {
//			// logger.debug("user-agent = " +
//			// ServletActionContext.getRequest().getHeader("user-agent"));
//			throw new RuntimeException("Please use Firefox !");
//		}
//
//		Criteria<Users> criteria = new Criteria(Users.class);
//		criteria.and(Restrictions.eq(Users.Account, userId));
//		criteria.and(Restrictions.eq(Users.Password, MD5.digest(passWord)));
//		user = criteria.get();
//		if (user != null) {
//			logger.debug("############login success#########");
//			HttpServletRequest req = ServletActionContext.getRequest();
//			SessionMgr.login(req, user);
//			this.session.put("User", user);
//			return "success";
//		}
//
//		logger.debug("############login failed#########");
//		return "fail";
	}

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
		SearchAction.logger = logger;
	}

	public String getNapaId() {
		return NapaId;
	}

	public void setNapaId(String napaId) {
		NapaId = napaId;
	}

	public String getCname() {
		return Cname;
	}

	public void setCname(String cname) {
		Cname = cname;
	}

	public NapaStores getNapastores() {
		return napastores;
	}

	public void setNapastores(NapaStores napastores) {
		this.napastores = napastores;
	}

}
