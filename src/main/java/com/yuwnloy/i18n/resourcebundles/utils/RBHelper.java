package com.yuwnloy.i18n.resourcebundles.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author xiaoguang.gao
 *
 * @date May 24, 2016
 */
public class RBHelper {
	public static void main(String[] args){
		
	}
	/**
	 * get locale list from accept-language
	 * @param acceptHeader
	 * @return
	 */
	public static List<Locale> ParseAcceptLanguages(String acceptHeader) {
		List<Locale> list = new ArrayList<Locale>();
		if(acceptHeader!=null && !acceptHeader.trim().equals("")){
			String[] unitArray = acceptHeader.split(",");
			//sort the locale based on q-value
			for(int i=0;i<unitArray.length;i++){
				for(int j=i+1;j<unitArray.length;j++){
					double iq = ParseQValue(unitArray[i].split(";"));					
					double jq = ParseQValue(unitArray[j].split(";"));
					if(iq<jq){
						String tmp = unitArray[i];
						unitArray[i] = unitArray[j];
						unitArray[j] = tmp;
					}
				}
			}
			for (String str : unitArray) {
				String[] arr = str.trim().replace("-", "_").split(";");
				// Parse the locale
				Locale locale = getLocaleFromStr(arr[0]);
	
				// Parse the q-value
				//Double q = ParseQValue(arr);
				
				if (locale != null)
					list.add(locale);
				// Print the Locale and associated q-value
				/*System.out.println(q + " - " + arr[0] + "\t "
						+ locale.getDisplayLanguage());*/
			}
		}
		return list;
	}
	private static double ParseQValue(String[] localeUnit){
		Double q = 1.0D;
		for (String s : localeUnit) {
			s = s.trim();
			if (s.startsWith("q=")) {
				q = Double.parseDouble(s.substring(2).trim());
				break;
			}
		}
		return q;
	}
	

	/**
	 * parse locale string to Locale object. such as en_US
	 * 
	 * @param localeStr
	 * @return
	 */
	public static Locale getLocaleFromStr(String localeStr) {
		Locale locale = null;
		if(localeStr==null)return null;
		if(localeStr.trim().equals("*")) return new Locale("en");
		String[] l = localeStr.split("_");
		switch (l.length) {
		case 2:
			locale = new Locale(l[0], l[1]);
			break;
		case 3:
			locale = new Locale(l[0], l[1], l[2]);
			break;
		default:
			locale = new Locale(l[0]);
			break;
		}
		return locale;
	}
}
