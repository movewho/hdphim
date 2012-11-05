/**
 * 
 */
package com.hd.phim.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hd.phim.Utility.Configs;

import android.util.Log;

/**
 * This class get data from server. response server information is JSON data.
 * connection with socket 80. use method POST and GET	
/**
 * @author nguyenquocchinh
 *
 */
public class GetDataJsonFromServer {

	/**
	 * 
	 * @param url
	 * @param params
	 * @param socketFactory
	 * @param userAgent
	 * @return
	 * @throws SSLPeerUnverifiedException
	 */
	public static JSONArray getJSONfromURL(String url, List<NameValuePair> params ,int socketFactory, String userAgent) throws SSLPeerUnverifiedException{
		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
	    try{
	    		SchemeRegistry scheme = new SchemeRegistry();
	    		scheme.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), socketFactory));
	    		
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpProtocolParams.setUserAgent(httpclient.getParams(), userAgent);
	            
//	            HttpParams para = new BasicHttpParams();
//	            
//	            int len = params.size();
//	            for (int i = 0; i < len; i++) {
//	            	para.setParameter(params.get(i).getName(), params.get(i).getValue());
//				}
//	            
//	            httpget.setParams(para);
	            
	            if(!url.endsWith("?"))
	                url += "?";
	            String paramString = URLEncodedUtils.format(params, "utf-8");

	            HttpGet httpget = new HttpGet(url + paramString);
	            HttpResponse response = httpclient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            

	    }catch(Exception e){
	            Log.e(Configs.TAG_LOG, "Error in http connection "+e.toString());
	    }
	    
	  //convert response to string
	    try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            result=sb.toString();
	    }catch(Exception e){
	            Log.e(Configs.TAG_LOG, "Error converting result "+e.toString());
	    }
	    
	    try{
	    	
            jArray = new JSONArray(result);            
	    }catch(JSONException e){
	            Log.e(Configs.TAG_LOG, "Error parsing data "+e.toString());
	    }
    
	    return jArray;
	}

}