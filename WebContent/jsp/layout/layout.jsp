<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>

<link href="<%=request.getContextPath()%>/css/displaytag.css" rel="stylesheet" type="text/css"/>
<link href='<%=request.getContextPath()%>/css/style.css' rel='stylesheet' type='text/css' />
<html>
<head>
	<title>
		<fmt:bundle basename="ApplicationResources" >
		<fmt:message key="common.system.title"/>
		</fmt:bundle>
	</title>
</head>
<body>
<script type="text/javascript">
var menuTitle;
</script>
    <table width="100%" height="100%" cellspacing="0">  
         <tr height="1"> <td valign="top"> <tiles:insert attribute="header"/> </td> </tr> 
         <tr height="1"> <td valign="top"> <tiles:insert attribute="menu"/> </td> </tr> 
         <tr height="1"> <td valign="top"> <tiles:insert attribute="message"/> </td> </tr> 
         <tr> <td valign="top" height="*"> <tiles:insert attribute="content"/> </td></tr> 
       <tr> <td align="center" valign="bottom" height="15%"><tiles:insert attribute="footer"/></td></tr> 
    </table>
</body>
</html>