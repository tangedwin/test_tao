package com.edwin.tao.util;

import java.util.ArrayList;
import java.util.List;

public enum DataInfo {
	SOURCE_TAOBAO("taobao","taobao","淘宝"),
	SOURCE_TMALL("tmall","tmall","天猫"),

	SORT_TAOBAO_DEFAULT("default","default","综合","taobao"),
	SORT_TAOBAO_RENQI("renqi","renqi-desc","人气","taobao"),
	SORT_TAOBAO_SALE("sale","sale-desc","销量","taobao"),
	SORT_TAOBAO_CREDIT("credit","credit-desc","信用","taobao"),
	SORT_TAOBAO_PRICED("priced","price-desc","价格降序","taobao"),
	SORT_TAOBAO_PRICEA("pricea","price-asc","价格升序","taobao"),
	SORT_TAOBAO_TOTAL("total","total-desc","总价降序","taobao"),
	
	SORT_TMALL_DEFAULT("default","s","综合","tmall"),
	SORT_TMALL_RENQI("renqi","rq","人气","tmall"),
	SORT_TMALL_NEW("new","new","新品","tmall"),
	SORT_TMALL_SALE("sale","d","销量","tmall"),
	SORT_TMALL_PRICED("priced","p","价格升序","tmall"),
	SORT_TMALL_PRICEA("pricea","pd","价格降序","tmall"),

	
	SOURCE_TMALL1("tmall","tmall","tmall");

	
	private String value;
	private String key;
	private String descript;
	private String source;
	
	private DataInfo(String key, String value, String descript){
		this.key = key;
		this.value = value;
		this.descript = descript;
	}
	private DataInfo(String key, String value, String descript, String source){
		this.key = key;
		this.value = value;
		this.descript = descript;
		this.source = source;
	}
	
	public static DataInfo getByDescript(String descript, String source){
		for(DataInfo di:DataInfo.values()){
			if(di.getDescript().equals(descript) && (di.getSource()==null || di.getSource().equals(source))){
				return di;
			}else continue;
		}
		return null;
	}
	
	public static List<DataInfo> getBySource(String source){
		List<DataInfo> list = new ArrayList<DataInfo>();
		for(DataInfo di:DataInfo.values()){
			if(di.getSource()!=null && di.getSource().equals(source)){
				list.add(di);
			}else continue;
		}
		return list;
	}
	

	public String getKey(){
		return key;
	}
	public String getValue(){
		return value;
	}
	public String getDescript(){
		return descript;
	}
	public String getSource(){
		return source;
	}
}
