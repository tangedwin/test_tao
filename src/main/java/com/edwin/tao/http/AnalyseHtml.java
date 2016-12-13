package com.edwin.tao.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edwin.tao.pojo.SearchData;
import com.edwin.tao.util.DataInfo;
import com.edwin.tao.util.Logger;


public class AnalyseHtml {
	public static final String TAOBAO = "taobao";
	public static final String TMALL = "tmall";
	
	private List<String[]> alllist;
	private String[] titles;
	private String file = "D://308605/Downloads/file/";
	private String fileType = "xls";
	
	private int pagelimit = 0;
	private int linelimit = 0;
	private int linelimitbypage = 0;
	
	
	public AnalyseHtml(){
		file = file+GenerateFile.sdf.format(new Date())+".xls";
		alllist = new ArrayList<String[]>();
		List<String> liststr = new ArrayList<String>();
		liststr.add("id");
		liststr.add("shopName");
		liststr.add("price");
		liststr.add("cjl");
		liststr.add("location");
		liststr.add("sellerCredit");
		liststr.add("totalRate");
		liststr.add("title");
		liststr.add("picUrl");
		liststr.add("detailUrl");
		liststr.add("shopUrl");
		titles = liststr.toArray(new String[liststr.size()]);
	}
	
	public AnalyseHtml(String filePath, String fileName, int pagelimit, int linelimit){
		if(fileName!=null){
			if(fileName.endsWith(".csv")) fileType = "csv";
			else if(fileName.endsWith(".xls")) fileType = "xls";
			else fileName = GenerateFile.sdf.format(new Date())+".xls";
		}else{
			fileName = GenerateFile.sdf.format(new Date())+".xls";
		}
		
		GenerateFile.checkDis(filePath);
		
		file = (filePath==null?file:filePath) + "/" + fileName;
		alllist = new ArrayList<String[]>();
		
		this.pagelimit = pagelimit;
		this.linelimit = linelimit;
		List<String> liststr = new ArrayList<String>();
		liststr.add("id");
		liststr.add("shopName");
		liststr.add("price");
		liststr.add("cjl");
		liststr.add("location");
		liststr.add("sellerCredit");
		liststr.add("totalRate");
		liststr.add("title");
		liststr.add("picUrl");
		liststr.add("detailUrl");
		liststr.add("shopUrl");
		titles = liststr.toArray(new String[liststr.size()]);
		
	}
	
