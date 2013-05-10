<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<ctcb:optionsSelect id="topic" name="topic" mapSource="session" mapName="TOPIC_MAP" selectedKey="${sessionScope.id}" hasEmptyOption="true"/>

