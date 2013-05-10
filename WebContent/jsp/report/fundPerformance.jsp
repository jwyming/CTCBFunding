
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources">
   
    
	<form name="fundPerformanceForm" action="getFundPerformanceList.do" method="post">

	<table>
		<tr>
			<td> <fmt:message key="fundPerformance.jsp.label.fundType"/></td>
			<td><ctcb:optionsSelect name="fundType" mapSource="session"
				selectedKey="${fundPerformanceForm.fundType}" mapName="fundTypeMap" /></td>
			<td> <fmt:message key="fundPerformance.jsp.label.domain"/></td>
			<td><ctcb:optionsSelect name="fundArea" mapSource="session"
				selectedKey="${fundPerformanceForm.fundArea}" mapName="fundAreaMap" /></td>
			<td> <fmt:message key="fundPerformance.jsp.label.industry"/></td>
			<td><ctcb:optionsSelect name="fundIndustry" mapSource="session"
				selectedKey="${fundPerformanceForm.fundIndustry}"
				mapName="fundIndustryMap" /></td>

		</tr>
		<tr>
			<td><fmt:message key="fundPerformance.jsp.label.timeRank"/></td>
			<td><ctcb:optionsSelect name="timeRank" mapSource="session"
				selectedKey="${fundPerformanceForm.timeRank}" mapName="timeRankMap" /></td>
			<td> <fmt:message key="fundPerformance.jsp.label.order"/></td>
			<td><ctcb:optionsSelect name="indexRank" mapSource="session"
				selectedKey="${fundPerformanceForm.indexRank}"
				mapName="indexRankMap" /></td>
			<td><fmt:message key="fundPerformance.jsp.label.currency"/></td>
			<td><ctcb:optionsSelect name="currency" mapSource="session"
				selectedKey="${fundPerformanceForm.currency}" mapName="currencyMap" /></td>
			<td colspan="4">
			
			<ctcb:imgButton imgSrc="images/search.png" href="javascript:document.fundPerformanceForm.submit()"/>
			</td>

		</tr>
	</table>
	</form>


	<p><fmt:message key="fundPerformance.jsp.searchResult"/>
	<br>

	
	<br>
	 <c:choose>
		<c:when test="${resultSize==0}">
			
			<table align="center" width="100%">
				<tr align="center">
					<td bgcolor="#00655b" align="center" ><font color="white"><fmt:message key="fundPerformance.jsp.resultNotFound"/></font></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>		
			<display:table name="${fundPerformancePageBean}" uid="fund"
				requestURI="pagingFundPerformanceList.do">
				<display:column titleKey="report.rank" property="index"></display:column>
				<display:column titleKey="report.fund.name" property="fundName"></display:column>
				<display:column titleKey="report.fund.code" property="fundCode"></display:column>
				<display:column titleKey="report.fund.type" property="fundType"></display:column>
				<display:column titleKey="report.fund.company" property="fundCompany"></display:column>
				<display:column titleKey="report.fund.domain" property="fundArea"></display:column>
				<display:column titleKey="report.fund.industry" property="fundIndustry"></display:column>
				<display:column title="${startEquityDay}">
				<div style="text-align:right">	<ctcb:formatMoney value="${fund.oldEquity}" /></div>
				</display:column>
				<display:column title="${endEquityDay}" >
				<div style="text-align:right">	<ctcb:formatMoney value="${fund.todayEquity}" /></div>
				</display:column>
				<display:column titleKey="report.fund.currencyType" property="fundCurrency">				
				</display:column>
				<display:column titleKey="report.fund.performance">
				<div style="text-align:right"><ctcb:percentFormat value="${fund.performanceValue}" /></div>
				</display:column>
			</display:table>
		</c:otherwise>
	</c:choose>

</fmt:bundle>
