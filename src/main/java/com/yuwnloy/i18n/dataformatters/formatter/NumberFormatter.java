package com.yuwnloy.i18n.dataformatters.formatter;
import java.util.Locale;

import java.text.NumberFormat;
import java.text.ParseException;
import java.math.BigDecimal;

import java.math.RoundingMode;

import java.text.DecimalFormat;

/**
 * Used to formatter number. Contained Integer, Double, Currency and Percent.
 * @author xiaoguang.gao
 *
 */
public class NumberFormatter implements IFormatter{
    
    public enum NumberType{
        Double,Currency,Integer,Number,Percent;
    };
  
    private Locale locale = Locale.getDefault();
    private String pattern = "#0.00";
    private int scale = 0;
    private NumberType numberType = NumberType.Double;
    private int maximumFractionDigits = 13;
    private int minimumFractionDigits = 0;

    /**
     * 
     * @param numberType
     *     Specify Number Type. Scope: Double,Currency,Integer,Number,Percent
     * @param pattern
     *     Specify the pattern for formatter. Such as <code>'#0.00'</code>
     * @param locale
     *     Specify locale.
     */
    private NumberFormatter(NumberType numberType,String pattern, Locale locale) {
        super();
        if(numberType != null)
            this.numberType = numberType;
        if(locale != null)
            this.locale = locale;
        if(pattern != null)
            this.pattern = pattern;
    }

    /**
     * Get the default number formatter.
     * Default is double type.
     * @return
     */
    public static NumberFormatter getInstance() {
        return new NumberFormatter(NumberType.Double,null, Locale.getDefault());
    }

    /**
     * Get the number formatter. Default is double type.
     * @param locale
     *     Specify the locale.
     * @return
     */
    public static NumberFormatter getInstance(Locale locale) {
        return new NumberFormatter(NumberType.Double,null, locale);
    }

    /**
     * Get the number formatter.
     * @param pattern
     *     Specify the pattern for formatter. Such as <code>'#0.00'</code>
     * @param locale
     *     Specify the locale.
     * @return
     */
    public static NumberFormatter getInstance(String pattern, Locale locale) {
        return new NumberFormatter(NumberType.Double,pattern, locale);
    }
    
