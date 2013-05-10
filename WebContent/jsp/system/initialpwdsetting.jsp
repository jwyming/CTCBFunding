
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>

<fmt:bundle basename="ApplicationResources" >
<!-- br />
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="sysParam.initpwd.setting"/></span></td>
  </tr>
</table>
<br /-->
<form name="sysParamForm"  id="sysParamForm" method="post" action="setInitialPwd.do" >
<table width="60%%" border="0">
  <tr>
    <td width="20%"><fmt:message key="sysParam.user.initPwd"/><ctcb:isRequried /></td>
    <td width="80%"><label>

      <input name="value" id="password" type="text" value="<c:out value="${initial_password}"/>" 
       onchange="checkPwd(this.value)" size="20" onkeydown="if(event.keyCode == 13){submitForm();}"/>
    </label>
    
    <span class="STYLE2"><fmt:message key="user.name.length"/></span>
    </td>
  </tr>
 
  <tr>
    <td>
    	<label>
    		<IMG SRC="images/commitSet.png" width="101" height="19" border="0" 
    		onClick="submitForm();" style=cursor:hand>
    	</label>
    </td>
    <td>
    	<label>
    		<IMG SRC="images/reset.png" width="101" height="19" border="0"
    		onClick="document.forms.sysParamForm.reset();" style=cursor:hand>
		</label>
	</td>
  </tr>
</table>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<html:javascript formName="sysParamForm"/>


<script type="text/javascript">
function submitForm(){
	if(validateSysParamForm(document.getElementById('sysParamForm'))){

	document.getElementById('sysParamForm').submit();

	}	
}

function checkPwd(s)    
{    
	var patrn=/^(\w)+$/;    
	if (!patrn.exec(s)) 
		{alert("<fmt:message key="sysParam.alert.initPwd"/>");
		document.getElementById("password").value="";
	} 
}

</script>

</fmt:bundle>
