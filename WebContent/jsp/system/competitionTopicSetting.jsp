
<%@ taglib uri="/WEB-INF/tag/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tag/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/ctcb.tld" prefix="ctcb"%>
<fmt:bundle basename="ApplicationResources" >
<!-- table width="100%" border="0">
  <tr>
    <td><span class="STYLE1"><fmt:message key="sysParam.cometition.topicsetting"/></span></td>
  </tr>
</table>
<br /-->
<form name="topicForm" id="topicForm" action="setCompetitionTopic.do" method="post">
<table width="60%%" border="0">
<tr>
    <td width="20%"><fmt:message key="sysParam.cometition.year"/><ctcb:isRequried/></td>
    <td width="80%">
    <label>
        <ctcb:optionsSelect id="year"  name="year" mapSource="static" mapName="YearType" selectedKey="${sessionScope.TopicForm.year}" hasEmptyOption="true"/>
    </label>

    </td>
  </tr>

  <tr>
    <td width="20%"><fmt:message key="sysParam.cometition.quarter"/><ctcb:isRequried/></td>
    <td width="80%"><label>
   		
        	<ctcb:optionsSelect id="quarter" name="quarter" mapSource="session" mapName="QUARTER_MAP"  hasEmptyOption="true"/>
      	
    </label>
    </td>
  </tr>
 
  <tr>
    <td width="20%"><fmt:message key="sysParam.cometition.topic"/><ctcb:isRequried/></td>
    <td width="80%" onkeydown="if(event.keyCode == 13){submitForm();}">
    <label>
    	<ctcb:optionsSelect id="topic" name="topic" mapSource="session" mapName="TOPIC_MAP"  hasEmptyOption="true"/>
      	</label>
     </td>
  </tr>
  <tr>
    <td><p>&nbsp;</p>      </td>
    <td><label></label></td>
  </tr>
  
  <tr>
  
    <td>
    	<label>
    		<IMG SRC="images/commitSet.png" width="101" height="19" border="0" 
    			onClick="submitForm();" style=cursor:hand>
    	</label>
    </td>
    <td>
    	<label>
    		<IMG SRC="images/reset.png" width="101" height="19" border="0"
    		onClick="document.forms.topicForm.reset();" style=cursor:hand>
		</label>
    </td>
  </tr>
</table>
</form>

<ctcb:cascadeSelectScript selectBoxIds="year;quarter;topic" urls="getTopicQuarter.do;getTopic.do"/>

<html:javascript formName="topicForm"/>


<script type="text/javascript">

function submitForm(){
	if(validateTopicForm(document.getElementById('topicForm'))){

	document.getElementById('topicForm').submit();

	}
}
</script>

</fmt:bundle>