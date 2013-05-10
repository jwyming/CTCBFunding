package com.eds.ctcb.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.constant.Global;
import com.eds.ctcb.constant.LogType;
import com.eds.ctcb.constant.RoleType;
import com.eds.ctcb.constant.UserStatus;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.User;
import com.eds.ctcb.form.system.PwdForm;
import com.eds.ctcb.form.system.SimpleUserForm;
import com.eds.ctcb.form.system.UserForm;
import com.eds.ctcb.form.system.UserFormEx;
import com.eds.ctcb.form.system.UserQryForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class UserAction extends BaseAction {
	private static LogEx log = new LogEx(UserAction.class);
	public ActionForward preAddUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pwd=this.sysManagementBiz.getInitPwd();
		List<Role> roleList=this.userBiz.getRoleList();
		HashMap roleMap=new LinkedHashMap();
		Iterator it=roleList.iterator();
		while(it.hasNext()){
			Role role=(Role)it.next();
			roleMap.put(role.getId(),role.getName());
		}
		
		request.getSession().setAttribute(SessionKey.MAP_ROLE, roleMap);
		List<Currency> currencyList=this.userBiz.getNtdCurrency();
		HashMap currencyMap=new LinkedHashMap();
		it=currencyList.iterator();
		while(it.hasNext()){
			Currency currency=(Currency)it.next();
			currencyMap.put(currency.getType(), currency.getName());
		}
		request.getSession().setAttribute(SessionKey.MAP_CURRENCY, currencyMap);
				
		UserForm userForm=new UserForm();
		userForm.setPassword(pwd);
		request.getSession().setAttribute(SessionKey.FORM_USER, userForm);
		return mapping.findForward("success");
	}
	
	
	public ActionForward addUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		UserForm userForm=(UserForm)form;
		User user = this.userBiz.getUserByUserNameForAll(userForm.getUserName());
		if(user!= null){
			ActionMsgsUtil.saveErrorMessage(request, "user.error.duplicateUserName",
					null, "");
		
			return mapping.getInputForward();
			
		}
		
		
//		get Login info for logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+
			" ADD user "+ userForm.getUserName()+" at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true) ;
		
		
		String role=userForm.getRole();
		Long roleId=Long.parseLong(role);
		HashMap roleMap=( LinkedHashMap)request.getSession().getAttribute(SessionKey.MAP_ROLE);
		String roleName=(String)roleMap.get(new Long(role));
	
		//userForm.setRole(roleName);
		
		try{
			this.userBiz.createUserSeparetely(userForm,id,logContent);
//			for Logging 
//			this.sysManagementBiz.createSysParamLog(LogType.USER_OPERATION,id,logContent);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "user.createUserSucess",
					null, jumpUrl);
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "user.createUserFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
	}

	public ActionForward preManageUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List<Role> roleList=this.userBiz.getRoleList();
		HashMap roleMap=new LinkedHashMap();
		Iterator it=roleList.iterator();
		while(it.hasNext()){
			Role role=(Role)it.next();
			roleMap.put(role.getId(),role.getName());
		}
		
		request.getSession().setAttribute(SessionKey.MAP_ROLE1, roleMap);
		
		
		UserQryForm userQryForm=(UserQryForm)form;
		//request.getSession().setAttribute(SessionKey.FORM_USER_QRY, userQryForm);
		
		PageBean userExPageBean=this.userBiz.qryUserWithRoleNameInPage(userQryForm, Global.DEFAULT_PAGE_SIZE, 1);
		request.setAttribute("userExPageBean", userExPageBean);
		
		return mapping.findForward("success");
	}
	
	
	public ActionForward pagingUserList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		int page = 1;
		if (request.getParameter("page") != null
				&& !"".equals(request.getParameter("page"))) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		UserQryForm userQryForm = (UserQryForm) (request.getSession()
				.getAttribute(SessionKey.FORM_USER_QRY));
		PageBean userExPageBean = this.userBiz.qryUserWithRoleNameInPage(userQryForm, Global.DEFAULT_PAGE_SIZE, page);
		request.setAttribute("userExPageBean", userExPageBean);
		return mapping.findForward("success");
	}
	
	public ActionForward resetUserPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");
		//after reset the password the user status should be changed to initial
		this.userBiz.resetUserPassword(Long.parseLong(userId));
		return mapping.findForward("success");
	}
	
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String userId = request.getParameter("id");
		
