package com.github.yu_jay.jay_compiler.iter;

/**
 * webpack config interface
 * @author yujie
 *
 */
public interface IWebpackConfig extends IConfig {

	/**
	 * webpack.js 安装位置
	 * @return webpack.js 安装位置
	 */
	String getWebpackPrefix();

	/**
	 * 输出文件在tomcat中的位置，不是真实的代码，是为了解决tomcat中的缓存问题
	 * @return 输出件在tomcat中的位置，不是真实的代码，是为了解决tomcat中的缓存问题
	 */
	String getWebFilePath();
	
	/**
	 * 是否在编译完成后执行编译的后续任务，可选值：yes,no
	 * @return 是否在编译完成后执行编译的后续任务，可选值：yes,no
	 */
	String getDoCompileAfter();
	
}
