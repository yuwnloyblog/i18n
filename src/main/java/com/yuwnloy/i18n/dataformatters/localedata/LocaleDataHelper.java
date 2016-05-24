package com.yuwnloy.i18n.dataformatters.localedata;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import com.yuwnloy.i18n.dataformatters.resourcebundle.AbsResourceBundle;
/**
 * This class used to get the data stored in properties file 'LocaleResource'.
 * These properties file contained some i18n data used in this package. 
 * Such as One char for days, Person Name pattern, Special Date Time Pattern, Period Unit, and so on.
 * @author xiaoguang.gao
 *
 */
public class LocaleDataHelper{
    public static String WEEKDAY_NAME_1CHAR = "WEEKDAY_NAME_1_CHAR";
    private static final String BUNDLE_NAME = "com.oracle.cloud.view.i18n.localedata.LocaleResource";
    private static LocaleDataHelper helper = null;
    
    public LocaleDataHelper(){
        
    }
    public LocaleDataHelper(Locale locale){
        
    }
    /**
     * Get the instance.
     * @return
     */
    public static synchronized LocaleDataHelper getInstance(){
        if(helper==null){
            helper =  new LocaleDataHelper();
        }
        return helper;        
    }
    /**
     * Get the date time pattern. 
     * @param pName
     *     The name of date time pattern. @see DatePattern
     * @param locale
     *     Specify locale.
     * @return
     */
    public String getPattern(String pName, Locale locale) {
    	AbsResourceBundle rb = new LocaleDataResourceBundle(locale);
        return rb.getString(pName,true);    
    }
    /**
     * Get the one char for days.
     * @param cal
     *     Calendar.
     * @param locale
     *     Specify locale.
     * @return
     */
    public String getShortWeekDayName(Calendar cal,Locale locale){
    	AbsResourceBundle rb = new LocaleDataResourceBundle(locale);
        return rb.getString(getStringNameOfWeek(cal.get (Calendar.DAY_OF_WEEK)),true);
    }
    private String getStringNameOfWeek(int day){
        switch (day)
        {
        case 1:
           return "Sunday";
        
        case 2:
           return "Monday";
        
        case 3:
           return "Tuesday";
        
        case 4:
           return "Wednesday";
        
        case 5:
           return "Thursday";
        
        case 6:
           return "Friday";
        
        case 7:
           return "Saturday";
        }
        
        return null;
    }
    /**
     * Get person name pattern of long display.
     * @param locale
     * @return
     */
    public String getLongDisplayFormat(Locale locale){
    	AbsResourceBundle rb = new LocaleDataResourceBundle(locale);
    	return rb.getString("DisplayNameLongFormat",true);
    }
    /**
     * Get person name pattern of short display.
     * @param locale
     * @return
     */
    public String getShortDisplayFormat(Locale locale){
    	AbsResourceBundle rb = new LocaleDataResourceBundle(locale);
    	return rb.getString("DisplayNameLongFormat",true);
    }
    /**
     * Get the data based key from <code>LocaleResource</code> file. Will use LocaleDetermination to specify the locale.
     * @param key
     * @return
     */
    public String getString(String key){
        AbsResourceBundle rb = new LocaleDataResourceBundle();
        return rb.getString(key,true);
    }
    /**
     * Get the data based key from <code>LocaleResource</code> file.
     * @param key
     * @param locale
     * @return
     */
    public String getString(String key, Locale locale){
        AbsResourceBundle rb = new LocaleDataResourceBundle(locale);
        return rb.getString(key,true);
    }
}

