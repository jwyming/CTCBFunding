<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>    
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<fmt:bundle basename="ApplicationResources">
<form id="regularInvestmentChangeForm" name="regularInvestmentChangeForm" method="post" action="regularInvestmentChange.do">
<table width="100%" border="0">
<tr>
  	<td width="10%"><fmt:message key="regularInvestmentChange.tableTitle.fundName"/></td>
	<td width="90%"><span id="fundName" name="fundName"><c:out value="${sessionScope.RegularInvestmentChangeForm.fundName}"/></span>
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
  </select>
<fmt:message key="deal.date"/></td>
</tr>
  
<tr>
  <td width="10%"><fmt:message key="deal.monthlyAmount"></fmt:message><ctcb:isRequried/></td>
  <td width="90%">
    <input type="text" id="investmentAmount" name="investmentAmount" value="${sessionScope.RegularInvestmentChangeForm.investmentAmount}" size="20" maxlength="20" onkeydown="if(event.keyCode == 13){javascript: if(validateRegularInvestmentChangeForm(document.regularInvestmentChangeForm)) {document.regularInvestmentChangeForm.submit();}}"/>
<fmt:message key="deal.RMB"/></td>
</tr>



 </table>
<p align="left">
  <ctcb:imgButton imgSrc="images/commitTrans.png" href="javascript: if(validateRegularInvestmentChangeForm(document.regularInvestmentChangeForm)) {document.regularInvestmentChangeForm.submit();}"/>
  <ctcb:imgButton imgSrc="images/reset.png" href="javascript: document.regularInvestmentChangeForm.reset();"/>
  <br>
 <input name="savingPlanId" id="savingPlanId" type="hidden" value="${sessionScope.RegularInvestmentChangeForm.savingPlanId}" /> 
</form>
<html:javascript formName="regularInvestmentChangeForm" />
</fmt:bundle>