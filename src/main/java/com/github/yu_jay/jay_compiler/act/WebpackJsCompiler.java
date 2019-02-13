package com.github.yu_jay.jay_compiler.act;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.github.yu_jay.jay_common.utils.FileUtil;
import com.github.yu_jay.jay_compiler.er.CompileException;
import com.github.yu_jay.jay_compiler.iter.IFileChangeInfo;
import com.github.yu_jay.jay_compiler.iter.IWebpackConfig;
import com.github.yu_jay.jay_compiler.iter.IWorkPalce;
import com.github.yu_jay.jay_compiler.pojo.DocumentOrganization;

/**
 * webpack 编译 js文件
 * @author yujie
 *
 */
public class WebpackJsCompiler extends AbstractCompiler {
	
	/**
	 * 编译器的配置
	 */
	private IWebpackConfig config = null;
	
	/**
	 * 工作空间
	 */
	private IWorkPalce workPlace = null;
	
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(WebpackJsCompiler.class);
	
	/**
	 * 空构造方法
	 */
	public WebpackJsCompiler() {
		super();
	}

	/**
	 * 初始化编译器
	 * @param config 配置
	 */
	public WebpackJsCompiler(IWebpackConfig config) {
		init(config);
	}
	
	/**
	 * 构造方法
	 * @param configPath 配置文件
	 */
	public WebpackJsCompiler(String configPath) {
		init(configPath);
	}
	
	/**
	 * 构造方法
	 * @param config 配置
	 * @param workPlace 工作空间 为了使可以执行编译后动作
	 */
	public WebpackJsCompiler(IWebpackConfig config, IWorkPalce workPlace) {
		init(config);
		setWorkPlace(workPlace);
	}
	
	/**
	 * 构造方法
	 * @param configPath 配置
	 * @param workPlace 工作空间 为了使可以执行编译后动作
	 */
	public WebpackJsCompiler(String configPath, IWorkPalce workPlace) {
		init(configPath);
		setWorkPlace(workPlace);
	}

	/**
	 * 初始化编译器
	 * @param webpackConfig webpack配置
	 */
	public void init(IWebpackConfig webpackConfig) {
		this.config = webpackConfig;
	}
	
	/**
	 * 通过配置文件初始化编译器
	 * @param configPath 配置文件
	 */
	public void init(String configPath) {
		WebpackJsConfig config = new WebpackJsConfig(configPath);
		init(config);
	}
	
	/**
	 * 设置工作空间
	 * @param workPlace 工作空间
	 */
	public void setWorkPlace(IWorkPalce workPlace) {
		this.workPlace = workPlace;
	}
	
	/**
	 * 获取配置文件
	 * @return 配置文件
	 */
	public IWebpackConfig getConfig() {
		return config;
	}

	@Override
	protected void doCompile(IFileChangeInfo fileChangeInfo) throws CompileException {
		if(null != config) {
			executeCompile(fileChangeInfo, config);
		}else {
			log.debug("webpack config: " + config);
		}
	}
	
	/**
	 * 执行编译
	 * @param fileChangeInfo
	 * @param webpackConfig
	 * @throws CompileException
	 */
	private void executeCompile(IFileChangeInfo fileChangeInfo, IWebpackConfig webpackConfig) 
			throws CompileException {
		
		DocumentOrganization document = new DocumentOrganization(fileChangeInfo, webpackConfig);
		String command = document.createWebpackCommand();
		log.debug("webpack command: " + command);
		
		String result = "编译失败！";
		
		if(null != command) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(command);
				process.waitFor();
			} catch (IOException | InterruptedException e) {
				log.error("编译时抛出异常,自动结束本次编译任务");
				throw new CompileException("webpack编译命令无效， 编译失败");  //编译完成(退出此次编译任务)
			} finally {
				if(process.exitValue() == 0) {
					result = "编译成功！";
				}
				process.destroy();
				process = null;
				log.debug(result);
			}
			
			if("yes".equals(config.getDoCompileAfter())) {
				log.debug("开始执行收尾任务");
				//处理收尾工作  解决tomcat缓存问题
				afterExecute(document);
			}
			
		}else {
			log.debug("编译命令为null, " + result);
			throw new CompileException("webpack编译命令为 null，编译失败");  //编译完成(退出此次编译任务)
		}
		
	}
	
	/**
	 * 执行后序收尾任务
	 */
	private void afterExecute(DocumentOrganization doc) throws CompileException {
		if(null == workPlace) {
			log.debug("workPlace:" + workPlace);
			return;
		}
		String srcPath = workPlace.getWebAbsoluteOutFile(doc);
		String sourcePath = doc.getAbsoluteOutFile();
		log.info("sourcePath:" + doc.getAbsoluteOutFile());
		log.info("srcPath:"+ srcPath);
		if(null != srcPath && null != sourcePath) {
			File source = new File(sourcePath);
			File src = new File(srcPath);
			if(source.exists()) {
				//复制文件到tomcat中 解决tomcat缓存问题
				try {
					FileUtil.copyFileUsingFileStreams(source, src);
					throw new IOException();
				} catch (IOException e) {
					log.error("复制文件到tomcat中失败了,自动结束此次任务");
					throw new CompileException("编译已完成，执行收尾任务失败");  //编译完成(退出此次编译任务)
				}
			}
		}else {
			log.error("编译已完成，执行收尾任务失败");
			throw new CompileException("编译已完成，执行收尾任务失败");
		}
	}

}
