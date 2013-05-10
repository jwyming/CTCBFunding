package com.eds.ctcb.demo.ftp;

/*
 * default constructor mothod: FtpDownLoad()
 * 
 * all the methods are static:
 *     @ public void connect2Server(String ip,String user,String password):
 * this method is used to connect to FTP server with the provided ip,user,password. when you bigen
 * to download,you must call this mothod first!
 * 
 *     @ public void  downLoadFile(String remotePath,String remoteName,String localPath)
 *this method is used to download file from FTP server.you should specify the rometePath,
 *and its format accroding to the server system type:
 *UNIX: "/sub/"   NT:"\\sub\\"
 *the user is the full file name you want to download.
 *the localPath is the local path where you store your file. if the path is not exist or 
 *increcte,it will accurd a invalid path exception. its format also according to system.
 *UNIX: "downloads/zh" NT:"downloads\\zh"
 *
 *     @ public void closeFTP()
 *this method is used to disconnect the session with FTP server. when you complete your 
 *download  task, you must call it.
 *
 *     @public FTPFile [] getFiles()
 *this method is used to get the files  in the main WorkingDirectory.then you can get the 
 *files information VIA getName(),getSize(),getTimeStamp().you can reffer it in Test.java.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.*;

import org.apache.commons.net.ftp.FTPClientConfig;


public class FtpDownLoad {

	private  FTPClient ftp = new FTPClient();

	private  FTPClientConfig conf = new FTPClientConfig(
			FTPClientConfig.SYST_NT);

	public  void connect2Server(String ip, String user, String password) {

		int replyCode;
		try {
			ftp.addProtocolCommandListener(new PrintCommandListener(
					new PrintWriter(System.out)));
			ftp.setControlEncoding("GBK");
			ftp.connect(ip);

			System.out.println("connect to server :" + ip);
			System.out.println(ftp.getReplyString());
			replyCode = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
			// login
			if (!ftp.login(user, password)) {
				ftp.logout();
			}
			System.out.println("Remote system is " + ftp.getSystemName());

			// set attriute about ftp
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalActiveMode();

			// ftp.enterLocalPassiveMode();
		} catch (ConnectException e) {
			System.out.println("connection refused!");

		} catch (SocketException e) {
			 System.out.println("can not find server!");
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	
	public int downLoadFile(String path, String name, String dePath) {
		boolean isExist = false;
		int ok=-1;
		DateFormat df = DateFormat.getDateTimeInstance();
		if (ftp.isConnected()) {
			try {
				ftp.changeWorkingDirectory(path);

				String[] fileNames = ftp.listNames();
				for (int i = 0; i < fileNames.length; i++) {
					if (fileNames[i].equals(name)) {

						isExist = true;
					}
				}

				if (isExist == true) {
					System.out.println("start to download......");
					Date d = new Date();
					OutputStream output;
					String pathName = dePath + "\\" + name;
//					System.out.println(pathName);
					File f = new File(pathName);
					output = new FileOutputStream(f);

					ftp.retrieveFile(name, output);

					output.close();
					ok=0;
					Date d2 = new Date();
					Long cost = d2.getTime() - d.getTime();
					System.out.println("it costs " + cost + " (MS)!");
					System.out.println("dowmload successful......");

				} else {
					throw new FileNotFoundException();

				}
			} catch (FileNotFoundException e) {
				System.out.println("invalid path!");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} 
		return ok;
	}

	public  FTPFile[] getFiles() {
		FTPFile[] files = null;
		if (ftp.isConnected()) {
			try {
				ftp.changeWorkingDirectory("/");
				files = ftp.listFiles();

			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			System.out.println("is not connected to server");
		}

		return files;
	}

	public  void closeFTP() {
		if (ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException ioe) {
				System.out.println("exception to disconnect ftp");
			}
		}

	}
}
