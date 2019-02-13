package com.github.yu_jay.jay_compiler.util;

import org.apache.log4j.Logger;

import com.github.yu_jay.jay_compiler.iter.IFileChangeInfo;
import com.github.yu_jay.jay_compiler.iter.IWebpackConfig;
import com.github.yu_jay.jay_compiler.pojo.FileChangeType;

/**
 * 判断是否符合编译的要求
 * @author yujie
 *
 */
public class Judger {
	
	private final static Logger log = Logger.getLogger(Judger.class);
	
	/**
	 * 检查webpack配置文件是否齐全
	 * @param webpackConfig webpack配置
	 * @return 布尔值
	 */
	public static boolean checkConfig(IWebpackConfig webpackConfig) {
		if(null == webpackConfig) {
			return false;
		}
		if(null != webpackConfig.getWebpackPrefix() 
				&& null != webpackConfig.getContext() 
				&& null != webpackConfig.getOutPath() 
				&& null != webpackConfig.getModel() 
				&& null != webpackConfig.getProjectName() 
				&& "js".equals(webpackConfig.getFileType())) {
			log.debug("配置文件成功加载，生效的文件类型：" + webpackConfig.getFileType());
			return true;
		}
		log.debug("配置文件未成功加载");
		return false;
	}
	
	/**
	 * 核对项目名与配置的项目名是否一致
	 * @param webpackConfig webpack配置
	 * @param info 变化的文件信息
	 * @return 布尔值
	 */
	public static boolean checkProject(IWebpackConfig webpackConfig, IFileChangeInfo info) {
		if(null == webpackConfig) {
			log.debug("webpack配置：" + webpackConfig);
			return false;
		}
		if(null == info) {
			log.debug("变化的文件信息：" + info);
			return false;
		}
		String projectName = webpackConfig.getProjectName();
		if(null != projectName) {
			String[] PNames = projectName.split("/");
			for(int i=0; i<PNames.length; i++) {
				if(info.getProjectName().equals(PNames[i])) {
					return true;
				}
			}
		}
		log.debug("项目名不对");
		return false;
	}
	
	/**
	 * 判断文件变化信息是否匹配配置路径
	 * @param webpackConfig webpack配置
	 * @param info 变化文件信息
	 * @return 布尔值
	 */
	public static boolean mateContext(IWebpackConfig webpackConfig, IFileChangeInfo info) {
		if(null == webpackConfig) {
			log.debug("webpack配置：" + webpackConfig);
			return false;
		}
		if(null == info) {
			log.debug("变化的文件信息：" + info);
			return false;
		}
		if(!"js".equals(info.getFileType())) {
			log.debug("只能编译js文件，现在文件类型：" + info.getFileType());
			return false;
		}
		if(null != info 
				&& FileChangeType.CHANGE == info.getOpearType() 
				&& null != info.getFileLocation()) {
			if(info.getFileLocation().startsWith(info.getProjectLocation() + webpackConfig.getContext())) {
				return true;
			}
			log.debug("变化文件位置：" + info.getFileLocation());
			log.debug("配置输入文件位置：" + info.getProjectLocation() + webpackConfig.getContext());
			return false;
		}
		log.debug("没有变化的文件或信息不完整");
		return false;
	}

}
