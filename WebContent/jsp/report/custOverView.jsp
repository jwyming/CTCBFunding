
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >


<%-- 1.reachperfect fund--%>
<c:if test="${not empty sessionScope.fundCodeList}">
<font color="red">

<fmt:message key="custOverView.jsp.label.reachPerfectFund"/>
<c:forEach items="${sessionScope.fundCodeList}" var="fundCode">
<${fundCode}>
</c:forEach>
</font>
</c:if>

<p>

<table width="100%">
<tr>

<td  align="left" width="30%">
<%-- 3.customer overview--%>
<c:if test="${not empty sessionScope.custOverViewList}">

<fmt:message key="custOverView.jsp.custOverView"/>
<table  width="80%" class="tableBorder" >
<tr class="th" align="center"><td><fmt:message key="custOverView.jsp.items"/></td><td><fmt:message key="custOverView.jsp.balance"/></td>
</tr>
<tr class="tabletr"><td align="center"><fmt:message key="custOverView.jsp.cash"/></td><td><div style="text-align:right">	<ctcb:formatMoney value="${sessionScope.custOverViewList[0]}"/></div></td>
</tr>
<tr class="tabletr"><td align="center"><fmt:message key="custOverView.jsp.investmentAsset"/></td><td><div style="text-align:right">	<ctcb:formatMoney value="${sessionScope.custOverViewList[1]}"/></div></td>
</tr>
<tr class="tabletr" ><td align="center"><font color="red" size="3"><fmt:message key="custOverView.jsp.total"/></font></td><td><div style="text-align:right"><font color="red" size="3"><ctcb:formatMoney value="${sessionScope.custOverViewList[2]}"/></font></div></td>
</tr>
</table>

</c:if>
</td>
<td width="10%">
<td width="60%">

<%-- 2.customer's investment order--%>
<a href="menu_17.do">
<fmt:message key="custOverView.jsp.performanceOrder.pre"/>
<c:out value="${sessionScope.userOrder}"/>
<fmt:message key="custOverView.jsp.performanceOrder.last"/></a>
</td>
</tr>
</table>
<p>
<%-- 4.investment list--%>
<c:if test="${not empty sessionScope.custInvestmentList}">

<fmt:message key="custOverView.jsp.custInvestmentOverView"/>
<br>
<display:table name="${sessionScope.custInvestmentList}" uid="custInvestmentBean">
<display:column titleKey="report.dFund.code" property="fundCode"></display:column>
<display:column titleKey="report.dFund.name" property="fundName"></display:column>
<display:column titleKey="report.currencyType.investmentAndCompute">

<c:out value="${custInvestmentBean.investmentCurrency}"></c:out>
<br>
<c:out value="${custInvestmentBean.cashCurrency}"></c:out>

</display:column>
<display:column titleKey="report.totalAmount">
<ctcb:formatMoney value="${custInvestmentBean.totalCount}" />
</display:column>

<display:column titleKey="report.massExchangeRate.massEquity" >
<div style="text-align:right">	
<ctcb:formatMoney value="${custInvestmentBean.sellingEquity}"/>
<br>

<ctcb:formatMoney value="${custInvestmentBean.sellingRate}"/>
</div>
</display:column>
<display:column titleKey="report.total.investment.baseAsset">
<div style="text-align:right">	
<ctcb:formatMoney value="${custInvestmentBean.totalInitCash}"/>
<br>

<ctcb:formatMoney value="${custInvestmentBean.totalFundValue}"/>
</div>
</display:column>
<display:column titleKey="report.massIncoming.massIntrestRate" >
<div style="text-align:right">	
<ctcb:formatMoney value="${custInvestmentBean.currentComingSet}"/>
<br>

<ctcb:percentFormat value="${custInvestmentBean.totalProfit}"/>
</div>

</display:column>

</display:table>
</c:if>
<p>

<%-- 5.not executed investments --%>

  <c:if test="${not empty sessionScope.custPreInvestmentPageBean}">
  <fmt:message key="custOverView.jsp.preExcuteTrade"></fmt:message>
  <display:table name="${custPreInvestmentPageBean}" uid="trade1"
		requestURI="pagingPreInvestmentList.do" >
		<display:column titleKey="trade.id" property="id"></display:column>
<display:column titleKey="trade.type" >
<ctcb:optionsLabel mapName="tradeType" selectedKey="${trade1.type}" mapSource="static"/>
</display:column>
<display:column titleKey="trade.accountBySaccountId.id" property="accountBySaccountId"></display:column>
<display:column titleKey="trade.accountByDaccountId.id" property="accountByDaccountId"></display:column>
<display:column titleKey="trade.tradeMode" >
<ctcb:optionsLabel mapName="tradeMode" selectedKey="${trade1.tradeMode}" mapSource="static"/>
</display:column>
<display:column titleKey="trade.count">
<ctcb:formatMoney value="${trade1.count}"/>
</display:column>
<display:column titleKey="trade.createTime" >
<ctcb:formatDate value="${trade1.createTime}"/>
</display:column>
<display:column titleKey="trade.setTime" >
<ctcb:formatDate value="${trade1.setTime}"/>
</display:column>

	  </display:table>
	
    </c:if>

</fmt:bundle>
