package com.yuwnloy.i18n.dataformatters.formatter;
import java.text.DateFormat;
//import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Locale;
import java.util.Date;

import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Used to formatter data time.
 * 
 * @author xiaoguang.gao
 *
 */
public class DateFormatter  implements IFormatter{
    private static Logger logger = Logger.getLogger(DateFormatter.class.getName());
    private TimeZone timeZone = null;
    private Locale locale = null;
    private DateTimeStyle dateStyle = null;
    private DateTimeStyle timeStyle = null;
    //private Calendar calendar;
    private String pattern = null;

    public enum DateTimeStyle{
        Default(DateFormat.DEFAULT),
        Full(DateFormat.FULL),
        Long(DateFormat.LONG),
        Medium(DateFormat.MEDIUM),
        Short(DateFormat.SHORT);
        
        private int dateTimeStyle = DateFormat.DEFAULT;
        private DateTimeStyle(int dateTimeStyle){
            this.dateTimeStyle = dateTimeStyle;
        }
        public int getDateTimeStyle(){
            return this.dateTimeStyle;
        }
    }
    /**
     * 
     * @param dateStyle
     * 	   The style of date part. Scope: Default, Full, Long, Medium, Short.
     * @param timeStyle
     * 	   The style of time part. Scope: Default, Full, Long, Medium, Short.
     * @param timeZone
     *     Use this time zone to formatter date time.
     * @param locale
     *     Use this locale to formatter date time.
     */
    private DateFormatter(DateTimeStyle dateStyle,DateTimeStyle timeStyle,TimeZone timeZone,Locale locale){
        super();
        this.timeZone = timeZone;
        this.locale = locale;        
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        if(this.dateStyle==null && this.timeStyle == null){
            this.dateStyle=DateTimeStyle.Default; 
        }
    }
    /**
     * 
     * @param pattern
     *     Custom the pattern to format date time. Such as <code>'yyyy-MM-dd hh:mm:ss'.</code>
     * @param timeZone
     *     Use this time zone to formatter date time.
     * @param locale
     *     Use this locale to formatter date time.
     */
    private DateFormatter(String pattern,TimeZone timeZone,Locale locale) {
        super();
        this.timeZone = timeZone;
        this.locale = locale;
        this.pattern = pattern;    
    }
    
    /**
     *
     * @param pattern
     *     @see DatePattern
     *     I18n package define more than 20 date patterns. Caller can reference them directly. 
     * @param timeZone
     *     Use this time zone to formatter date time.
     * @param locale
     *     Use this locale to formatter date time.
     * @return 
     */
    public static DateFormatter getInstance(DatePattern pattern, TimeZone timeZone, Locale locale){
       if(pattern == null) {
          pattern = DatePattern.DefaultPattern;
       }
       String strPattern = pattern.getPattern(locale);
       return getInstance(strPattern, timeZone, locale);
    }
    
