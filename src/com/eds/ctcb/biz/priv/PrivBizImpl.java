package com.eds.ctcb.biz.priv;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.bean.MenuBean;
import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.db.Resource;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;
import com.eds.ctcb.form.LoginForm;
import com.eds.ctcb.util.DataUtil;
import com.eds.ctcb.constant.LogType;
public class PrivBizImpl extends BaseBiz implements PrivBiz{

	/* (non-Javadoc)
	 * @see com.eds.ctcb.biz.priv.PrivBiz#login(javax.servlet.http.HttpServletRequest, com.eds.ctcb.form.LoginForm)
	 */
	public LoginBean login(HttpServletRequest request,LoginForm loginForm){	

		if(loginForm == null){
			//?????????
		}
		
		User user = this.userDao.getUserByUserName(loginForm.getUsername());
		if(user == null){
			throw (new BizException(BizExceptionCode.LOGIN_USER_NOT_EXIST,"login.error.userNotExist",null,null));
		}
		String password1 = DataUtil.getEncodedStr(loginForm.getPassword()+user.getAddition());
		
		String password2 = user.getPassword();
		if(!DataUtil.isStrEqual(password1, password2)){
			throw (new BizException(BizExceptionCode.LOGIN_PASSWORD_ERROR,"login.error.pwdError",null,null));
		}
		
		Role role = this.userRoleDao.getRoleByUserId(user.getId());
		Long roleId = role.getId();
		
		LoginBean loginBean = new LoginBean();
		loginBean.setUserId(user.getId().longValue());
		loginBean.setUserName(user.getUserName());
		loginBean.setUserStatus(user.getStatus().intValue());
		loginBean.setLoginTime(request.getSession().getCreationTime());
		loginBean.setRoleId(roleId.longValue());
		loginBean.setResourceList(this.roleResourceDao.getResourceListOfRole(roleId));
		loginBean.setLocationList(this.resourceLocationDao.getAccessableUrlList4Role(roleId));
		
		List<MenuBean> menuBeanList = new ArrayList<MenuBean>();
		List<Resource> menuList = this.roleResourceDao.getMenuListOfRole(roleId,new Long(0l));		
		HashMap<String,String> menuStructureMap =new HashMap<String,String>();
		String url = "";
		if(menuList!=null){
			for(int i=0;i<menuList.size();i++){
				Resource menu = menuList.get(i);
				MenuBean menuBean = new MenuBean(menu.getId().intValue(), menu.getName(), "#");
				List<Resource> subMenuList = this.roleResourceDao.getMenuListOfRole(roleId,menu.getId());
				if(subMenuList!=null && subMenuList.size()>0){
					for(int j=0;j<subMenuList.size();j++){
						Resource subMenu = subMenuList.get(j);
						url = "menu_"+subMenu.getId()+".do";
						menuBean.addSubMenu(subMenu.getId().intValue(), subMenu.getName(), url);
						menuStructureMap.put(url, menu.getId()+";"+subMenu.getId());
					}
				}else{
					url = "menu_"+menu.getId()+".do";
					menuBean.setHref(url);
					menuStructureMap.put(url, menu.getId()+";-1");
				}				
				menuBeanList.add(menuBean);
			}
		}
		

		loginBean.setMenuList(menuBeanList);
		loginBean.setMenuStructureMap(menuStructureMap);

				
		request.getSession().setAttribute(SessionKey.GLOBAL_LOGIN_INFO, loginBean);
		LoginBean.loginMap.put(loginBean.getUserName(), new Long(request.getSession().getCreationTime()));
		String log = loginBean.getUserName()+" login at "+DataUtil.date2Str(this.userDao.getSysdate(), true) ;
		this.createLog(LogType.LOGIN, loginBean.getUserId(), log);
		return loginBean;
	}
	
	public void logout(HttpServletRequest request){	
		try{
			request.getSession().invalidate();		
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	

	
	public boolean isAvaibleResource(HttpServletRequest request, String resourceId){
		if(DataUtil.isEmptyStr(resourceId)){
			return true;//???????
		}
		LoginBean loginBean = (LoginBean)(request.getSession().getAttribute(SessionKey.GLOBAL_LOGIN_INFO));
		if(loginBean == null || loginBean.getResourceList()==null){
			return false;
		}
		List<String> resourceList = loginBean.getResourceList();
		if(resourceList.contains(resourceId)){
			return true;
		}else{
			return false;
		}

	}
	
	



}
