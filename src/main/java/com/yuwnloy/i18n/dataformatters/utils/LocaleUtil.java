package com.yuwnloy.i18n.dataformatters.utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import java.util.logging.Logger;
/**
 * A collection of utility methods for locale determination.
 */
public class LocaleUtil
{   
  
    private static Logger logger = Logger.getLogger(LocaleUtil.class.getName());
     
  /* languages with irregular mappings */
  private static String  specialLanguages[] = 
  { 
    "zh" /* Chinese */,
    "pt" /* Portuguese */,
    "iw" /* Hebrew(he) */,
    "ji" /* Yiddish(yi) */,
    "in" /* Indonesian(id) */
  };  
  
  /* irregular mappings */
  private static String  specialMap[][] =
  {
    { "pt_BR", "pt-BR" },
    { "zh_TW", "zh-TW" },
    { "zh_HK", "zh-TW" },
    { "zh_MO", "zh-TW" },
    { "iw","he" },
    { "ji","yi" },
    { "in","id" },
    { "zh","zh-CN" } // moot
  };  
  
  private static final String M_C_DASH_CHAR = "-";
  private static final String M_C_UNDERSCORE_CHAR = "_";
  
  private static final String LANGUAGE_ZH_HANS = "ZH-HANS";
  private static final String REGION_CN = "CN";
  private static final String LANGUAGE_ZH_HANT = "ZH-HANT";
  private static final String REGION_TW = "TW";
  
  private static final String REG_EXP_2_LETTER = "[a-zA-Z]{2}";
  private static final String REG_EXP_4_LETTER = "[a-zA-Z]{4}";
  private static final String REG_EXP_3_DIGIT = "\\d{3}";
  
  private static Pattern pattern2l;
  private static Pattern pattern3d;
  private static Pattern pattern4l;
  
  static
  {
    // precompile regular expressions
    pattern2l = Pattern.compile(REG_EXP_2_LETTER);
    pattern3d = Pattern.compile(REG_EXP_3_DIGIT);
    pattern4l = Pattern.compile(REG_EXP_4_LETTER);
  }

  /**
   * Constructs a Locale array from the given language list string.
   * 
   * @param langString
   *          The standard language list, the language should split by comma.
   *          Every language should match the language-tag specification syntax
   *          (RFC 4646). The language can be language[-script][-region]
   *          (-variant)(-extension), the script will be ignored if the 
   *          language contains region. If the language-tag only contains two 
   *          fields, the language will be language-region but no script.
   *          Each language can be with the quality value. The returned locale
   *          array is sorted by q-value in decreasing order.Qulity value should
   *          be in the range 0.0 - 1.0. If the value is not specified or out of
   *          range, the q-value will be set to 1.0.  
   *          For example, 
   *          &quot;<code>en;q=0.9, fr, it;q=0.8</code>&quot; 
   *          means &quot;<code>fr, en, it</code>&quot;. 
   *          The connector of ISO 639 and ISO 3166 can be either 
   *          &quot;<code>-</code>&quot;
   *          (U+002d) or &quot;<code>_</code>&quot (U+005f);
   * 
   * @return the generated <code>Locale Array</code>; null if the input  
   *         language list string is null, or 0 length string after trimming
   */
  public static Locale[] getLocalesFromLangList(String langString)
  {
    if(langString == null || langString.trim().equals("")) return null;
    int semi;
    String lang;
    Float q;
    StringTokenizer tokenizer = new StringTokenizer(langString, ",");
    List lst = new ArrayList();
    Locale locale = null;

    while (tokenizer.hasMoreTokens())
    {
      lang = tokenizer.nextToken().trim();

      if ((semi = lang.indexOf(';')) != -1)
      { // extract q
        try{
          q = Float.valueOf(lang.substring(semi + 3));
        }catch(Exception nfe) {
	  // not a valid number, q-value set to 1.0
          q = new Float(1.0f);
        }
        if(q.floatValue() > 1 || q.floatValue() < 0)
        {
	  // q-value if out of range, set to 1.0
          q = new Float(1.0f);
        }
        
        lang = lang.substring(0, semi);
      } else
      {
        q = new Float(1.0f); // default q = 1
      }

      try
      {
        locale = getLocaleFromString(lang);
        if(locale != null)
        lst.add(new Object[] { q, locale });
      }catch (IllegalArgumentException ie)
      {
	//do not add null to the result
      }    
    }

    Collections.sort(lst, m_cmp_locales);
    Locale[] result = new Locale[lst.size()];
    int i = 0;
    
    for (Iterator itr = lst.iterator(); itr.hasNext();)
    {
      Object[] o = (Object[]) itr.next();
      result[i++] = (Locale)o[1];
    }
    
    return result;
  }

