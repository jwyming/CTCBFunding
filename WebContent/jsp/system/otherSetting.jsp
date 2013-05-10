
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<!--br />
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="sysParam.misc.setting"/></span></td>
  </tr>
</table>
<br /-->
<form name="miscSettingForm" id="miscSettingForm" 
action="setMisc.do" method="post"><table width="60%%" border="0">
  <tr>
    <td width="20%"><fmt:message key="sysParam.misc.minAmt"/><ctcb:isRequried/></td>
    <td width="80%"><label>
      <input name="minAmt" type="text" 
      value="<c:out value="${sessionScope.MiscSettingForm.minAmt}" />" size="10" onkeydown="if(event.keyCode == 13){submitForm();}"/>
    <fmt:message key="sysParam.handletariff.unit.yuan"/></label></td>
  </tr>
  <tr>
    <td><fmt:message key="sysParam.misc.minUnit"/><ctcb:isRequried/></td>
    <td><input name="minUnit" type="text" 
    value="<c:out value="${sessionScope.MiscSettingForm.minUnit}"/>" size="10" onkeydown="if(event.keyCode == 13){submitForm();}"/></td>
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
    		onClick="document.forms.miscSettingForm.reset();" style=cursor:hand>
		</label>
	</td>
  </tr>
</table>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<html:javascript formName="miscSettingForm"/>
<script type="text/javascript">
function submitForm(){
	if(validateMiscSettingForm(document.getElementById('miscSettingForm'))){

	document.getElementById('miscSettingForm').submit();

	}
}
</script>

</fmt:bundle>
