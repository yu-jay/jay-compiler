package com.github.yu_jay.jay_compiler;

import com.github.yu_jay.jay_common.act.Log4jLogger;
import com.github.yu_jay.jay_common.iter.ILogger;
import com.github.yu_jay.jay_compiler.act.WebpackJsCompiler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        String logPath = "G:/Template/log4j.properties";
        ILogger logger = new Log4jLogger(logPath);
        
        WebpackJsCompiler compiler = new WebpackJsCompiler();
        
        WebpackConfig config = new WebpackConfig();
        
        compiler.init(config);
        FileChangeInfo info = new FileChangeInfo();
        
        compiler.compile(info);
        
    }
}
