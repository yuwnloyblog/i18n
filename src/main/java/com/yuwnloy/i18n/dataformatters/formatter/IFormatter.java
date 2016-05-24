package com.yuwnloy.i18n.dataformatters.formatter;
import java.util.Locale;
/**
 * 
 * @author xiaoguang.gao
 *
 */
public interface IFormatter {
    public String format(Object obj);
    public Object parse(String source);
    
    public void setLocale(Locale locale);
    public Locale getLocale();
    
    public void setPattern(String pattern);
    public String getPattern();
}

