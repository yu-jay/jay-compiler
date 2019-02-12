package com.github.yu_jay.jay_compiler.iter;

import com.github.yu_jay.jay_compiler.pojo.DocumentOrganization;

/**
 * 工作空间
 * @author yujie
 *
 */
public interface IWorkPalce {

	/**
	 * 获取变化的文件对应在web中的绝对位置
	 * @param doc 变化的文件
	 * @return 路径
	 */
	String getWebAbsoluteOutFile(DocumentOrganization doc);
	
}
