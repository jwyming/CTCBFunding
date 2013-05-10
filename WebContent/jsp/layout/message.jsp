<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>

<html:messages id="success" message="true" property="MSG_KEY_SUCCESS">
	<table align="center" width="100%" border="0" cellpadding="1"
		cellspacing="1" style="border: 2px solid #009999 ">
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<img src="<%=request.getContextPath()%>/images/success.gif">
					</td>
					<td valign="middle" style="color: #009999">
					&nbsp;&nbsp;<bean:write name="success" />
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>	
</html:messages>
<html:messages id="error" message="true" property="MSG_KEY_ERROR">
	<table align="center" width="100%" border="0" cellpadding="1"
		cellspacing="1" style="border: 2px solid #990000 ">
		<tr>
			<td>
			<table>
				<tr>
					<td>
					<img src="<%=request.getContextPath()%>/images/validateError.gif">
					</td>
					<td valign="middle" style="color: #990000">
					&nbsp;&nbsp;<bean:write name="error" />
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:messages>

<html:errors />
<br>
<h3>&nbsp;
<script type="text/javascript">
if(menuTitle){
	document.write(menuTitle);
}
</script>
</h3>
<br>