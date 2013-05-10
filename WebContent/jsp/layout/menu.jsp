<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tag/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tag/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tag/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>

<%@page import="java.util.List"%>
<%@page import="com.eds.ctcb.bean.MenuBean"%>
<%@page import="com.eds.ctcb.common.SessionKey"%>
<%@page import="com.eds.ctcb.bean.LoginBean"%>
<%@page import="com.eds.ctcb.constant.UserStatus"%>
<%
if(session.getAttribute(SessionKey.GLOBAL_LOGIN_INFO)!=null ){
LoginBean loginBean =(LoginBean) (session.getAttribute(SessionKey.GLOBAL_LOGIN_INFO));
if(
loginBean.getMenuList()!=null
){
			List<MenuBean> menuList = ((LoginBean) (session
			.getAttribute(SessionKey.GLOBAL_LOGIN_INFO))).getMenuList();
			
%>

<link href='css/menu.css' rel='stylesheet'
	type='text/css' />
<fmt:bundle basename="ApplicationResources">
	<script language='JavaScript' type='text/JavaScript'
		src='js/menu.js'></script>


<script language="javascript">
<%if(session.getAttribute("clickTab")!=null && String.valueOf(session.getAttribute("clickTab")).length()>0){%>
	var clickTab = ${sessionScope.clickTab};
<%}else{%>
	var clickTab = -1;
<%}%>

<%if(session.getAttribute("clickBar")!=null && String.valueOf(session.getAttribute("clickBar")).length()>0){%>
	var clickBar = ${sessionScope.clickBar};
<%}else{%>
	var clickBar = -1;
<%}%>

</script>
	<table id="menu" width="100%" border="0" cellspacing="0" cellpadding="0">
		<!-------------------------------------tab set start----------------------------------->
		<tr>
			<td height="32" background="images/menu/tabBg.gif"
				align="center" valign="baseline">

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10" height="32">&nbsp;</td>
					<td>

					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<%
							for (MenuBean menu : menuList) {
							%>

							<td onMouseOver="do_tab_on(<%=menu.getId() %>)"
								onMouseOut="reset_menu()">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="15"><img src="images/menu/tab_l_off.gif"
										id="tab_l_<%=menu.getId() %>" align="center" valign="middle" />
									</td>

									<td align="center" valign="middle" bordercolor="#000000"
										class="menu_off" id="tab_m_<%=menu.getId() %>"><a
										href="<%=menu.getHref() %>" class="menu_off_a"
										id="tab_a_<%=menu.getId() %>"> <%=menu.getTitle()%> </a></td>

									<td width="15"><img src="images/menu/tab_r_off.gif"
										id="tab_r_<%=menu.getId() %>" align="center" valign="center" /></td>
								</tr>
							</table>
							</td>
							<td width="5">&nbsp;</td>


							<%
							}
							%>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<!-------------------------------------tab set end----------------------------------->


		<!-------------------------------bar set start -------------------------------------->
		<tr style="margin-top:0">
			<td height="28" bgcolor="#dadada">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<%
			for (MenuBean menu : menuList) {
			%>
			
				<tr id="bar<%=menu.getId() %>" style="display:none;" onmouseover="do_tab_on(<%=menu.getId() %>)" onmouseout="reset_menu()">
				
					<td>&nbsp; <%
 			if (menu.getSubMenu() != null && menu.getSubMenu().size() > 0) {
 			for (MenuBean subMenu : menu.getSubMenu()) {
 %> <a href="<%=subMenu.getHref() %>" class="subMenu_off_a"
						id="bar_a_<%=menu.getId() %>_<%=subMenu.getId() %>"> <%=subMenu.getTitle()%></a>&nbsp;&nbsp;&nbsp;
					<%
							}
							}
					%>
					</td>
				</tr>
			
			<%
			}
			%>
			</table>
			</td>
		</tr>
		<!--------------------------------------bar set end-------------------------------------------->
	</table>
</fmt:bundle>

<script type="text/javascript">
<%
if(loginBean.getUserStatus() == UserStatus.INIT){
%>
document.getElementById('menu').style.display='none';
<%}%>
var menu_link;

if(clickTab!=-1){
	menu_link=document.getElementById('tab_a_'+clickTab);
}

if(clickBar!=-1){
	menu_link=document.getElementById('bar_a_'+clickTab+'_'+clickBar);
}


if(menu_link){
	menuTitle = menu_link.innerText;
}


</script>

<%}} %>
