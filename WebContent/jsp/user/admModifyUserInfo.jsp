
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>    
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<fmt:bundle basename="ApplicationResources" >
<!--br />
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="user.info.mod"/>
    </span></td>
  </tr>
</table>
<br /-->
<form id="userFormEx" name="userFormEx" method="post" action="admModifyUserInfo.do">
   <input type="hidden" name="id" value="<c:out value="${sessionScope.UserFormEx.id}"/>"/>
  <table width="60%%" border="0">
    <tr>
      <td><fmt:message key="user.userName"/><ctcb:isRequried/><span class="STYLE2"></span></td>
      <td>${sessionScope.UserFormEx.userName}</td>
    </tr>
    <tr>
      <td width="18%"><fmt:message key="user.realName"/><ctcb:isRequried/></td>
      <td width="82%"><input name="realName" type="text"
       value="<c:out value="${sessionScope.UserFormEx.realName}"/>" size="10" />
       <span class="STYLE2"><fmt:message key="user.realName.length"/></span>
       </td>
    </tr>
    <tr>
      <td><fmt:message key="user.sex"/><ctcb:isRequried/><span class="STYLE2"></span></td>
      <td>
      <label>
      	<ctcb:optionsRadioGroup name="sex" mapSource="static" mapName="UserSex" 
      		selectedKey="${sessionScope.UserFormEx.sex}" style="horizontal"/>
     	</label>
     </td>
    </tr>
	<tr>
      <td><fmt:message key="user.role.setting"/><ctcb:isRequried/><span class="STYLE2"></span></td>
      <td><label>
      
      	<ctcb:optionsSelect name="role" mapSource="session" mapName="MAP_ROLE1" selectedKey="${sessionScope.UserFormEx.role}" hasEmptyOption="false"/>
      </label></td>
    </tr>
    <tr>
      <td><fmt:message key="user.company"/></td>
      <td><label>
        <input name="company" type="text" style="width:260px" 
         value="<c:out value="${sessionScope.UserFormEx.company}"/>"/>
      <span class="STYLE2"><fmt:message key="user.company.length" /></span></label></td>
    </tr>
    <tr>
      <td><fmt:message key="user.address"/></td>
      <td><label>
       <textarea name="address" 
       	cols="30" rows="5" id="address" 
       	style="text-align:left;overflow:visible">${sessionScope.UserFormEx.address}</textarea>
       <span class="STYLE2"><fmt:message key="user.address.length" /></span></label></td>
    </tr>
    <tr>
      <td><fmt:message key="user.phone"/></td>
      <td><label>
        <input id="phone" name="phone"  onchange="checkPhone(this.value)"
        style="width:120px" type="text"
         value="<c:out value="${sessionScope.UserFormEx.phone}"/>" size="15" />
      </label>
      <span class="STYLE2"><fmt:message
		key="user.phone.numberonly" /></span></td>
    </tr>
    <tr>
      <td>E-mail</td>
      <td onkeydown="if(event.keyCode == 13){submitForm();}"><label>
        <input name="email" type="text"  style="width:120px" value="<c:out value="${sessionScope.UserFormEx.email}"/>" />
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
    			onClick="document.forms.userFormEx.reset();" style=cursor:hand>
			</label>
		</td>
    </tr>
  </table>
</form>
<html:javascript formName="userFormEx"/>
<script type="text/javascript">
document.getElementById('role').disabled=true;
function submitForm(){
	if(validateUserFormEx(document.getElementById('userFormEx'))){

	document.getElementById('userFormEx').submit();

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