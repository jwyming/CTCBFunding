<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@page import="com.eds.ctcb.biz.deal.FundIdRadio"%>
<%@ include file="/calendar/calendar.jsp" %>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>

<fmt:bundle basename="ApplicationResources" >
<form id="singleInvestmentForm" name="singleInvestmentForm" method="post" action="singleInvestment.do" >
<table width="100%">
<tr>
<td colspan="2" width="50%">
<fieldset>
<legend ><fmt:message key="deal.select.fundCode" /><ctcb:isRequried /></legend>
<p>
    <input type="radio" name="fundIdRadio" id="fundIdRadio" value="<%=FundIdRadio.ONE %>" onClick="javascript: document.singleInvestmentForm.fundTypeId.disabled = false;document.singleInvestmentForm.fundCode1.disabled = false;
  document.singleInvestmentForm.fundCompanyId.disabled = true;document.singleInvestmentForm.fundCode2.disabled = true;document.singleInvestmentForm.fundCode3.disabled = true;"/>
    <fmt:message key="deal.fundName"></fmt:message>
    <ctcb:optionsSelect id="fundTypeId" name="fundTypeId" mapSource="session" mapName="<%=SessionKey.MAP_FUND_TYPE %>" emptyOptionTitleKey="deal.select.fund.type" selectedKey="${sessionScope.SingleInvestmentForm.fundTypeId}" />
	<ctcb:optionsSelect id="fundCode1" name="fundCode1" mapSource="session" mapName="<%=SessionKey.MAP_FUND1 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="${sessionScope.SingleInvestmentForm.fundCode1}" />
	<ctcb:cascadeSelectScript selectBoxIds="fundTypeId;fundCode1" urls="getFundsByType.do"/>
</p>
<p>
  <input type="radio" name="fundIdRadio" id="fundIdRadio" value="<%=FundIdRadio.TWO %>" onClick="javascript: document.singleInvestmentForm.fundTypeId.disabled = true;document.singleInvestmentForm.fundCode1.disabled = true;
  document.singleInvestmentForm.fundCompanyId.disabled = false;document.singleInvestmentForm.fundCode2.disabled = false;document.singleInvestmentForm.fundCode3.disabled = true;"/>
    <fmt:message key="deal.fundName"></fmt:message>
  	<ctcb:optionsSelect id="fundCompanyId" name="fundCompanyId" mapSource="session" mapName="<%=SessionKey.MAP_FUND_COMPANY %>" emptyOptionTitleKey="deal.select.fund.company" selectedKey="${sessionScope.SingleInvestmentForm.fundCompanyId}" />
	<ctcb:optionsSelect id="fundCode2" name="fundCode2" mapSource="session" mapName="<%=SessionKey.MAP_FUND2 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="${sessionScope.SingleInvestmentForm.fundCode2}" />
	<ctcb:cascadeSelectScript selectBoxIds="fundCompanyId;fundCode2" urls="getFundsByCompany.do"/>
</p>
<p>
  <input type="radio" name="fundIdRadio" id="fundIdRadio" value="<%=FundIdRadio.THREE %>" onClick="javascript: document.singleInvestmentForm.fundTypeId.disabled = true;document.singleInvestmentForm.fundCode1.disabled = true;
  document.singleInvestmentForm.fundCompanyId.disabled = true;document.singleInvestmentForm.fundCode2.disabled = true;document.singleInvestmentForm.fundCode3.disabled = false;document.singleInvestmentForm.fundCode3.focus();"/>
  <fmt:message key="deal.fundCode"></fmt:message>
  <input type="text" name="fundCode3" value="${sessionScope.SingleInvestmentForm.fundCode3}" size="15" maxlength="20"/>
</p>
</fieldset>
</td><td ></td></tr>

<tr>
  <td width="10%"><fmt:message key="deal.currency"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <ctcb:optionsRadioGroup name="currency" mapSource="session" mapName="<%=SessionKey.MAP_ACCOUNT_CURRENCY%>" selectedKey="${sessionScope.SingleInvestmentForm.currency}"/>
 </td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.investmentAmount"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <input type="text" id="tradeAmount" name="tradeAmount" value="${sessionScope.SingleInvestmentForm.tradeAmount}" size="20" maxlength="20" />
¤¸</td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.handlingTariff" /></td>
  <td width="90%"><span id="handlingTariff" ></span><fmt:message key="deal.RMB" /></td>
 
</tr>
 <ctcb:ajaxScript triggerElementId="tradeAmount" triggerEvent="onchange" targetElementId="handlingTariff" url="computeHandlingTariff.do"/>


<tr>
  <td width="10%"><fmt:message key="deal.investmentDate"></fmt:message><ctcb:isRequried /></td>
  <td width="90%"><label>
 
    <input name="investmentDate" id="investmentDate" type="text" value="${sessionScope.SingleInvestmentForm.investmentDate}" readonly onkeydown="if(event.keyCode == 13){javascript: if(validateSingleInvestmentForm(document.getElementById('singleInvestmentForm'))) {document.getElementById('singleInvestmentForm').submit();}}">
  
  </label>
	<ctcb:calendar targetTexBoxId="investmentDate" />
</td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.investmentWarningRate" /></td>
  <td width="90%">
    <input name="investmentWarningRate" type="text" value="${sessionScope.SingleInvestmentForm.investmentWarningRate}" size="6" maxlength="6" onkeydown="if(event.keyCode == 13){javascript: if(validateSingleInvestmentForm(document.getElementById('singleInvestmentForm'))) {document.getElementById('singleInvestmentForm').submit();}}"/>
%<fmt:message key="deal.investmentWarningRate.message" /></td>
</tr>
</table>
<p align="left">
 
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript: if(validateSingleInvestmentForm(document.getElementById('singleInvestmentForm'))) {document.getElementById('singleInvestmentForm').submit();}"/> 
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.singleInvestmentForm.reset();"/>
  <br>
</p>
</form>





<script type="text/javascript">
<!--

var form = document.singleInvestmentForm;
var fundIdRadioValue = "${sessionScope.SingleInvestmentForm.fundIdRadio}";
var fundTypeIdValue = "${sessionScope.SingleInvestmentForm.fundTypeId}";
var fundCode1Value = "${sessionScope.SingleInvestmentForm.fundCode1}";
var fundCompanyIdValue = "${sessionScope.SingleInvestmentForm.fundCompanyId}";
var fundCode2Value = "${sessionScope.SingleInvestmentForm.fundCode2}";

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

	
}
function checkRadio(){	
	var check = 3;
	for(var i=0;i<form.fundIdRadio.length;i++){
		if(form.fundIdRadio[i].value == fundIdRadioValue ){			
			 check = i;
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
}

checkRadio()
keepRecord();



//-->
</script>
<html:javascript formName="singleInvestmentForm" />

</fmt:bundle>
