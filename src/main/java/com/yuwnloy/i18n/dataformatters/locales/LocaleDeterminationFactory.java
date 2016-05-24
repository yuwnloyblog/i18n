package com.yuwnloy.i18n.dataformatters.locales;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Logger;

public abstract class LocaleDeterminationFactory {
    private static Logger logger = Logger.getLogger(LocaleDeterminationFactory.class.getName());
    public LocaleDeterminationFactory() {
        
        super();
    }
    /**
     * Return the best match locale based on specified sequence/priority.
     * @return
     */
    public abstract Locale getLocale();
    /**
     * Verify the locale.
     * @param locale
     * @return
     */
    public abstract Locale verifyLocale(Locale locale);
    /**
     * Return the default locale.
     * @return
     */
    public abstract Locale getDefaultLocale();
    
    /**
     * Search the locale list as priority and return the first best match locale.
     * 
     * @param locales
     * @return
     */
    public Locale getLocale(Iterator<Locale> locales){
        if(locales != null){
            while(locales.hasNext()){
                Locale loc = (Locale)locales.next();
                Locale rtnLoc = this.verifyLocale(loc);
                if(rtnLoc != null)
                    return rtnLoc;
            }
        }
        return null;
    }
    /*public Locale getLocaleFromStr(String strLocale){
      Locale ret = null;
      if(strLocale!=null && !strLocale.trim().equals("")){
        ret = LocaleUtil.getLocaleFromString(strLocale);
      }
      return ret;
    }*/
    private static LocaleDeterminationFactory viewLocaleDeter = null;
    /**
     * Get the view locale determination instance.
     * @return
     */
    public static synchronized LocaleDeterminationFactory getViewLocaleDetermination(){
        
        
        if(viewLocaleDeter == null){
            viewLocaleDeter = new ViewLocaleDetermination();
        }
        return viewLocaleDeter;
    }
    private static LocaleDeterminationFactory formattingLocaleDeter = null;
    /**
     * Get the formatting locale determination instance.
     * @return
     */
    public static synchronized LocaleDeterminationFactory getFormattingLocaleDetermination(){
        if(formattingLocaleDeter == null){
            formattingLocaleDeter = new FormattingLocaleDetermination();
        }
        return formattingLocaleDeter;
    }    
}

