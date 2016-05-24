package com.yuwnloy.i18n.dataformatters;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import com.yuwnloy.i18n.dataformatters.config.I18nConfigure;

/**
 * Used to custom some common setting for package.
 * @author xiaoguang.gao
 *
 */
public class I18nManageFactory {
    private static I18nManageFactory factory = null;
    private static I18nConfigure CONFIGURE = null;
    /**
     * Register the caller's configure object to package.
     * @param config
     *     The sub class of I18nConfigure needed to implementation by caller.
     */
    public static void setConfigure(I18nConfigure config) {
        CONFIGURE = config;
    }

    /**
     * Return an instance.
     * @return
     * @throws Exception
     */
    public static synchronized I18nManageFactory getInstance() {
        if (CONFIGURE == null)
            CONFIGURE = new I18nConfigure(){

				@Override
				public Locale getDefaultViewLocale() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Locale getDefaultFormatLocale() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public TimeZone getDefaultTimeZone() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Locale getPreferenceViewLocale() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Locale getPreferenceFormatLocale() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public TimeZone getPreferenceTimeZone() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Iterator<Locale> getSupportedViewLocales() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Iterator<Locale> getAcceptLanguages() {
					// TODO Auto-generated method stub
					return null;
				}
        	
        };

        if (factory == null)
            factory = new I18nManageFactory();
        return factory;
    }

    private I18nManageFactory() {
    }
    /**
     * Return the current configure object.
     * @return
     */
    public I18nConfigure getI18nConfigure() {
        return CONFIGURE;
    }
}
