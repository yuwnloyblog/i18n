package com.yuwnloy.i18n.dataformatters.locales;

import java.util.Locale;

import com.yuwnloy.i18n.dataformatters.I18nManageFactory;
import com.yuwnloy.i18n.dataformatters.config.GetterSeries;
import com.yuwnloy.i18n.dataformatters.config.I18nConfigure;

/**
 * FormattingLocaleDetermination provides implementation for formatting locale determination
 */
public class FormattingLocaleDetermination extends LocaleDeterminationFactory {

    protected static final Locale FALLBACK_LOCALE = new Locale("en", "US");

    /**
     * Constructor
     */
    public FormattingLocaleDetermination() {
    }

    /**
     * Verify the locale whether is valid. This method will return null 
     * if locale is not valid.
     *
     * @param locale the specified locale to be matched
     */
    public Locale verifyLocale(Locale locale) {
      if(locale != null) return locale;
      return null;
    }

    /**
     * Returns the default locale specified in <code>I18nConfigure.getDefaultFormatLocale</code>. If the method 
     * <code>I18nConfigure.getDefaultFormatLocale</code> return null, default format locale will be "en-US" 
     */
    public Locale getDefaultLocale() {
        Locale locale = I18nManageFactory.getInstance().getI18nConfigure().getDefaultFormatLocale();
        if(locale != null){
            return locale;
        }
        return FALLBACK_LOCALE;
    }
    /**
     * Determine the formatting locale based on the sequence defined in 
     * <code>I18nConfigure.getFormatLocaleSeries.</code>
     * 
     */
    public Locale getLocale() {
        I18nConfigure configure = I18nManageFactory.getInstance().getI18nConfigure();
        GetterSeries<Locale> ser = configure.getFormatLocaleSeries();
        Locale retLoc = ser.getFinalResult();
        if(retLoc!=null)
            return retLoc;
        return this.getDefaultLocale();
    }
}

