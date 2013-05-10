<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@page import="com.eds.ctcb.biz.deal.SellTypeRadio"%>
<%@ include file="/calendar/calendar.jsp" %>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<fmt:bundle basename="ApplicationResources" >
<form id="sellingForm" name="sellingForm" method="post" action="selling.do">
<table width="100%">
<tr>
<td width="10%"><fmt:message key="deal.selling.fundCode"></fmt:message><ctcb:isRequried/></td>
<td width="90%">
<ctcb:optionsSelect id="sellFundCode" name="sellFundCode" mapSource="session" mapName="<%=SessionKey.MAP_CUST_FUND %>" emptyOptionTitleKey="deal.select.cust.fund" selectedKey="${sessionScope.SellingForm.sellFundCode}" />
</td></tr>

<tr>
  <td width="10%"><fmt:message key="deal.selling.currency"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <ctcb:optionsRadioGroup name="currency" mapSource="session" mapName="<%=SessionKey.MAP_ACCOUNT_CURRENCY%>" selectedKey="${sessionScope.SellingForm.currency}" />
 </td>
</tr>


<tr>
<td width="10%" valign="top"><fmt:message key="deal.selling.sellTypeRadio"></fmt:message><ctcb:isRequried /></td>
<td width="90%">
<p>
<input type="radio" name="sellTypeRadio" id="sellTypeRadio" value=<%=SellTypeRadio.ONE %> onclick="javascript:document.sellingForm.partSellAmount.disabled = true ;document.sellingForm.sellValue.disabled = true ;">
<fmt:message key="deal.selling.allSellAmount"></fmt:message><span id="sumAmount"></span>
<ctcb:ajaxScript triggerElementId="sellFundCode" triggerEvent="onchange" targetElementId="sumAmount" url="getSumAmount.do"/>

<p>
<input type="radio" name="sellTypeRadio" id="sellTypeRadio" value=<%=SellTypeRadio.TWO %> onClick="javascript:document.sellingForm.partSellAmount.disabled = false;document.sellingForm.partSellAmount.focus();document.sellingForm.sellValue.disabled = true;">
<fmt:message key="deal.selling.part"></fmt:message>
<input type="text" name="partSellAmount" id="partSellAmount" value="${sessionScope.SellingForm.partSellAmount}">
<p>
<input type="radio" name="sellTypeRadio" id="sellTypeRadio" value=<%=SellTypeRadio.THREE %> onClick="javascript:document.sellingForm.partSellAmount.disabled = true;document.sellingForm.sellValue.disabled = false;document.sellingForm.sellValue.focus();">
<fmt:message key="deal.selling.value"></fmt:message>
<input type="text" name="sellValue" id="sellValue" value="${sessionScope.SellingForm.sellValue}"><fmt:message key="deal.RMB"></fmt:message>
</td></tr>


<tr>
  <td width="10%"><fmt:message key="deal.investmentDate"></fmt:message><ctcb:isRequried /></td>
  <td width="90%"><label>
    <input name="investmentDate" id="investmentDate" type="text" value="${sessionScope.sellingForm.investmentDate}" readonly onkeydown="if(event.keyCode == 13){javascript: if(validateSellingForm(document.getElementById('sellingForm'))) {document.getElementById('sellingForm').submit();}}
    "/>
  </label>

  <ctcb:calendar targetTexBoxId="investmentDate" />
  </td>
</tr>

</table>
<p align="left">
 
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript: if(validateSellingForm(document.getElementById('sellingForm'))) {document.getElementById('sellingForm').submit();}"/> 
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.sellingForm.reset();"/>
  <br>

</form>
<script type="text/javascript">
<!--
var form = document.sellingForm;
var sellTypeRadioValue = "${sessionScope.sellingForm.sellTypeRadio}";
function keepRecord(){
	for(var i=0;i<form.sellTypeRadio.length;i++){
		if(form.sellTypeRadio[i].value == sellTypeRadioValue){
			form.sellTypeRadio[i].checked=true;			
		}
	}
}
function checkRadio(){	
	var check = 3;
	for(var i=0;i<form.sellTypeRadio.length;i++){
		if(form.sellTypeRadio[i].value == sellTypeRadioValue ){			
			 check = i;
			 break;
		}	
	}
	if(check == 3 || check == 0){
		form.partSellAmount.disabled = true;
		form.sellValue.disabled = true;
	}else if(check == 1){
		form.partSellAmount.disabled = false;
		form.sellValue.disabled = true;
	}else if(check == 2){
		form.partSellAmount.disabled = true;
		form.sellValue.disabled = false;

	}
}
document.getElementsByName("currency").value = "1";
checkRadio();
keepRecord();
//-->
</script>
<html:javascript formName="sellingForm" />

</fmt:bundle>
