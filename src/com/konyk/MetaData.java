package com.konyk;

public class MetaData {
	String fileName;
	String fileSize;
	String fileCreationDate;

	public MetaData(String fileName, String fileSize, String fileCreationDate) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileCreationDate = fileCreationDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileCreationDate() {
		return fileCreationDate;
	}

	public void setFileCreationDate(String fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}

	@Override
	public String toString() {
		return "\"fileName\":" + "\"" + fileName + "\"" + ", \"fileSize\":"
				+ "\"" + fileSize + "\"" + ", \"fileCreationDate\":" + "\""
				+ fileCreationDate + "\"";
	}

}
