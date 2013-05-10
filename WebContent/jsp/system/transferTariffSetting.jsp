
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<!--br />
<table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="sysParam.transfer.tariffsetting"/></span></td>
  </tr>
</table>
<br /-->
<form name="transferTariffForm" id="transferTariffForm" 
action="setTransferTariff.do" method="post">
<table width="60%%" border="0">
  <tr>
    <td width="20%"><fmt:message key="sysParam.transfertariff.min"/><ctcb:isRequried/></td>
    <td width="80%"><label>
      <input name="min" type="text" value="<c:out value="${sessionScope.TransferTariffForm.min}"/>" size="10" />
   <fmt:message key="sysParam.handletariff.unit.yuan"/></label></td>
  </tr>
  <tr>
    <td><fmt:message key="sysParam.transfertariff.max"/><ctcb:isRequried/></td>
    <td><input name="max" type="text" value="<c:out value="${sessionScope.TransferTariffForm.max}"/>" size="10" />
<fmt:message key="sysParam.handletariff.unit.yuan"/></td>
  </tr>
  <tr>
    <td><p><fmt:message key="sysParam.transferTariff.rate"/><ctcb:isRequried/></p>      </td>
    <td><label><fmt:message key="sysParam.handletariff.amount"/>x 
      <input name="rate" type="text" value="<c:out value="${sessionScope.TransferTariffForm.rate}"/>" size="10" />
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
    		onClick="document.forms.transferTariffForm.reset();" style=cursor:hand>
		</label>
	</td>
  </tr>
</table>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<html:javascript formName="transferTariffForm"/>
<script type="text/javascript">
function submitForm(){
	if(validateTransferTariffForm(document.getElementById('transferTariffForm'))){

	document.getElementById('transferTariffForm').submit();

	}
}
</script>

</fmt:bundle>
