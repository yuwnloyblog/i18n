package com.yuwnloy.i18n.resourcebundles;
import java.util.Locale;
import java.util.ResourceBundle;

import com.yuwnloy.i18n.resourcebundles.datagetter.IDataGetter;
import com.yuwnloy.i18n.resourcebundles.datagetter.XlfDataGetter;

/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class ResourceBundleFactory {
    private final ClassLoader myClassLoader = new ResourceBundleClassLoader();
    private static ResourceBundleFactory resourceBundle = null;

    private ResourceBundleFactory() {
    }

    /**
     * Get a instance of ResourceBundleFactory
     * @return
     */
    public static synchronized ResourceBundleFactory getInstance() {
        if (ResourceBundleFactory.resourceBundle == null) {
            ResourceBundleFactory.resourceBundle = new ResourceBundleFactory();
        }
        return ResourceBundleFactory.resourceBundle;
    }

    /**
     * Get xlf ResourceBundle by resource name
     * match xliff file like this:
     * 			com.oracle.strings.Strings_en_US.xlf
     * @param baseName
     * @param locale
     * @return
     */
    public ResourceBundle getXlfBundle(String baseName,Locale locale) {
    	IDataGetter dataGetter = new XlfDataGetter();
        BasicControl control = new BasicControl(dataGetter);
        return ResourceBundle.getBundle(baseName, locale, myClassLoader, control);
    }
    /**
     * Get xlf ResourceBundle by resource name
     * match xliff file like this:
     * 			com.oracle.strings.Strings_en_US.xlf
     * @param baseName
     * @return
     */
    public ResourceBundle getXlfBundle(String baseName) {
    	IDataGetter dataGetter = new XlfDataGetter();
        BasicControl control = new BasicControl(dataGetter);
        return ResourceBundle.getBundle(baseName, Locale.getDefault(), myClassLoader, control);
    }

    /**
     * Get default ResourceBundle by resource name
     * @param baseName
     * @return
     */
    public ResourceBundle getBundle(String baseName) {
        BasicControl control = new BasicControl();
        return ResourceBundle.getBundle(baseName, Locale.getDefault(), myClassLoader, control);
    }
    /**
     * Get default ResourceBundle by resource name
     * @param baseName
     * @param locale
     * @return
     */
    public ResourceBundle getBundle(String baseName,Locale locale) {
        BasicControl control = new BasicControl();
        return ResourceBundle.getBundle(baseName, locale, myClassLoader, control);
    }

    /**
     * Clear the cache by default classloader
     * @return
     */
    public void clearCache() {
        ResourceBundle.clearCache(myClassLoader);
    }
}