  /**
   * Constructs a Locale object for the given locale string.
   * 
   * <p>
   * Either a dash (U+002d) or a underscore (U+005f) can be used to 
   * connect ISO 639 and ISO 3166 elements.
   * </p>
   * 
   * @param localeString
   *          the string representing an ISO locale or a language-tag. 
   *          For example, <code>ja-JP</code> represents Japanese language
   *          used in Japan. The connector of ISO 639 and ISO 3166 can be 
   *          either &quot;<code>-</code>&quot; or &quot;<code>_</code>&quot;
   *          It also support the language-tag syntax (RFC4646). 
   *          like &quot;language[-script][-region](-variant)(-extension)&quot. 
   * 
   * @return the generated <code>Locale</code>object; null if the input locale 
   *          string is null, or 0 length string after trimming
   */
  public static Locale getLocaleFromString(String localeString)
  {
    if(localeString == null || localeString.trim().equals("")) return null;
    
    int underIndex = localeString.indexOf(M_C_UNDERSCORE_CHAR);
    String subtags[];
    String lang = "";
    String region = "";
    String variant = "";

    if (underIndex != -1)
    {
      // replace underscore to dash
      localeString = localeString.replace(M_C_UNDERSCORE_CHAR, M_C_DASH_CHAR);
    }

    // ignore the underscore and split the string with dash
    subtags = localeString.split(M_C_DASH_CHAR, 4);
    
    /* parse the language tag */
    lang = subtags[0];
    if (subtags.length > 1)
    {
      /* script subtag: 4 letters */
      if (pattern4l.matcher(subtags[1]).matches()) 
      {
        if (localeString.equalsIgnoreCase(LANGUAGE_ZH_HANS))
          region = REGION_CN;
        else if (localeString.equalsIgnoreCase(LANGUAGE_ZH_HANT))
          region = REGION_TW;
      }
      /* region subtag: 2 letters or 3 digits */ 
      else if ((pattern2l.matcher(subtags[1]).matches()) || 
          (pattern3d.matcher(subtags[1]).matches())) 
      {
        region = subtags[1];
      }            
      else
        variant = subtags[1];
    }  
    if (subtags.length > 2)
    {
      /* region subtag: 2 letters or 3 digits */ 
      if ((pattern2l.matcher(subtags[2]).matches()) || 
          (pattern3d.matcher(subtags[2]).matches())) 
        region = subtags[2];
      else
        variant = subtags[2];
    }  
    if (subtags.length > 3) variant = subtags[3];
    
    if (lang.length() == 1)
    {
      if ((lang.toLowerCase().equals("i")) && (subtags.length > 1))
        // for special language tag
        return (new Locale(subtags[1]));
      else if (subtags[0].toLowerCase().equals("x"))
        // for private use language tag, not supported
        return null;
      else
        // other single character is not supported
        return null;
    }
    else
    {
        return (new Locale(lang, region, variant));
    }     
  }

  /**
   * Only for the getLocalesFromLangList method use.
   */
  private static final Comparator m_cmp_locales = new Comparator()
  {
    public int compare(Object o1, Object o2)
    {
      Object[] s1 = (Object[]) o1;
      Object[] s2 = (Object[]) o2;
      int re = ((Float) s2[0]).compareTo((Float) s1[0]);
      return re;
    }
  };
  
  /**
   * Extracts preferred language from preferred locale.
   * 
   * @param locale preferred locale of a user, per standard language tag RFC 4646
   * @return preferred language of the user, per standard language tag RFC 4646;
   *         null if the input locale is null
   */
  public static String getLanguageFromLocale( String locale )
  {
    return getLanguageFromLocale( getLocaleFromString( locale ) );
  }  
  
  /**
   * Extracts preferred language from preferred locale.
   * 
   * @param locale preferred locale of a user
   * @return preferred language of the user, per standard language tag RFC 4646;
   *         null if the input locale is null
   */
  public static String getLanguageFromLocale( Locale locale )
  {
    if (locale == null) return null;

    if ("true".equalsIgnoreCase(System.getProperty("oracle.fusion.appsMode"))
        && locale.equals(Locale.CANADA_FRENCH))
    {
      return "fr-CA";
    }   
 
    String  language = locale.getLanguage();
    for ( int i = 0 ; i < specialLanguages.length ; i++ )
    {
      if ( language.equals(specialLanguages[i]) )
      {
        for ( int j = 0 ; j < specialMap.length ; j++ )
        {
          String  langtag = specialMap[j][0];
          if ( locale.toString().startsWith(langtag) )
            return specialMap[j][1];
        }
      }
    }

    return language ;
  }   
}

