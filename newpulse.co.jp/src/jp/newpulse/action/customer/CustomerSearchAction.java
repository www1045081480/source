package jp.newpulse.action.customer;

import java.util.List;

import com.np.base.utils.UJson;
import com.np.order.biz.mast.CustomerMgr;
import com.np.order.models.Customer;

import jp.newpulse.action.BaseJsonAction;

@SuppressWarnings("serial")
public class CustomerSearchAction extends BaseJsonAction {
	private List<Customer> customers;
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
		cus.setEnName(enname);
		cus.setEnShortName(enshortname);
		cus.setCountry(country);
		cus.setJpName(jpname);
		cus.setCnName(cnname);
		cus.setPostCd(postcd);
		cus.setAddress1(enaddress1);
		cus.setAddress2(address2);
		cus.setAddress3(address3);
		cus.setEnAddress1(enaddress1);
		cus.setEnAddress2(enaddress2);
		cus.setEnAddress3(enaddress3);
		cus.setTel1(tel1);
		cus.setTel2(tel2);
		cus.setFax(fax);
		customers = CustomerMgr.searchCustomers(cus);
		System.out.println(UJson.toJson(customers.get(0)));
		response(customers);
		return "success";
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
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
