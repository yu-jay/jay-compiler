package com.github.yu_jay.jay_compiler.act;

import org.apache.log4j.Logger;

import com.github.yu_jay.jay_common.act.Properties;
import com.github.yu_jay.jay_compiler.iter.IWebpackConfig;

/**
 * webpack 编译 js 文件 配置类
 * @author yujie
 *
 */
public class WebpackJsConfig implements IWebpackConfig {
	
	private String context = null;
	
	private String outPath = null;
	
	private String projectName = null;
	
	private String recentProjectName = null;
	
	/**
	 * 编译模式  开发环境/生产环境,默认开发环境
	 */
	private String model = "development";
	
	/**
	 * 仅编译js文件
	 */
	private String fileType = "js";
	
	/**
	 * 默认编译的文件名：index.js
	 */
	private String fileName = "index.js";
	
	private String webpackPrefix = null;
	
	private String webFilePath = null;
	
	/**
	 * 是否执行编译后操作，默认执行
	 */
	private String doCompileAfter = "yes";
	
	private final static Logger log = Logger.getLogger(WebpackJsConfig.class);

	public WebpackJsConfig(String configPath) {
		super();
		Properties properties = new Properties(configPath);
		webpackPrefix = properties.getProperty("webpackPrefix");
		log.debug("init webpackPrefix: " + webpackPrefix);
		context = properties.getProperty("context");
		log.debug("init content: " + context);
		outPath = properties.getProperty("outPath");
		log.debug("init outPath: " + outPath);
		projectName = properties.getProperty("projectName");
		log.debug("init projectName: " + projectName);
		recentProjectName = properties.getProperty("recentProjectName");
		log.debug("init recentProjectName: " + recentProjectName);
		webFilePath = properties.getProperty("webFilePath");
		log.debug("init webFilePath: " + webFilePath);
		String outP = properties.getProperty("model");
		if(null != outP) {
			model = outP;
		}
		log.debug("init model: " + model);
		String fileN = properties.getProperty("fileName");
		if(null != fileN) {
			fileName = fileN;
		}
		log.debug("init fileName: " + fileName);
		String doCompileA = properties.getProperty("doCompileAfter");
		if(null != doCompileA) {
			doCompileAfter = doCompileA;
		}
		log.debug("init doCompileAfter: " + doCompileAfter);
	}

	public WebpackJsConfig(String context, String outPath, String projectName, String recentProjectName, String model,
			String fileType, String fileName, String webpackPrefix, String webFilePath,
			String doCompileAfter) {
		super();
		this.context = context;
		log.debug("init content: " + context);
		this.outPath = outPath;
		log.debug("init outPath: " + outPath);
		this.projectName = projectName;
		log.debug("init projectName: " + projectName);
		this.recentProjectName = recentProjectName;
		log.debug("init recentProjectName: " + recentProjectName);
		this.model = model;
		log.debug("init model: " + model);
		this.fileType = fileType;
		log.debug("init fileType: " + fileType);
		this.fileName = fileName;
		log.debug("init fileName: " + fileName);
		this.webpackPrefix = webpackPrefix;
		log.debug("init webpackPrefix: " + webpackPrefix);
		this.webFilePath = webFilePath;
		log.debug("init webFilePath: " + webFilePath);
		this.doCompileAfter = doCompileAfter;
		log.debug("init doCompileAfter: " + doCompileAfter);
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public String getOutPath() {
		return outPath;
	}

	@Override
	public String getProjectName() {
		return projectName;
	}

	@Override
	public String getRecentProjectName() {
		return recentProjectName;
	}

	@Override
	public String getModel() {
		return model;
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
	public String getWebpackPrefix() {
		return webpackPrefix;
	}

	@Override
	public String getWebFilePath() {
		return webFilePath;
	}

	@Override
	public String getDoCompileAfter() {
		return doCompileAfter;
	}

}
