package com.yuwnloy.i18n.resourcebundles.datagetter;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class MapMetaData {
	private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	private Locale locale = null;
	
	public MapMetaData(ConcurrentHashMap<String, Object> map, Locale locale) {
		super();
		this.map = map;
		this.locale = locale;
	}
	public ConcurrentHashMap<String, Object> getMap() {
		return map;
	}
	public Locale getLocale() {
		return locale;
	}
	
}
