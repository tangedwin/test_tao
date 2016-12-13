package com.edwin.tao.util;

import java.util.ArrayList;
import java.util.List;

public enum DataInfo {
	SOURCE_TAOBAO("taobao","taobao","�Ա�"),
	SOURCE_TMALL("tmall","tmall","��è"),

	SORT_TAOBAO_DEFAULT("default","default","�ۺ�","taobao"),
	SORT_TAOBAO_RENQI("renqi","renqi-desc","����","taobao"),
	SORT_TAOBAO_SALE("sale","sale-desc","����","taobao"),
	SORT_TAOBAO_CREDIT("credit","credit-desc","����","taobao"),
	SORT_TAOBAO_PRICED("priced","price-desc","�۸���","taobao"),
	SORT_TAOBAO_PRICEA("pricea","price-asc","�۸�����","taobao"),
	SORT_TAOBAO_TOTAL("total","total-desc","�ܼ۽���","taobao"),
	
	SORT_TMALL_DEFAULT("default","s","�ۺ�","tmall"),
	SORT_TMALL_RENQI("renqi","rq","����","tmall"),
	SORT_TMALL_NEW("new","new","��Ʒ","tmall"),
	SORT_TMALL_SALE("sale","d","����","tmall"),
	SORT_TMALL_PRICED("priced","p","�۸�����","tmall"),
	SORT_TMALL_PRICEA("pricea","pd","�۸���","tmall"),

	
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
