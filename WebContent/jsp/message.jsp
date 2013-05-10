<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@page import="com.eds.ctcb.util.DataUtil"%>
<br>
<br>
<br>
<br>
<%
			String JUMP_URL = (String) (request.getAttribute("JUMP_URL"));
				if (DataUtil.isEmptyStr(JUMP_URL)) {
					String currentMenuLink = (String) (request.getSession()
					.getAttribute(SessionKey.CURRENT_MENU_LINK));
					if (DataUtil.isEmptyStr(currentMenuLink)) {
						JUMP_URL = "preLogin.do";
					} else {
						JUMP_URL = currentMenuLink;
					}
				}
			%> 
<fmt:bundle basename="ApplicationResources">
	<table  align="center" width="600" border="0" cellpadding="1"
		cellspacing="1" style="border: 1px solid #000000; " >
		<tr>
			<td align="center" bgcolor="#586868" height="25"><br>
			</td>
		</tr>
		<tr>
			<td align="center"><br>
			</td>
		</tr>
		<tr>
			<td align="center"><br>
			</td>
		</tr>
		<tr>
			<td align="center" style="font: 16" valign="middle"><html:messages
				id="success" message="true" property="MSG_KEY_SUCCESS">
				<img src="<%=request.getContextPath()%>/images/success.gif">&nbsp;&nbsp;
				<font style="color: #009999;font-weight: bold;"><bean:write name="success" /></font>
			</html:messages> <html:messages id="error" message="true" property="MSG_KEY_ERROR">
				<img src="<%=request.getContextPath()%>/images/validateError.gif">&nbsp;&nbsp;
				<font style="color: #990000;font-weight: bold;"><bean:write name="error" /></font>
			</html:messages></td>
		</tr>
		<tr>
			<td align="center"><br>
			</td>
		</tr>
		<tr>
			<td align="center"><img src="images/line.gif"></td>
		</tr>
		<tr>
			<td align="center">
			<ctcb:imgButton href="<%= JUMP_URL%>"
				imgSrc="images/button/ok.gif" id="ok"/></td>
		</tr>
		<tr>
			<td align="center"><br>
			</td>
		</tr>
	</table>

</fmt:bundle>
<<script type="text/javascript">
<!--
function   document.onkeydown()     
{     
    var   e=event.srcElement;    
        if(event.keyCode==13)     
        {     
            document.getElementById("ok").click();    
            return   false;     
        }
}
//-->
</script>
