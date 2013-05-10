package com.eds.ctcb.demo.syn;

import com.eds.ctcb.demo.ftp.FtpDownLoad;

public class FileTransfers {
	
	private FtpDownLoad client;

	private String ftpServer;

	private String userName;

	private String password;

	private String directory;

	/**
	 * initializing
	 * 
	 * @param inServer
	 *            the address of FTP server
	 * @param inUserName
	 *            login name of FTP
	 * @param inPassword
	 *            login password of FTP
	 * @param inDirectory
	 *            root directory of FTP server
	 */
	public void init(String inServer, String inUserName, String inPassword,
			String inDirectory) {
		this.ftpServer = inServer;
		this.userName = inUserName;
		this.password = inPassword;
		this.directory = inDirectory;
		client = new FtpDownLoad();
		client.connect2Server(this.ftpServer, this.userName, this.password);
	}

	/**
	 * get the file specified by parameter
	 * 
	 * @param inFileName
	 *            the specific file name
	 * @return if success return 0, otherwise return negative
	 * @throws Exception
	 *             file transfer exception
	 */
	public int getFile(String inFileName) throws Exception {

		return client.downLoadFile(directory, inFileName, "downloads");
	}

	/**
	 * close ftp connection
	 */
	public void close() {

		client.closeFTP();
	}
}
