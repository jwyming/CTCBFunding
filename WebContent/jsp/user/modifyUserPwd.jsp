
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>    
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources">
<!--br/>
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="user.modify.pwd"/></span></td>
  </tr>
</table>
<br /-->
<form id="pwdForm" name="pwdForm"" method="post" action="modifyUserPwd.do">
  <table width="60%%" border="0">
    <tr>
      <td width="18%"><fmt:message key="user.pwd.inputold"/><ctcb:isRequried/></td>
      <td width="82%"><input type="password" name="oldPwd"
       value="<c:out value="${sessionScope.PwdForm.oldPwd}"/>"/></td>
    </tr>
    <tr>
      <td><fmt:message key="user.pwd.inputnew"/><ctcb:isRequried /></td>
      <td><input type="password" id ="newPwd" name="newPwd" onchange="checkPwd1(this.value)"/>
      <span class="STYLE2"><fmt:message key="user.pwd.length6to20"/></span>
      </td>
    </tr>
    <tr>
      <td><fmt:message key="user.pwd.inputcfm"/><ctcb:isRequried/></td>
      <td><input type="password" id ="cfmPwd" name="cfmPwd" onchange="checkPwd2(this.value)" onkeydown="if(event.keyCode == 13){submitForm();}"/></td>
    </tr>
 
    <tr>
    <td>
    	<label>
    			<IMG SRC="images/setPassword.png" width="101" height="19" border="0" 
    			onClick="submitForm();" style=cursor:hand>
    		</label>
    	</td>
    	<td>
    		<label>
    			<IMG SRC="images/reset.png" width="101" height="19" border="0"
    			onClick="document.forms.pwdForm.reset();" style=cursor:hand>
			</label>
		</td>

    </tr>
  </table>
</form>
<html:javascript formName="pwdForm"/>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<script type="text/javascript">
function submitForm(){
 if(validatePwdForm(document.getElementById('pwdForm'))){

	document.getElementById('pwdForm').submit();

	}
}

function checkPwd1(s)    
{    
	var patrn=/^(\w)+$/;    
	if (!patrn.exec(s)) 
	{	alert("<fmt:message key="sysParam.alert.newPwd"/>");
		document.getElementById("newPwd").value="";
	} 
   
}  

function checkPwd2(s)    
{    
	var patrn=/^(\w)+$/;    
	if (!patrn.exec(s)) 
	{
		alert("<fmt:message key="sysParam.alert.cfmPwd"/>");
		document.getElementById("cfmPwd").value="";
	} 
   
}    
  


</script>

</fmt:bundle>