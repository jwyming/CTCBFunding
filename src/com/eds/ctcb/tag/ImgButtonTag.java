package com.eds.ctcb.tag;

import java.io.IOException;
import com.eds.ctcb.util.DataUtil;
import javax.servlet.jsp.JspException;
public class ImgButtonTag extends BaseTag {
  private static final long serialVersionUID = 1L;
  private String imgSrc;
  private String href;
  private String id;
  

  public int doStartTag() throws JspException {    
	StringBuffer sb = new StringBuffer();
	String idStr = "";
	if(!DataUtil.isEmptyStr(this.id)){
		idStr = "id=\""+this.id+"\"";
	}
	sb.append("<a "+idStr+" href=\""+this.href+"\">");
	sb.append("<img src=\""+this.imgSrc+"\" border=\"0\">");
	sb.append("</a>");	

    try {
      pageContext.getOut().print(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;

  }


public String getHref() {
	return href;
}


public void setHref(String href) {
	this.href = this.expressionHandle("href", href);
}


public String getImgSrc() {
	return imgSrc;
}


public void setImgSrc(String imgSrc) {
	this.imgSrc = this.expressionHandle("imgSrc", imgSrc);
}


public String getId() {
	return id;
}


public void setId(String id) {
	this.id = this.expressionHandle("id", id);
}








}
