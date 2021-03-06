package com.github.yu_jay.jay_compiler.iter;

import com.github.yu_jay.jay_compiler.pojo.FileChangeType;

/**
 * 文件属性
 * @author jayu
 *
 */
public interface IFileInfo {

	/**
	 * 文件操作类型
	 * @return 文件操作类型
	 */
	FileChangeType getOpearType();
	
	/**
	 * 文件类型 例如：js
	 * @return 文件类型
	 */
	String getFileType();
	
	/**
	 * 文件名 例如： test.js
	 * @return 文件名
	 */
	String getFileName();
	
}
