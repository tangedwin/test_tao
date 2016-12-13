package com.edwin.tao.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.csvreader.CsvWriter;

public class GenerateFile {
	public static int indexpic = 0;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static void checkDis(String path){
		File file = new File(path);
		if(!file.exists()) file.mkdirs();
	}
	
	
	public static void writeCsc(String file, List<String[]> list){
		try {
			CsvWriter wr =new CsvWriter(file,',',Charset.forName("GBK"));
			for(String[] strs : list){                  
				wr.writeRecord(strs);
			}
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void writeExcel(String file, String sheetName, List<String[]> list, String[] titles, boolean downloadPic){
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		try {
			FileInputStream fs = new FileInputStream(file);
	        POIFSFileSystem ps=new POIFSFileSystem(fs);
	        workbook = new HSSFWorkbook(ps);
	        sheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException e2) {
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
		} catch (IOException e) {
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
		}
		
		sheet.setDefaultColumnWidth((short) 15);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);
		
		
		
		if(titles!=null){
			HSSFRow row = sheet.createRow(0);
			for(short i = 0; i < titles.length; i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(titles[i]);
				cell.setCellValue(text);
			}
		}

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.getDrawingPatriarch();
		if(patriarch==null) patriarch = sheet.createDrawingPatriarch();
		
		List<Integer> picpic = new ArrayList<Integer>();
		
		if(list==null || list.size()<=0) return;
		for(int index=0; index<list.size(); index++){
			String[] record = list.get(index);
			HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);
			for(short i = 0; i < record.length; i++){
				HSSFCell cell = row.createCell(i);
				if(record[i]==null) continue;
				
				
				
				if(downloadPic && (record[i].endsWith(".jpg") || record[i].endsWith(".png"))){
					String picUrl = record[i];
					byte[] bytes = HttpUtil.getImageFromURL(picUrl);
					if(bytes!=null){
						picpic.add(workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG));
					}
				}
				cell.setCellStyle(style2);
				HSSFRichTextString text = new HSSFRichTextString(record[i]);
				cell.setCellValue(text);
			}
		}
		
		if(downloadPic){
			for(int i=1; i<=sheet.getLastRowNum(); i++){
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 8, i, (short) 8, i);
				anchor.setAnchorType(3);
				patriarch.createPicture(anchor, i);
			}
		}
		
		
		
		OutputStream out;
		try {
			out = new FileOutputStream(file);
	        out.flush();
	        workbook.write(out);
			out.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}