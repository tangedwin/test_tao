package com.edwin.http.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;

public class HttpUtil {
	public static byte[] jsonPost(String urlPath, String content) { 
        byte[] data = content.getBytes(); 
        HttpURLConnection conn = null; 
        try { 
            URL url = new URL(urlPath); 
            conn = (HttpURLConnection) url.openConnection(); 
            conn.setDoInput(true); 
            // conn.setDoOutput(true); 
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(6000);
            
            
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setConnectTimeout(5*1000);
            conn.setDoOutput(true);
            OutputStream outStream = conn.getOutputStream();
            outStream.write(data);
            outStream.flush();
            outStream.close();
            
            if (conn.getResponseCode() == 200) { 
            	InputStream inStream = conn.getInputStream();  
            	StringBuffer str = new StringBuffer();
                byte[] b = new byte[4096];
                for(int n; (n = inStream.read(b)) != -1;){
                	str.append(new String(b, 0, n, "utf-8"));
                }
                System.out.println(str.toString());
                inStream.close();
            } else{
            	System.out.println("wrong "+ conn.getResponseCode());
            } 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {
            conn.disconnect();           
        } 
        return data; 
    } 
	
	
	
	
}
