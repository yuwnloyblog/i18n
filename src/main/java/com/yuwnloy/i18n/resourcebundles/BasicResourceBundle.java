package com.yuwnloy.i18n.resourcebundles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class BasicResourceBundle extends ResourceBundle{
	private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    public BasicResourceBundle(ConcurrentHashMap<String, Object> map,Locale targetLocale) {
        this.map = map;
        this.locale = targetLocale;
    }
    public static BasicResourceBundle getBundle(String baseName, Locale locale,
            ClassLoader loader){
    	ResourceBundle rb = ResourceBundle.getBundle(baseName, locale, loader);
    	if(rb instanceof BasicResourceBundle)
    		return (BasicResourceBundle)rb;
    	return null;
    }
    public static BasicResourceBundle getBundle(String baseName, Locale targetLocale,
			   ClassLoader loader, Control control) {
    	ResourceBundle rb = (BasicResourceBundle)ResourceBundle.getBundle(baseName, targetLocale, loader,control);
    	if(rb instanceof BasicResourceBundle)
    		return (BasicResourceBundle)rb;
    	return null;
    }
	 /**
     * Get the value from map by key
     * @param key
     * @return Object
     */
    protected Object handleGetObject(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return map.get(key);
    }

    /**
     * Get an Enumeration of the keys contained in
     * this XliffResourceBundle and its parent bundles.
     * @return
     */
    public Enumeration<String> getKeys() {
    	return this.getAllKeys(map.keySet());
    }
    
	/**
	 * Return all keys, contained parent ResourceBundle's keys.
	 * @param keySet
	 * @return
	 */
    protected Enumeration<String> getAllKeys(Set<String> keySet){
    	ResourceBundle parent = this.parent;
        Set<String> xlifKeys = keySet;
        Collection<String> coll = new ArrayList<String>();
        
        for(String key : xlifKeys){
        	coll.add(key);
        }
        
        if (parent != null) {
            Enumeration<String> parentKeys = parent.getKeys();
            if (parentKeys != null) {
                while (parentKeys.hasMoreElements()) {
                    String key = parentKeys.nextElement();
                    if(!coll.contains(key))
                    	coll.add(key);
                }
            }
        }
        return Collections.enumeration(coll);
    }
    private Locale locale;
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale){
    	this.locale = locale;
    }
    /**
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public final Object getObject(String key,Object defaultValue) {
    	Object obj = null;
    	try{
    		obj = this.getObject(key);
    	}catch(MissingResourceException mre){
    		obj = defaultValue;
    	}
        return obj;
    }
    /**
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public final String getString(String key,String defaultValue) {
    	return (String)this.getObject(key, defaultValue);
    }
}

