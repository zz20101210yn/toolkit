package com.paic.jrkj.tk.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.paic.jrkj.tk.net.Connection;
import com.paic.jrkj.tk.net.ConnectionListener;
import com.paic.jrkj.tk.net.UploadAndDownload;

public class SftpClient implements UploadAndDownload, Connection<SftpClient>{

	private Log logger = LogFactory.getLog(SftpClient.class);
	
	private Session session;
	private ChannelSftp client;
	
	private int timeout = 30000;
	
	public final static String KEY_ATTRS_FILE = "file";
	public final static String KEY_ATTRS_DIR = "dir";
	
	private String ip;
	private int port;
	private String username;
	private String password;
	
	public SftpClient(String ip, int port, String username, String password, int timeout){
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeout = timeout;
	}

	@Override
	public void connect() throws IOException {
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(username, ip, port);
			session.setPassword(password);//password
			session.setConfig("StrictHostKeyChecking", "no");//no | ask | yes
			session.connect(timeout);//connect and set timeout
			//create sftp channel
			Channel channel = session.openChannel("sftp");
			channel.connect();
			logger.info("connect ["+ip+"] : ["+port+"]");
			client = (ChannelSftp)channel;
		} catch (JSchException e) {
			throw new IOException("sftp connect exception", e);
		}
	}

	@Override
	public void addConnectionListener(ConnectionListener<SftpClient> listener) {
	}

	@Override
	public void disconnect() {
		client.disconnect();
		session.disconnect();
	}

	@Override
	public void upload(String directory, String uploadFile, String saveFile) throws IOException{
		FileInputStream fo = null;
		try {
			client.cd(directory);
			fo = new FileInputStream(new File(uploadFile));
			client.put(fo, saveFile);
		} catch (SftpException e) {
			throw new IOException("upload ["+directory+"] ["+uploadFile+"] exception", e);
		} finally {
			if(fo != null){
				fo.close();
			}
		}
	}

	@Override
	public void download(String directory, String downloadFile, String saveFile) throws IOException{
		FileOutputStream fo = null;
		try {
			client.cd(directory);
			fo = new FileOutputStream(new File(saveFile));
			client.get(downloadFile, fo);
		} catch (SftpException e) {
			throw new IOException("download ["+directory+"] ["+downloadFile+"] exception", e);
		} finally {
			if(fo != null){
				fo.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<String>> ls(String directory) throws IOException {
		try {
			Vector<LsEntry> vector = client.ls(directory);
			if(vector == null){
				logger.warn("ls ["+directory+"] is null");
				return null;
			}
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			List<String> files = new ArrayList<String>();
			List<String> dirs = new ArrayList<String>();
			for(int i = 0; i < vector.size(); i++){
				LsEntry e = vector.get(i);
				if(e.getAttrs().isDir()){
					dirs.add(e.getFilename());
				} else {
					files.add(e.getFilename());
				}
			}
			if(!files.isEmpty()){
				map.put(KEY_ATTRS_FILE, files);
			}
			if(!dirs.isEmpty()){
				map.put(KEY_ATTRS_DIR, dirs);
			}
			return map;
		} catch (SftpException e) {
			throw new IOException("ls ["+directory+"] exception", e);
		} 
	}

	@Override
	public void mkdir(String directory, String dir) throws IOException {
		try {
			client.cd(directory);
			client.mkdir(dir);
		} catch (SftpException e) {
			throw new IOException("mkdir ["+directory+"] ["+dir+"] exception", e);
		} 
	}

}
