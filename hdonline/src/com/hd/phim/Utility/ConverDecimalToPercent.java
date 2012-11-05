/**
 * 
 */
package com.hd.phim.Utility;

/**
 * @author hdtua_000
 *
 */
public class ConverDecimalToPercent {

	public static String converDecimalToPercent(String decimal) {
		float temp = Float.parseFloat(decimal) * 10;
		return ""+(int)temp ;
	}

}
