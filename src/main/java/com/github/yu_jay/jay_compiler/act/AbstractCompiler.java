package com.github.yu_jay.jay_compiler.act;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.github.yu_jay.jay_compiler.er.CompileException;
import com.github.yu_jay.jay_compiler.iter.ICompiler;
import com.github.yu_jay.jay_compiler.iter.IFileChangeInfo;
import com.github.yu_jay.jay_compiler.pojo.CompilerStatus;

/**
 * 抽象编译器
 * @author yujie
 *
 */
public abstract class AbstractCompiler implements ICompiler {
	
	//executor service
	private final ExecutorService executor = Executors.newFixedThreadPool(1);
	
	/**
	 * 编译器状态，默认：空闲
	 */
	private CompilerStatus status = CompilerStatus.IDLE;
	
	/**
	 * 是否有剩余未被编译的任务，
	 * （当正常编译的时候又触发编译任务，这个时候不会编译，而是包编译任务保留下来,等编译器空闲再进行编译）
	 */
	private boolean remain = Boolean.FALSE;
	
	//remain锁
	private Object remainLock = new Object();
	
	//状态锁
	private Object statusLock = new Object();
	
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(AbstractCompiler.class);

	@Override
	public void compile(IFileChangeInfo fileChangeInfo) {
		log.debug("fileChangeInfo: " + fileChangeInfo);
		if(fileChangeInfo != null && shouldCompile()) {
			executor.submit(() -> {
				setStatus(CompilerStatus.BUSY);
				loopDoCompile(fileChangeInfo);
				setStatus(CompilerStatus.IDLE);
			});
		}
	}
	
	/**
	 * 接力执行编译任务
	 * @param fileChangeInfo 变化的文件信息
	 */
	private void loopDoCompile(IFileChangeInfo fileChangeInfo) {
		log.debug("开始loop编译");
		try {
			doCompile(fileChangeInfo);
		} catch (CompileException e) {
			setStatus(CompilerStatus.IDLE);
			//错误日志
			log.error("loopDoCompile编译失败了");
			//e.printStackTrace();
		}
		if(Boolean.TRUE == getRemain()) {
			setRemain(Boolean.FALSE);
			loopDoCompile(fileChangeInfo);
		}
	}

	/**
	 * 执行编译
	 * @param fileChangeInfo 变化的文件信息
	 * @throws CompileException 抛出编译异常
	 */
	protected abstract void doCompile(IFileChangeInfo fileChangeInfo) 
			throws CompileException;
	
	/**
	 * 判断是否应该被编译
	 * @return
	 */
	private boolean shouldCompile() {
		if(CompilerStatus.IDLE == getStatus()) {
			log.debug("编译器空闲");
			return true;
		}else {
			log.debug("编译器忙碌");
			if(Boolean.FALSE == getRemain()) {
				log.debug("无保留编译任务");
				setRemain(Boolean.TRUE);
			}
		}
		return false;
	}
	
	/**
	 * 获取是否有保留任务
	 * @return
	 */
	private boolean getRemain() {
		synchronized (remainLock) {
			return remain;
		}
	}
	
	/**
	 * 设置是否有保留任务
	 * @param remain
	 */
	private void setRemain(boolean remain) {
		synchronized (remainLock) {
			this.remain = remain;
		}
	}

	/**
	 * 获取编译器状态
	 * @return
	 */
	private CompilerStatus getStatus() {
		synchronized (statusLock) {
			return status;
		}
	}
	
	/**
	 * 设置编译器状态
	 * @param status
	 */
	private void setStatus(CompilerStatus status) {
		synchronized (statusLock) {
			this.status = status;
		}
	}

}
