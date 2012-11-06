/**
 * 
 */
package com.hd.phim.Utility;

import java.util.regex.Pattern;

/**
 * @author hdtua_000
 *
 */
public class DataUtils {
	 public static final Pattern EMAIL_ADDRESS_PATTERN
	    = Pattern.compile( "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + 
	    		"\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
	      );
	/**
	 * method is used for checking valid email id format.
	 * 
	 * @param email
	 * @return boolean true for valid false for invalid
	 */
	public static boolean isEmailValid(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
}
