package com.edwin.api.util.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpTest {
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
            
//            conn.setRequestProperty("Accept-Charset", "UTF-8");
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
	
	public static void main(String[] args){
		String url = "http://192.168.68.89:8180/itf/webservice/tpsMobileRest/dealMobileMesssage";
//		String url = "http://localhost:8088/tps/webservice/tpsMobileRest/dealMobileMesssage";
//		String content = "{\"type\": \"TPS_LOGIN_MOBILE\",\"requestEntity\": {\"deviceNumber\": \"88888888888888888888\",\"phoneNumber\": \"15555220001\",\"verifyCode\": \"15555220001\"}}";
		String content = "{\"type\": \"MOBILE_TRANSACTIONINFO_TPS\",\"requestEntity\": {\"deviceNumber\": \"88888888888888888888\",\"phoneNumber\": \"15555220001\",\"verifyCode\": \"15555220001\"}}";

//		String content = "{\"type\": \"TPS_LOGIN_MOBILE\",\"requestEntity\": {\"deviceNumber\": \"88888888888888888888\",\"phoneNumber\": \"18765432101\",\"verifyCode\": \"qqqqqq\"}}";
//		String content = "{\"type\": \"TPS_LOGIN_MOBILE\",\"requestEntity\": {\"deviceNumber\": \"88888888888888888888\",\"phoneNumber\": \"15666666666\",\"verifyCode\": \"qqqqqq\"}}";
//		String content = "{\"type\": \"MOBILE_QUERYINTENTIONLINE_TPS\",\"requestEntity\": {\"tokenid\": \"31f53cb0-70a4-4779-9118-e3478bf3a8b7\",\"telephoneNumber\": \"15666666666\"}}";
//		String content = "{\"type\": \"MOBILE_QUERYVEHICLEINFO_TPS\",\"requestEntity\": {\"tokenid\": \"b79cd14d-31d1-4ac1-86a7-7e20c9d0a9a4\",\"telephoneNumber\": \"15666666666\"}}";
//		String content = "{\"type\": \"MOBILE_QUERYDRIVERINFO_TPS\",\"requestEntity\": {\"tokenid\": \"b79cd14d-31d1-4ac1-86a7-7e20c9d0a9a4\",\"telephoneNumber\": \"15666666666\"}}";
//		String content = "{\"type\": \"MOBILE_QUERYINTENTIONLINE_TPS\",\"requestEntity\": {\"tokenid\": \"1aaa1d87-a38c-424a-93dc-53b2df6c2f80\",\"telephoneNumber\": \"1346461656\"}}";
//		String content = "{\"type\": \"MOBILE_FULLREGISTER_TPS\",\"requestEntity\": {\"tokenid\": \"88888888888888888888\",\"phoneNumber\": \"13916247646\",\"requestType\":\"\"}}";
//		String content = "{\"type\": \"MOBILE_FULLREGISTER_TPS\",\"requestEntity\": {"+
//				"\"vehicleNo\": \"FD8877225\",\"billNumber\":\"343434\",\"useType\":\"1\",\"tokenid\": \"bd974f74-20e0-4cdd-9722-9d77e9069565\",\"requestType\": \"3\",\"phoneNumber\": \"15822558800\","
//				+"\"lineCity\": [{\"destRegionName\": \"\",\"startRegionName\": \"xxx\",\"startRegionCode\": \"110000-1\",\"destRegionCode\": \"\"}]"
//				+"}}";
		
//		String content = "{\"type\": \"MOBILE_DRIVERINFO_TPS\",\"requestEntity\": {\"telephoneNumber\": \"15822558800\"}}";
//		String content = "{\"type\": \"TPS_LOGIN_MOBILE\",\"requestEntity\": {\"deviceNumber\": \"88888888888888888888\",\"phoneNumber\": \"15822558800\",\"verifyCode\": \"qqqqqq\"}}";
		
		
		
		/*String content = "{"
				+"\"type\": \"MOBILE_FULLREGISTER_TPS\","
			+"\"requestEntity\": {"
			+"\"phoneNumber\": \"18987654328\","
		+"\"logicState\": \"\","
		+"\"modifyDate\": \"\","
		+"\"modifyUser\": \"xxxxxxx\","
		+"\"isovertwo\": \"1\","
		+"\"isattached\": \"0\","
		+"\"isoverten\": \"0\","
		+"\"vehicleNo\": \"DDH5363\","
		+"\"billNumber\": \"CL-20160129-000005\","
		+"\"driverNo\": \"320200198008177639\","
		+"\"Idaddress\": \"zzzcccc\","
		+"\"customerNumber\": \"\","
		+"\"effectdate\": \"\","
		+"\"producingArea\": \"0\","
		+"\"driverName\": \"dsdfsas\","
		+"\"driverId\": \"\","
		+"\"isalreadyGPS\": \"0\","
		+"\"id\": \"\","
		+"\"tokenid\": \"323c912f-406b-46f8-933c-d57470537011\","
		+"\"requestType\": \"2\","
		+"\"model\": \"\","
		+"\"exPhoneNumber\": \"18987654328\","
		+"\"createDate\": \"\","
		+"\"createUser\": \"\","
		+"\"attachList\": [{"
		+"\"attachStr\": \"\","
		+"\"attachName\": \"xxxxxxxxx.png\""
		+"}, {"
		+"\"attachStr\": \"\","
			+"\"attachName\": \"xvvvvvvvvvvv.png\""
			+"}],"
			+"\"certdate\": \"\","
		+"\"isallrisks\": \"0\""
		+"}"
		+"}";*/
		try {
			content = new String(content.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(content);
		jsonPost(url, content);
	}
}
