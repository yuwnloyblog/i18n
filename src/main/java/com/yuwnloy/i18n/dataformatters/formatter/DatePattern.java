package com.yuwnloy.i18n.dataformatters.formatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import com.yuwnloy.i18n.dataformatters.localedata.LocaleDataHelper;
/**
 * Define some special date time patterns. 
 * @author xiaoguang.gao
 *
 */
public enum DatePattern {    
    // Date in locale resouce bundle
	/**
	 * Default pattern.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>	
	 * en &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          dd-MMM-yyyy h:mm:ss a z<br/>
	 * es &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d/M/yy H:mm:ss z<br/>
	 * de &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          dd.MM.yy HH:mm:ss z<br/>
	 * fr &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          dd/MM/yyyy HH:mm:ss z<br/>
	 * it &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          dd/MM/yy HH:mm:ss z<br/>
	 * ja &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy/MM/dd H:mm:ss z<br/>
	 * ko &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yy. M. d. a h:mm:ss z<br/>
	 * pt_br &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       dd/MM/yy HH:mm:ss z<br/>
	 * zh_cn &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       yy-M-d ah:mm:ss z<br/>
	 * zh_tw &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       yyyy/M/d ah:mm:ss z<br/>
	 * </p>
	 */
    DefaultPattern(DateFormatter.DateTimeStyle.Short,DateFormatter.DateTimeStyle.Medium,null,"default", true),
    /**
	 * Contained Year, Month, Date and Timezone.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>	
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d-MMM-yyyy z<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy z<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMMM yyyy z<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy z<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy z<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 z<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4 d\uC77C z<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy z<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 z<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 z<br/>
	 * </p>
	 */
    DateOfYMDOfTZPattern(DateFormatter.DateTimeStyle.Full,null,"GyMdz","DateOfYMDOfTZ", true),
    /**
	 * Contained Year, Month, Date.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d-MMM-yyyy<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMMM yyyy<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4 d\uC77C<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5<br/>
	 * </p>
	 */
    DateOfYMDPattern(DateFormatter.DateTimeStyle.Full,null,"GyMd","DateOfYMD", true),
    /**
	 * Contained Year, Month.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM yyyy<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM' de 'yyyy<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM yyyy<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM yyyy<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM yyyy<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM' de 'yyyy<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708<br/>
	 * </p>
	 */
    DateOfYMPattern(DateFormatter.DateTimeStyle.Full,null,"GyM","DateOfYM", true),
    /**
	 * Contained Year, Month.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM yyyy<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM.' de 'yyyy<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM. yyyy<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM yyyy<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM yyyy<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM' de 'yyyy<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708<br/>
	 * </p>
	 */
    DateOfYMPattern_ABBR(DateFormatter.DateTimeStyle.Long,null,"GyM","DateOfYM_ABBR", true),
    /**
	 * Contained Year, Month. (Only number)
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/yyyy<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/yyyy<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M.yyyy<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/yyyy<br/>
 	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/yyyy<br/>
 	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy/M<br/>
 	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy. M.<br/>
 	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MM/yyyy<br/>
 	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy-M <br/>
     * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy/M<br/>
	 * </p>
	 */
    DateOfYMPattern_NUM(DateFormatter.DateTimeStyle.Medium,null,"GyM","DateOfYM_NUM", true),
    /**
	 * Contained Month, Date. 
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM d<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMMM<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708d\u65E5<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\uBCC4 d\uC77C<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708d\u65E5<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708d\u65E5<br/>
	 * </p>
	 */
    DateOfMDPattern(DateFormatter.DateTimeStyle.Full,null,"Md","DateOfMD", true),
    /**
	 * Contained Month, Date. 
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM d<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMM.<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMM.<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMM<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMM<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708d\u65E5<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\uBCC4 d\uC77C<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMM<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708d\u65E5<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708d\u65E5<br/>
	 * </p>
	 */
    DateOfMDPattern_ABBR(DateFormatter.DateTimeStyle.Long,null,"Md","DateOfMD_ABBR", true),
    /**
	 * Contained Month, Date. (Only number)
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/d<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d/M<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d.M.<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d/M<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d/M<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/d<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M. d.<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d/M<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M-d<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M/d<br/>
	 * </p>
	 */
    DateOfMDPattern_NUM(DateFormatter.DateTimeStyle.Medium,null,"Md","DateOfMD_NUM", true),
    /**
	 * Contained Year.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74<br/>
	 * </p>
	 */
    DateOfYPattern(DateFormatter.DateTimeStyle.Full,null,"Gy","DateOfY", true),
    /**
	 * Contained Month.
	 * <p> 
	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMMM<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708<br/>
	 * </p>
	 */
    DateOfMPattern(DateFormatter.DateTimeStyle.Full,null,"M","DateOfM", true),
    /**
   	 * Contained Month.
   	 * <p> 
   	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
   	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM.<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM\u6708<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          MMM<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708<br/>
   	 * </p>
   	 */
    DateOfMPattern_ABBR(DateFormatter.DateTimeStyle.Long,null,"M","DateOfM_ABBR", true),
    /**
   	 * Contained Month. (Only number)
   	 * <p> 
   	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
   	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\uBCC4<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          M\u6708<br/>
   	 * </p>
   	 */
    DateOfMPattern_NUM(DateFormatter.DateTimeStyle.Medium,null,"M","DateOfM_NUM", true),
    /**
   	 * Contained Date. 
   	 * <p> 
   	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
   	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d\u65E5<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d\uC77C<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d\u65E5<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d\u65E5<br/>
   	 * </p>
   	 */
    DateOfDPattern(DateFormatter.DateTimeStyle.Full,null,"d","DateOfD", true),
    // DateTime
    /**
   	 * Contained Year, Month, Date, Hour, Minute. 
   	 * <p> 
   	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
   	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d-MMM-yyyy h:mm a<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy HH:mm<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMMM yyyy HH:mm<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy HH:mm<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy HH:mm<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 hh\u6642mm\u5206<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4 d\uC77C a h\uAC04 mm\uBD84<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy HH:mm<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 ahh时mm\u5206<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 ahh\u6642mm\u5206<br/>
   	 * </p>
   	 */
    DateTimeOfYMDHMAPattern(DateFormatter.DateTimeStyle.Full,DateFormatter.DateTimeStyle.Long,"GyMdHhma","DateTimeOfYMDHMA", true),
    /**
   	 * Contained Year, Month, Date, Hour, Minute and Timezone. 
   	 * <p> 
   	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
   	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d-MMM-yyyy h:mm a z<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy HH:mm z<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMMM yyyy HH:mm z<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy HH:mm z<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy HH:mm z<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 hh\u6642mm\u5206 z<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4 d\uC77C a h\uAC04 mm\uBD84 z<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy HH:mm z<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 ahh时mm\u5206 z<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 ahh\u6642mm\u5206 z<br/>
   	 * </p>
   	 */
    DateTimeOfYMDHMAOfTZPattern(DateFormatter.DateTimeStyle.Full,DateFormatter.DateTimeStyle.Long,"GyMdHhmaz","DateTimeOfYMDHMAOfTZ", true),
    /**
   	 * Contained Year, Month, Date, Hour, Minute, Second and Timezone. 
   	 * <p> 
   	 * Locale&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pattern<br/>
   	 * en&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d-MMM-yyyy h:mm:ss a z<br/>
	 * es&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy HH:mm:ss z<br/>
	 * de&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d. MMMM yyyy HH:mm:ss z<br/>
	 * fr&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy HH:mm:ss z<br/>
	 * it&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d MMMM yyyy HH:mm:ss z<br/>
	 * ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 hh\u6642mm\u5206ss\u79D2 z<br/>
	 * ko&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\uB144 M\uBCC4 d\uC77C a h\uAC04 mm\uBD84 ss\uCD08 z<br/>
	 * pt_br&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          d' de 'MMMM' de 'yyyy HH:mm:ss z<br/>
	 * zh_cn&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 ahh时mm\u5206ss\u79D2 z<br/>
	 * zh_tw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;          yyyy\u5E74M\u6708d\u65E5 ahh\u6642mm\u5206ss\u79D2 z<br/>
   	 * </p>
   	 */
    DateTimeOfYMDHMSAOfTZPattern(DateFormatter.DateTimeStyle.Full,DateFormatter.DateTimeStyle.Long,"GyMdHhmsaz","DateTimeOfYMDHMSAOfTZ", true);
    //To Be Extended in future
    

