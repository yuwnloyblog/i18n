package com.yuwnloy.i18n.dataformatters.formatter;

import java.util.Locale;
import java.util.TimeZone;

import com.yuwnloy.i18n.dataformatters.locales.LocaleDeterminationFactory;
import com.yuwnloy.i18n.dataformatters.timezones.TimeZoneDeterminationFactory;
/**
 * Factory class, used to get the formatter.
 * @author xiaoguang.gao
 *
 */
public class FormatterFactory{
    static LocaleDeterminationFactory deter = null;
    static{
        deter = LocaleDeterminationFactory.getFormattingLocaleDetermination();        
    }
    /**
     * get the number formatter
     * @param numberType
     * @return
     */
    public static IFormatter getNumberFormatter(NumberFormatter.NumberType numberType){
        Locale locale = deter.getLocale();
        return NumberFormatter.getInstance(numberType, locale);
    }
    /**
     * get the datetime formatter
     * @param pattern
     *     Specify the <code>DatePattern</code>.
     * @return
     */
    public static IFormatter getDateTimeFormatter(DatePattern pattern){
        Locale locale = deter.getLocale();        
        TimeZone timeZone = TimeZoneDeterminationFactory.getTimeZoneDetermination().getTimeZone();
        return DateFormatter.getInstance(pattern, timeZone, locale);
    }
    /**
     * get the datetime formatter
     * @param pattern
     *     Specify the <code>DatePattern</code>.
     * @param timeZone
     *     Specify the <code>TimeZone</code>.
     * @return
     */
    public static IFormatter getDateTimeFormatter(DatePattern pattern, TimeZone timeZone){
        Locale locale = deter.getLocale();        
        return DateFormatter.getInstance(pattern, timeZone, locale);
    }
    /**
     * get Period Formatter
     * @return
     */
    public static PeriodFormatter getPeriodFormatter(){
       return PeriodFormatter.getInstance();
    }
    /**
     * get Person Name Formatter
     * @return
     */
    public static PersonNameFormatter getPersonNameFormatter(){
        Locale locale = deter.getLocale();
        return PersonNameFormatter.getInstance(locale);
    }
}
