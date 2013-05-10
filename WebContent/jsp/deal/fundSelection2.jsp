<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>


<ctcb:optionsSelect id="fundCode2" name="fundCode2" mapSource="session" mapName="<%=SessionKey.MAP_FUND2 %>" emptyOptionTitleKey="deal.select.fund.number" selectedKey="" />