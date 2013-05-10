package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.form.BaseForm;
import com.eds.ctcb.util.DataUtil;

public class PwdForm extends BaseForm {
	private String oldPwd;
	private String newPwd;
	private String cfmPwd;
	
	

	public PwdForm() {
		
	}



	public PwdForm(String oldPwd, String newPwd, String cfmPwd) {
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
		this.cfmPwd = cfmPwd;
	}



	public String getCfmPwd() {
		return cfmPwd;
	}


	public void setCfmPwd(String cfmPwd) {
		this.cfmPwd = cfmPwd;
	}



	public String getNewPwd() {
		return newPwd;
	}



	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}


	public String getOldPwd() {
		return oldPwd;
	}



	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}



	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors actionErrors = new ActionErrors();
		if(!DataUtil.isStrEqual(newPwd, cfmPwd) ){
			actionErrors.add("pwd",new ActionMessage("user.error.pwdunmatched"));
		}
		return actionErrors;
	}

}
