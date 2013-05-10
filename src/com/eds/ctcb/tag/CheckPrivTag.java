package com.eds.ctcb.tag;

import javax.servlet.jsp.JspException;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.priv.PrivBiz;

public class CheckPrivTag extends BaseTag{
	private static final long serialVersionUID = 1L;
	private String resourceId;	
	private PrivBiz privBiz = BizFactory.getInstance().getPrivBiz();
	public int doStartTag() throws JspException {	

		if(privBiz.isAvaibleResource(this.getRequest(), this.resourceId)){
			return EVAL_BODY_INCLUDE;
		}else{
			return SKIP_BODY;
		}
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = this.expressionHandle("resourceId", resourceId);
	}
}
