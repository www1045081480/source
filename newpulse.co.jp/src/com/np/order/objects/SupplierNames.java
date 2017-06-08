package com.np.order.objects;

import java.util.ArrayList;
import java.util.List;

public class SupplierNames {
	private List<String> jpNames;
	private List<String> cnNames;
	private List<String> enNames;
	private List<String> enShortNames;

	public SupplierNames() {
		jpNames = new ArrayList<String>();
		cnNames = new ArrayList<String>();
		enNames = new ArrayList<String>();
		enShortNames = new ArrayList<String>();
	}

	public List<String> getJpNames() {
		return jpNames;
	}

	public void setJpName(String jpName) {
		this.jpNames.add(jpName);
	}

	public List<String> getCnNames() {
		return cnNames;
	}

	public void setCnName(String cnName) {
		this.cnNames.add(cnName);
	}

	public List<String> getEnNames() {
		return enNames;
	}

	public void setEnName(String enName) {
		this.enNames.add(enName);
	}

	public List<String> getEnShortName() {
		return enShortNames;
	}

	public void setEnShortName(String enShortName) {
		this.enShortNames.add(enShortName);
	}

}