//		get Login info for logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		//login user cannot delete himself
		if(Long.parseLong(userId)==id){
			
			String jumpUrl = request.getContextPath()+ mapping.findForward("success").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "user.error.deleteself",
					null, jumpUrl);
			
			return mapping.findForward("showMsg");
			
		}
		
		String logContent =loginfo.getUserName()+
			" delete user "+ this.userBiz.getUserById(Long.parseLong(userId)).getUserName()+" at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true) ;
		
		this.userBiz.deleteUser(Long.parseLong(userId),id,logContent);
		return mapping.findForward("success");
	}
	
	public ActionForward preAdmModifyUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long id=Long.parseLong(request.getParameter("id"));
		
		User user=this.userBiz.getUserById(id);
		
		String userName=user.getUserName();
		String realName=user.getName();
		Integer sex=user.getSex();
		Long roleId=this.userBiz.getRoleByUserId(id).getId();
		String company=user.getCompany();
		String address=user.getAddress();
		
		String phone=user.getPhone();
		String email=user.getEmail();
		
				
		UserFormEx userFormEx=new UserFormEx(userName,realName,sex,roleId,company,address,
				phone,email);
		userFormEx.setId(id);
		request.getSession().setAttribute("ROLEID", roleId);
		request.getSession().setAttribute(SessionKey.FORM_USER_EX, userFormEx);
		return mapping.findForward("success");
	}
	
	
	public ActionForward admModifyUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserFormEx userFormEx=(UserFormEx)form;
		
		
		try{
			
			Long roleId=(Long)request.getSession().getAttribute("ROLEID");
			
			User user=this.userBiz.getUserById(userFormEx.getId());
			user.setName(userFormEx.getRealName());
			user.setSex(userFormEx.getSex());
			user.setCompany(userFormEx.getCompany());
			user.setAddress(userFormEx.getAddress());
			user.setPhone(userFormEx.getPhone());
			user.setEmail(userFormEx.getEmail());
			
			//Role role=this.userBiz.getRoleById(userFormEx.getRole());
			Role role=this.userBiz.getRoleById(roleId);
			this.userBiz.updateUserInfo(user);
		
			this.userBiz.updateUserRole(user,role);
			
			
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "user.modifyUserInfoSucess",
					null, jumpUrl);
		
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "user.modifyUserInfoFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
		
	}
	

	public ActionForward preModifyUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long userId = loginfo.getUserId();
		
		User user=this.userBiz.getUserById(userId);
		
		String userName=loginfo.getUserName();
		String realName=user.getName();
		if(realName==null){
			realName="";
		}
		Integer sex=user.getSex();
		if(sex==null){
			sex=new Integer(0);
		}
		String company=user.getCompany();
		if(company==null){
			company="";
		}
		String address=user.getAddress();
		if(address==null){
			address="";
		}
		String phone=user.getPhone();
		if(phone==null){
			phone="";
		}
		String email=user.getEmail();
		if(email==null){
			email="";
		}
		
		SimpleUserForm simpleUserForm=new SimpleUserForm(realName,sex.toString(),company,address,phone,email);
		simpleUserForm.setId(userId);
		request.getSession().setAttribute(SessionKey.FORM_SIMPLE_USER,simpleUserForm);
		return mapping.findForward("success");
	}
	
	public ActionForward modifyUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SimpleUserForm simpleUserForm=(SimpleUserForm)form;
		try{
			
			User user=this.userBiz.getUserById(simpleUserForm.getId());
			user.setName(simpleUserForm.getRealName());
			user.setSex(Integer.parseInt(simpleUserForm.getSex()));
			user.setCompany(simpleUserForm.getCompany());
			user.setAddress(simpleUserForm.getAddress());
			user.setPhone(simpleUserForm.getPhone());
			user.setEmail(simpleUserForm.getEmail());
			
			this.userBiz.updateUserInfo(user);
			
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "user.modifyUserInfoSucess",
					null, jumpUrl);
		
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "user.modifyUserInfoFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
		
	}
	
	public ActionForward preModifyUserPwd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		return mapping.findForward("success");
	}
	
	public ActionForward modifyUserPwd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PwdForm pwdForm=(PwdForm)form;
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long userId = loginfo.getUserId();
		User user=this.userBiz.getUserById(userId);
		String password1 = user.getPassword();
		String password2 = DataUtil.getEncodedStr(pwdForm.getOldPwd()+user.getAddition());
		if(!DataUtil.isStrEqual(password1, password2)){
			ActionMsgsUtil.saveErrorMessage(request, "user.error.oldpwd",
					null, "");
			return mapping.getInputForward();
		}
		
		if(DataUtil.isStrEqual
				(password1, DataUtil.getEncodedStr(pwdForm.getNewPwd()+user.getAddition()))){
			ActionMsgsUtil.saveErrorMessage(request, "user.error.samepwd",
					null, "");
			return mapping.getInputForward();
			
		}
		
		
		//after change the password the status of the user should be changed to normal
		user.setPassword(DataUtil.getEncodedStr(pwdForm.getNewPwd()+user.getAddition()));
		user.setStatus(UserStatus.NORMAL);
		
		try{
			this.userBiz.updateUserInfo(user);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "user.modifyUserPwdSucess",
					null, jumpUrl);
			LoginBean loginBean =(LoginBean) (request.getSession().getAttribute(SessionKey.GLOBAL_LOGIN_INFO));
			loginBean.setUserStatus(UserStatus.NORMAL);
			request.getSession().setAttribute(SessionKey.GLOBAL_LOGIN_INFO,loginBean);
		
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "user.modifyUserPwdFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
		
	}
}
