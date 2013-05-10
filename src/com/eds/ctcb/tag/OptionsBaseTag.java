package com.eds.ctcb.tag;

import java.util.HashMap;

import com.eds.ctcb.util.I18NUtil;
import com.eds.ctcb.util.OptionsUtil;

public abstract class OptionsBaseTag extends BaseTag{
	private static final long serialVersionUID = 1L;
	
	public static final String MAP_SOURCE_STATIC="STATIC";
	public static final String MAP_SOURCE_SESSION="SESSION";
	
	protected String mapSource;
	protected String mapName;
	protected String selectedKey;
	
	protected HashMap getOptionsMap(){
		HashMap map = null;
		if(this.mapSource != null && this.mapSource.toUpperCase().trim().equals(MAP_SOURCE_SESSION)){
			map = (HashMap)(this.getRequest().getSession().getAttribute(mapName));
		}else{			
			map = OptionsUtil.getOptionsMap(mapName, I18NUtil.getResourceBundle(this.getRequest()));
		}
		return map;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = this.expressionHandle("mapName", mapName);
	}

	public String getSelectedKey() {
		return selectedKey;
	}

	public void setSelectedKey(String selectedKey) {
		this.selectedKey = this.expressionHandle("selectedKey", selectedKey);
	}

	public String getMapSource() {
		return mapSource;
	}

	public void setMapSource(String mapSource) {
		this.mapSource = mapSource;
	}

}
