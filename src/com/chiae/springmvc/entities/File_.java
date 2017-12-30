package com.chiae.springmvc.entities;

import java.util.Comparator;

public class File_ {

	private String file_name;
	private String file_url;
	private String file_data;
	private int file_download;
	private int file_size;
	private String file_currenttime;
	private String file_Uper;
	private String file_desc;
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_data() {
		return file_data;
	}
	public void setFile_data(String file_data) {
		this.file_data = file_data;
	}
		
	
	public String getFile_Uper() {
		return file_Uper;
	}
	public void setFile_Uper(String file_Uper) {
		this.file_Uper = file_Uper;
	}
	public File_() {}
	public String getFile_currenttime() {
		return file_currenttime;
	}
	public void setFile_currenttime(String file_currenttime) {
		this.file_currenttime = file_currenttime;
	}
	
	public String getFile_desc() {
		return file_desc;
	}
	public void setFile_desc(String file_desc) {
		this.file_desc = file_desc;
	}
	public File_(String file_name, String file_url, String file_data) {
		super();
		this.file_name = file_name;
		this.file_url = file_url;
		this.file_data = file_data;
	}
	public File_(String file_name, String file_url, String file_data, int file_download, String file_currenttime,
			int file_size, String file_Uper, String file_desc) {
		super();
		this.file_name = file_name;
		this.file_url = file_url;
		this.file_data = file_data;
		this.file_download = file_download;
		this.file_currenttime = file_currenttime;
		this.file_size = file_size;
		this.file_Uper = file_Uper;
		this.file_desc = file_desc;
	}
	
	public int getFile_download() {
		return file_download;
	}
	public void setFile_download(int file_download) {
		this.file_download = file_download;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	
}


