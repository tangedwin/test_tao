package com.edwin.tao.acitivity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.edwin.tao.http.AnalyseHtml;
import com.edwin.tao.http.GenerateFile;
import com.edwin.tao.pojo.SearchData;
import com.edwin.tao.util.DataInfo;
import com.edwin.tao.util.Logger;

public class JFrameMain {  
    String s1=null;  
    char[] s2=null;  
    JFrame frame=new JFrame();
    
    JFileChooser fileChooser;
    JTextField fileLocation;
    JTextField fileName;
    JComboBox<String> fileType;
    

    JTextField goodName;
    
    
    JComboBox<String> sourceType;
    JComboBox<String> limitPage;
    JComboBox<String> limitLine;
    JRadioButton limitPageRadio;
    JRadioButton limitLineRadio;
    
    JCheckBox downloadPic;
    
    JComboBox<String> sort;
    
    AnalyseHtml ana;
    
    
    JButton submit;  
    JButton stop;
    JButton openFile;
    
    JFrameMain() {
    	fileChooser = new JFileChooser();
    	
    	fileLocation = new JTextField(System.getProperty("user.dir"),30);
    	fileName = new JTextField(GenerateFile.sdf.format(new Date()),17);
    	fileType = new JComboBox<String>();
    	fileType.addItem(".xls");
    	fileType.addItem(".csv");
    	
        JPanel panel1=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel1.add(new JLabel("保存文件"));
        panel1.add(fileLocation);
        panel1.add(fileName);
        panel1.add(fileType);
        
        

    	
    	goodName = new JTextField("",10);
        sourceType = new JComboBox<String>();
        sourceType.addItem(DataInfo.SOURCE_TAOBAO.getDescript());
        sourceType.addItem(DataInfo.SOURCE_TMALL.getDescript());
    	
    	limitPage = new JComboBox<String>();
    	limitPage.addItem("5页");
    	limitPage.addItem("10页");
    	limitPage.addItem("50页");
    	limitPage.addItem("100页");
    	
    	limitLine = new JComboBox<String>();
    	limitLine.addItem("500条");
    	limitLine.addItem("1000条");
    	limitLine.addItem("5000条");

		limitLine.setEnabled(false);
    	limitPageRadio = new JRadioButton();
    	limitPageRadio.setSelected(true);
    	limitLineRadio = new JRadioButton();
    	ButtonGroup group = new ButtonGroup();
    	group.add(limitPageRadio);
    	group.add(limitLineRadio);
    	
        downloadPic = new JCheckBox("下载图片");
        sort = new JComboBox<String>();
        this.setSortSelection();
        
        JPanel panel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2.add(new JLabel("货物"));
        panel2.add(goodName);
        panel2.add(new JLabel("来源"));
        panel2.add(sourceType);
        panel2.add(new JLabel("限制"));
        panel2.add(limitPageRadio);
        panel2.add(limitPage);
        panel2.add(limitLineRadio);
        panel2.add(limitLine);
        panel2.add(downloadPic);
        panel2.add(new JLabel("排序"));
        panel2.add(sort);
 
        submit = new JButton("提交");
        stop = new JButton("停止");
        openFile = new JButton("打开文件");
        
        stop.setEnabled(false);
        openFile.setEnabled(false);
        
        JPanel panel3=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel3.add(submit);
        panel3.add(stop);
        panel3.add(openFile);
        

        Logger.getInstance().getResponseLog().setFont(new Font("宋体", Font.PLAIN,12));
        Logger.getInstance().getResponseLog().setForeground(Color.black);
                
        JScrollPane scroll = new JScrollPane(Logger.getInstance().getResponseLog());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        scroll.setPreferredSize(new Dimension(600,300));
        
    	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Container c=frame.getContentPane();  
        
        GridBagLayout layout = new GridBagLayout();
        c.setLayout(layout);
        
        c.add(panel1, new GBC(0,0).setFill(GBC.BOTH).setWeight(0, 0));  
        c.add(panel2, new GBC(0,1).setFill(GBC.BOTH).setWeight(0, 0));  
        c.add(panel3, new GBC(0,2).setFill(GBC.BOTH).setWeight(0, 0));  
        c.add(scroll, new GBC(0,3).setFill(GBC.BOTH).setWeight(1, 1));  
        
        frame.setLocation(255, 255);
        c.setMinimumSize(new Dimension(600,600));
        
        
        
        
        limitLineRadio.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(limitLineRadio.isSelected()){
					limitPage.setEnabled(false);
					limitLine.setEnabled(true);
				}else{
					limitPage.setEnabled(true);
					limitLine.setEnabled(false);
				}
			}
		});
        
        sourceType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setSortSelection();
			}
		});
        
        fileLocation.addMouseListener(new MouseAdapter(){
			@SuppressWarnings("static-access")
			@Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                	 int i = fileChooser.showOpenDialog(null);
                     if(i== fileChooser.APPROVE_OPTION){
                    	 fileLocation.setText(fileChooser.getSelectedFile().getAbsolutePath());
                     }
                }
        	}
        });
        
        
        openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						try {
							String filePath = fileLocation.getText()+"\\";
							String fName = fileName.getText() + fileType.getSelectedItem().toString();
							
							Runtime.getRuntime().exec("cmd /c start "+filePath+fName);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		});
        
        submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						submit.setEnabled(false);
						stop.setEnabled(true);
						openFile.setEnabled(false);
						Logger.getInstance().setStart(true);
						String filePath = fileLocation.getText()+"/";
						String fName = fileName.getText() + fileType.getSelectedItem().toString();
						
						
						DataInfo source = DataInfo.getByDescript((String) sourceType.getSelectedItem(), null);
						DataInfo sortd = source==null?null:DataInfo.getByDescript((String) sort.getSelectedItem(), source.getValue());
						int pagelimit = Integer.valueOf(limitPage.getSelectedItem().toString().replace("页", ""));
						int linelimit = Integer.valueOf(limitLine.getSelectedItem().toString().replace("条", ""));
						
						if(!limitLineRadio.isSelected()) linelimit = 0; 
						else if(!limitPageRadio.isSelected()) pagelimit = 0; 
						
						boolean containPic = false;
						if(downloadPic.isSelected()) containPic = true;
						
						SearchData sd = new SearchData(goodName.getText(), sortd==null?null:sortd.getValue(), null, null, 0);
						ana = new AnalyseHtml(filePath, fName, pagelimit, linelimit);
						ana.saveData(source,sd,containPic);
						submit.setEnabled(true);
						stop.setEnabled(false);
						openFile.setEnabled(true);
					}
				});
				thread.start();
			}
		});
        
        stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logger.getInstance().setStart(false);
				submit.setEnabled(false);
				stop.setEnabled(false);
			}
        	
        });
        
        frame.pack();
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    } 
    
    
    public void setSortSelection(){
    	DataInfo source = DataInfo.getByDescript((String) sourceType.getSelectedItem(), null);
    	List<DataInfo> sorts = DataInfo.getBySource(source.getValue());
    	sort.removeAllItems();
    	for(DataInfo di:sorts){
        	sort.addItem(di.getDescript());
    	}
    }
    
    
    
    public static void main(String[] args) {  
        new JFrameMain();  
    } 
}