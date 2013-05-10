
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<!--br/>
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="sysParam.handletariff.setting"/></span></td>
  </tr>
</table>
<br /-->
<form name="handleTariffForm" id="handleTariffForm" action="setHandleTariff.do" method="post"><table width="60%%" border="0">
  <tr>
    <td width="20%"><fmt:message key="sysParam.handletariff.min"/><ctcb:isRequried /></td>
    <td width="80%"><label>
      <input name="min" type="text" value="<c:out value="${sessionScope.HandleTariffForm.min}"/>" size="10" />
    	<fmt:message key="sysParam.handletariff.unit.yuan"/></label></td>
  </tr>
  <tr>
    <td><fmt:message key="sysParam.handletariff.max"/><ctcb:isRequried /></td>
    <td><input name="max" type="text" value="<c:out value="${sessionScope.HandleTariffForm.max}"/>" size="10" />
<fmt:message key="sysParam.handletariff.unit.yuan"/>
</td>
  </tr>
  <tr>
    <td><p><fmt:message key="sysParam.handletariff.rate"/><ctcb:isRequried/></p>      </td>
    <td><label><fmt:message key="sysParam.handletariff.amount"/> x
      <input name="rate" type="text" value="<c:out value="${sessionScope.HandleTariffForm.rate}"/>" size="10" onkeydown="if(event.keyCode == 13){submitForm();}"/>
    %</label>
    <span class="STYLE2"><fmt:message key="sysParam.handletariff.lt100"/></span>
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
    		onClick="document.forms.handleTariffForm.reset();" style=cursor:hand>
		</label>
	</td>
  </tr>
</table>
</form>
</fmt:bundle>
<html:javascript formName="handleTariffForm"/>
<script type="text/javascript">
function submitForm(){
	if(validateHandleTariffForm(document.getElementById('handleTariffForm'))){

	document.getElementById('handleTariffForm').submit();

	}
}
</script>
