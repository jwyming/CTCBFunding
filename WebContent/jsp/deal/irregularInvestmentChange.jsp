<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@page import="com.eds.ctcb.biz.deal.FundIncrementRadio"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>    
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<fmt:bundle basename="ApplicationResources">
<form id="irregularInvestmentChangeForm" name="irregularInvestmentChangeForm" method="post" action="irregularInvestmentChange.do">
<table width="100%" border="0">
<tr>
  	<td width="10%"><fmt:message key="regularInvestmentChange.tableTitle.fundName"/></td>
	<td width="90%"><span id="fundName" name="fundName"><c:out value="${sessionScope.IrregularInvestmentChangeForm.fundName}"/></span>
  	</td>
</tr>
  
<tr>
  <td width="10%"><fmt:message key="deal.monthlyDate"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
  <select name="investmentDate">
    <option value="6">6</option>
    <option value="12">12</option>
    <option value="16">16</option>
    <option value="26">26</option>
  </select><fmt:message key="deal.date"></fmt:message>
  </td>
</tr>
  
<tr>
  <td width="10%"><fmt:message key="deal.firstAmount"></fmt:message><ctcb:isRequried/></td>
  <td width="90%"> 
    <input type="text" id="investmentAmount" name="investmentAmount" value="${sessionScope.IrregularInvestmentChangeForm.investmentAmount}" size="20" maxlength="20" />
<fmt:message key="deal.RMB"/></td>
</tr>

<tr>
<td width="10%"><fmt:message key="deal.fundIncrementRadio"></fmt:message><ctcb:isRequried/></td>
<td width="90%">
<input type="radio" name="incrementRadio" value="<%=FundIncrementRadio.ONE %>" onClick="javascript: 
document.irregularInvestmentChangeForm.incrementPercentage.disabled = false; document.irregularInvestmentChangeForm.incrementValue.disabled = true;
document.irregularInvestmentChangeForm.incrementPercentage.focus()" onkeydown="if(keyCode == 13){javascript: if(validateIrregularInvestmentChangeForm(document.irregularInvestmentChangeForm)) {document.irregularInvestmentChangeForm.submit();}}" checked/>
<input name="incrementPercentage" type="text"  value="${sessionScope.IrregularInvestmentChangeForm.incrementPercentage}"> %
<input type="radio" name="incrementRadio" value="<%=FundIncrementRadio.TWO %>" onClick="javascript: 
document.irregularInvestmentChangeForm.incrementPercentage.disabled = true; document.irregularInvestmentChangeForm.incrementValue.disabled = false; 
document.irregularInvestmentChangeForm.incrementValue.focus()" onkeydown="if(keyCode == 13){javascript: if(validateIrregularInvestmentChangeForm(document.irregularInvestmentChangeForm)) {document.irregularInvestmentChangeForm.submit();}}"/>
<input name="incrementValue" type="text"  value="${sessionScope.IrregularInvestmentChangeForm.incrementValue}"> <fmt:message key="deal.RMB"/>

</td>
</tr>

 </table>
<p align="left">
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript: if(validateIrregularInvestmentChangeForm(document.irregularInvestmentChangeForm)) {document.irregularInvestmentChangeForm.submit();}"/>
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.irregularInvestmentChangeForm.reset();"/>
  <br>
 <input name="savingPlanId" id="savingPlanId" type="hidden" value="${sessionScope.IrregularInvestmentChangeForm.savingPlanId}" /> 
</form>
<html:javascript formName="irregularInvestmentChangeForm" />
</fmt:bundle>