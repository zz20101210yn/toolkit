package com.paic.jrkj.tk.net;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UploadAndDownload {

	void upload(String directory, String uploadFile, String saveFile) throws IOException;
	
	void download(String directory, String downloadFile, String savaFile) throws IOException;
	
	Map<String, List<String>> ls(String directory) throws IOException;

	void mkdir(String directory, String dir) throws IOException;
}
