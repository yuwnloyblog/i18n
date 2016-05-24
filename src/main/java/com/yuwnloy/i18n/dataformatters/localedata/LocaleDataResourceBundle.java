package com.yuwnloy.i18n.dataformatters.localedata;

import java.util.Locale;

import com.yuwnloy.i18n.dataformatters.resourcebundle.AbsResourceBundle;
/**
 * Used to access the data stored in LocaleResource file. This is a child class of <code>AbsResourceBundle</code>.
 * @author xiaoguang.gao
 *
 */
public class LocaleDataResourceBundle extends AbsResourceBundle{
    public LocaleDataResourceBundle() {
        super();
    }
    public LocaleDataResourceBundle(Locale locale){
        super(locale);
    }

    public String getBundleName() {
        return "com.oracle.cloud.view.i18n.localedata.LocaleResource";
    }
}
