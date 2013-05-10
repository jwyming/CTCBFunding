<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>

<fmt:bundle basename="ApplicationResources">




	<form name="investmentOrderForm" action="getCustInvestmentList.do"
		onSubmit="return validateInvestmentOrderForm(this);" method="post">
		

	<table>
		<tr>
			<td><fmt:message key="custInvestment.jsp.form.userNikeName"/></td>
			<td><input name="userNickName" type="text"
				value="<c:out value="${sessionScope.investmentOrderForm.userNickName}"/>" /></td>
			<td><fmt:message key="custInvestment.jsp.form.orderArrange"/><input name="beginOrder" type="text"
				value="<c:out value="${sessionScope.investmentOrderForm.beginOrder}"/>" />
			~<input name="endOrder" type="text"
				value="<c:out value="${sessionScope.investmentOrderForm.endOrder}"/>" /></td>

			<td colspan="4">	
			
			<ctcb:imgButton imgSrc="images/search.png" href="javascript: if(validateInvestmentOrderForm(document.investmentOrderForm)) {document.investmentOrderForm.submit();}"/>
			</td>
		</tr>

	</table>
	<p><fmt:message key="custInvestment.jsp.custInvestmentOrder"/><br>
	<c:choose>
		<c:when test="${resultSize==0}">

			<table align="center" width="100%">
				<tr align="center">
					<td bgcolor="#00655b" align="center"><font color="white"><fmt:message key="custInvestment.jsp.resultNotFound"/></font></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<display:table name="${custInvestmentListPageBean}" uid="investment"
				requestURI="pagingCustInvestmentList.do">

				<display:column titleKey="report.rank" property="order"></display:column>
				<display:column titleKey="report.userNickName" property="userNickName"></display:column>
				<display:column titleKey="report.initBaseAsset">
				<div style="text-align:right">	<ctcb:formatMoney value="${investment.cashAsset}" /></div>
				</display:column>
				<display:column titleKey="report.investmentAmount">
				<div style="text-align:right">	<ctcb:formatMoney value="${investment.investmentAsset}" /></div>
				</display:column>
				<display:column titleKey="report.currentInvestmentAmount">
				<div style="text-align:right">	<ctcb:formatMoney value="${investment.currentInvestmentAsset}" /></div>
				</display:column>
				<display:column titleKey="report.customer.totalAsset">
				<div style="text-align:right">	<ctcb:formatMoney value="${investment.totalAsset}" /></div>
				</display:column>

			</display:table>
		</c:otherwise>
	</c:choose> <html:javascript formName="investmentOrderForm" />	
	</form>
	<table>
		<tr>
			<td><%-- produce links--%>
			 <c:if test="${not empty sessionScope.reportInfoList}">

				<c:forEach items="${sessionScope.reportInfoList}" var="reportInfo">
				    <td width="5%"></td>
					<td>
					<a href="getReportDataList.do?param=<c:out value='${reportInfo.id}'/>">
						<c:out
						value='${reportInfo.year}' />-<c:out value='${reportInfo.quarter}'/><ctcb:optionsLabel
						mapName='CompetitionTopicType' selectedKey='${reportInfo.topic}'
						mapSource='static' />
						</a></td>
						
						
					
				</c:forEach>
			</c:if>
		</tr>
	</table>
	<p>
	
     <c:if test="${not empty sessionScope.reportPageBean}">
     <table>

     <tr><td>
  <c:out value="${sessionScope.title}"/> 
  </td>
     </tr>

     <tr>	
	 <display:table name="${reportPageBean}" uid="reportData"
		requestURI="pagingReportDataList.do" >
		<display:column titleKey="report.rank" property="rank"></display:column>
		<display:column titleKey="report.userNickName" property="user.userName"></display:column>
		<display:column titleKey="report.quarter.startAsset">
		<div style="text-align:right">	<ctcb:formatMoney value="${reportData.startEquity}" /></div>
		</display:column>
		<display:column titleKey="report.quarter.endAsset" >
		<div style="text-align:right">	<ctcb:formatMoney value="${reportData.endEquity}" /></div>
		</display:column>
		<display:column titleKey="report.incoming" >
		<div style="text-align:right">	<ctcb:formatMoney value="${reportData.incoming}" /></div>
		</display:column>
		<display:column titleKey="report.intrestRate" >
		<div style="text-align:right"><ctcb:percentFormat value="${reportData.incomingRate}"></ctcb:percentFormat></div>
		</display:column>
	  </display:table>
	  </tr>
	  </table>
    </c:if>

</fmt:bundle>
