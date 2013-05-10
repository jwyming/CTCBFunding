package com.eds.ctcb.bean;

import com.eds.ctcb.util.DataUtil;

public class BaseBean {
	public String toString(){
		return DataUtil.bean2String(this);
	}
}
