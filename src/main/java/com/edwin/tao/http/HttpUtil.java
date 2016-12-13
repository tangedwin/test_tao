package com.edwin.tao.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.edwin.tao.util.Logger;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpUtil {
	
	/*public static Document getContent(String web, String goodsName, int num){
		return getContent(web, goodsName, num, null);
	}
	public static Document getContent(String web, String goodsName, int num, String sort){
		return getContent(web, goodsName, num, sort, false);
	}
	public static Document getContent(String web, String goodsName, int num, String sort, boolean postFee){
		return getContent(web, goodsName, num, sort, postFee, null, null);
	}
	public static Document getContent(String web, String goodsName, int num, String sort, Double startPrice, Double endPrice){
		return getContent(web, goodsName, num, sort, false, startPrice, endPrice);
	}
	public static Document getContent(String web, String goodsName, int num, String sort, boolean postFee, Double startPrice, Double endPrice){
		return getContent(web, goodsName, num, sort, postFee, false, false, startPrice, endPrice,false,false,false,false);
	}*/
	public static Document getContent(String web, String goodsName, int num, String sort, boolean postFee, 
			boolean miaosha, boolean wwonline, Double startPrice, Double endPrice, boolean supportCod,
			boolean globalbuy, boolean filterFineness, boolean tianmao){
		Document itemDoc = null;
		String URL = getUrlStr(web, goodsName, num, sort, postFee, miaosha, wwonline, startPrice, endPrice, supportCod, globalbuy, filterFineness, tianmao);

		Logger.getInstance().appendText("正在请求链接"+URL);
		try {
			if(URL!=null){
				System.out.println(URL);
				itemDoc = Jsoup.connect(URL).timeout(5000).get();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return itemDoc;
	}
	
	
	
	
	
	public static Document getContent1(String web, String goodsName, int num, String sort, boolean postFee, 
			boolean miaosha, boolean wwonline, Double startPrice, Double endPrice, boolean supportCod,
			boolean globalbuy, boolean filterFineness, boolean tianmao){
		
		String URL = getUrlStr(web, goodsName, num, sort, postFee, miaosha, wwonline, startPrice, endPrice, supportCod, globalbuy, filterFineness, tianmao);

		Logger.getInstance().appendText("正在请求链接"+URL);
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		try {
			HtmlPage page = webClient.getPage(URL);
			return new Document(page.asXml());
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	public static Document getContent2(String web, String goodsName, int num, String sort, boolean postFee, 
			boolean miaosha, boolean wwonline, Double startPrice, Double endPrice, boolean supportCod,
			boolean globalbuy, boolean filterFineness, boolean tianmao){
		Document itemDoc = null;            	
		String URL = getUrlStr(web, goodsName, num, sort, postFee, miaosha, wwonline, startPrice, endPrice, supportCod, globalbuy, filterFineness, tianmao);
		Logger.getInstance().appendText("正在请求链接"+URL);
		HttpGet httpGet = new HttpGet(URL);
		try { 
        	@SuppressWarnings("deprecation")
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){ 
            	HttpEntity httpEntity = httpResponse.getEntity();  
                String result = EntityUtils.toString(httpEntity);
                itemDoc = new Document(result);
            }else{
            	httpGet.abort(); 
            } 
        }catch (ClientProtocolException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return itemDoc;  
	}
	
	
	
	private static String getUrlStr(String web, String goodsName, int num, String sort, boolean postFee, 
			boolean miaosha, boolean wwonline, Double startPrice, Double endPrice, boolean supportCod,
			boolean globalbuy, boolean filterFineness, boolean tianmao){
		String URL = null;
		
		if(AnalyseHtml.TMALL.equals(web)){
			URL = "https://list.tmall.com/search_product.htm?q="+goodsName+"&s="+num;
			
			if(sort!=null) URL = URL + "&sort="+sort;
			if(postFee) URL = URL + "&post_fee=-1";
			if(miaosha) URL = URL + "&miaosha=1";
			if(wwonline) URL = URL + "&wwonline=1";
			//sarea_code=六位地区编码 地区
			if(startPrice!=null) URL = URL + "&start_price="+startPrice;
			if(endPrice!=null) URL = URL + "&start_price="+endPrice;
		}else if(AnalyseHtml.TAOBAO.equals(web)){
			URL = "https://s.taobao.com/search?q="+goodsName+"&s="+num;
			if(sort!=null) URL = URL + "&sort="+sort;
			if(postFee) URL = URL + "&baoyou=1";
			if(miaosha) URL = URL + "&miaosha=1";
			if(wwonline) URL = URL + "&wwonline=1";
			if(supportCod) URL = URL + "&support_cod=1";
			if(globalbuy) URL = URL + "&globalbuy=1";
			if(filterFineness) URL = URL + "&filterFineness=1";
			if(tianmao) URL = URL + "&filter_tianmao=tmall";
			
			//loc=甘肃 地区
			//filter=reserve_price%5B12%2C34%5D
			//filter=reserve_price[12,34] 价格范围
			if(startPrice!=null) URL = URL + "&start_price="+startPrice;
			if(endPrice!=null) URL = URL + "&start_price="+endPrice;
		}
		return URL;
	}
	
	
	
	
	public static byte[] getImageFromURL(String urlPath) { 
        byte[] data = null; 
        InputStream is = null; 
        HttpURLConnection conn = null; 
        try { 
            URL url = new URL(urlPath); 
            conn = (HttpURLConnection) url.openConnection(); 
            conn.setDoInput(true); 
            // conn.setDoOutput(true); 
            conn.setRequestMethod("GET"); 
            conn.setConnectTimeout(6000);
            is = conn.getInputStream(); 
            if(is==null) return null;
            if (conn.getResponseCode() == 200) { 
                data = readInputStream(is); 
            } else{ 
                data=null; 
            } 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                if(is != null){ 
                    is.close(); 
                }                
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
            conn.disconnect();           
        } 
        return data; 
    } 
	public static byte[] readInputStream(InputStream is) { 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        byte[] buffer = new byte[1024]; 
        int length = -1; 
        try { 
            while ((length = is.read(buffer)) != -1) { 
                baos.write(buffer, 0, length); 
            } 
            baos.flush(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        byte[] data = baos.toByteArray(); 
        try { 
            is.close(); 
            baos.close(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return data; 
    } 
}
