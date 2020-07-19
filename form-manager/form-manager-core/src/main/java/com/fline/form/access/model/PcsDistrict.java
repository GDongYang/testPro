package com.fline.form.access.model;

import com.feixian.tp.model.LifecycleModel;

public class PcsDistrict extends LifecycleModel {

	private static final long serialVersionUID = 1L;

	/**
	 * dm
	 */
	private String dm;

	/**
	 * mc
	 */
	private String mc;

	/**
	 * qhdm
	 */
	private String qhdm;

	public String getDm() {
		return dm;
	}

	public String getMc() {
		return mc;
	}

	public String getQhdm() {
		return qhdm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public void setQhdm(String qhdm) {
		this.qhdm = qhdm;
	}


}
