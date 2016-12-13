package com.edwin.tao.http;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.MalformedURLException;  
import java.net.URL;  
  
/** 
 * ��������̨������Ԥ��API  
 * */  
public class AreaCodeUtil {  
      
        /** 
         * ��ȡ�����й� ʡ�ݼ�һ������ 
         * */  
        public String weather() {  
                // TODO Auto-generated method stub  
                String ws_url = "http://m.weather.com.cn/data5/city.xml";  
                String str= "";  
                try {  
                    URL url = new URL(ws_url);  
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));//�����������  
                    StringBuffer sb = new StringBuffer();  
                    String s = "";  
                    while ((s = br.readLine()) != null) {  
                        sb.append(s + "\r\n"); //�����ݶ�ȡ��StringBuffer��  
                    }  
                    br.close();  
                    //System.out.println(sb.toString()); ��Ļ  
                    str = new String(sb.toString().getBytes());  
                } catch (MalformedURLException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                return str;  
        }  
          
        /** 
         * ���ݴ��������ȡ�������� 
         * */  
        public String secondCity(String id){  
            String ws_url = "http://m.weather.com.cn/data5/city"+id+".xml";  
            String str= "";  
            try {  
                URL url = new URL(ws_url);  
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));//�����������  
                StringBuffer sb = new StringBuffer();  
                String s = "";  
                while ((s = br.readLine()) != null) {  
                    sb.append(s + "\r\n"); //�����ݶ�ȡ��StringBuffer��  
                }  
                br.close();  
                //System.out.println(sb.toString()); ��Ļ  
                str = new String(sb.toString().getBytes());  
            } catch (MalformedURLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            return str;  
        }  
        /** 
         * ���ݴ�������õ���������Ԥ����ϢID, ��ʵҲ����ֱ�ӵ������淽����Ϊ��ⷽ�㣬�ʶ��һ�� 
         * */  
        public String weatherCityId(String id){  
            String ws_url = "http://m.weather.com.cn/data5/city"+id+".xml";  
            String str= "";  
            try {  
                URL url = new URL(ws_url);  
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));//�����������  
                StringBuffer sb = new StringBuffer();  
                String s = "";  
                while ((s = br.readLine()) != null) {  
                    sb.append(s + "\r\n"); //�����ݶ�ȡ��StringBuffer��  
                }  
                br.close();  
                //System.out.println(sb.toString()); ��Ļ  
                str = new String(sb.toString().getBytes());  
            } catch (MalformedURLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            return str;  
        }  
    public static void main(String[] args) {  
    	AreaCodeUtil w=new AreaCodeUtil();  
        String[] strArray=w.weather().split(",");  
        for(int i=0;i<strArray.length;i++){  
            String[] strArr=strArray[i].split("\\|");  
            String[] strArray2=w.secondCity(strArr[0]).split(",");  
            for(int j=0;j<strArray2.length;j++){  
                System.out.println(strArray2[j]);
               /* String[] strArray3=w.weatherCityId(strArray2[j].split("\\|")[0]).split(",");  
                for(int m=0;m<strArray3.length;m++){  
                    System.out.println(strArray3[m].split("\\|")[1]+"  "+w.weatherCityId(strArray3[m].split("\\|")[0]).split("\\|")[1]);  
                }  */
            } 
              
        }  
    }  
}  