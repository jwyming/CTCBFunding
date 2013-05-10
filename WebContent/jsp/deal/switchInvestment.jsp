<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@page import="com.eds.ctcb.biz.deal.SellTypeRadio"%>
<%@page import="com.eds.ctcb.biz.deal.FundIdRadio"%>
<%@ include file="/calendar/calendar.jsp" %>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<fmt:bundle basename="ApplicationResources" >
<form id="switchInvestmentForm" name="switchInvestmentForm" method="post" action="switchInvestment.do">
<table width="100%">
<tr>
<td width="10%"><fmt:message key="deal.selling.fundCode"></fmt:message><ctcb:isRequried/></td>
<td width="300px">
<ctcb:optionsSelect id="sellFundCode" name="sellFundCode" mapSource="session" mapName="<%=SessionKey.MAP_CUST_FUND %>" emptyOptionTitleKey="deal.select.cust.fund" selectedKey="${sessionScope.SwitchInvestmentForm.sellFundCode}" />
</td></tr>


<tr>
<td width="10%" valign="top"><fmt:message key="deal.changeFund.inFund"></fmt:message><ctcb:isRequried /></td>
<td width="50%" colspan="2">
<fieldset>
<legend><fmt:message key="deal.select.fundCode"/></legend>
<p>
    <input type="radio" name="fundIdRadio" value="<%=FundIdRadio.ONE %>" onClick="javascript:document.switchInvestmentForm.fundTypeId.disabled = false;
    document.switchInvestmentForm.fundCode1.disabled = false;document.switchInvestmentForm.fundCompanyId.disabled = true;
    document.switchInvestmentForm.fundCode2.disabled = true;document.switchInvestmentForm.fundCode3.disabled = true;"/>
    <fmt:message key="deal.fundName"></fmt:message>
    <ctcb:optionsSelect id="fundTypeId" name="fundTypeId" mapSource="session" mapName="<%=SessionKey.MAP_FUND_TYPE %>" emptyOptionTitleKey="deal.select.fund.type" selectedKey="${sessionScope.SwitchInvestmentForm.fundTypeId}" />
	<ctcb:optionsSelect id="fundCode1" name="fundCode1" mapSource="session" mapName="<%=SessionKey.MAP_FUND1 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="${sessionScope.SwitchInvestmentForm.fundCode1}" />
	<ctcb:cascadeSelectScript selectBoxIds="fundTypeId;fundCode1" urls="getFundsByType.do"/> 
</p>
<p>
  <input type="radio" name="fundIdRadio" value="<%=FundIdRadio.TWO %>" onClick="javascript:document.switchInvestmentForm.fundTypeId.disabled = true;
    document.switchInvestmentForm.fundCode1.disabled = true;document.switchInvestmentForm.fundCompanyId.disabled = false;
    document.switchInvestmentForm.fundCode2.disabled = false;document.switchInvestmentForm.fundCode3.disabled = true;"/>
<fmt:message key="deal.fundName"></fmt:message>
  	<ctcb:optionsSelect id="fundCompanyId" name="fundCompanyId" mapSource="session" mapName="<%=SessionKey.MAP_FUND_COMPANY %>" emptyOptionTitleKey="deal.select.fund.company" selectedKey="${sessionScope.SwitchInvestmentForm.fundCompanyId}" />
	<ctcb:optionsSelect id="fundCode2" name="fundCode2" mapSource="session" mapName="<%=SessionKey.MAP_FUND2 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="${sessionScope.SwitchInvestmentForm.fundCode2}" />
	<ctcb:cascadeSelectScript selectBoxIds="fundCompanyId;fundCode2" urls="getFundsByCompany.do"/>
</p>
<p>
  <input type="radio" name="fundIdRadio" value="<%=FundIdRadio.THREE %>" onClick="javascript:document.switchInvestmentForm.fundTypeId.disabled = true;
    document.switchInvestmentForm.fundCode1.disabled = true;document.switchInvestmentForm.fundCompanyId.disabled = true;
    document.switchInvestmentForm.fundCode2.disabled = true;document.switchInvestmentForm.fundCode3.disabled = false;switchInvestmentForm.fundCode3.focus();"/>
<fmt:message key="deal.fundCode"></fmt:message>
  <input type="text" name="fundCode3" value="${sessionScope.SwitchInvestmentForm.fundCode3}" size="15" maxlength="20"/>
</p>
</fieldset>
</td>
<td width="40"><!-- used to make the td looks better --></td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.selling.currency"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <ctcb:optionsRadioGroup name="currency" mapSource="session" mapName="<%=SessionKey.MAP_ACCOUNT_CURRENCY%>" selectedKey="${sessionScope.SwitchInvestmentForm.currency}"/>
 </td>
</tr>
<tr>
<td width="10%" valign="top"><fmt:message key="deal.selling.sellTypeRadio"></fmt:message><ctcb:isRequried/></td>
<td width="90%">
<p>
<input type="radio" name="sellTypeRadio" id="sellTypeRadio" value=<%=SellTypeRadio.ONE %> onClick="javascript:document.switchInvestmentForm.partSellAmount.disabled = true;document.switchInvestmentForm.sellValue.disabled = true;">
<fmt:message key="deal.selling.allSellAmount"></fmt:message><span id="sumAmount"></span>
<ctcb:ajaxScript triggerElementId="sellFundCode" triggerEvent="onchange" targetElementId="sumAmount" url="getSumAmount.do"/>

<br >
<span class="STYLE4"><fmt:message key="deal.selling.allSellAmount.message"/></span>
<p>

