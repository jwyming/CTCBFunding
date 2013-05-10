<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>


<ctcb:optionsSelect id="inFundCode" name="inFundCode" mapSource="session" mapName="<%=SessionKey.MAP_FUND3 %>" emptyOptionTitleKey="deal.select.inFund" selectedKey="${sessionScope.changeFundForm.inFundCode}" />
