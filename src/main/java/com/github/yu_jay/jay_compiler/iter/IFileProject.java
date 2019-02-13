package com.github.yu_jay.jay_compiler.iter;

/**
 * 文件所属项目
 * @author jayu
 *
 */
public interface IFileProject {

	/**
	 * 项目名
	 * @return 项目名
	 */
	String getProjectName();
	
	/**
	 * 项目绝对路径，
	 * 例如(linux)：/home/jayu/codeRepository/compiler
	 * 例如(window)：E:/codeRepository/compiler
	 * @return 项目路径
	 */
	String getProjectLocation();
	
}