<input type="radio" name="sellTypeRadio" id="sellTypeRadio" value=<%=SellTypeRadio.TWO %> onclick="javascript:document.switchInvestmentForm.partSellAmount.disabled = false ;document.switchInvestmentForm.partSellAmount.focus() ;document.switchInvestmentForm.sellValue.disabled = true;">
<fmt:message key="deal.selling.part"></fmt:message>
<input type="text" name="partSellAmount" id="partSellAmount" value="${sessionScope.SwitchInvestmentForm.partSellAmount}">
<br />
<span class="STYLE4"><fmt:message key="deal.selling.part.message"/></span>
<p>
<input type="radio" name="sellTypeRadio" id="sellTypeRadio" value=<%=SellTypeRadio.THREE %> onclick="javascript: document.switchInvestmentForm.partSellAmount.disabled = true ;document.switchInvestmentForm.sellValue.disabled = false;document.switchInvestmentForm.sellValue.focus();">
<fmt:message key="deal.selling.value"></fmt:message>
<span class="STYLE4"><fmt:message key="deal.selling.value.message"></fmt:message> </span>
<input type="text" name="sellValue" id="sellValue" value="${sessionScope.switchInvestmentForm.sellValue}"><fmt:message key="deal.RMB"></fmt:message>
</td>
</tr>

<tr>
  <td width="10%"><fmt:message key="deal.investmentDate"></fmt:message><ctcb:isRequried /></td>
  <td width="90%"><label>
    <input name="investmentDate" id="investmentDate" type="text" value="${sessionScope.SwitchInvestmentForm.investmentDate}" readonly onkeydown="if(event.keyCode == 13){javascript: if(validateSwitchInvestmentForm(document.getElementById('switchInvestmentForm'))) {document.getElementById('switchInvestmentForm').submit();}}"/>
  </label>

  <ctcb:calendar targetTexBoxId="investmentDate" />
 </td>
</tr>

</table>
<p align="left">
 
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript: if(validateSwitchInvestmentForm(document.getElementById('switchInvestmentForm'))) {document.getElementById('switchInvestmentForm').submit();}"/> 
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.switchInvestmentForm.reset();"/>
  <br>

</form>
<script type="text/javascript">
<!--
var form = document.switchInvestmentForm;
var fundIdRadioValue = "${sessionScope.SwitchInvestmentForm.fundIdRadio}";
var sellTypeRadioValue = "${sessionScope.SwitchInvestmentForm.sellTypeRadio}";
var fundTypeIdValue = "${sessionScope.SwitchInvestmentForm.fundTypeId}";
var fundCode1Value = "${sessionScope.SwitchInvestmentForm.fundCode1}";
var fundCompanyIdValue = "${sessionScope.SwitchInvestmentForm.fundCompanyId}";
var fundCode2Value = "${sessionScope.SwitchInvestmentForm.fundCode2}";

function keepRecord(){
	for(var i=0;i<form.fundIdRadio.length;i++){
		if(form.fundIdRadio[i].value == fundIdRadioValue){
			form.fundIdRadio[i].checked = true;			
		}
	}
	for(var i=0;i<form.fundTypeId.length;i++){	
		if(form.fundTypeId[i].value == fundTypeIdValue){
			form.fundTypeId[i].selected = true;			
		}
	}
	for(var i=0;i<form.fundCode1.length;i++){
		if(form.fundCode1[i].value == fundCode1Value){
			form.fundCode1[i].selected = true;			
		}
	}
	for(var i=0;i<form.fundCompanyId.length;i++){	
		if(form.fundCompanyId[i].value == fundCompanyIdValue){
			form.fundCompanyId[i].selected = true;			
		}
	}
	for(var i=0;i<form.fundCode2.length;i++){
		if(form.fundCode2[i].value == fundCode2Value){
			form.fundCode2[i].selected = true;			
		}
	}
	for(var i=0;i<form.sellTypeRadio.length;i++){
		if(form.sellTypeRadio[i].value == sellTypeRadioValue){
			form.sellTypeRadio[i].checked = true;			
		}
	}

	
}
function checkRadio(){	
	var check = 3;
	var check2 = 3;
	for(var i=0;i<form.fundIdRadio.length;i++){
		if(form.fundIdRadio[i].value == fundIdRadioValue ){			
			 check = i;
			 break;
		}		
	}
	for(var i=0;i<form.sellTypeRadio.length;i++){
		if(form.sellTypeRadio[i].value == sellTypeRadioValue ){			
			 check2 = i;
			 break;
		}		
	}
	if(check == 3){
		form.fundTypeId.disabled = true;
		form.fundCode1.disabled = true;
		form.fundCompanyId.disabled = true;
		form.fundCode2.disabled = true;
		form.fundCode3.disabled = true;
	}else if(check == 0){
		form.fundTypeId.disabled = false;
		form.fundCode1.disabled = false;
		form.fundCompanyId.disabled = true;
		form.fundCode2.disabled = true;
		form.fundCode3.disabled = true;
	}else if(check == 1){
		form.fundTypeId.disabled = true;
		form.fundCode1.disabled = true;
		form.fundCompanyId.disabled = false;
		form.fundCode2.disabled = false;
		form.fundCode3.disabled = true;
	}else if(check == 2){
		form.fundTypeId.disabled = true;
		form.fundCode1.disabled = true;
		form.fundCompanyId.disabled = true;
		form.fundCode2.disabled = true;
		form.fundCode3.disabled = false;
	}
	if(check2 == 3 || check2 == 0){
		form.partSellAmount.disabled = true;
		form.sellValue.disabled = true;
	}
	if(check2 == 1){
		form.partSellAmount.disabled = false;
		form.sellValue.disabled = true;
	}
	if(check2 == 2){
		form.partSellAmount.disabled = true;
		form.sellValue.disabled = false;
	}
	
}

checkRadio();
keepRecord();
//-->
</script>
<html:javascript formName="switchInvestmentForm" />

</fmt:bundle>