    /**
     * Get the default date formatter instance.
     * Use default date style, default time style, jvm default timezone and jvm default locale.
     * @return
     */
    public static DateFormatter getInstance(){
        return new DateFormatter(DateTimeStyle.Default,DateTimeStyle.Default,TimeZone.getDefault(),Locale.getDefault());
    }
    /**
     * Use jvm default timezone and jvm default locale to get instance.
     * 
     * @param dateStyle
     * 	   The style of date part. Scope: Default, Full, Long, Medium, Short.
     * @param timeStyle
     *     The style of time part. Scope: Default, Full, Long, Medium, Short.
     * @return
     */
    public static DateFormatter getInstance(DateTimeStyle dateStyle,DateTimeStyle timeStyle){
        return new DateFormatter(dateStyle,timeStyle,TimeZone.getDefault(),Locale.getDefault());
    }
    /**
     *
     * @param dateStyle
     *     The style of date part. Scope: Default, Full, Long, Medium, Short.
     * @param timeStyle
     *     The style of time part. Scope: Default, Full, Long, Medium, Short.
     * @param timeZone
     *     Use this time zone to formatter date time.
     * @param locale
     *     Use this locale to formatter date time.
     * @return
     */
    public static DateFormatter getInstance(DateTimeStyle dateStyle,DateTimeStyle timeStyle,TimeZone timeZone,Locale locale){
        return new DateFormatter(dateStyle,timeStyle,timeZone,locale);
    }
    /**
     *
     * @param timeZone
     *     Use this time zone to formatter date time.
     * @param locale
     *     Use this locale to formatter date time.
     * @return
     */
    public static DateFormatter getInstance(TimeZone timeZone,Locale locale){
        return new DateFormatter(null,null,timeZone,locale);
    }
    /**
     *
     * @param pattern
     *     Specify the pattern to format date time. Such as <code>'yyyy-MM-dd hh:mm:ss'.</code>
     * @param timeZone
     * @param locale
     * @return
     */
    public static DateFormatter getInstance(String pattern,TimeZone timeZone,Locale locale){
        return new DateFormatter(pattern,timeZone,locale);
    }
    /**
     * This instance only format date part and omit time part. For example: 2015-2-10
     * @return
     */
    public static DateFormatter getDateInstance(){
        return new DateFormatter(DateTimeStyle.Default,null,TimeZone.getDefault(),Locale.getDefault());
    }
    /**
     * This instance only format date part and omit time part. For example: 2015-2-10
     * 
     * @param dateStyle
     *     The style of date part. Scope: Default, Full, Long, Medium, Short.
     * @return
     */
    public static DateFormatter getDateInstance(DateTimeStyle dateStyle){
        return new DateFormatter(dateStyle,null,TimeZone.getDefault(),Locale.getDefault());
    }
    /**
     * This instance only format date part and omit time part. For example: 2015-2-10
     * 
     * @param dateStyle
     *     The style of date part. Scope: Default, Full, Long, Medium, Short.
     * @param timeZone
     *      Use this time zone to formatter date time.
     * @param locale
     *      Use this locale to formatter date time.
     * @return
     */
    public static DateFormatter getDateInstance(DateTimeStyle dateStyle,TimeZone timeZone,Locale locale){
        return new DateFormatter(dateStyle,null,timeZone,locale);
    }
    /**
     * This instance only format time part and omit date part. For example: 17:03:12
     * @return
     */
    public static DateFormatter getTimeInstance(){
        return new DateFormatter(null,DateTimeStyle.Default,TimeZone.getDefault(),Locale.getDefault());
    }
    /**
     * This instance only format time part and omit date part. For example: 17:03:12
     * @param timeStyle
     *     The style of time part. Scope: Default, Full, Long, Medium, Short.
     * @return
     */
    public static DateFormatter getTimeInstance(DateTimeStyle timeStyle){
        return new DateFormatter(null,DateTimeStyle.Default,TimeZone.getDefault(),Locale.getDefault());
    }
    /**
     * This instance only format time part and omit date part. For example: 17:03:12
     * @param timeStyle
     * @param timeZone
     * @param locale
     * @return
     */
    public static DateFormatter getTimeInstance(DateTimeStyle timeStyle,TimeZone timeZone,Locale locale){
        return new DateFormatter(null,timeStyle,timeZone,locale);
    }    
    
