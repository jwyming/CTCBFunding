<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@page import="com.eds.ctcb.biz.deal.FundIdRadio"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<form id="regularInvestmentForm" name="regularInvestmentForm" method="post" action="regularInvestment.do">
<table width="100%">
<tr><td colspan="2">
<fieldset>
<legend><fmt:message key="deal.select.fundCode"></fmt:message><ctcb:isRequried/></legend>
<p>
    <input type="radio" name="fundIdRadio" value="<%=FundIdRadio.ONE %>" onClick="javascript: document.regularInvestmentForm.fundTypeId.disabled = false;document.regularInvestmentForm.fundCode1.disabled = false;
  document.regularInvestmentForm.fundCompanyId.disabled = true;document.regularInvestmentForm.fundCode2.disabled = true;document.regularInvestmentForm.fundCode3.disabled = true;"/>
  <fmt:message key="deal.fundName"></fmt:message>
    <ctcb:optionsSelect id="fundTypeId" name="fundTypeId" mapSource="session" mapName="<%=SessionKey.MAP_FUND_TYPE %>" emptyOptionTitleKey="deal.select.fund.type" selectedKey="${sessionScope.RegularInvestmentForm.fundTypeId}" />
	<ctcb:optionsSelect id="fundCode1" name="fundCode1" mapSource="session" mapName="<%=SessionKey.MAP_FUND1 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="${sessionScope.RegularInvestmentForm.fundCode1}" />
	<ctcb:cascadeSelectScript selectBoxIds="fundTypeId;fundCode1" urls="getFundsByType.do"/>
</p>
<p>
  <input type="radio" name="fundIdRadio" value="<%=FundIdRadio.TWO %>" onClick="javascript: document.regularInvestmentForm.fundTypeId.disabled = true;document.regularInvestmentForm.fundCode1.disabled = true;
  document.regularInvestmentForm.fundCompanyId.disabled = false;document.regularInvestmentForm.fundCode2.disabled = false;document.regularInvestmentForm.fundCode3.disabled = true;"/>
  <fmt:message key="deal.fundName"></fmt:message>
  	<ctcb:optionsSelect id="fundCompanyId" name="fundCompanyId" mapSource="session" mapName="<%=SessionKey.MAP_FUND_COMPANY %>" emptyOptionTitleKey="deal.select.fund.company" selectedKey="${sessionScope.RegularInvestmentForm.fundCompanyId}" />
	<ctcb:optionsSelect id="fundCode2" name="fundCode2" mapSource="session" mapName="<%=SessionKey.MAP_FUND2 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="${sessionScope.RegularInvestmentForm.fundCode2}" />
	<ctcb:cascadeSelectScript selectBoxIds="fundCompanyId;fundCode2" urls="getFundsByCompany.do"/>
</p>
<p>
  <input type="radio" name="fundIdRadio" value="<%=FundIdRadio.THREE %>" onClick="javascript: document.regularInvestmentForm.fundTypeId.disabled = true;document.regularInvestmentForm.fundCode1.disabled = true;
  document.regularInvestmentForm.fundCompanyId.disabled = true;document.regularInvestmentForm.fundCode2.disabled = true;document.regularInvestmentForm.fundCode3.disabled = false;document.regularInvestmentForm.fundCode3.focus();"/>
<fmt:message key="deal.fundCode"></fmt:message>
  <input type="text" name="fundCode3" value="${sessionScope.RegularInvestmentForm.fundCode3}" size="15" maxlength="20"/>
</p>
</fieldset>
</td></tr>
<tr>
  <td width="10%"><fmt:message key="deal.currency"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <ctcb:optionsRadioGroup name="currencyId" mapSource="session" mapName="<%=SessionKey.MAP_ACCOUNT_CURRENCY%>" selectedKey="${sessionScope.RegularInvestmentForm.currencyId}"/>
   </td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.monthlyAmount"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <input type="text" id="monthlyAmount" name="monthlyAmount" value="${sessionScope.RegularInvestmentForm.monthlyAmount}" size="20" maxlength="20" />
<fmt:message key="deal.RMB" /></td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.monthlyDate"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
  <select name="investmentDay" onkeydown="if(event.keyCode == 13){if(validateRegularInvestmentForm(document.regularInvestmentForm)) {document.regularInvestmentForm.submit();}}">
    <option value="6">6</option>
    <option value="12">12</option>
    <option value="16">16</option>
    <option value="26">26</option>
  </select>
<fmt:message key="deal.date"></fmt:message> </td>
</tr>
<tr>
  <td width="10%"><fmt:message key="deal.investmentWarningRate" /></td>
  <td width="90%">
    <input name="investmentWarningRate" type="text" value="${sessionScope.RegularInvestmentForm.investmentWarningRate}" size="6" maxlength="6" onkeydown="if(keyCode == 13){javascript: if(validateRegularInvestmentForm(document.regularInvestmentForm)) {document.regularInvestmentForm.submit();}}"/>
%<fmt:message key="deal.investmentWarningRate.message" /></td>
</tr>
</table>
<p align="left">
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript: if(validateRegularInvestmentForm(document.regularInvestmentForm)) {document.regularInvestmentForm.submit();}"/>
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.regularInvestmentForm.reset();"/>
  <br>
</p>

</form>
<script type="text/javascript">
<!--

var form = document.regularInvestmentForm;
var fundIdRadioValue = "${sessionScope.RegularInvestmentForm.fundIdRadio}";
var fundTypeIdValue = "${sessionScope.RegularInvestmentForm.fundTypeId}";
var fundCode1Value = "${sessionScope.RegularInvestmentForm.fundCode1}";
var fundCompanyIdValue = "${sessionScope.RegularInvestmentForm.fundCompanyId}";
var fundCode2Value = "${sessionScope.RegularInvestmentForm.fundCode2}";
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
		if(form.fundIdRadio[i].value == fundIdRadioValue  ){	
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
checkRadio();
keepRecord();


//-->
</script>
<html:javascript formName="regularInvestmentForm" />

</fmt:bundle>