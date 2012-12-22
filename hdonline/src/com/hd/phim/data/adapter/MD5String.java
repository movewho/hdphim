package com.hd.phim.data.adapter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import thanhpham.crypto.*;

public class MD5String {
	
	public String ConverStringToMD5()
	{
		String str = null;
		String strdate = null;
		
		Date date = new Date();
		
		    String s = String.valueOf(date.getTime()/1000);
		    String a = s+"apihdo-!@#$%^";
		    String res = getMD5(a);
		    String md5 = s+"-"+res;
		    //java.lang.System.out.println(md5);
		    Log.w("thanhan",md5);
		    return md5;
		
	}
	
	 public static String getMD5(String input) {
	      
	            MessageDigest md;
				try {
					md = MessageDigest.getInstance("MD5");
					byte[] messageDigest = md.digest(input.getBytes());
		            BigInteger number = new BigInteger(1, messageDigest);
		            String hashtext = number.toString(16);
		            // Now we need to zero pad it if you actually want the full 32 chars.
		            while (hashtext.length() < 32) {
		                hashtext = "0" + hashtext;
		            }
		            return hashtext;
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
				return "Loi";
	        
	    }
}
