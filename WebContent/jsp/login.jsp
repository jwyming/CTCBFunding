<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<fmt:bundle basename="ApplicationResources" >
<br>
<br>

<form id="loginForm" name="loginForm" action="login.do" method="post">

<table align="center">
	<tr>
		<td colspan="2" align="center"><h3> <fmt:message key="login.title"/></h3></td>
	</tr>
	<tr>
		<td><fmt:message key="login.username"/></td>
		<td><input type="text" name="username" value="${LoginForm.username}" tabindex="0" style="width: 100px" /></td>
	</tr>
	<tr>
		<td><fmt:message key="login.password"/></td>
		<td><input type="password" name="password" style="width: 100px"   onkeydown="if(event.keyCode == 13){submitForm()}"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
        <ctcb:imgButton id="commit" 
			href="javascript:submitForm();"
			imgSrc="images/login.gif" />
        </td>
	</tr>
</table>
</form>
<br>
<br>
<br>
<script type="text/javascript">
function submitForm(){
	if(validateLoginForm(document.getElementById("loginForm"))){
		document.getElementById("loginForm").submit();
	}
}
document.getElementById("loginForm").username.focus();
</script>
<html:javascript formName="loginForm" />
</fmt:bundle>