    @SuppressWarnings("compatibility:2736845654370475815")
    private static final long serialVersionUID = 1L;
    

    private DatePattern(DateFormatter.DateTimeStyle dateStyle, 
                        DateFormatter.DateTimeStyle timeStyle,
                        String contains, 
                        String name,
                        Boolean resBundle){
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        this.contains = contains;
        this.name = name;
        this.resBundle = resBundle;
    }
    /**
     * get the pattern
     * @param locale
     * @return
     */
    public String getPattern(Locale locale){
        // get pattern from locale data resource bundle
        if(this.resBundle) {
            try
            {
                String localePattern = LocaleDataHelper.getInstance().getPattern(this.name, locale);
                if(localePattern != null && localePattern != "") {
                    return localePattern;
                }
            } catch (MissingResourceException e)
            {
                // todo
            }
        }
        //
    	if(locale.getLanguage().equalsIgnoreCase("en")){
    		return this.getEnPattern();
    	}
        SimpleDateFormat sdf = (SimpleDateFormat)getJDKDateFormat(this.dateStyle,this.timeStyle,locale);
        String pattern = sdf.toPattern();
        if(this.contains!=null){   	
            //pattern = getSpecifyUnit(sdf.toPattern(), this.contains);
            if(!this.isCover(pattern, this.contains)){            	
                int rate = this.rateOfCover(pattern, this.contains);
            	String bestPattern = this.findBestPattern(this.contains,locale,rate);
            	if(bestPattern!=null)
            		pattern = bestPattern;
            }
            pattern = getSpecifyUnit(pattern, this.contains);
        }
        pattern = this.ExchangeYM(pattern, locale);
        return pattern;
    }
    /**
     * return en_US pattern
     * @return
     */
    private String getEnPattern(){	
    	String pattern = "dd-MMM-yyyy h:mm:ss a z";
        if(this.compareTo(DateOfYMDOfTZPattern)==0){
                pattern = "d-MMM-yyyy z";
        }else if(this.compareTo(DateOfYMDPattern)==0){
    		pattern = "d-MMM-yyyy";
    	}else if(this.compareTo(DateOfYMPattern)==0){
    		pattern = "MMMM yyyy";
    	}else if(this.compareTo(DateOfMDPattern)==0){
    		pattern = "MMM dd";
    	}else if(this.compareTo(DateOfYPattern)==0){
    		pattern = "yyyy";
    	}else if(this.compareTo(DateOfMPattern)==0){
    		pattern = "MMMM";
    	}else if(this.compareTo(DateOfDPattern)==0){
    		pattern = "d";
    	}else if(this.compareTo(DateTimeOfYMDHMAPattern)==0){
    		pattern = "d-MMM-yyyy h:mm a";
    	}else if(this.compareTo(DateTimeOfYMDHMAOfTZPattern)==0){
    		pattern = "d-MMM-yyyy h:mm a z";
    	}else if(this.compareTo(DateTimeOfYMDHMSAOfTZPattern)==0){
    		pattern = "d-MMM-yyyy h:mm:ss a z";
    	}
    	return pattern;
    }
    /**
     * Exchange the order of Year and Month
     * @return
     */
    private String ExchangeYM(String pattern,Locale locale){
    	if(pattern==null&&pattern.trim().equals(""))return pattern;
    	String retPattern = pattern;
    	if(this == DateOfYMPattern){
	    	Locale[] array = new Locale[]{new Locale("hr"),new Locale("hr","HR"),new Locale("ga"),
	    			new Locale("in"),new Locale("mt"),new Locale("mt","MT")};
	    	for(Locale l : array){
	    		if(l.equals(locale)){//exchange
	    			String[] pattArr = pattern.split(" ");
	    			String ret = "";
	    			for(int i=pattArr.length-1;i>=0;i--){
	    				ret = ret+" "+pattArr[i];
	    			}
	    			if(!ret.trim().equals(""))
	    				retPattern = ret.trim();
	    		}
	    	}
    	}
    	return retPattern;
    }
    /**
     * Judge whether have char of contains is not found in pattern
     * @param pattern
     * @param contains
     * @return
     */
    private boolean isCover(String pattern,String contains){
    	boolean ret = true;
    	if(contains!=null&&!contains.trim().equals("")){
    		pattern = pattern.toLowerCase();
    		contains = contains.toLowerCase();
	    	for(int i=0;i<contains.length();i++){
	    		char c = contains.charAt(i);
	    		if(pattern.indexOf(c)<0){
	    			return false;
	    		}
	    	}
    	}
    	return ret;
    }
    /**
     * get the rate of cover
     * @param pattern
     * @param contains
     * @return
     */
    private int rateOfCover(String pattern, String contains){
      int num = -1;
      if(contains!=null && !contains.trim().equals("")){
        pattern = pattern.toLowerCase();
        contains = contains.toLowerCase();
        for(int i=0;i<contains.length();i++){
          char c = contains.charAt(i);
          if(pattern.indexOf(c)>=0)
            num++;
        }
      }
      return num;
    }
    /**
     * return the best pattern
     * @param contains
     * @return
     */
    private String findBestPattern(String contains,Locale locale, int beforeRate){
        if(this.timeStyle==null)this.timeStyle = DateFormatter.DateTimeStyle.Default;
        if(this.dateStyle==null)this.dateStyle = DateFormatter.DateTimeStyle.Default;
        int num = beforeRate;
        String retPattern = null;
    	for(int i=0;i<2;i++){
    	  for(int j=0;j<2;j++){	
            if(this.dateStyle.getDateTimeStyle()==i&&this.timeStyle.getDateTimeStyle()==j)continue;
    			
    	    SimpleDateFormat sdf = (SimpleDateFormat)DateFormat.getDateTimeInstance(i, j, locale);
    	    String pattern = sdf.toPattern();
            int curNum = this.rateOfCover(pattern, contains);
            
    	    if(curNum > num){
    	      num = curNum;
              retPattern = pattern;
    	    }
    	  }
    	}
    	return retPattern;
    }
    /**
     * JDK pattern
     * @param locale
     * @return
     */
    public String getOriginalPattern(Locale locale){
    	SimpleDateFormat sdf = (SimpleDateFormat)getJDKDateFormat(this.dateStyle,this.timeStyle,locale);
        String pattern = sdf.toPattern();
        return pattern;
    }
    /**
     * get the name of pattern
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retain the needed unit from date time pattern
     * @param pattern
     * @param contain
     * @return
     */
    private String getSpecifyUnit(String pattern, String contain/*, Locale locale*/){
        if(pattern != null && contain != null){
            int length = pattern.length();
            //if(locale == null) locale = Locale.getDefault();
            //DateFormatSymbols dfs = new DateFormatSymbols(locale);
            String dic = "GyMdkHmsSEDFwWahKzZ";
            StringBuffer buf = new StringBuffer();
            boolean flag = true;
            boolean isAllwaysRemove=false;
            for(int i=0;i<length;i++){
                char c = pattern.charAt(i);
                if(!flag){
	                if(c=='\''){
	                	isAllwaysRemove = !isAllwaysRemove;
	                }
	                if(isAllwaysRemove)
	                {
	                	continue;
	                }
                }
                if(c==' ')
                	buf.append(c);
                else if(contain.indexOf(c) > -1){
                    buf.append(c);
                    for(i=i+1;i<length;i++){
                    	char cj = pattern.charAt(i);
                    	if(cj==c){
                    		buf.append(cj);}
                    	else{
                    		i--;
                    		break;
                    	}
                    }
                    flag = true;
                }else if(dic.indexOf(c) > -1){
                    flag = false;
                }else if(flag){
                	buf.append(c);
                	if(c=='\''){
                		for(i=i+1;i<length;i++){
                			char cj = pattern.charAt(i);
                			buf.append(cj);
                			if(cj=='\''){
                				break;
                			}
                		}
                	}
                }
            } 
            //remove the special symbols on fore and aft
            String specialSmbols = ",.:-";
            pattern = buf.toString();
            pattern = pattern.trim();
            flag = true;
            while(flag&&pattern.length()>0){
                flag = false;
                char first = pattern.charAt(0);
                if(specialSmbols.indexOf(first)>-1){
                    pattern = pattern.substring(1);
                    flag = true;
                }
                if(pattern.length()>0){
                    char last = pattern.charAt(pattern.length()-1);
                    if(specialSmbols.indexOf(last)>-1){
                        pattern = pattern.substring(0,pattern.length()-1);
                        flag = true;
                    }
                }
                pattern = pattern.trim();
            }
            while(pattern.contains("  ")){
                pattern = pattern.replaceAll("  ", " ");
            }
            pattern = pattern.replaceAll(": ", " ");
            return pattern;
        }
        return null;
    }
    /**
     * Get the DateFormat form jdk based date time Style
     * @param dateStyle
     * @param timeStyle
     * @param locale
     * @return
     */
    public static DateFormat getJDKDateFormat(DateFormatter.DateTimeStyle dateStyle,DateFormatter.DateTimeStyle timeStyle, Locale locale){
        DateFormat formatter = null;
        if(dateStyle != null && timeStyle != null){
           formatter = DateFormat.getDateTimeInstance(dateStyle.getDateTimeStyle(), timeStyle.getDateTimeStyle(), locale);
        }else if(dateStyle == null && timeStyle != null){
           formatter = DateFormat.getTimeInstance(timeStyle.getDateTimeStyle(), locale);
        }else if(timeStyle == null && dateStyle != null){
           formatter = DateFormat.getDateInstance(dateStyle.getDateTimeStyle(), locale);
        }else{
           formatter = DateFormat.getDateTimeInstance(DateFormatter.DateTimeStyle.Default.getDateTimeStyle(), DateFormatter.DateTimeStyle.Default.getDateTimeStyle(), locale);
        }
        return formatter;
    }
    
    private static Map<String, DatePattern> getEnumMap(){
        if(datePatternNameMap == null){
            datePatternNameMap = new HashMap<String, DatePattern>();
            for(DatePattern dp : DatePattern.values()){
                datePatternNameMap.put(dp.name.toLowerCase(), dp);
            }
        }
        return datePatternNameMap;
    }
    /**
     * get the DatePattern object from name
     * @param name
     * @return
     */
    public static DatePattern getValidPattern(String name){
        String key = name.toLowerCase();
        if(getEnumMap().containsKey(key))
            return getEnumMap().get(key);
        return DefaultPattern;
    }
        
    private static Map<String, DatePattern> datePatternNameMap = null;
    private DateFormatter.DateTimeStyle dateStyle;
    private DateFormatter.DateTimeStyle timeStyle;
    private String contains;
    private String name;
    private Boolean resBundle;
}
