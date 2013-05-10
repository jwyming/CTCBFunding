
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>

<fmt:bundle basename="ApplicationResources">
	<!--br />
	<table width="100%" border="0">
		<tr>
			<td><span class="STYLE1"><strong><fmt:message
				key="user.add" /></strong></span></td>
		</tr>
	</table>
	<br /-->

	<form id="userForm" name="userForm" method="post" action="addUser.do">
	<table width="60%%" border="0">
		<tr>
			<td><fmt:message key="user.userName" /><ctcb:isRequried /></td>
			<td><input name="userName" type="text"
				value="<c:out value="${sessionScope.UserForm.userName}"/>" size="20" />
			<span class="STYLE2"><fmt:message key="user.name.length" /></span></td>
		</tr>
		<tr>
			<td><fmt:message key="user.password.initial" /></td>
			<td><!--c:out value="${sessionScope.UserForm.password}"/--> <input
				name="password" type="text"
				value="<c:out value="${sessionScope.UserForm.password}"/>" size="20"
				readOnly /> <span class="STYLE2"><fmt:message
				key="user.password.infochange" /></span></td>
		</tr>
		<tr>
			<td width="18%"><fmt:message key="user.realName" /><ctcb:isRequried /></td>
			<td width="82%"><input name="realName" type="text"
				value="<c:out value="${sessionScope.UserForm.realName}"/>" /> <span
				class="STYLE2"><fmt:message key="user.realName.length" /></span></td>
		</tr>
		<tr>
			<td><fmt:message key="user.sex" /><ctcb:isRequried /><span
				class="STYLE2"></span></td>
			<td><label> <ctcb:optionsRadioGroup name="sex"
				mapSource="static" mapName="UserSex" selectedKey="${sessionScope.UserForm.sex}"
				style="horizontal" /> </label></td>
		</tr>
		<tr>
			<td><fmt:message key="user.role" /><ctcb:isRequried /><span
				class="STYLE2"></span></td>
			<td><label> <ctcb:optionsSelect id="role" name="role"
				mapSource="session" mapName="MAP_ROLE" selectedKey="${sessionScope.UserForm.role}"
				hasEmptyOption="true"/> </label></td>
		</tr>
		<tr>
			<td><fmt:message key="user.company" /></td>
			<td><label> <input name="company" type="text"
				style="width:260px"
				value="<c:out value="${sessionScope.UserForm.company}"/>" /> 
				<span class="STYLE2"><fmt:message key="user.company.length" /></span></label></td>
		</tr>
		<!--  style="overflow:visible" size="10" maxlength="50"-->
		<tr>
			<td><fmt:message key="user.address" /></td>
			<td><label> <textarea name="address" cols="30" rows="5"
				style="text-align:left;overflow:visible">${sessionScope.UserForm.address}</textarea>
			<span class="STYLE2"><fmt:message key="user.address.length"/></span></label></td>
		</tr>
		<tr>
			<td><fmt:message key="user.phone" /></td>
			<td><label> <input  id="phone" name="phone" type="text"
				onBlur="checkPhone(this.value)"
				value="<c:out value="${sessionScope.UserForm.phone}"/>"/> </label>
				<span class="STYLE2"><fmt:message
				key="user.phone.numberonly" /></span></td>
		</tr>
		<tr>
			<td>E-mail</td>
			<td onkeydown="if(event.keyCode == 13){submitForm();}"><label> <input name="email" type="text"
				value="<c:out value="${sessionScope.UserForm.email}"/>" /> </label></td>
		</tr>
		<tr id="curr" name="curr">
			<td><fmt:message key="user.currency" /><ctcb:isRequried /></td>
			<td onkeydown="if(event.keyCode == 13){submitForm();}"><ctcb:optionsSelect name="currency" id="currency"
				mapSource="session" mapName="MAP_CURRENCY" 
				selectedKey="${sessionScope.UserForm.currency}"
				hasEmptyOption="false" /></td>
		</tr>
		<tr>
			<td>
			<div align="left"><label> <IMG
				SRC="images/addCustomer.png" width="101" height="19" border="0"
				onClick="submitForm();" style=cursor:hand> </label></div>
			</td>
			<td><label> <IMG SRC="images/reset.png" width="101" height="19"
				border="0" onClick="document.forms.userForm.reset();"
				style=cursor:hand> </label></td>
		</tr>
	</table>
	</form>




<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<html:javascript formName="userForm" />
<script type="text/javascript">
function submitForm(){
	if(validateUserForm(document.getElementById('userForm'))){

	document.getElementById('userForm').submit();

	}
}

document.getElementById('role').onchange=hiddenCurrency;
function hiddenCurrency(){
	if(document.getElementById('role').value==1){
				document.getElementById('curr').style.display = 'none';
	}else{
				document.getElementById('curr').style.display = '';
	}
}

function checkPhone(s)    
{    
	var patrn=/^(\w)+$/;    
	if (!patrn.exec(s)) 
		{alert("<fmt:message key="user.alert.phone"/>");
		document.getElementById("phone").value="";
	} 
}
</script>
</fmt:bundle>
