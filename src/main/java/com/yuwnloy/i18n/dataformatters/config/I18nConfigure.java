package com.yuwnloy.i18n.dataformatters.config;

import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import com.yuwnloy.i18n.dataformatters.locales.LocaleDeterminationFactory;
import com.yuwnloy.i18n.dataformatters.timezones.TimeZoneDeterminationFactory;
/**
 * This class used to provide the necessary data for cloud i18n package. 
 * I18n package's Locale/Timezone determination mechanism depends on this configure class to execute. 
 * This determination logic only provide an default fallback order for fetching locale/timezone.
 * 
 * @author xiaoguang.gao@oracle.com
 *
 */
public abstract class I18nConfigure {
    //get default data
	/**
	 * Return the default view locale
	 * @return
	 */
    public abstract Locale getDefaultViewLocale();
    /**
     * Return the default format locale
     * @return
     */
    public abstract Locale getDefaultFormatLocale();
    /**
     * Return the default Timezone
     * @return
     */
    public abstract TimeZone getDefaultTimeZone();
    //get preference data
    /**
     * get the preference view locale of user
     * @return
     */
    public abstract Locale getPreferenceViewLocale();
    /**
     * get the preference format locale of user
     * @return
     */
    public abstract Locale getPreferenceFormatLocale();
    /**
     * get the preference timezone of user
     * @return
     */
    public abstract TimeZone getPreferenceTimeZone();
    /**
     * Return the supported locales list.
     * I18n package only provide translated string for these locales.
     * @return
     */
    public abstract Iterator<Locale> getSupportedViewLocales();
    /**
     * Return accept languages list from http request header 'Accept-Language'.
     * Usually, this list is sorted based priority. The first locale of this list have the highest priority.
     * 
     * @return
     */
    public abstract Iterator<Locale> getAcceptLanguages();

    //set View Locale getter priority
    /**
     * According to the priority to get the view locale.  The default priority is :
     * Preference view locale > accept language locale > default view locale.
     * 
     * It can custom this priority by override this method. For example:
     * <code>
     * public GetterSeries<Locale> getViewLocaleSeries(){
     *    GetterSeries<Locale> ser = new GetterSeries<Locale>();
     *    ser.clearGetterList();
     *    //first priority
     *    ser.addGetter(new GetterSeries.Getter<Locale>(){
     *    	 public Locale getResult(){
     *          // add your logic to get locale.
     *       }
     *    });
     *    //second priority
     *    ser.addGetter(new GetterSeries.Getter<Locale>(){
     *    	 public Locale getResult(){
     *          // add your logic to get locale.
     *       }
     *    });
     * }
     * </code>
     * 
     * @return
     */
    public GetterSeries<Locale> getViewLocaleSeries() {
        GetterSeries<Locale> ser = new GetterSeries<Locale>();
        ser.clearGetterList();
        //Priority 1 : Preference View Locale
        ser.addGetter(new GetterSeries.Getter<Locale>() {
                @Override
                public Locale getResult() {
                    return getPreferenceViewLocale();
                }

            });
        //Priority 2 : AcceptLanguage Locale
        ser.addGetter(new GetterSeries.Getter<Locale>() {
                @Override
                public Locale getResult() {
                    return LocaleDeterminationFactory.getViewLocaleDetermination().getLocale(getAcceptLanguages());
                }
            });
        //Priority 3 : Default View Locale
        ser.addGetter(new GetterSeries.Getter<Locale>() {
                @Override
                public Locale getResult() {
                    return LocaleDeterminationFactory.getViewLocaleDetermination().getDefaultLocale();
                }
            });
        return ser;
    }
    /**
     * According to the priority to get the format locale.  The default priority is :
     * Preference view locale > accept language locale > default view locale.
     * 
     * It can custom this priority by override this method. For example:
     * <code>
     * public GetterSeries<Locale> getFormatLocaleSeries(){
     *    GetterSeries<Locale> ser = new GetterSeries<Locale>();
     *    ser.clearGetterList();
     *    //first priority
     *    ser.addGetter(new GetterSeries.Getter<Locale>(){
     *    	 public Locale getResult(){
     *          // add your logic to get locale.
     *       }
     *    });
     *    //second priority
     *    ser.addGetter(new GetterSeries.Getter<Locale>(){
     *    	 public Locale getResult(){
     *          // add your logic to get locale.
     *       }
     *    });
     * }
     * </code>
     * 
     * @return
     */
    public GetterSeries<Locale> getFormatLocaleSeries() {
        GetterSeries<Locale> ser = new GetterSeries<Locale>();
        ser.clearGetterList();
        //Priority 1 : Preference Format Locale
        ser.addGetter(new GetterSeries.Getter<Locale>() {
                @Override
                public Locale getResult() {
                    return getPreferenceFormatLocale();
                }

            });
        //Priority 2 : AcceptLanguage Locale
        ser.addGetter(new GetterSeries.Getter<Locale>() {
                @Override
                public Locale getResult() {
                    Iterator<Locale> acctLocales = getAcceptLanguages();
                    if (acctLocales != null && acctLocales.hasNext()) {
                        return acctLocales.next();
                    }
                    return null;
                }
            });
        //Priority 3 : Default Format Locale
        ser.addGetter(new GetterSeries.Getter<Locale>() {
                @Override
                public Locale getResult() {
                    return LocaleDeterminationFactory.getFormattingLocaleDetermination().getDefaultLocale();
                }
            });
        return ser;
    }
    /**
     * According to the priority to get the time zone.  The default priority is :
     * Preference time zone > default view locale.
     * 
     * It can custom this priority by override this method. For example:
     * <code>
     * public GetterSeries<TimeZone> getTimeZoneSeries(){
     *    GetterSeries<TimeZone> ser = new GetterSeries<TimeZone>();
     *    ser.clearGetterList();
     *    //first priority
     *    ser.addGetter(new GetterSeries.Getter<TimeZone>(){
     *    	 public TimeZone getResult(){
     *          // add your logic to get time zone.
     *       }
     *    });
     *    //second priority
     *    ser.addGetter(new GetterSeries.Getter<TimeZone>(){
     *    	 public TimeZone getResult(){
     *          // add your logic to get time zone.
     *       }
     *    });
     * }
     * </code>
     * 
     * @return
     */
    public GetterSeries<TimeZone> getTimeZoneSeries() {
        GetterSeries<TimeZone> ser = new GetterSeries<TimeZone>();
        ser.clearGetterList();
        //Priority 1 : Preference TimeZone
        ser.addGetter(new GetterSeries.Getter<TimeZone>() {
                @Override
                public TimeZone getResult() {
                    return getPreferenceTimeZone();
                }
            });
        //Priority 2 : Default TimeZone
        ser.addGetter(new GetterSeries.Getter<TimeZone>() {
                @Override
                public TimeZone getResult() {
                    return TimeZoneDeterminationFactory.getTimeZoneDetermination().getDefaultTimeZone();
                }
            });
        return ser;
    }
}