    /**
     * Get the number formatter.
     * @param numberType
     *     Specify Number Type. Scope: Double,Currency,Integer,Number,Percent
     * @param pattern
     *     Specify the pattern for formatter. Such as <code>'#0.00'</code>
     * @param locale
     *     Specify the locale.
     * @return
     */
    public static NumberFormatter getInstance(NumberType numberType,String pattern, Locale locale){
        return new NumberFormatter(numberType,pattern, locale);
    }
    /**
     * Get the number formatter.
     * @param numberType
     *     Specify Number Type. Scope: Double,Currency,Integer,Number,Percent
     * @param locale
     *     Specify the locale.
     * @return
     */
    public static NumberFormatter getInstance(NumberType numberType, Locale locale){
        return new NumberFormatter(numberType,null, locale);
    }
    /**
     * 
     * @param val
     * @return
     */
    private String formatBase(Object val) {
        if (val != null) {
        	NumberFormat nf = this.getNumberFormat();
        	nf.setMaximumFractionDigits(maximumFractionDigits);
        	nf.setMinimumFractionDigits(minimumFractionDigits);
            if(scale < 1){
            	return nf.format(val);	
            } else{
            	return nf.format(this.getScaledNumber(val));
            }
            	
                
        }
        return null;
    }
    /**
     * Format number object to string
     * @param obj
     */
    public String format(Object obj) {
        return formatBase(obj);
    }
    /**
     * Format double number to string
     * @param d
     */
    public String format(double d) {
        return formatBase(d);
    }
    /**
     * Format long number to string
     * @param l
     */
    public String format(long l) {
        return formatBase(l);
    }
    /**
     * Based a number object(such as 0.23) to get its scale.
     * @param val
     *     An number object.
     * @return
     */
    public Double getScaledNumber(Object val) {
        Double dval = Double.valueOf(val.toString());
        BigDecimal bd = new BigDecimal(dval);
        int newScale = scale + bd.scale() - bd.precision();
        BigDecimal newbd = bd.setScale(newScale, RoundingMode.HALF_UP);

        return newbd.doubleValue();
    }
    /**
     * Parse a number string to number object.
     * @param source
     */
    public Object parse(String source){
        Object ret = null;
        try {
            ret = this.getNumberFormat().parse(source);
        } catch (ParseException e) {
          
        }
        return ret;
    }
    /**
     * 
     * @return
     */
    private NumberFormat getNumberFormat(){
        NumberFormat formatter = null;
        if(this.numberType == NumberType.Currency){
            formatter = NumberFormat.getCurrencyInstance(this.getLocale());
        }else if(this.numberType == NumberType.Integer){
            formatter = NumberFormat.getIntegerInstance(this.getLocale());
        }else if(this.numberType == NumberType.Number){
            formatter = NumberFormat.getNumberInstance(this.getLocale());
        }else if(this.numberType == NumberType.Percent){
            formatter = NumberFormat.getPercentInstance(this.getLocale());
        }else{
            //DecimalFormatSymbols symbols = new DecimalFormatSymbols(this.getLocale());
            //formatter = new DecimalFormat(this.getPattern(),symbols);
            formatter = NumberFormat.getInstance(this.getLocale());
        }
        return formatter;
    }
    /**
     * Get the char used to separate the group. 
     * Example: 123,456,789
     *   The separator char is ','.
     * @return
     */
    public char getGroupingSeparator() {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(this.locale);
        return format.getDecimalFormatSymbols().getGroupingSeparator();
    }
    /**
     * Get the char used to separate the decimal.
     * Example: 0.25
     *    The separator char is '.'. 
     * @return
     */
    public char getDecimalSeparator() {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(this.locale);
        return format.getDecimalFormatSymbols().getDecimalSeparator();
    }
    /**
     * Set the scale used to specify the length of string after format. 
     * @param digit
     * @return
     */
    public NumberFormatter setScale(int digit) {
        this.scale = digit;
        return this;
    }
    /**
     * Get the scale.
     * @return
     */
    public int getScale() {
        return scale;    
    }
    /**
     * Set the length of fraction part.
     * @param digit
     *    The length of fraction part.
     * @return
     */
    public NumberFormatter setFractionDigits(int digit) {
        this.setMinimumFractionDigits(digit);
        this.setMaximumFractionDigits(digit);
        return this;
    }
    /**
     * Set the max length of fraction part.
     * @param newValue
     */
    public void setMaximumFractionDigits(int newValue) {
        maximumFractionDigits = Math.max(0,newValue);
        if (maximumFractionDigits < minimumFractionDigits) {
            minimumFractionDigits = maximumFractionDigits;
        }
    }
    /**
     * Get the max length of fraction part.
     * @return
     */
    public int getMaximumFractionDigits() {
        return maximumFractionDigits;
    }
    /**
     * Set the min length of fraction part.
     * @param newValue
     */
    public void setMinimumFractionDigits(int newValue) {
        minimumFractionDigits = Math.max(0,newValue);
        if (maximumFractionDigits < minimumFractionDigits) {
            maximumFractionDigits = minimumFractionDigits;
        }
    }
    /**
     * Get the min length of fraction part.
     * @return
     */
    public int getMinimumFractionDigits() {
        return minimumFractionDigits;
    }
    /**
     * Set the current locale.
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    /**
     * Get the current locale.
     * @return
     */
    public Locale getLocale() {
        return locale;
    }
    /**
     * Set current pattern.
     * @param
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    /**
     * Get current pattern.
     * @return
     */
    public String getPattern() {
        /*if(this.pattern == null){
            ResourceBundle rb = LocaleData.getNumberFormatData(this.getLocale());
            String[] all = rb.getStringArray("NumberPatterns");
            pattern = all[0];
        }*/
        return pattern;
    }
}

