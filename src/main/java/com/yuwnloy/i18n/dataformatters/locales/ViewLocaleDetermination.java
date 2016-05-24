package com.yuwnloy.i18n.dataformatters.locales;

import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Logger;

import com.yuwnloy.i18n.dataformatters.I18nManageFactory;
import com.yuwnloy.i18n.dataformatters.config.GetterSeries;
import com.yuwnloy.i18n.dataformatters.config.I18nConfigure;
import com.yuwnloy.i18n.dataformatters.utils.LocaleUtil;

import java.util.Map;


/**
 * ViewLocaleDetermination provides implementation for view locale determination
 */
public class ViewLocaleDetermination extends LocaleDeterminationFactory {
    private static Logger logger = Logger.getLogger(ViewLocaleDetermination.class.getName());
    private static final String ZH_LANG = "zh";

    // the country code list belonging to lang zh that can be matched to zh_TW locale
    private static final String[] ZH_TWLOCAL = { "HK", "MO", "TW" };

    private static final String ZH_TW_LOCALE = "zh_TW";
    private static final String ZH_CN_LOCALE = "zh_CN";

    private static final String ZH_CN_COUNTRY = "CN";
    private static final String ZH_TW_COUNTRY = "TW";


    // the language code list that can be matched to "no" locale;
    private static final String[] NORWAY_LANG = { "nn", "nb", "no" };

    private static final String NO_LANG = "no";

    private static final String NO_LOCALE = "no";

    // When there is no matching locale and the default locale is null, return "en" locale
    private static final Locale FALLBACK_LOCALE = new Locale("en");

    /**
     * Constructor
     */
    public ViewLocaleDetermination() {
    }

    /**
     * Do validation for specified locale. The process contained two parts:
     * 1)Judge that specified locale whether in supported locales scope. 
     * 2)Fall back 'zh-HK','zh-MO' to 'zh-TW'. 'zh', 'zh-*' to 'zh-CN'.
     *
     * @param locale the specified locale to be matched
     */
    public Locale verifyLocale(Locale locale) {
        String iteLang, iteCountry;

        String lang = locale.getLanguage();
        String country = locale.getCountry();

        // the flag that there is a lang matched locale from the supported locale list
        boolean bLangMatch = false;

        // the flag that the specified locale is zh language
        boolean bZHLang = false;

        // the flag of the specified locale is norway, language is in NORWAY_LANG
        boolean bNorwayLang = false;

        // iteLocale contains the ZH_TW locale
        boolean bZHTWLocal = false;

        // iteLocale contains the ZH_CN locale
        boolean bZHCNLocal = false;

        // iteLocale contains the no locale
        boolean bNOLocale = false;

        I18nConfigure configure = I18nManageFactory.getInstance().getI18nConfigure();
        if(configure == null){
           return null;
        }
        Iterator<Locale> iteSupportedLocale = configure.getSupportedViewLocales();



        // When no supported locale is provided, return the locale to be matched directly
        if (iteSupportedLocale==null||!iteSupportedLocale.hasNext()) {
            return locale;
        }

        if ((lang.substring(0, 2)).equals(ZH_LANG)) {
            bZHLang = true;
        }

        for (int i = 0; i < NORWAY_LANG.length; i++) {
            if ((lang.substring(0, 2)).equals(NORWAY_LANG[i])) {
                bNorwayLang = true;
                break;
            }
        }

        while (iteSupportedLocale.hasNext()) {
            Locale loc = (Locale)iteSupportedLocale.next();
            iteLang = loc.getLanguage();
            iteCountry = loc.getCountry();

            if (iteLang.equals(lang)) {
                if (iteCountry.equals(country)) {
                    return loc;
                } else {

                    // existing the language matched locale without country value
                    if (iteCountry.length() == 0) {
                        bLangMatch = true;
                    }

                    // Check if there are "zh-TW" and "zh-CN" locale in the supported locale list
                    if (bZHLang) {
                        if (iteCountry.equals(ZH_CN_COUNTRY)) {
                            bZHCNLocal = true;
                        }
                        if (iteCountry.equals(ZH_TW_COUNTRY)) {
                            bZHTWLocal = true;
                        }
                    }
                }
            }

            // Check if this is a "no" locale in the supported locale list
            if (bNorwayLang) {
                if (iteLang.equals(NO_LANG)) {
                    bNOLocale = true;
                }
            }
        }


        if (bZHLang) {
            // For the locales "zh-HK" or "zh-MO", when "supported locales" contains
            // "zh-TW", returns "zh-TW".
            for (int i = 0; i < ZH_TWLOCAL.length; i++) {
                if (country.equals(ZH_TWLOCAL[i]) && bZHTWLocal) {
                    return LocaleUtil.getLocaleFromString(ZH_TW_LOCALE);
                }
            }

            //For the locales, ISO-639 code "zh" (i.e. "zh" or ""zh-*"), when
            //"supported locales" contains "zh-CN", returns "zh-CN".
            if (bZHCNLocal) {
                return LocaleUtil.getLocaleFromString(ZH_CN_LOCALE);
            }
        }

        // For the locales, ISO-639 code "nb" or "nn" (i.e. "nn" or ""nn-*" or ""nb" or ""nb-*"),
        // when "supported locales" contains "no", returns "no" locale.
        if (bNorwayLang) {
            if (bNOLocale)
                return LocaleUtil.getLocaleFromString(NO_LOCALE);
        }

        //Existing one supported locale that matches only language part and without country,
        //return it.
        if (bLangMatch) {
            return LocaleUtil.getLocaleFromString(lang);
        }

        // none from the supported locales matches the specified locale, returns null
        return null;
    }

    /**
     * Returns the default locale specified in <code>I18nConfigure.getDefaultViewLocale</code>. If the method 
     * <code>I18nConfigure.getDefaultViewLocale</code> return null, default format locale will be "en" 
     */
    public Locale getDefaultLocale() {
        Locale defaultLocale = null;
        I18nConfigure configure = I18nManageFactory.getInstance().getI18nConfigure();
        defaultLocale = configure.getDefaultViewLocale();
        if(defaultLocale != null)
            return defaultLocale;
        return FALLBACK_LOCALE;
    }
    /**
     * Determine the formatting locale based on the sequence defined in 
     * <code>I18nConfigure.getViewLocaleSeries.</code>
     * @return
     */
    public Locale getLocale() {
        I18nConfigure configure = I18nManageFactory.getInstance().getI18nConfigure();
        GetterSeries<Locale> ser = configure.getViewLocaleSeries();
        Locale retLoc = ser.getFinalResult();
        if(retLoc != null)
            return retLoc;
        return this.getDefaultLocale();
    }
}
