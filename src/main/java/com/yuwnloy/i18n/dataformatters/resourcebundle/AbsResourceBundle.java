package com.yuwnloy.i18n.dataformatters.resourcebundle;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import java.util.ResourceBundle.Control;
import java.util.logging.Logger;

import com.yuwnloy.i18n.dataformatters.locales.LocaleDeterminationFactory;
/**
 * This class used to load properties file and return translated string based locale. 
 * Some special rule to control the file's loading defined in class  @see AdfControl. 
 * 
 * @author xiaoguang.gao
 *
 */
public abstract class AbsResourceBundle extends ResourceBundle{    
    private static Logger logger = Logger.getLogger(AbsResourceBundle.class.getName());

    private AdfControl control = new AdfControl();
    private LocaleDeterminationFactory viewLocaleDeter = null;
    private Locale locale = null;
    private boolean isRefreshLocale = true;

    /**
     * Constructor. 
     * If caller not specify locale, i18n package will use locale returned from
     * <code>ViewLocaleDetermination.getLocale</code>.
     */
    public AbsResourceBundle() {
        super();
        viewLocaleDeter = LocaleDeterminationFactory.getViewLocaleDetermination();
    }
    /**
     * Constructor.
     * @param locale
     *     Specified the locale to load properties.
     */
    public AbsResourceBundle(Locale locale){
        super();
        this.locale = locale;
        this.isRefreshLocale = false;
    }
    /**
     * Access properties file and return translated string based key.
     * @param key
     *     the key of properties file.
     */
    protected Object handleGetObject(String key) {      
        ResourceBundle bundle = this.getResourceBundle();
        if(bundle!=null){
            return bundle.getString(key);
        }
        
        return null;
    }
    /**
     * Get <code>ResourceBundle</code> based locale.
     * @return
     */
    private ResourceBundle getResourceBundle(){
        if(this.isRefreshLocale)
            this.locale = viewLocaleDeter.getLocale();
        ResourceBundle rb = null;
        try{
           rb = ResourceBundle.getBundle(this.getBundleName(), this.locale, control); 
        }catch(Exception ex){
        	logger.severe("Can not find resource ["+this.getBundleName()+"].");
        }
        return rb;
    } 
    /**
     * Return all the key of properties file.
     */
    public Enumeration<String> getKeys() {
        ResourceBundle bundle = this.getResourceBundle();
        if(bundle!=null)
            return bundle.getKeys();
        return null;
    }
    /**
     * Return the properties file's path in jar.
     * Such as 'com.oracle.cloud.view.i18n.localedata.LocaleResource'.
     * @return
     */
    public abstract String getBundleName();
    /**
     * Return the translated string based key.
     * 
     * @param key
     * @param defaultToKey
     *     If this param is true, this method will return key directly 
     *     when can not find this key in properties file.
     *     If this param is false, this method will return '!'+key+'!' 
     *     when can not find this key in properties file.
     * @return
     */
    public String getString(String key, boolean defaultToKey){
       ResourceBundle bundle = this.getResourceBundle();
       if(key == null||bundle == null){
           return null;
       }
       
       try{
           return bundle.getString(key);
       }catch(MissingResourceException e){
           return defaultToKey?key:"!"+key+"!";
       }
       
    }
    /**
     * Return the translated string after format use <code>params</code>.
     * The return string will contain format parameter, such as :
     * properties file: <code>asOfHoursMinsAgo=as of {0} hr {1} min ago</code>
     * 
     * This method use <code>java.text.MessageFormat</code> to format above string 
     * use params. 
     * 
     * String ret = resource.getString("asOfHoursMinsAgo",1,30);
     * 
     * The value of 'ret' is "as of 1 hr 30 min ago"
     *     
     * @param key
     * @param params
     * @return
     */
    public String getString(String key, Object... params){
        ResourceBundle bundle = this.getResourceBundle();
        if(bundle==null)return null;
        try{
            return MessageFormat.format(bundle.getString(key), params);
        }catch(MissingResourceException e){
            return "!"+key+"!";
        }
    }
    /**
     * Return the translated string base key. If key not found, just return null.
     * @param key
     * @return
     */
    public String getStringNullIfNotFound(String key){
        String translated = this.getString(key, false);
        if(translated != null && translated.length()>0 && translated.charAt(0)=='!'){
            return null;
        }
        return translated;
    }
    /**
     * Return the translated string base key. If key not found, just return defaultValue.
     * @param key
     * @param defaultValue
     *     The default value specified by caller.
     * @return
     */
    public String getDefaultIfNotFound(String key, String defaultValue){
        String ret = this.getStringNullIfNotFound(key);
        if(ret == null)
            return defaultValue;
        return ret;
    }
}

