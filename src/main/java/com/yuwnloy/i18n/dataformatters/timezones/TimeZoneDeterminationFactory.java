package com.yuwnloy.i18n.dataformatters.timezones;
/**
 * Used to get TimeZoneDetermination.
 * @author xiaoguang.gao
 *
 */
public class TimeZoneDeterminationFactory {
    private static TimeZoneDetermination tzDeter = null;
    /**
     * Return the TimeZoneDetermination.
     * @return
     */
	public static synchronized TimeZoneDetermination getTimeZoneDetermination(){
		if(tzDeter==null)
			tzDeter = new TimeZoneDetermination();
		return tzDeter;
	}
}
