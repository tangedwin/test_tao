package com.edwin.tao.pojo;

public class SearchData {	
	private String goodsName;
	private String sort;
	private boolean postFee = false;
	private boolean miaosha = false;
	private boolean wwonline = false;
	private Double startPrice;
	private Double endPrice;
	private boolean supportCod = false;
	private boolean globalbuy = false;
	private boolean filterFineness = false;
	private boolean tianmao = false;
	private int num;
	
	
	public SearchData(String goodsName, int num) {
		super();
		this.goodsName = goodsName;
		this.num = num;
	}
	public SearchData(String goodsName, String sort, Double startPrice,
			Double endPrice, int num) {
		super();
		this.goodsName = goodsName;
		this.sort = sort;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
		this.num = num;
	}
	public SearchData(String goodsName, String sort, boolean postFee,
			boolean miaosha, boolean wwonline, Double startPrice,
			Double endPrice, boolean supportCod, boolean globalbuy,
			boolean filterFineness, boolean tianmao, int num) {
		super();
		this.goodsName = goodsName;
		this.sort = sort;
		this.postFee = postFee;
		this.miaosha = miaosha;
		this.wwonline = wwonline;
		this.startPrice = startPrice;
		this.endPrice = endPrice;
		this.supportCod = supportCod;
		this.globalbuy = globalbuy;
		this.filterFineness = filterFineness;
		this.tianmao = tianmao;
		this.num = num;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public boolean isPostFee() {
		return postFee;
	}
	public void setPostFee(boolean postFee) {
		this.postFee = postFee;
	}
	public boolean isMiaosha() {
		return miaosha;
	}
	public void setMiaosha(boolean miaosha) {
		this.miaosha = miaosha;
	}
	public boolean isWwonline() {
		return wwonline;
	}
	public void setWwonline(boolean wwonline) {
		this.wwonline = wwonline;
	}
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	public Double getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}
	public boolean isSupportCod() {
		return supportCod;
	}
	public void setSupportCod(boolean supportCod) {
		this.supportCod = supportCod;
	}
	public boolean isGlobalbuy() {
		return globalbuy;
	}
	public void setGlobalbuy(boolean globalbuy) {
		this.globalbuy = globalbuy;
	}
	public boolean isFilterFineness() {
		return filterFineness;
	}
	public void setFilterFineness(boolean filterFineness) {
		this.filterFineness = filterFineness;
	}
	public boolean isTianmao() {
		return tianmao;
	}
	public void setTianmao(boolean tianmao) {
		this.tianmao = tianmao;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
