<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@page import="com.eds.ctcb.biz.deal.SellTypeRadio"%>
<%@ include file="/calendar/calendar.jsp" %>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<form id="changeFundForm" name="changeFundForm" method="post" action="changeFund.do">
<table width="100%">
<tr>
<td width="10%"><fmt:message key="deal.changeFund.outFund"></fmt:message><ctcb:isRequried/></td>
<td width="90%">
<ctcb:optionsSelect id="outFundCode" name="outFundCode" mapSource="session" mapName="<%=SessionKey.MAP_CUST_FUND %>" emptyOptionTitleKey="deal.select.outFund" selectedKey="${sessionScope.ChangeFundForm.outFundCode}" />
</td></tr>

<tr>
<td ><fmt:message key="deal.changeFund.inFund"></fmt:message><ctcb:isRequried/></td>
<td >
<ctcb:optionsSelect id="inFundCode" name="inFundCode" mapSource="session" mapName="<%=SessionKey.MAP_FUND3 %>" emptyOptionTitleKey="deal.select.inFund" selectedKey="${sessionScope.ChangeFundForm.inFundCode}" />
<ctcb:cascadeSelectScript selectBoxIds="outFundCode;inFundCode" urls="getCompanyFundsByFundCode.do"/> 
</td></tr>

<tr>
  <td width="10%"><fmt:message key="deal.selling.currency"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <ctcb:optionsRadioGroup name="currency" mapSource="session" mapName="<%=SessionKey.MAP_ACCOUNT_CURRENCY%>" selectedKey="${sessionScope.ChangeFundForm.currency}"/>
 </td>
</tr>


<tr>
<td width="10%" valign="top"><fmt:message key="deal.changeFund.changeFundRadio"></fmt:message><ctcb:isRequried /></td>
<td width="90%">
<p>
<input type="radio" name="changeFundRadio" id="changeFundRadio" value="<%=SellTypeRadio.ONE %>" onClick="javascript:document.changeFundForm.partChangeAmount.disabled = true;">
<fmt:message key="deal.selling.allSellAmount"></fmt:message><span id="sumAmount"></span>
<ctcb:ajaxScript triggerElementId="outFundCode" triggerEvent="onblur" targetElementId="sumAmount" url="getSumAmount.do"/>
<span class="STYLE3"><fmt:message key="deal.changeFund.allChange.message"/></span>
<p>
<input type="radio" name="changeFundRadio" id="changeFundRadio" value=<%=SellTypeRadio.TWO %> onClick="javascript:document.changeFundForm.partChangeAmount.disabled = false;document.changeFundForm.partChangeAmount.focus();">
<fmt:message key="deal.selling.part"></fmt:message>
<input type="text" name="partChangeAmount" id="partChangeAmount" value="${sessionScope.ChangeFundForm.partChangeAmount}">
<span class="STYLE3">
<fmt:message key="deal.changeFund.part.message"/></span>
</td>
</tr>

<tr>
  <td width="10%"><fmt:message key="deal.investmentDate"></fmt:message><ctcb:isRequried /></td>
  <td width="90%"><label>
    <input name="investmentDate" id="investmentDate" type="text" value="${sessionScope.ChangeFundForm.investmentDate}" readonly onkeydown="javascript:if(validateChangeFundForm(document.changeFundForm)){ document.changeFundForm.submit();}"/>
  </label>

  <ctcb:calendar targetTexBoxId="investmentDate" />
  </td>
</tr>

</table>
<p align="left">
 
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript:if(validateChangeFundForm(document.changeFundForm)){ document.changeFundForm.submit();}"/>
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.changeFundForm.reset();"/>
  <br>
</p>
</form>
<script type="text/javascript">
<!--
var form = document.changeFundForm;
var changeFundRadioValue = "${sessionScope.changeFundForm.changeFundRadio}";
var inFundCodeValue = "${sessionScope.changeFundForm.inFundCode}";
function keepRecord(){
	for(var i=0;i<form.changeFundRadio.length;i++){
		if(form.changeFundRadio[i].value == changeFundRadioValue){
			form.changeFundRadio[i].checked = true;			
		}
	}
	for(var i=0;i<form.inFundCode.length;i++){
		if(form.inFundCode[i].value == inFundCodeValue){
			form.inFundCode[i].selected = true;			
		}
	}
}
function checkRadio(){	
	var check = 2;
	for(var i=0;i<form.changeFundRadio.length;i++){
		if(form.changeFundRadio[i].value == changeFundRadioValue ){			
			 check = i;
			 break;
		}	
	}
	if(check == 2 || check == 0){
		form.partChangeAmount.disabled = true;
	}else if(check == 1){
		form.partChangeAmount.disabled = false;
	}
}
checkRadio();
keepRecord();
//-->
</script>
<html:javascript formName="changeFundForm" />

</fmt:bundle>
