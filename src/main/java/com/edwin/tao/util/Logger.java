package com.edwin.tao.util;

import java.util.Date;

import javax.swing.JTextArea;

import com.edwin.tao.http.GenerateFile;


public class Logger {
	private String logAppend;
	private JTextArea responseLog;
	private boolean start;
	private static Logger logger;
	
	private Logger(){
		setLogAppend(new String());
		setResponseLog(new JTextArea());
		setStart(true);
	}
	
	
	public static Logger getInstance(){
		if(logger==null){
			logger = new Logger();
			/*Thread thread = new Thread(new Runnable() {
				public void run() {
					Long times = System.currentTimeMillis();
					while(true){
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						logger.appendText("haha this is append " + (System.currentTimeMillis()-times));
					}
				}
			});
	        thread.start();*/
		}
		
		return logger;
	}
	
	public void appendText(String content){
		this.responseLog.append("["+GenerateFile.sdf1.format(new Date())+"]"+content+"\n");
		this.responseLog.setCaretPosition(this.responseLog.getText().length());
		this.responseLog.paintImmediately(this.responseLog.getBounds());
//		if(i>4) return;
//		System.out.println(content);
//		this.responseLog.insert(content, this.responseLog.getText().length());
//		this.responseLog.insert("\n", this.responseLog.getText().length());
//		this.responseLog.setCaretPosition(this.responseLog.getText().length());
//		this.responseLog.paintImmediately(this.responseLog.getBounds());
//		i++;
	}
	


	public String getLogAppend() {
		return logAppend;
	}


	public void setLogAppend(String logAppend) {
		this.logAppend = logAppend;
	}


	public JTextArea getResponseLog() {
		return responseLog;
	}


	public void setResponseLog(JTextArea responseLog) {
		this.responseLog = responseLog;
	}


	public boolean isStart() {
		return start;
	}


	public void setStart(boolean start) {
		this.start = start;
	}
}
