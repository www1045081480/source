package com.np.order.biz.mast;

import java.util.List;

import com.np.base.db.Query;
import com.np.base.db.SequenceMgr;
import com.np.base.db.TxMgr;
import com.np.base.orm.Criteria;
import com.np.base.orm.Restrictions;
import com.np.base.utils.UList;
import com.np.base.utils.UString;
import com.np.order.models.Customer;
import com.np.order.objects.CustomerNames;

public class CustomerMgr {

	public static void createSamples() throws Exception {
		for (int i = 0; i < 3; i++) {
			Customer customer = new Customer();
			customer.setEnName("En Name-" + (i + 1));
			customer.setCnName("Chineses Name-" + (i + 1));
			customer.setJpName("Japanese Name-" + (i + 1));
			customer.setEnShortName("EnShortName-" + (i + 1));

			customer.setPostCd("xxx-xxx" + (i + 1));
			customer.setAddress1("address1-" + (i + 1));
			customer.setAddress2("address2-" + (i + 1));
			customer.setAddress3("address3-" + (i + 1));

			CustomerMgr.registerCustomer(customer);
		}
	}

	public static List<Customer> searchCustomers(Customer customer) throws Exception {
		return Query.query(customer);
	}

	public static CustomerNames getCustomersNames() throws Exception {
		List<Customer> list = Query.query(new Customer());

		CustomerNames names = new CustomerNames();
		for (Customer customer : list) {
			names.setCnName(customer.getCnName());
			names.setJpName(customer.getJpName());
			names.setEnName(customer.getEnName());
			names.setEnShortName(customer.getEnShortName());
		}
		return names;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Customer searchCustomer(Long customerId) {

		Criteria<Customer> criteria = new Criteria(Customer.class);
		criteria.and(Restrictions.eq(Customer.CustomerId, customerId));

		return criteria.get();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Customer> searchCustomerAddresses(Customer customer) {

		Criteria<Customer> criteria = new Criteria(Customer.class);

		criteria.and(Restrictions.eq(Customer.EnName, customer.getEnName()));
		criteria.and(Restrictions.eq(Customer.CnName, customer.getCnName()));
		criteria.and(Restrictions.eq(Customer.JpName, customer.getJpName()));
		criteria.and(Restrictions.eq(Customer.EnShortName, customer.getEnShortName()));

		criteria.addSelectedColumn(Customer.CustomerId);
		criteria.addSelectedColumn(Customer.PostCd);
		criteria.addSelectedColumn(Customer.Address1);
		criteria.addSelectedColumn(Customer.Address2);
		criteria.addSelectedColumn(Customer.Address3);
		criteria.addSelectedColumn(Customer.CnName);
		criteria.addSelectedColumn(Customer.JpName);
		criteria.addSelectedColumn(Customer.EnName);

		List<Customer> result = criteria.list();
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Customer> getCustomers() {
		Criteria<Customer> criteria = new Criteria(Customer.class);
		List<Customer> result = criteria.list();
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Customer searchCustomerAddresses(Long customerId) {

		Criteria<Customer> criteria = new Criteria(Customer.class);
		criteria.and(Restrictions.eq(Customer.CustomerId, customerId));

		criteria.addSelectedColumn(Customer.CustomerId);
		criteria.addSelectedColumn(Customer.PostCd);
		criteria.addSelectedColumn(Customer.Address1);
		criteria.addSelectedColumn(Customer.Address2);
		criteria.addSelectedColumn(Customer.Address3);

		List<Customer> result = criteria.list();
		return (UList.isEmpty(result)) ? null : UList.first(result);
	}

	public static boolean registerCustomer(Customer customer) throws Exception {
		/*
		 * TODO:存在チェック
		 */

		if (UString.notEmpty(customer.getJpName()) || UString.notEmpty(customer.getCnName())) {
			if (UString.isEmpty(customer.getJpName()))
				customer.setJpName(customer.getCnName());
			if (UString.isEmpty(customer.getCnName()))
				customer.setCnName(customer.getJpName());

			if (UString.isEmpty(customer.getEnName())) {
				customer.setEnName(customer.getJpName());
			}
		}
		if (exists(customer))
			throw new IllegalStateException("Customer has exist");

		boolean hasCnAddress = UString.notEmpty(customer.getAddress1()) && UString.notEmpty(customer.getAddress2())
				&& UString.notEmpty(customer.getAddress3());
		boolean hasEnAddress = UString.notEmpty(customer.getEnAddress1()) && UString.notEmpty(customer.getEnAddress2())
				&& UString.notEmpty(customer.getEnAddress3());

		if (hasCnAddress == false || hasEnAddress == false) {
			if (hasCnAddress) {
				customer.setEnAddress1(customer.getAddress1());
				customer.setEnAddress2(customer.getAddress2());
				customer.setEnAddress3(customer.getAddress3());
			} else if (hasEnAddress) {
				customer.setAddress1(customer.getEnAddress1());
				customer.setAddress2(customer.getEnAddress2());
				customer.setAddress3(customer.getEnAddress3());
			}
		}

		/*
		 * 自動採番
		 */
		Long customerId = SequenceMgr.nextSeq(Customer.class);
		customer.setCustomerId(customerId);

		/*
		 * DBにInsert
		 */
		TxMgr.insert(customer);
		return true;
	}

	public static boolean modifyCustomer(Customer customer) throws Exception {
		if (exists(customer))
			throw new IllegalStateException("Customer has exist");

		TxMgr.update(customer);
		return true;
	}

	public static boolean removeCustomer(Long customerId) throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		TxMgr.delete(customer);
		return true;
	}

	public static String getCustomerShortName(Long customerId) {
		Customer result = searchCustomer(customerId);
		return (result == null) ? null : result.getEnShortName();
	}

	public static String getCustomerName(Long customerId) {
		Customer result = searchCustomer(customerId);
		return (result == null) ? null : result.getJpName();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Long getId(String name) throws Exception {
		if (UString.isEmpty(name))
			return null;
		Criteria<Customer> criteria = new Criteria(Customer.class);
		criteria.and(Restrictions.like(Customer.JpName, name));
		criteria.or(Restrictions.like(Customer.EnShortName, name));
		Customer result = criteria.get();
		return (result == null) ? null : result.getCustomerId();
	}

	public static boolean exists(Customer customer) throws Exception {
		long customerId = (customer.getCustomerId() == null) ? -1 : customer.getCustomerId().longValue();
		List<Customer> customers = getCustomers();
		for (Customer existCustomer : customers) {
			if (existCustomer.getCustomerId().longValue() == customerId)
				continue;

			if (UString.equals(existCustomer.getCnName(), customer.getCnName()))
				return true;
			if (UString.equals(existCustomer.getJpName(), customer.getJpName()))
				return true;
			if (UString.equals(existCustomer.getEnName(), customer.getEnName()))
				return true;
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Customer> searchCustomersByName(Customer customer) {

		Criteria<Customer> criteria = new Criteria(Customer.class);

		criteria.and(Restrictions.like(Customer.EnName, customer.getEnName()));
		criteria.and(Restrictions.like(Customer.CnName, customer.getCnName()));
		criteria.and(Restrictions.like(Customer.JpName, customer.getJpName()));
		criteria.and(Restrictions.like(Customer.EnShortName, customer.getEnShortName()));

		List<Customer> result = criteria.list();
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Customer> getCustomers(List<Long> customerIds) {
		Criteria<Customer> criteria = new Criteria(Customer.class);
		criteria.and(Restrictions.in(Customer.CustomerId, customerIds));
		return criteria.list();
	}

}
