
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>    
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>

<fmt:bundle basename="ApplicationResources">
<!--br /-->
<form id="simpleUserForm" name="simpleUserForm" method="post" action="modifyUserInfo.do">
  <input type="hidden" name="id" value="<c:out value="${sessionScope.SimpleUserForm.id}"/>"/>	
  <table width="60%%" border="0">
    <tr>
      <td width="18%"><fmt:message key="user.realName"/><ctcb:isRequried/></td>
      <td width="82%"><input name="realName" type="text" 
      	value="<c:out value="${sessionScope.SimpleUserForm.realName}"/>" size="10" />
      	<span class="STYLE2"><fmt:message key="user.realName.length"/></span>
      	
      </td>
    </tr>
    <tr>
      <td><fmt:message key="user.sex"/><ctcb:isRequried/><span class="STYLE2"></span></td>
      <td>
      <label>
      	<ctcb:optionsRadioGroup name="sex" mapSource="static" mapName="UserSex" 
      		selectedKey="${sessionScope.SimpleUserForm.sex}" style="horizontal"/>
     	</label>
     </td>
    </tr>
    <tr>
      <td><fmt:message key="user.company"/></td>
      <td><label>
        <input name="company" type="text" style="width:260px"
        	value="<c:out value="${sessionScope.SimpleUserForm.company}"/>"/>
        <span class="STYLE2"><fmt:message key="user.company.length" /></span></label></td>
    </tr>
    <tr>
      <td><fmt:message key="user.address"/></td>
      <td><label>
       <textarea name="address" 
       	cols="30" rows="5" id="address"
       	style="overflow:visible">${sessionScope.SimpleUserForm.address}</textarea>
        <span class="STYLE2"><fmt:message key="user.address.length" /></span></label></td>
    </tr>
    <tr>
      <td><fmt:message key="user.phone"/></td>
      <td><label>
        <input id="phone" name="phone" type="text" 
        onchange="checkPhone(this.value)"
        value="<c:out value="${sessionScope.SimpleUserForm.phone}"/>" size="15"  style="width:120px" />
      </label>
      <span class="STYLE2"><fmt:message
		key="user.phone.numberonly" /></span></td>
    </tr>
    <tr>
      <td>E-mail</td>
      <td onkeydown="if(event.keyCode == 13){submitForm();}"><label>
        <input name="email" type="text"  style="width:120px" value="<c:out value="${sessionScope.SimpleUserForm.email}"/>" />
      </label></td>
    </tr>
    <tr>
    
    	<td>
    		<label>
    			<IMG SRC="images/commitModify.png" width="101" height="19" border="0" 
    			onClick="submitForm();" style=cursor:hand>
    		</label>
    	</td>
    	<td>
    		<label>
    			<IMG SRC="images/reset.png" width="101" height="19" border="0"
    			onClick="document.forms.simpleUserForm.reset();" style=cursor:hand>
			</label>
		</td>
    </tr>
  </table>
</form>

<html:javascript formName="simpleUserForm"/>
<html:errors/>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<script type="text/javascript">
function submitForm(){
	if(validateSimpleUserForm(document.getElementById('simpleUserForm'))){

	document.getElementById('simpleUserForm').submit();

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

function checkLength() 
{ 
	if(document.getElementById('address').value.replace(/[^\x00-\xff]/g,'**').length>10) 
	{    alert("<fmt:message key="user.alert.addresslength"/>");
		document.getElementById('address').value="";
		document.getElementById('address').focus();
		event.returnvalue=false; 
	}
} 
</script>
</fmt:bundle>