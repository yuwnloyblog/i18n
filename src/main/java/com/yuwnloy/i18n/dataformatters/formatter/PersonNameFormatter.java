package com.yuwnloy.i18n.dataformatters.formatter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import com.yuwnloy.i18n.dataformatters.localedata.LocaleDataHelper;
import com.yuwnloy.i18n.dataformatters.locales.LocaleDeterminationFactory;
/**
 * Used to format person name.
 * @author xiaoguang.gao
 *
 */
public class PersonNameFormatter{
    private static Logger logger = Logger.getLogger(DateFormatter.class.getName());
    private Locale locale = null;
    //private Calendar calendar;
    private DisplayFormat pattern = null;
    private static Map<String, DisplayFormat> namePatternNameMap = null;
    
    public enum DisplayFormat {               
        Long("DisplayNameLongFormat", "Long"),
        Short("DisplayNameShortFormat", "Short");
        
        private String key;
        private String name;
        private DisplayFormat(String key, String name) {this.key = key; this.name = name;}
    }

    /**
     * get the DatePattern object from name
     * @param name
     * @return
     */
    public static DisplayFormat getValidPattern(String name){
        String key = name.toLowerCase();
        if(getEnumMap().containsKey(key))
            return getEnumMap().get(key);
        return DisplayFormat.Short;
    }

    private static Map<String, DisplayFormat> getEnumMap(){
        if(namePatternNameMap == null){
            namePatternNameMap = new HashMap<String, DisplayFormat>();
            for(DisplayFormat df : DisplayFormat.values()){
                namePatternNameMap.put(df.name.toLowerCase(), df);
            }
        }
        return namePatternNameMap;
    }

    //////////////getInstance()////////////////
    /**
     * get default instance.
     * @return
     */
    public static PersonNameFormatter getInstance(){     
       return getInstance(null, LocaleDeterminationFactory.getFormattingLocaleDetermination().getLocale());
    }
    /**
     * get instance.
     * @param pattern
     *    Specify the display format. Scope: Long, Short. 
     * @return
     */
    public static PersonNameFormatter getInstance(DisplayFormat pattern){     
       return getInstance(pattern, LocaleDeterminationFactory.getFormattingLocaleDetermination().getLocale());
    }
    
    /**
     * Get instance.
     * @param locale
     *     Specify locale.
     * @return 
     */
    public static PersonNameFormatter getInstance(Locale locale){     
       return getInstance(null, locale);
    }
    
     /**
     *  Get instance.
     * @param pattern
     * @param locale
     * @return 
     */
    public static PersonNameFormatter getInstance(DisplayFormat pattern, Locale locale){
       if(pattern == null) {
          pattern = DisplayFormat.Short;
       }
       PersonNameFormatter pf = new PersonNameFormatter();
       pf.setPattern(pattern);
       pf.setLocale(locale);
       return pf;
       }
    
    ////////////////////////format/////////////////
    /**
     * Format person name to string. 
     * @param givenName
     *     Given Name.
     * @param familyName
     *     Family Name.
     * @return
     */
    public String format(String givenName, String familyName){
        return this.format(null, givenName, null, familyName, null);
    }
    /**
     * Format to string. 
     * @param prefix
     *     Such as 'Miss.', 'Mr.'. 
     * @param givenName
     * @param familyName
     * @return
     */
    public String format(String prefix, String givenName, String familyName){
        return this.format(prefix, givenName, null, familyName, null);
    }
    /**
     * Format to string. Follow part will have different sequence under different locale.
     * @param prefix
     * @param givenName
     * @param middleName
     * @param familyName
     * @return
     */
    public String format(String prefix, String givenName, String middleName, String familyName){
        return this.format(prefix, givenName, middleName, familyName, null);
    }
    /**
     * 
     * @param prefix
     * @param givenName
     * @param middleName
     * @param familyName
     * @param suffix
     *     
     * @return
     */
    public String format(String prefix, String givenName, String middleName, String familyName, String suffix){
        String pattern = LocaleDataHelper.getInstance().getPattern(this.pattern.key, this.locale);
        
        // prefix
        if(pattern.contains("{P}")) {
            if(prefix == null || prefix  == "")
                pattern = pattern.replace("{P} ", "");
            else
                pattern = pattern.replace("{P}", prefix);
        }
        // suffix
        if(pattern.contains("{S}")) {
            if(suffix == null || suffix == "")
                pattern = pattern.replace(", {S}", "");
            else
                pattern = pattern.replace("{S}", suffix);
        }
        // middle
        if(pattern.contains("{M}")) {
            if(middleName == null || middleName == "")
                pattern = pattern.replace("{M} ", "");
            else
                pattern = pattern.replace("{M}", middleName);
        }
        // givenName and familyName
        // Bug 20287570 - protect against NPE.
        if (givenName == null) 
        {
           givenName = "";
        }
        if (familyName == null) 
        {
           familyName = "";
        }        
        pattern = pattern.replace("{G}", givenName).replace("{F}", familyName).trim();

        return pattern;
    }
    
    ///////////////////others/////////////////
    /**
     * Not supported at present.
     * @param name
     * @return
     */
    public String parse(String name){
        return null;
    }
    /**
     * Set current locale.
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    /**
     * Get current locale.
     * @return
     */
    public Locale getLocale() {
        return locale;
    }
    /**
     * Set current display format.
     * @param pattern
     */
    public void setPattern(DisplayFormat pattern) {
        this.pattern = pattern;
    }
    /**
     * Get current display format.
     * @return
     */
    public DisplayFormat getPattern() {
        return pattern;
    } 
}



