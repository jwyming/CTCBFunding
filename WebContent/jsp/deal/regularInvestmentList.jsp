<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<display:table name="regularInvestmentPageBean" uid="regularInvestment" requestURI="getRegularInvestmentList.do"  >
<display:column titleKey="regularInvestmentChange.tableTitle.fundCode" sortable="true" property="fund.code">
    <c:out value="${regularInvestment.fund.code}"/>
</display:column>
<display:column titleKey="regularInvestmentChange.tableTitle.fundName" sortable="true" property="fund.name">
    <c:out value="${regularInvestment.fund.name}"/>
</display:column>
<display:column titleKey="regularInvestmentChange.tableTitle.investmentDay" property="day" sortable="true"></display:column>
<display:column titleKey="regularInvestmentChange.tableTitle.investmentAmount" sortable="true" sortProperty="count">
<div style="text-align:right"><ctcb:formatMoney value="${regularInvestment.count}"/></div>
</display:column>
<display:column titleKey="irregularInvestmentChange.tableTitle.createTime" sortable="true" property="createTime">
<c:out value="${regularInvestment.createTime}"/>
</display:column>
<display:column titleKey="common.status" sortable="true"  sortProperty="status">
    <c:choose>
        <c:when test="${regularInvestment.status==valid}">
            <fmt:message key="common.status.valid"/>
        </c:when>
        <c:when test="${regularInvestment.status==invalid}">
            <font color="#ff0000" > <fmt:message key="common.status.invalid"/> </font>
        </c:when>
    </c:choose>
</display:column>
<display:column titleKey="common.operate.operate">
    <a href="preRegularInvestmentChange.do?savingPlanId=<c:out value='${regularInvestment.id}'/>">
        <fmt:message key="common.operate.modify"/>
    </a>
|
    <c:choose>
        <c:when test="${regularInvestment.status==invalid}">
            <a href="javascript: resume(<c:out value='${regularInvestment.id}'/>);">
                <fmt:message key="common.operate.resume"/>
            </a>
        </c:when>
        <c:when test="${regularInvestment.status==valid}">
            <a href="javascript: stop(<c:out value='${regularInvestment.id}'/>);">
                <fmt:message key="common.operate.stop"/>
           </a>
        </c:when>
    </c:choose>
</display:column>
</display:table>
<script type="text/javascript">
    function resume(id) {
        if(confirm("<fmt:message key="deal.confirm.resume"/>")) {
            location.href="reIrregularInvest.do?savingPlanId=" + id;
        }
    }
    function stop(id) {
        if(confirm("<fmt:message key="deal.confirm.stop"/>")) {
            location.href="stopIrregularInvest.do?savingPlanId=" + id;
        }
    }
</script>
</fmt:bundle>