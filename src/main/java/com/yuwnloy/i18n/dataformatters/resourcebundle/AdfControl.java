package com.yuwnloy.i18n.dataformatters.resourcebundle;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
/**
 * 
 * @author xiaoguang.gao
 *
 */
public class AdfControl extends ResourceBundle.Control{
    public AdfControl() {
        super();
    }
    
//    @Override
//    public List<String> getFormats(String baseName){
//        return Collections.singletonList("properties");
//    }
    /**
     * Forbidden fallback to jvm default locale. 
     */     
    @Override
    public Locale getFallbackLocale(String baseName, Locale locale){
        if (baseName == null) {
           throw new NullPointerException();
        }
        return null;
    }
    /**
	 * Returns a <code>List</code> of <code>Locale</code>s as candidate
	 * locales.
	 *
	 * <p>The sequence of the candidate locales corresponds to the
	 * runtime resource lookup path. The last element of the list
	 * must be a {@linkplain Locale#ROOT root locale}.
	 *
	 * <p>If the given locale is equal to <code>Locale.ROOT</code> (the
	 * root locale), a <code>List</code> containing only the root
	 * <code>Locale</code> must be returned.
	 * 
	 * <p>The default implementation returns a <code>List</code> containing
	 * <code>Locale</code>s in the following sequence:
	 * <pre>
	 *     Locale(language, country, variant)
	 *     Locale(language, country)
	 *     Locale(language)
	 *     Locale.ROOT
	 * </pre>
	 * where <code>language</code>, <code>country</code> and
	 * <code>variant</code> are the language, country and variant values
	 * of the given <code>locale</code>, respectively. Locales where the
	 * final component values are empty strings are omitted.
	 *
	 * <p>The default implementation uses an {@link ArrayList} that
	 * overriding implementations may modify before returning it to the
	 * caller. However, a subclass must not modify it after it has
	 * been returned by <code>getCandidateLocales</code>.
	 *
	 * <p>For example, if the given <code>resource</code> is "com.oracle.Messages"
	 * and the given <code>locale</code> is
	 * <code>Locale("fr","FR")</code>, then a
	 * <code>List</code> of <code>Locale</code>s:
	 * <pre>
	 *     Locale("fr", "FR")
	 *     Locale("fr")
	 *     Locale.ROOT
	 * </pre>
	 * is returned. And if the resource bundles for the "fr_FR","fr" and
	 * "" <code>Locale</code>s are found, then the runtime resource
	 * lookup path (parent chain) is:
	 * <pre>
	 *     Messages_fr_FR -> Messages_fr -> Messages
	 * </pre>
	 *
	 * Special handing: 
	 *    This method also do special handing for locale "zh-*" and "pt-BR" before
	 *    calculate candidate locale list.
	 *      1)"zh-HK", "zh-MO" will fallback to "zh-TW", other "zh-*" will fallback to "zh-CN".
	 *      2)Locale "pt-BR" 's candidate locale list is "pt-BR, Locale.Root".
	 *      3)Locale "pt" 's candidate locale list is "pt, Locale.Root".
	 * More details is in <link>https://stbeehive.oracle.com/teamcollab/wiki/OPC_CDC_Team:Language+Fallback+Rules</link>
	 *
     * @param baseName
	 *        the base name of the resource bundle, a fully
	 *        qualified class name
	 * @param locale
	 *        the locale for which a resource bundle is desired
	 * @return a <code>List</code> of candidate
	 *        <code>Locale</code>s for the given <code>locale</code>
         * @exception NullPointerException
         *        if <code>baseName</code> or <code>locale</code> is
         *        <code>null</code>
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
}
