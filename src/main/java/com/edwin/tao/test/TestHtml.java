package com.edwin.tao.test;

import javax.swing.JTextArea;

import com.edwin.tao.http.AnalyseHtml;
import com.edwin.tao.pojo.SearchData;
import com.edwin.tao.util.DataInfo;

public class TestHtml {
	public static void main(String[] args){
//		SearchData sd = new SearchData("sdf", SearchData.TMALL_XIAOLIANG, null, null, 0);
//		AnalyseHtml ana = new AnalyseHtml();
//		ana.saveData(AnalyseHtml.TMALL,sd, true);
		
		SearchData sd = new SearchData("sdff", DataInfo.SORT_TAOBAO_RENQI.getValue(), null, null, 0);
		AnalyseHtml ana = new AnalyseHtml();
		ana.saveData(DataInfo.SOURCE_TAOBAO,sd,true);
	}
	
	
	public static void setText(JTextArea area, String content){
		area.insert("/n", area.getText().length());
		area.insert(content, area.getText().length());
	}
}
