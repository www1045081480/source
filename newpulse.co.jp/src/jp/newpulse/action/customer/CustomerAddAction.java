package jp.newpulse.action.customer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.Customer;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CustomerAddAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(CustomerAddAction.class);

	private String customerid;
	private String enname;
	private String enshortname;
	private String country;
	private String jpname;
	private String cnname;
	private String postcd;
	private String address1;
	private String address2;
	private String address3;
	private String enaddress1;
	private String enaddress2;
	private String enaddress3;
	private String tel1;
	private String tel2;
	private String fax;

	public String execute() throws Exception {
		Customer cus = new Customer();
		if (cnname.indexOf("(例)XXX机械制造厂") == 0) {
			cnname = null;
		}
		if (enname.indexOf("(例)XXX mechanism") == 0) {
			enname = null;
		}
		if (jpname.indexOf("(例)XXX機械製作所") == 0) {
			jpname = null;
		}
		if (address1.indexOf("(例)愛知県") == 0) {
			address1 = null;
		}
		if (enaddress1.indexOf("(例) Anqing City,Anhui Province") == 0) {
			enaddress1 = null;
		}
		if (address2.indexOf("(例)岡崎市美合町") == 0) {
			address2 = null;
		}
		if (enaddress2.indexOf("(例)Yingbin Road of Development Area") == 0) {
			enaddress2 = null;
		}
		if (address3.indexOf("(例)XXXX8番14号") == 0) {
			address3 = null;
		}
		if (enaddress3.indexOf("(例)No.317") == 0) {
			enaddress3 = null;
		}
		cus.setEnName(enname);
		cus.setEnShortName(enshortname);
		cus.setCountry(country);
		cus.setJpName(jpname);
		cus.setCnName(cnname);
		cus.setPostCd(postcd);
		cus.setAddress1(address1);
		cus.setAddress2(address2);
		cus.setAddress3(address3);
		cus.setEnAddress1(enaddress1);
		cus.setEnAddress2(enaddress2);
		cus.setEnAddress3(enaddress3);
		cus.setTel1(tel1);
		cus.setTel2(tel2);
		cus.setFax(fax);

		if ("".equals(customerid) || customerid == null || "undefined".equals(customerid)) {
			logger.debug("=======register=====");
			logger.debug(cus);
			CustomerMgr.registerCustomer(cus);
		} else {
			cus.setCustomerId(Long.parseLong(customerid));
			logger.debug("=======modify=====" + customerid);
			CustomerMgr.modifyCustomer(cus);
		}
		return "success";
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getEnshortname() {
		return enshortname;
	}

	public void setEnshortname(String enshortname) {
		this.enshortname = enshortname;
	}

	public String getJpname() {
		return jpname;
	}

	public void setJpname(String jpname) {
		this.jpname = jpname;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getPostcd() {
		return postcd;
	}

	public void setPostcd(String postcd) {
		this.postcd = postcd;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getEnaddress1() {
		return enaddress1;
	}

	public void setEnaddress1(String enaddress1) {
		this.enaddress1 = enaddress1;
	}

	public String getEnaddress2() {
		return enaddress2;
	}

	public void setEnaddress2(String enaddress2) {
		this.enaddress2 = enaddress2;
	}

	public String getEnaddress3() {
		return enaddress3;
	}

	public void setEnaddress3(String enaddress3) {
		this.enaddress3 = enaddress3;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
