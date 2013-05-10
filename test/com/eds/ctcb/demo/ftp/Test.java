package com.eds.ctcb.demo.ftp;
/*
 * this is a test class.
 */
import java.text.DateFormat;

import org.apache.commons.net.ftp.FTPFile;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FtpDownLoad ftpd=new FtpDownLoad();
		ftpd.connect2Server("139.73.51.87", "anonymous", "");
		ftpd.downLoadFile("/", "welcome.txt","downloads");
		ftpd.downLoadFile("/sub/", "ÖÐÎÄ.txt","downloads");
		ftpd.downLoadFile("/", "List.mkv","downloads");
		FTPFile [] file=ftpd.getFiles();
		DateFormat df=DateFormat.getDateInstance(DateFormat.SHORT);
		if(file!=null){
			for(int i=0;i<file.length;i++){
				Long size=file[i].getSize();
				StringBuffer str=new StringBuffer();
				if(size>=1024*1024){
					str.append(size/(1024*1024));
					str.append(".");
					StringBuffer str1=new StringBuffer();
					str1.append(size%(1024*1024));
					str1.toString();
					str.append(str1.substring(0, 1));
					str.append("M");
				}
				else{
					str.append(size);
					str.append("bytes");
				}
				
//				System.out.println("file:"+file[i].getName()+
//						"    size:"+str.toString()+"  ..."+df.format(file[i].getTimestamp().getTime()));
			}
		}
		ftpd.closeFTP();

	}

}
