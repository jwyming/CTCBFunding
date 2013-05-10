
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<!--table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="sysParam.investamt.setting"/></span></td>
  </tr>
</table>
<br /-->
<form name="invAmountForm" id="invAmountForm" action="setInitInvAmount.do" method="post"><table width="60%%" border="0">
  <tr>
    <td width="20%"><fmt:message key="sysParam.invest.amt"/><ctcb:isRequried/></td>
    <td width="80%"><label>
      <input name="amount" type="text" value="<c:out  value="${init_inv_amount}" />" size="20" onkeydown="if(event.keyCode == 13){submitForm();}"/>
   <fmt:message key="sysParam.handletariff.unit.yuan"/></label>
    <span class="STYLE2"><fmt:message key="sysParam.investamt.lt2decimal"/></span></td>
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
    		onClick="document.forms.invAmountForm.reset();" style=cursor:hand>
		</label>
	</td>
  </tr>
</table>
</form>
<html:javascript formName="invAmountForm"/>
<html:errors/>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<script type="text/javascript">
function submitForm(){
	if(validateInvAmountForm(document.getElementById('invAmountForm'))){

	document.getElementById('invAmountForm').submit();

	}
}
</script>

</fmt:bundle>