    /**
     * Formats the given <code>Date</code> object into a string.
     * @param date
     * @return a date time string
     */
    public String format(Date date){
        String ret = this.getDateFormat().format(date);
        return ret;
    }
    /**
     * Formats the given <code>Date</code> object into a string.
     * @param obj
     * @return a date time string 
     */
    public String format(Object obj) {
        String ret = this.getDateFormat().format((Date)obj);
        return ret;
    }
    /**
     * Parse an given date time string into a <code>Date</code> object.
     * @param source
     * @return a Date object
     */
    public Date parse(String source){
        Date date = null;
        try {
            date = this.getDateFormat().parse(source);
        } catch (ParseException e) {
            logger.severe("Encounter Error when parse '"+source+"'");
        }
        return date;
    }
    /**
     * get the dateFormat
     * @return
     */
    private DateFormat getDateFormat(){
        DateFormat formatter = null;
        
	if(this.pattern == null){
            this.pattern = this.calculatePattern(dateStyle, timeStyle, locale);
        }
        if(this.pattern!=null&&!this.pattern.trim().equals("")){
            formatter = new SimpleDateFormat(this.pattern,this.locale);
        }else
            formatter = DatePattern.getJDKDateFormat(this.dateStyle, this.timeStyle, this.locale);

        if(formatter!=null)
            formatter.setTimeZone(this.timeZone);
        return formatter;
    }
   
    /**
     * Based on the date's style to get the pattern contained timezone
     * @param dateStyle
     * @param timeStyle
     * @param locale
     * @return
     */
    private String calculatePattern(DateTimeStyle dateStyle,DateTimeStyle timeStyle,Locale locale){
       /* int intDateStyle = -1;
        int intTimeStyle = -1;
        if(dateStyle!=null){
            intDateStyle = dateStyle.getDateTimeStyle();
        }
        if(timeStyle!=null){
            intTimeStyle = timeStyle.getDateTimeStyle();
        }*/
        String sysPattern = null;
        try{
           SimpleDateFormat formatter = (SimpleDateFormat)DatePattern.getJDKDateFormat(dateStyle, timeStyle, locale);
           sysPattern = formatter.toPattern();
        }catch(Exception e){
           sysPattern = null;
           logger.severe("Can not cast to SimpleDateFormat. '"+e.getMessage()+"'");
        }
        /*ResourceBundle r = LocaleData.getDateFormatData(locale);
        String[] dateTimePatterns = r.getStringArray("DateTimePatterns");
        if ((intTimeStyle >= 0) && (intDateStyle >= 0)) {
            Object[] dateTimeArgs = {dateTimePatterns[intTimeStyle],
                                     dateTimePatterns[intDateStyle + 4]};
            sysPattern = MessageFormat.format(dateTimePatterns[8], dateTimeArgs);
        }
        else if (intTimeStyle >= 0) {
            sysPattern = dateTimePatterns[intTimeStyle];
        }
        else if (intDateStyle >= 0) {
            sysPattern = dateTimePatterns[intDateStyle + 4];
        }
        else {
            throw new IllegalArgumentException("No date or time style specified");
        }*/
        //add the timezone string
        if(sysPattern != null && timeStyle != null && sysPattern.indexOf("z") < 0){
            sysPattern = sysPattern + " z";
        }
        return sysPattern;
    }
    /**
     * Set the current timezone.
     * @param timeZone
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    /**
     * get the current timezone.
     * @return
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }
    /**
     * set the current locale.
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    /**
     * get the current locale.
     * @return
     */
    public Locale getLocale() {
        return locale;
    }
    /**
     * set the current string pattern.
     * @param
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    /**
     * get the current string pattern.
     * @return
     */
    public String getPattern() {
        return pattern;
    }
    /**
     * Set the current date style.
     * @param dateStyle
     */
    public void setDateStyle(DateFormatter.DateTimeStyle dateStyle) {
        this.dateStyle = dateStyle;
    }
    /**
     * Get the current date style.
     * @return
     */
    public DateFormatter.DateTimeStyle getDateStyle() {
        return dateStyle;
    }
    /**
     * Set the current time style.
     * @param timeStyle
     */
    public void setTimeStyle(DateFormatter.DateTimeStyle timeStyle) {
        this.timeStyle = timeStyle;
    }
    /**
     * Get the current time style.
     * @return
     */
    public DateFormatter.DateTimeStyle getTimeStyle() {
        return timeStyle;
    }
   
}



