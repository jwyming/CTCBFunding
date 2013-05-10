
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>    
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<fmt:bundle basename="ApplicationResources">
<!--br />
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="user.info.modify" /></span></td>
  </tr>
</table>
<br /-->
<form id="userQryForm" name="userQryForm" method="post" action="preManageUserInfo.do">
<table width="60%%" border="0">
  <tr>
    <td width="22%"><fmt:message key="user.userName" />
        <label>
        <input name="userName" type="text" value="<c:out value="${sessionScope.UserQryForm.userName}"/>" size="15" />
        </label>    </td>
    <td width="20%"><fmt:message key="user.realName"/>
      <label>
      <input name="realName" type="text" value="<c:out value="${sessionScope.UserQryForm.realName}"/>" size="10" />
      </label></td>
    <td width="17%"><label><fmt:message key="user.list.sex"/>
     <ctcb:optionsSelect name="sex" mapSource="static" mapName="UserSex" selectedKey="${sessionScope.UserQryForm.sex}" hasEmptyOption="true"/>
    </label></td>
    <td width="22%"><label><fmt:message key="user.role"/>
        <ctcb:optionsSelect name="role" mapSource="session" mapName="MAP_ROLE1" selectedKey="${sessionScope.UserQryForm.role}" hasEmptyOption="true"/>
	</label></td>
    <td width="19%">
    	<label>
    	<IMG SRC="images/choose.png" width="70" height="19" border="0" 
    		onClick="document.getElementById('userQryForm').submit();" style=cursor:hand>
    	</label>
    </td>
  </tr>
</table>
</form>

<!-- html:javascript formName="userQryForm"/ -->
<br>
<strong><fmt:message key="user.portfolio"/></strong>
<display:table name="userExPageBean" uid="userEx" requestURI="pagingUserList.do" >
<display:column titleKey="user.list.username" property="userName"></display:column>
<display:column titleKey="user.list.realname" property="realName"></display:column>
<display:column titleKey="user.list.sex" >
<ctcb:optionsLabel  mapName="UserSex" mapSource="static" selectedKey="${userEx.sex}"/>
</display:column>
<display:column titleKey="user.list.role" property="role"></display:column>
<display:column titleKey="user.list.phone" property="phone"></display:column>
<display:column titleKey="user.list.email" property="email" autolink="true"></display:column>
<display:column titleKey="user.list.operation">

<a href="preAdmModifyUserInfo.do?id=<bean:write name="userEx" property="userExId"/>">
<fmt:message key="user.modify.info"/></a>
|
<a href="resetUserPwd.do?id=<bean:write name="userEx" property="userExId"/>"
onclick="{if(confirm('<fmt:message key="user.cfm.resetpwd"/>')){return true;}return false;}">
<fmt:message key="user.pwd.reset"/></a>

|

<a href="deleteUser.do?id=<bean:write name="userEx" property="userExId"/>"
onclick="{if(confirm('<fmt:message key="user.cfm.delete"/>')){return true;}return false;}">

<fmt:message key="user.delete"/></a>

</display:column>
</display:table>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

</fmt:bundle>
