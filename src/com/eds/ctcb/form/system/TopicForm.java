package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.eds.ctcb.form.BaseForm;

public class TopicForm extends BaseForm {
	
	 private Long id;
     private Integer year;
     private Integer quarter;
     private String topic;
     
	public TopicForm() {
		
	}

	public TopicForm(Integer year, Integer quarter, String topic) {
		this.year = year;
		this.quarter = quarter;
		this.topic = topic;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}


	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	public Integer getYear() {
		return year;
	}



	public void setYear(Integer year) {
		this.year = year;
	}


	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
