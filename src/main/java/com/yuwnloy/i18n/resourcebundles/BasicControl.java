package com.yuwnloy.i18n.resourcebundles;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

import com.yuwnloy.i18n.resourcebundles.datagetter.IDataGetter;
import com.yuwnloy.i18n.resourcebundles.datagetter.MapMetaData;
/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class BasicControl extends ResourceBundle.Control {
      private IDataGetter dataGetter = null;
      private long lastLoadTime = System.currentTimeMillis();
      private long expiration = TTL_NO_EXPIRATION_CONTROL;
      public BasicControl(){
		
      }
      public BasicControl(long expiration){
      	this.expiration = expiration;
      }
      public BasicControl(IDataGetter dataGetter,long expiration){
		this.dataGetter = dataGetter;
		this.expiration = expiration;
      }
      public BasicControl(IDataGetter dataGetter){
    	  this.dataGetter = dataGetter;
      }
	/**
	 * 
     * @param baseName
     * @param locale
	 */
	@Override
	public long getTimeToLive(String baseName, Locale locale) {
	    if (baseName == null || locale == null) {
		throw new NullPointerException();
	    }
	    return expiration;
	}
     /**
     * 
     * @param baseName
     * @param locale
     * @param format
     * @param loader
     * @param bundle
     * @param loadTime
     */
    @Override 
    public boolean needsReload(String baseName, Locale locale,
			   String format, ClassLoader loader,
			   ResourceBundle bundle, long loadTime) {
        //Q: clear all or just reload specific resourceBundle?
    	if(this.dataGetter!=null){
		    Timestamp modifiedTime = dataGetter.getModifiedTime(baseName,locale);
		    if(modifiedTime!=null && (modifiedTime.getTime() > this.lastLoadTime)){
		    	return true;
		    }else{
		    	return false;
		    }
    	}
		return super.needsReload(baseName, locale, format, loader, bundle, loadTime);
    }
	/**
     * Avoid match JVM locale when failed to match english locale
     * @param baseName
     * @param locale
     */
    @Override
    public Locale getFallbackLocale(String baseName, Locale locale) {
        if(baseName == null){
            throw new NullPointerException();
        }
        return null;
        /*if (locale.getLanguage().equals("en")) {
            if (baseName == null) {
                throw new NullPointerException();
            }
            return null;
        } else {
            return super.getFallbackLocale(baseName, locale);
        }*/
    }
    /**
   	 * 
   	 * @author xiaoguang.gao@oracle.com
   	 * @version 2013-03-22 Created
   	 * @param baseName
   	 * @param locale
   	 */
   	@Override
   	public List<Locale> getCandidateLocales(String baseName, Locale locale){
   		String language = locale.getLanguage();
        String country = locale.getCountry();

          if (language.toUpperCase().equals("ZH")) {
            if (country.toUpperCase().equals("TW") || country.toUpperCase().equals("HK") ||
                country.toUpperCase().equals("MO")) {
            	locale = new Locale("zh", "TW");
            } else if(country!=null&&!country.equals("")){
            	locale = new Locale("zh", "CN");
            }
          }else if(language.toUpperCase().equals("PT")){
            if(country.trim().equals("")||country.toUpperCase().equals("BR")){
              List<Locale> locales = new ArrayList<Locale>();
              locales.add(locale);
              locales.add(Locale.ROOT);
              return locales;
            }      
          }
   	  List<Locale> locales = super.getCandidateLocales(baseName, locale);
   	  //locales.add(new Locale("en","US",""));
   	  //locales.add(new Locale("en","",""));
   	  return locales;
   	}
     /**
     * Override function. Loaded data from database, and generate ResourceBundle object
     * @param baseName
     * @param locale
     * @param format
     * @param loader
     * @param reload
     * @throws IllegalAccessException,InstantiationException,IOException
     */
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                                    boolean reload) throws IllegalAccessException, InstantiationException,
                                                           IOException {

        if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
            throw new NullPointerException();
        }        
        ResourceBundle bundle = null;
        if(this.dataGetter!=null){
        	MapMetaData meta = dataGetter.getKeyValuePairs(baseName,locale);
	        if(meta!=null&&meta.getMap()!=null&&meta.getMap().size()>0){
	           bundle = new BasicResourceBundle(meta.getMap(),meta.getLocale());
	           this.lastLoadTime = System.currentTimeMillis();
	        }
        }else{
        	bundle = super.newBundle(baseName, locale, format, loader, reload);
        }
        return bundle;
    }
}

