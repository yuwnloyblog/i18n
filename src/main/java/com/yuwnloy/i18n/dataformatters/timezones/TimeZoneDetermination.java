package com.yuwnloy.i18n.dataformatters.timezones;

import java.util.TimeZone;

import com.yuwnloy.i18n.dataformatters.I18nManageFactory;
import com.yuwnloy.i18n.dataformatters.config.GetterSeries;
import com.yuwnloy.i18n.dataformatters.config.I18nConfigure;
/**
 * TimeZoneDetermination provides implementation for time zone determination.
 * @author xiaoguang.gao
 *
 */
public class TimeZoneDetermination {
protected static final TimeZone FALLBACK_TIMEZONE = TimeZone.getTimeZone ("UTC");
	public TimeZoneDetermination(){}
	/**
	 * Return the default time zone specified in <code>I18nConfigure.getDefaultTimeZone</code>. If the method 
     * <code>I18nConfigure.getDefaultTimeZone</code> return null, default time zone will be "UTC" 
	 * @return
	 */
	public TimeZone getDefaultTimeZone(){
		I18nConfigure configure = I18nManageFactory.getInstance().getI18nConfigure();
		TimeZone retTz = configure.getDefaultTimeZone();
		if(retTz != null)
			return retTz;
		return FALLBACK_TIMEZONE;
	}
	/**
	 * Determine the time zone based on the sequence defined in 
     * <code>I18nConfigure.getTimeZoneSeries.</code>
     * 
	 * @return
	 */
	public TimeZone getTimeZone(){
		I18nConfigure configure = I18nManageFactory.getInstance().getI18nConfigure();
		GetterSeries<TimeZone> ser = configure.getTimeZoneSeries();
		TimeZone retTz = ser.getFinalResult();
		if(retTz != null)
			return retTz;
		return FALLBACK_TIMEZONE;
	}    
}

