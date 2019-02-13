package com.github.yu_jay.jay_compiler.act;

import com.github.yu_jay.jay_compiler.iter.IFileChangeInfo;
import com.github.yu_jay.jay_compiler.pojo.FileChangeType;

/**
 * 文件变化信息
 * @author yujie
 *
 */
public class FileChangeInfo implements IFileChangeInfo {
	
	private FileChangeType opearType;
	
	private String fileType;
	
	private String fileName;
	
	private String filePath;
	
	private String fileLocation;
	
	private String projectName;
	
	private String projectLocation;

	public FileChangeInfo(FileChangeType opearType, String fileType, String fileName, String filePath,
			String fileLocation, String projectName, String projectLocation) {
		super();
		this.opearType = opearType;
		this.fileType = fileType;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileLocation = fileLocation;
		this.projectName = projectName;
		this.projectLocation = projectLocation;
	}

	@Override
	public String toString() {
		return "FileChangeInfo [opearType=" + opearType + ", fileType=" + fileType + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", fileLocation=" + fileLocation + ", projectName=" + projectName
				+ ", projectLocation=" + projectLocation + "]";
	}

	@Override
	public FileChangeType getOpearType() {
		return opearType;
	}

	@Override
	public String getFileType() {
		return fileType;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String getFileLocation() {
		return fileLocation;
	}

	@Override
	public String getProjectName() {
		return projectName;
	}

	@Override
	public String getProjectLocation() {
		return projectLocation;
	}

}
