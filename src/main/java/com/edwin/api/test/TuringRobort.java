package com.edwin.api.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import net.sf.json.JSONObject;

public class TuringRobort {
	public static String request(String httpArg) {
		String httpUrl = "http://apis.baidu.com/turing/turing/turing";
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?key=879a6cb3afb84dbf4fc84a1df2ab7319&info=" + httpArg+"&userid=eb2edb736";

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        // ÃÓ»ÎapikeyµΩHTTP header
	        connection.setRequestProperty("apikey",  "8d55f2eb9feeaa600bca00a05e9df50b");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	
	
	public static void main(String[] args){
		while(true){
			Scanner sc = new Scanner(System.in);
			String content = sc.nextLine();
			if("\\q".equals(content)){break;}
			String httpArg = "";
			try {
				httpArg = URLEncoder.encode(content,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonResult = request(httpArg);
			JSONObject json = JSONObject.fromObject(jsonResult);
			System.out.println(json.toString());
		}
	}
}