	public List<String[]> formatTmall(Document itemDoc){
		Logger.getInstance().appendText("解析天猫请求数据");
		List<String[]> list = new ArrayList<String[]>();
		Elements eles = itemDoc.getElementsByClass("product");
		if(eles!=null) for(Element ele:eles){
			if(ele==null) continue;
			
			List<String> liststr = new ArrayList<String>();
			String id=null,price=null,cjl=null/*成交量*/,picUrl=null,title=null,detailUrl=null,
					shopName=null,shopUrl=null;
			String loc=null,totalRate=null,sellerCredit=null;
			
			
			id = ele.attr("data-id");
			Elements eles1 = ele.getElementsByTag("em");
			if(eles1!=null) for(Element ele1:eles1){
				if(ele1!=null && !StringUtils.isBlank(ele1.attr("title"))){
					price=ele1.attr("title");
				}
				else if(ele1!=null && !StringUtils.isBlank(ele1.html())){
					cjl="月成交"+ele1.html();
				}
			}
			Elements eles2 = ele.getElementsByTag("a");
			if(eles2!=null) for(Element ele2:eles2){
				if(ele2!=null && !StringUtils.isBlank(ele2.attr("title"))){
					title=ele2.attr("title");
					detailUrl="https:"+ele2.attr("href");
				}
				else if(ele2!=null && ele2.hasClass("productShop-name")){
					shopName = ele2.html();
					shopUrl = "https:"+ele2.attr("href");
				}
				else if(ele2!=null && ele2.hasClass("productImg")){
					Elements eles21 = ele.getElementsByTag("img");
					if(eles21!=null) for(Element ele21:eles21){
						if(ele21!=null){
							if(!StringUtils.isBlank(ele21.attr("data-ks-lazyload"))) 
								picUrl="http:"+ele21.attr("data-ks-lazyload").replace("_b.jpg", "").replace("_30x30.jpg", "");
							if(!StringUtils.isBlank(ele21.attr("src"))) picUrl="http:"+ele21.attr("src");
						}
					}
				}
			}
			
			liststr.add(id);liststr.add(shopName);liststr.add(price);liststr.add(cjl);
			liststr.add(loc);liststr.add(sellerCredit);liststr.add(totalRate);
			liststr.add(title);liststr.add(picUrl);liststr.add(detailUrl);liststr.add(shopUrl);
			
			
			list.add(liststr.toArray(new String[liststr.size()]));

			Logger.getInstance().appendText("\t解析得到数据"+id+"["+title+"]");
			if(linelimitbypage>0 && list.size()>=linelimitbypage) break;
		}
		Logger.getInstance().appendText("本次解析"+list.size()+"条数据");
		return list;
	}
	
	
	public List<String[]> formatTaobao(Document itemDoc){
		Logger.getInstance().appendText("解析淘宝请求数据");
		List<String[]> list = new ArrayList<String[]>();
		try {
			Elements eles = itemDoc.getElementsByTag("script");
			for(Element ele:eles){
				if(ele!=null && ele.html().contains("g_page_config")){
					String[] strs = ele.html().split("\n");
					for(String str:strs){
						if(str.contains("g_page_config")){
							str = str.replace("g_page_config = ", "").replace("};", "}");
							JSONObject json = JSONObject.fromObject(str);
							if(json==null || json.isNullObject()) continue; 
							JSONObject mods = json.getJSONObject("mods");
							if(mods==null || mods.isNullObject()) continue; 
							JSONObject itemlist = mods.getJSONObject("itemlist");
							if(itemlist==null || itemlist.isNullObject()) continue; 
							JSONObject data = itemlist.getJSONObject("data");
							if(data==null || data.isNullObject()) continue; 
							if((data.get("auctions")==null) || !(data.get("auctions") instanceof JSONArray)) continue;
							JSONArray auctions = data.getJSONArray("auctions");
							if(auctions==null) continue; 
							for(Object object:auctions){
								List<String> liststr = new ArrayList<String>();
								String id=null,price=null,cjl=null/*成交量*/,picUrl=null,title=null,detailUrl=null,
										shopName=null,shopUrl=null;
								
								String loc=null,totalRate=null,sellerCredit=null;
								
								JSONObject jsonObj = JSONObject.fromObject(object);
								id = jsonObj.getString("nid");
								price = jsonObj.getString("view_price");
								loc = jsonObj.getString("item_loc");
								title = jsonObj.getString("raw_title");
								cjl = jsonObj.getString("view_sales");
								
								shopName = jsonObj.getString("nick");

								JSONObject shopcard = jsonObj.getJSONObject("shopcard");
								if(shopcard!=null){
									if(shopcard.get("sellerCredit")!=null) sellerCredit = shopcard.getString("sellerCredit");
									if(shopcard.get("totalRate")!=null) totalRate = shopcard.getString("totalRate");
								}
								picUrl = "http:"+jsonObj.getString("pic_url");
								detailUrl = "https:"+jsonObj.getString("detail_url");
								shopUrl = "https:"+jsonObj.getString("shopLink");
								
								liststr.add(id);liststr.add(shopName);liststr.add(price);liststr.add(cjl);
								liststr.add(loc);liststr.add(sellerCredit);liststr.add(totalRate);
								liststr.add(title);liststr.add(picUrl);liststr.add(detailUrl);liststr.add(shopUrl);

								list.add(liststr.toArray(new String[liststr.size()]));
								Logger.getInstance().appendText("\t解析得到数据"+id+"["+title+"]");
								if(linelimitbypage>0 && list.size()>=linelimitbypage) break;
							}
						}
						else continue;
					}
				}
				else continue;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.getInstance().appendText("本次解析"+list.size()+"条数据");
		return list;
	}
	
	public List<String[]> formatData(String web, Document itemDoc){
		if(AnalyseHtml.TAOBAO.equals(web)) return formatTaobao(itemDoc);
		else if(AnalyseHtml.TMALL.equals(web)) return formatTmall(itemDoc);
		else return null;
	}

	
	private Document getContent(String web, SearchData sd, int sum){
		String goodsName = "";
		try {
			goodsName = URLEncoder.encode(sd.getGoodsName(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HttpUtil.getContent(web,goodsName,sum,sd.getSort(),sd.isPostFee(),sd.isMiaosha(), sd.isWwonline(), sd.getStartPrice(),
				sd.getEndPrice(),sd.isSupportCod(),sd.isGlobalbuy(),sd.isFilterFineness(),sd.isTianmao());
	}
	
	public void saveData(DataInfo source, SearchData sd, boolean downloadPic){
		int num=-1, sum = 0;
		List<String[]> list = new ArrayList<String[]>();
		int i=0;
		
		
		while(num!=0 && Logger.getInstance().isStart()){
			if(pagelimit!=0 && pagelimit<=i){break;}
			if(linelimit!=0){
				if(linelimit<=sum){break;}
				else {
					linelimitbypage = linelimit-sum;
				}
			}
			i++;
			Logger.getInstance().appendText("开始第"+i+"次请求数据");
			list = formatData(source.getValue(), getContent(source.getValue(),sd,sum));
			num = list==null?0:list.size();
			sum = sum + num;
			if("csv".equals(fileType)){
				alllist.addAll(list);
			}else if("xls".equals(fileType)){
				GenerateFile.writeExcel(file, "sdf", list, titles, downloadPic);
				GenerateFile.indexpic=sum;
			}
			Logger.getInstance().appendText("本次保存"+num+"条数据至"+file.replace("//", "\\"));
			System.out.println("保存"+num+"条数据至"+file);
			Logger.getInstance().appendText("第"+i+"次请求数据结束，共找到"+sum+"条数据");
		}

		GenerateFile.writeCsc(file, alllist);
		Logger.getInstance().appendText("共找到"+sum+"条数据");
		System.out.println("共找到"+sum+"条数据");
	}
}
